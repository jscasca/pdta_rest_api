package com.pd.api.mail;

import com.pd.api.entity.Language;
import com.pd.api.entity.VerificationToken;
import com.pd.api.http.HttpConn;
import com.pd.api.http.HttpResponse;

import java.util.HashMap;

/**
 * Created by tin on 15/09/18.
 */
public class RemoteSibMailer implements Mailer {

    private static final String API_KEY = System.getProperty("rsibmailer.key");
    private static final String MAILER_URL = System.getProperty("mailer.url");

    @Override
    public void sendVerificationMail(VerificationToken token) {
        HashMap params = new HashMap<String, String>();
        params.put("key", API_KEY);
        params.put("email", token.getEmail());
        params.put("token", token.getToken());
        HttpConn con = new HttpConn();
        try {
            HttpResponse response = con.post(MAILER_URL, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVerificationMail(VerificationToken token, Language lang) {
        // TODO: implement this
    }
}
