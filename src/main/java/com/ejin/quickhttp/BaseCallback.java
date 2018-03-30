package com.ejin.quickhttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class BaseCallback implements Callback {

    private QuickClient client;
    private boolean enableLog = false;
    private Class templateClass;
    private int successCode = 0;

    BaseCallback set(QuickClient client) {
        this.client = client;
        this.enableLog = client.enableLog;
        this.templateClass = client.templateClass;
        this.successCode = client.successCode;
        return this;
    }

    boolean enableLog() {
        return enableLog;
    }

    Class getTemplateClass() {
        return templateClass;
    }

    int getSuccessCode() {
        return successCode;
    }

    @Override
    final public void onFailure(Call call, IOException e) {
        client.remove(call);
        client = null;
        onFailure2(call, e);
    }

    @Override
    final public void onResponse(Call call, Response response) {
        client.remove(call);
        client = null;
        onResponse2(call, response);
    }

    public abstract void onFailure2(Call call, IOException e);

    public abstract void onResponse2(Call call, Response response);

}
