package com.ejin.quickhttp;

import okhttp3.Callback;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class BaseCallback implements Callback {

    private boolean enableLog = false;
    private Class templateClass;
    private int successCode = 0;

    BaseCallback set(QuickClient client) {
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

}
