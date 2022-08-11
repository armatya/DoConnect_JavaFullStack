package com.greatlearning.DoConnect.util;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

//	private JavaMailSender mailSender;
    public boolean sendEmail(String toEmail, String subject, String body)
	{

		boolean f=false;
        String from="doconnect03@outlook.com";
		
		String host="smtp.office365.com";
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES"+properties);
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.transport.protocol", "smtps");
		properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required","true");
        properties.setProperty("mail.smtp.ssl.enable", "false");
        properties.setProperty("mail.transport.protocol", "smtp");
        
        Session session= Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("doconnect03@outlook.com","Doconnect@service");
			}
			
		
	});
		session.setDebug(true);
        
		MimeMessage message = new MimeMessage(session);
		
		try {
		message.setFrom(from);
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
		message.setSubject(subject);
		message.setText(body);
		Transport.send(message);
		System.out.println("----Sent Successful----");
		f=true;
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return f;

	}

}
