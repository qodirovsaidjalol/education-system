package com.education.utils;

import lombok.Getter;
import lombok.Setter;
import javax.mail.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


@Getter
@Setter
public class Mail {




    public void sendMessage() throws MessagingException {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");



        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("uchqun7475@gmail.com", "Uchqun99bek26");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("uchqun7475@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("uchqunabdurasulov9@gmail.com"));
        message.setSubject("Mail Subject");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

    }
}
