package com.ejin.quickhttp;

/**
 * Created by 11764 on 2018/11/22.
 */
public abstract class SimpleCallback extends ModelCallback<Object> {

    public abstract void onSuccess();

    @Override
    public void onSuccess(Object o) {
        onSuccess();
    }
}
