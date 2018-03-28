package com.ejin.quickhttp;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class DataCallback extends BaseCallback {

    public abstract void onSuccess(byte[] bytes);

    public abstract void onError(int code, String error);

    final public void onFailure(Call call, IOException e) {
        super.onFailure(call, e);
        if (enableLog()) {
            Log.e("Response [" + call.request().url() + "] error:" + e.getMessage());
        }
        onError(-10000, e.getMessage());
    }


    final public void onResponse(Call call, Response response) throws IOException {
        super.onResponse(call, response);
        ResponseBody body = response.body();

        if (body == null) {
            onFailure(call, new IOException("response body is null"));
            return;
        }

        if (!response.isSuccessful()) {
            onFailure(call, new IOException("network request error: " + response.code()));
            return;
        }

        try {
            byte[] bytes = body.bytes();
            if (enableLog()) {
                String s = new String(bytes, Charset.forName("UTF-8"));
                Log.d("Response [" + call.request().url() + "]\nData: " + s);
            }
            onSuccess(bytes);
        } catch (Exception e) {
            onFailure(call, new IOException("parse response error: " + e.getMessage()));
        }

    }
}
