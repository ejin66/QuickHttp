import okhttp3.*;

import java.nio.charset.Charset;

/**
 * Created by ejin on 2018/3/27.
 */
public class Sync {

    private OkHttpClient client;
    private boolean enableLog;

    Sync(OkHttpClient client, boolean enableLog) {
        this.client = client;
        this.enableLog = enableLog;
    }

    public String get(String url) {
        byte[] bytes = getBytes(url);
        if (bytes == null) return null;

        return new String(bytes, Charset.forName("UTF-8"));
    }

    public byte[] getBytes(String url) {
        Request request = new Request.Builder().url(url).get().build();
        requestLog(request, null);
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                responseErrorLog(request, "response error, code " + response.code());
                return null;
            }
            if (response.body() == null) {
                responseErrorLog(request, "response body is null ");
                return null;
            }
            byte[] bytes = response.body().bytes();
            responseLog(request, bytes);
            return bytes;
        } catch (Exception e) {
//            e.printStackTrace();
            responseErrorLog(request, "response error: " + e.getMessage());
        }
        return null;
    }

    public String post(String url) {
        return post(url, null);
    }

    public String post(String url, Object body) {
        byte[] bytes = postBytes(url, body);
        if (bytes == null) return null;

        return new String(bytes, Charset.forName("UTF-8"));
    }

    public byte[] postBytes(String url, Object body) {
        String requestBody = Utils.convertObj2String(body);
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        Request.Builder builder = new Request.Builder().url(url);
        if (requestBody == null) {
            builder.post(RequestBody.create(null, ""));
        } else {
            builder.post(RequestBody.create(jsonMediaType, requestBody));
        }
        Request request = builder.build();
        requestLog(request, requestBody);
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                responseErrorLog(request, "response error, code " + response.code());
                return null;
            }
            if (response.body() == null) {
                responseErrorLog(request, "response body is null ");
                return null;
            }
            byte[] bytes = response.body().bytes();
            responseLog(request, bytes);
            return bytes;
        } catch (Exception e) {
//            e.printStackTrace();
            responseErrorLog(request, "response error: " + e.getMessage());
        }
        return null;
    }

    private void requestLog(Request request, String body) {
        if (enableLog) {
            String log = "Request ["+ request.method() + "][" + request.url() +"]";
            if (body != null) {
                log += "\n" + body;
            }
            Log.d(log);
        }
    }

    private void responseErrorLog(Request request, String error) {
        if (enableLog) {
            Log.e("Response [" + request.url() + "] error:" + error);
        }
    }

    private void responseLog(Request request, byte[] bytes) {
        if (enableLog) {
            String s = new String(bytes, Charset.forName("UTF-8"));
            Log.d("Response [" + request.url() + "]\nData: " + s);
        }
    }

}
