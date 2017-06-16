package com.pd.api.mail;

import com.pd.api.entity.Language;
import com.pd.api.entity.VerificationToken;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

import java.io.IOException;

/**
 * Created by tin on 8/06/17.
 *
 * Mailer uses the sparks notes mailer
 */
public class SparkMailer implements Mailer {

    public static final String API_KEY = System.getProperty("sparkpost.key");

    public static final String SUPPORT_SENDER_MAIL = "support@prologes.com";
    public static final String SUPPORT_SENDER_NAME = "Support Prologes";

    public SparkMailer() {}

    public void sendSingleMail(String from, String recipient, String subject, String text, String html) {
        Client client = new Client( "56004d46308b04e57faef5d0946b959dad393b2f");
        try {
            client.sendMessage(from, recipient, subject, text, html);
        } catch (SparkPostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVerificationMail(VerificationToken token) {
        String[] addresses = new String[1];
        addresses[0] = token.getEmail();
        String email = "Tu token es " + token.getToken();
        String subject = "Change your password";
        String emailText = "Tu token es: " + token.getToken() + "\n" +
                "O ingresa a la liga http://prologes.com/reset.php?token=" + token.getToken() + "\n" +
                "blop blop blop";
        String emailHtml = "Tu token es: " + token.getToken() + "\n" +
                "O ingresa a la liga http://prologes.com/reset.php?token=" + token.getToken() + "\n" +
                "blop blop blop";
        try {
            sendSingleMail(SUPPORT_SENDER_MAIL, token.getEmail(), subject, emailText, emailHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendVerificationMail(VerificationToken token, Language lang) {
        //TODO: make this thing and store the text in db maybe?
    }
}
