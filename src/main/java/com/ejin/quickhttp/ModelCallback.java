package com.ejin.quickhttp;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class ModelCallback<T> extends StringCallback {

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
    final  public void onSuccess(String s) {
        if (tClass == null) {
            handlerError(-10000, "parse response error, T class is null");
            return;
        }

        try {
            String data = s;
            if (getTemplateClass() != null) {
                Object o = JSONObject.parseObject(s, getTemplateClass());
                TempData tempData = Utils.parseTemplateByAnnotation(o);

                if (tempData == null) {
                    handlerError(-10000, "parse template data with annotation failed");
                    return;
                }

                if (tempData.getCode() != getSuccessCode()) {
                    handlerError(tempData.getCode(), tempData.getError());
                    return;
                }
                data = tempData.getData();
            }

            T t = JSONObject.parseObject(data, tClass);
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
