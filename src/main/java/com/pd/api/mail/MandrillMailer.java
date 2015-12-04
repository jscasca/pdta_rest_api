package com.pd.api.mail;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.microtripit.mandrillapp.lutung.view.MandrillUserInfo;
import com.pd.api.entity.VerificationToken;

public class MandrillMailer implements Mailer{
    
    public static final String API_KEY = System.getProperty("mandrill.key");
    
    public static final String DEFAULT_FROM_EMAIL = "soporte@posdta.com";
    public static final String DEFAULT_FROM_NAME = "Soporte Posdta";
    
    public static final String VERIFICATION_EMAIL = "verification@posdta.com";
    public static final String VERIFICATION_NAME = "Verificacion Posdta";
    
    public MandrillMailer() {}

    @Override
    public void sendVerificationMail(VerificationToken token) {
        // TODO Auto-generated method stub
        String[] addresses = new String[1];
        addresses[0] = token.getEmail();
        String email = "Tu token es " + token.getToken();
        String subject = "Verifica tu correo";
        try {
            sendMail(addresses, email, subject, getTags("verificacion", "posdta", "contraseña"), VERIFICATION_EMAIL, VERIFICATION_NAME);
        } catch (MandrillApiError | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void sendMail(String[] addresses, String email, String subject) throws MandrillApiError, IOException {sendMail(addresses, email, subject, getDefaultTags(), DEFAULT_FROM_EMAIL, DEFAULT_FROM_NAME);}
    public void sendMail(String[] addresses, String email, String subject, ArrayList<String> tags) throws MandrillApiError, IOException {sendMail(addresses, email, subject, tags, DEFAULT_FROM_EMAIL, DEFAULT_FROM_NAME);}
    public void sendMail(String[] addresses, String email, String subject, ArrayList<String> tags, String fromEmail, String fromName) throws MandrillApiError, IOException {
        MandrillApi api = new MandrillApi(API_KEY);
        
        MandrillMessage message = new MandrillMessage();
        message.setSubject(subject);
        message.setHtml(email);
        message.setAutoText(true);
        message.setFromEmail(fromEmail);
        message.setFromName(fromName);
        ArrayList<Recipient> recipients = new ArrayList<Recipient>();
        for(String addess : addresses) {
            Recipient recipient = new Recipient();
            recipient.setEmail(addess);
            recipients.add(recipient);
        }
        message.setTo(recipients);
        message.setTags(tags);
        
        MandrillMessageStatus[] messageStatusReports = api.messages().send(message, false);
    }
    
    public static void main(String args[]) throws MandrillApiError, IOException {
        MandrillApi api = new MandrillApi("");
        MandrillUserInfo user = api.users().info();
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(gson));
        
        MandrillMessage message = new MandrillMessage();
        message.setSubject("Test mandril");
        message.setHtml("<h1>Hi</h1>testing the app");
        message.setAutoText(true);
        message.setFromEmail("verification@posdta.com");
        message.setFromName("Verificacion");
        
        ArrayList<Recipient> recipients = new ArrayList<Recipient>();
        Recipient recipient = new Recipient();
        recipient.setEmail("jscasca@gmail.com");
        recipients.add(recipient);
        message.setTo(recipients);
        message.setPreserveRecipients(true);
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test");
        tags.add("helloworlds");
        message.setTags(tags);
        
        MandrillMessageStatus[] messageStatusReports = api.messages().send(message, false);
        
    }
    
    public ArrayList<String> getTags(String...strings ) {
        ArrayList<String> tags = new ArrayList<String>();
        for(String string : strings) {
            tags.add(string);
        }
        return tags;
    }
    
    public ArrayList<String> getDefaultTags() {
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("Posdta");
        return tags;
    }

}
