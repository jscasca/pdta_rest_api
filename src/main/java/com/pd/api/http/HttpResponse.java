package com.pd.api.http;

/**
 * Created by tin on 15/09/18.
 */
public class HttpResponse {

    private int status;
    private String data;

    public HttpResponse(int status, String data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }
}
