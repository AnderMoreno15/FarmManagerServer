/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailing;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aitziber
 */
public class MailingService {
    
    private static final Logger logger = Logger.getLogger(MailingService.class.getName());
     
    private final String HOST = "localhost";
    private final String PORT = "25";
    private final String SENDER_EMAIL = "farmapp@support.com";
    private final String SENDER_PASSWORD = "";

    private Session session;

    public MailingService() {
        Properties props = new Properties();
        props.setProperty("mail.smtps.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.starttls.enable", "false");
        props.setProperty("mail.smtps.auth", "false");

        session = Session.getInstance(props, null);
    }

    public boolean sendEmail(String recipient, String subject, String content) {
        Transport transport = null;
        try {
            logger.info("starting mailing service");
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject(subject);
            msg.setText(content, "utf-8", "html");
            msg.setSentDate(new Date());

            transport = session.getTransport("smtp");
            transport.connect(HOST, SENDER_EMAIL, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            logger.info("mail sent to "+ recipient + " address");
            return true;
        } catch (MessagingException e) {
            Logger.getLogger(MailingService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                if (transport != null) transport.close();
            } catch (MessagingException e) {
                Logger.getLogger(MailingService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
