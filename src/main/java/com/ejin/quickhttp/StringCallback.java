package com.ejin.quickhttp;

import java.nio.charset.Charset;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class StringCallback extends DataCallback {

    public abstract void onSuccess(String s);

    public abstract void onError(int code, String error);

    @Override
    final public void onSuccess(byte[] bytes) {
        String s = new String(bytes, Charset.forName("UTF-8"));
        onSuccess(s);
    }
}
