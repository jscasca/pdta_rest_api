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
        //Client client = new Client( "56004d46308b04e57faef5d0946b959dad393b2f");
        Client client = new Client( API_KEY);
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
        String subject = "Change your password";
        String emailText = "Your token number is: " + token.getToken() + "\n" +
                "Access http://prologes.com/reset?token=" + token.getToken() + " to change your password.\n" +
                "";
        String emailHtml = "Your token number is: " + token.getToken() + "<br>\n" +
                "Access <a href='http://prologes.com/reset?token=" + token.getToken() + "'>http://prologes.com/reset?token=" + token.getToken() + "</a> to change your password.<br>\n" +
                "";
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
