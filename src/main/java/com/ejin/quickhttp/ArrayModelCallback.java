package com.ejin.quickhttp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class ArrayModelCallback<T> extends DataCallback {

    private Class<T> tClass;

    public ArrayModelCallback() {
        tClass = Utils.getType(getClass());
    }

    public ArrayModelCallback(Class<T> tClass) {
        this.tClass = tClass;
    }

    public abstract void onSuccess(List<T> list);

    public abstract void onError(int code, String error);

    @Override
    final public void onSuccess(byte[] data) {
        if (tClass == null) {
            onError(-10000, "parse response error, T class is null");
            return;
        }

        try {
            String s = new String(data, Charset.forName("UTF-8"));
            if (getTemplateClass() != null) {
                Object o = gson().fromJson(s, getTemplateClass());
                TempData tempData = Utils.parseTemplateByAnnotation(gson(), o);

                if (tempData == null) {
                    onError(-10000, "parse template data with annotation failed");
                    return;
                }

                if (tempData.getCode() != getSuccessCode()) {
                    onError(tempData.getCode(), tempData.getError());
                    return;
                }
                s = tempData.getData().toString();
            }
            List<T> tList = gson().fromJson(s, new ParameterizedTypeListImpl(tClass));
            onSuccess(tList);
        } catch (Exception e) {
            onError(-10000, "parse response error: " + e.getMessage());
        }
    }

    private class ParameterizedTypeListImpl implements ParameterizedType {

        private Class mClass;

        ParameterizedTypeListImpl(Class c) {
            this.mClass = c;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{mClass};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
