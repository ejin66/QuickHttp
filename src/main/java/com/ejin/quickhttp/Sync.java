package com.ejin.quickhttp;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

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

    public SyncResult get(String url) {
        return get(url, null);
    }

    public SyncResult post(String url, Object body) {
        return post(url, null, body);
    }

    public SyncResult put(String url, Object body) {
        return put(url, null, body);
    }

    public SyncResult delete(String url, Object body) {
        return delete(url, null, body);
    }

    public SyncResult get(String url, List<Header> headers) {
        return request(url, "GET", headers, null);
    }

    public SyncResult post(String url, List<Header> headers, Object body) {
        return request(url, "POST", headers, body);
    }

    public SyncResult put(String url, List<Header> headers, Object body) {
        return request(url, "PUT", headers, body);
    }

    public SyncResult delete(String url, List<Header> headers, Object body) {
         return request(url, "DELETE", headers, body);
    }

    private SyncResult request(String url, String method, List<Header> headers, Object body) {
        String requestBody = Utils.convertObj2String(body);
        MediaType jsonMediaType = MediaType.parse("application/json; charset=utf-8");
        Request.Builder builder = new Request.Builder().url(url);
        if (!"GET".equals(method)) {
            if (requestBody == null) {
                builder.method(method, RequestBody.create(null, ""));
            } else {
                builder.method(method, RequestBody.create(jsonMediaType, requestBody));
            }
        }
        if (headers != null) {
            for (Header item : headers) {
                builder.addHeader(item.getKey(), item.getValue());
            }
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
            return new SyncResult(bytes);
        } catch (Exception e) {
//            e.printStackTrace();
            responseErrorLog(request, "response error: " + e.getMessage());
        }
        return null;
    }

    private void requestLog(Request request, String body) {
        if (enableLog) {
            String log = "Request [" + request.method() + "][" + request.url() + "]";
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
            Log.d("Response [" + request.url() + "]\n" + s);
        }
    }

    public class SyncResult {

        private byte[] data;

        private SyncResult(byte[] data) {
            this.data = data;
        }

        public <T> T shift(Class<T> c) {
            if (data == null) return null;

            try {
                return JSONObject.parseObject(data, c);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public byte[] bytes() {
            return data;
        }

        public String string() {
            if (data == null) return null;

            try {
                return new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
