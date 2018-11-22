package com.ejin.quickhttp;

/**
 * Created by 11764 on 2018/11/22.
 */
public abstract class SimpleResultCallback extends ModelCallback<Object> {

    public abstract void onResult(boolean result);

    @Override
    public void onSuccess(Object o) {
        onResult(true);
    }

    @Override
    public void onError(int code, String error) {
        onResult(false);
    }
}
