package com.ejin.quickhttp;

import okhttp3.Headers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11764 on 2019/1/14.
 */
public class RawResponse {

    private int code;
    private List<Header> headers;
    private byte[] body;

    public RawResponse(int code, Headers headers) {
        List<Header> headerList = new ArrayList<Header>();
        for (String name: headers.names()) {
            headerList.add(new Header(name, headers.get(name)));
        }
        this.code = code;
        this.headers = headerList;
    }

    void setBody(byte[] body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        String bodyStr = "";
        try {
            bodyStr = new String(body, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder headerStr = new StringBuilder();
        if (headers.size() > 0) {
            headerStr.append("\n");
        }
        for(Header item : headers) {
            headerStr.append("\t\t").append(item.toString()).append("\n\t");
        }

        return String.format("Raw Response {\n\tcode = %d\n\tbody = [\n%s\n\t]\n\theader = [%s]\n}", code, bodyStr, headerStr.toString());
    }
}
