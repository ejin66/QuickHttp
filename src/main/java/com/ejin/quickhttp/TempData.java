package com.ejin.quickhttp;

/**
 * Created by ejin on 2018/3/27.
 */
public class TempData {

    private int code;
    private String error;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
