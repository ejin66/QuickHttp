package com.ejin.quickhttp;

import com.google.gson.JsonElement;

/**
 * Created by ejin on 2018/3/27.
 */
class TempData {

    private int code;
    private String error;
    private JsonElement data;

    TempData() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
