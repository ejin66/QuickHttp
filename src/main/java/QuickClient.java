import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ejin on 2018/3/26.
 */
public class QuickClient {

    private static final String TAG = "QuickClient";
    private List<Header> headers = new ArrayList<Header>();
    private long connectTimeout = 5000;
    boolean enableLog = false;
    Class templateClass;
    int successCode = 0;
    private OkHttpClient client;
    private Sync sync;
    private Map<Object, Call> callMap = new HashMap<Object, Call>();

    private QuickClient() {
    }

    private QuickClient make() {
        Interceptor headerInterceptor = new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                for (Header item : headers) {
                    builder.addHeader(item.getKey(), item.getValue());
                }
                return chain.proceed(builder.build());
            }
        };

        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(headerInterceptor)
                .build();
        sync = new Sync(client, enableLog);
        return this;
    }

    public Sync sync() {
        return sync;
    }

    public void get(@NotNull String url, @NotNull final BaseCallback callback) {
        get(null, url, null, callback);
    }

    public void get(@Nullable Object tag, @NotNull String url, @NotNull final BaseCallback callback) {
        get(tag, url, null, callback);
    }

    public void get(@Nullable Object tag, @NotNull String url, @Nullable List<Header> headers, @NotNull final BaseCallback callback) {
        Request.Builder builder = new Request.Builder().url(url).get();
        if (headers != null) {
            for (Header item : headers) {
                builder.addHeader(item.getKey(), item.getValue());
            }
        }
        Request request = builder.build();
        enqueue(request, tag, null, callback.set(this));
    }

    public void post(@NotNull String url, @NotNull final BaseCallback callback) {
        post(null, url, null, null, callback);
    }

    public void post(@Nullable Object tag, @NotNull String url, @NotNull final BaseCallback callback) {
        post(tag, url, null, null, callback);
    }

    public void post(@NotNull String url, @Nullable Object body, @NotNull final BaseCallback callback) {
        post(null, url, null, body, callback);
    }

    public void post(@Nullable Object tag, @NotNull String url, @Nullable Object body, @NotNull final BaseCallback callback) {
        post(tag, url, null, body, callback);
    }

    public void post(@Nullable Object tag, @NotNull String url, @Nullable List<Header> headers, @Nullable Object body, @NotNull final BaseCallback callback) {
        String requestBody = Utils.convertObj2String(body);
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        Request.Builder builder = new Request.Builder().url(url);
        if (requestBody == null) {
            builder.post(RequestBody.create(null, ""));
        } else {
            builder.post(RequestBody.create(jsonMediaType, requestBody));
        }
        if (headers != null) {
            for (Header item : headers) {
                builder.addHeader(item.getKey(), item.getValue());
            }
        }
        Request request = builder.build();
        enqueue(request, tag, requestBody, callback.set(this));
    }

    public void download(@NotNull String url, @NotNull final String path, @NotNull final ProgressCallback callback) {
        download(null, url, path, callback);
    }

    public void download(@Nullable Object tag, @NotNull String url, @NotNull final String path, @NotNull final ProgressCallback callback) {
        Request request = new Request.Builder().url(url).build();
        enqueue(request, tag, "Download To " + path, new Callback() {
            public void onFailure(Call call, IOException e) {
                callback.onError(-10000, e.getMessage());
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onFailure(call, new IOException("response error, code:" + response.code()));
                    return;
                }

                if (response.body() == null) {
                    onFailure(call, new IOException("response body is null"));
                    return;
                }

                long contentLength = response.body().contentLength();
                InputStream inputStream = response.body().byteStream();
                FileOutputStream outputStream = new FileOutputStream(path);
                byte[] buffer = new byte[1024 * 10];
                int len = 0;
                int receiveSize = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                    receiveSize += len;
                    int progress = (int) (100 * receiveSize / (float) contentLength);
                    callback.onProgress(progress);
                }
                outputStream.flush();
                callback.onSuccess();
                try {
                    outputStream.close();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    callback.onSuccess();
                }
            }
        });
    }

    public void upload(@NotNull String url, @NotNull final File file, @NotNull final ProgressCallback callback) {
        upload(null, url, file, callback);
    }

    public void upload(@Nullable Object tag, @NotNull String url, @NotNull final File file, @NotNull final ProgressCallback callback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        final Request request = new Request.Builder()
                .url(url)
                .post(new ProgressRequestBody(requestBody, callback))
                .build();
        enqueue(request, tag, "Upload File: " + file.getName(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(-1000, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onFailure(call, new IOException("response error, code:" + response.code()));
                    return;
                }
                callback.onSuccess();
            }
        });
    }

    public void cancel(@NotNull Object tag) {
        for (Map.Entry<Object, Call> item : callMap.entrySet()) {
            if (item.getKey() == tag) {
                item.getValue().cancel();
                callMap.remove(item.getValue());
            }
        }
    }

    public void remove(Call call) {
        for (Map.Entry<Object, Call> item : callMap.entrySet()) {
            if (item.getValue() == call) {
                callMap.remove(call);
            }
        }
    }

    private void enqueue(Request request, Object tag, String body, Callback callback) {
        if (enableLog) {
            String log = "Request ["+ request.method() + "][" + request.url() +"]";
            if (body != null) {
                log += "\n" + body;
            }
            Log.d(log);
        }
        Call call = client.newCall(request);
        call.enqueue(callback);
        if (tag != null) {
            callMap.put(tag, call);
        }
    }

    public static class Builder {

        private QuickClient client;

        public Builder() {
            client = new QuickClient();
        }

        public Builder enableLog(boolean enable) {
            client.enableLog = enable;
            return this;
        }

        public Builder connectTimeout(long timeout) {
            client.connectTimeout = timeout;
            return this;
        }

        public Builder setHeaders(List<Header> headers) {
            client.headers.clear();
            client.headers.addAll(headers);
            return this;
        }

        public Builder addHeader(Header header) {
            client.headers.add(header);
            return this;
        }

        public Builder setTemplate(Class c) {
            boolean result = Utils.checkTemplateClass(c);
            if (!result) {
                Log.e("Set Template Failed");
                return this;
            }
            client.templateClass = c;
            return this;
        }

        public Builder setSuccessCode(int code) {
            client.successCode = code;
            return this;
        }

        public QuickClient Build() {
            return client.make();
        }

    }

}
