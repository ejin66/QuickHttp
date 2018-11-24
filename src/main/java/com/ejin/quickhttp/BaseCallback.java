package com.ejin.quickhttp;

import com.google.gson.Gson;
import okhttp3.Callback;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class BaseCallback implements Callback {

    private boolean enableLog = false;
    private Class templateClass;
    private int successCode = 0;
    private Gson gson;

    BaseCallback set(QuickClient client) {
        this.enableLog = client.enableLog;
        this.templateClass = client.templateClass;
        this.successCode = client.successCode;
        this.gson = client.gson;
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

    Gson gson() {
        return gson;
    }

}
