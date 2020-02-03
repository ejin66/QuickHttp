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

    private RawResponse rawResponse;

    public abstract void onSuccess(byte[] bytes);

    public abstract void onError(int code, String error);

    @Override
    final public void onFailure(Call call, IOException e) {
        handlerError(ErrorCode.ERROR_NETWORK, "" + e.getMessage(), call);
    }

    @Override
    final public void onResponse(Call call, Response response) {
        ResponseBody body = response.body();

        if (body == null) {
            onFailure(call, new IOException("response body is null"));
            return;
        }

        rawResponse = new RawResponse(response.code(), response.headers());

        if (!response.isSuccessful()) {
            handlerError(ErrorCode.ERROR_HTTP_CODE, "http code error: " + response.code(), call);
            return;
        }

        try {
            byte[] bytes = body.bytes();
            rawResponse.setBody(bytes);
            if (enableLog()) {
                String s = new String(bytes, Charset.forName("UTF-8"));
                Log.d("Response [" + call.request().url() + "]\n" + s);
            }
            onSuccess(bytes);
        } catch (Exception e) {
            handlerError(ErrorCode.ERROR_PARSE_RESPONSE, "parse response error: " + e.getMessage(), call);
        }

    }

    final public RawResponse getRawResponse() {
        return rawResponse;
    }

    private void handlerError(int code, String error, Call call) {
        if (enableLog()) {
            Log.e("Response [" + call.request().url() + "] error: " + error);
        }
        onError(code, error);
    }
}
