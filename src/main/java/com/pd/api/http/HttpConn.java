package com.pd.api.http;

import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by tin on 8/09/18.
 */
public class HttpConn {

    public HttpConn() {
        // TODO: implement or move to static methods
    }

    public static JsonObject mapToJson(Map<String, String> bodyMap) {
        JsonObject body = new JsonObject();
        for(Map.Entry<String, String> entry : bodyMap.entrySet()) {
            body.addProperty(entry.getKey(), entry.getValue());
        }
        return body;
    }

    public HttpResponse post(String urlString, Map<String, String> bodyMap) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setDoInput(true);

        if (bodyMap != null ) {
            con.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(mapToJson(bodyMap).toString());
            wr.flush();
            wr.close();
        }

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        HttpResponse response = new HttpResponse(status, content.toString());
        return response;
    }

    // TODO :Fix this code
    public void get() throws Exception{
        URL url = new URL("http://beta.prologes.com/en/login.php");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println(content);
    }

    public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }
        String resultString = result.toString();
        return params.size() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }
}
