package com.ejin.quickhttp;

import java.nio.charset.Charset;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class ModelCallback<T> extends DataCallback {

    private Class<T> tClass;

    public ModelCallback() {
        tClass = Utils.getType(getClass());
    }

    public ModelCallback(Class<T> tClass) {
        this.tClass = tClass;
    }

    public abstract void onSuccess(T t);

    public abstract void onError(int code, String error);

    @Override
    final public void onSuccess(byte[] data) {
        if (tClass == null) {
            handlerError(-10000, "parse response error, T class is null");
            return;
        }

        try {
            String s = new String(data, Charset.forName("UTF-8"));
            if (getTemplateClass() != null) {
                Object o = gson().fromJson(s, getTemplateClass());
                TempData tempData = Utils.parseTemplateByAnnotation(gson(), o);

                if (tempData == null) {
                    handlerError(-10000, "parse template data with annotation failed");
                    return;
                }

                if (tempData.getCode() != getSuccessCode()) {
                    handlerError(tempData.getCode(), tempData.getError());
                    return;
                }
                s = tempData.getData().toString();
            }
            T t = gson().fromJson(s, tClass);
            onSuccess(t);
        } catch (Exception e) {
            handlerError(-10000, "parse response error: " + e.getMessage());
        }
    }

    private void handlerError(int code, String error) {
        if (enableLog())
            Log.e(error);
        onError(code, error);
    }
}
