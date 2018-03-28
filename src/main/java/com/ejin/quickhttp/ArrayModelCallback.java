package com.ejin.quickhttp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class ArrayModelCallback<T> extends StringCallback {

    private Class<T> tClass;

    public ArrayModelCallback() {
        tClass = Utils.getType(getClass());
    }

    public ArrayModelCallback(Class<T> tClass) {
        this.tClass = tClass;
    }

    abstract void onSuccess(List<T> list);

    abstract void onError(int code, String error);

    public String getDataString(String response) {
        return response;
    }

    @Override
    final void onSuccess(String s) {
        if (tClass == null) {
            onError(-10000, "parse response error, T class is null");
            return;
        }

        try {
            String data = s;
            if (getTemplateClass() != null) {
                Object o = JSONObject.parseObject(s, getTemplateClass());
                TempData tempData = Utils.parseTemplateByAnnotation(o);

                if (tempData == null) {
                    onError(-10000, "parse template data with annotation failed");
                    return;
                }

                if (tempData.getCode() != getSuccessCode()) {
                    onError(tempData.getCode(), tempData.getError());
                    return;
                }
                data = tempData.getData();
            }
            List<T> tList = JSONArray.parseArray(data, tClass);
            onSuccess(tList);
        } catch (Exception e) {
            onError(-10000, "parse response error: " + e.getMessage());
        }
    }
}
