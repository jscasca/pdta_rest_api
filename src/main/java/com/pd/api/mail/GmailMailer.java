package com.pd.api.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pd.api.entity.VerificationToken;

/**
 * Singleton class to handle the email sending through gmail
 * @author tin
 *
 */
public class GmailMailer implements Mailer {

    private static GmailMailer instance = null;
    
    private Session session;
    
    private final String username = "tinkalzetin@gmail.com";
    private final String password = "Zanahorias1.";
    private final String host = "smtp.gmail.com";
    
    private GmailMailer() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() { 
                return new PasswordAuthentication(username, password);
            }
        };
        session = Session.getInstance(props, auth);
        
    }
    
    public static GmailMailer getInstance() {
        if(instance == null) {
            instance = new GmailMailer();
        }
        return instance;
    }
    
    private void sendMail(MimeMessage msg) throws MessagingException {
        Transport.send(msg);
    }

    @Override
    public void sendVerificationMail(VerificationToken token) {
        MimeMessage msg = new MimeMessage(session);
        try {
            //TODO: make a nice html msg
            //TODO FIX MAILING
            msg.setSubject("VerificationMail");
            msg.setText("localhost/bootstrapd/password.php?token=" + token.getToken());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(token.getEmail()));
            sendMail(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
