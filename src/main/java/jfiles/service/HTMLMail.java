package jfiles.service;

//import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
//import javax.mail.PasswordAuthentication;

import jfiles.Constants.Email;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.internet.MimeMessage;
//
//import java.io.IOException;
//import java.util.Properties;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
// [END simple_includes]

// [START multipart_includes]
//import java.io.InputStream;
//import java.io.ByteArrayInputStream;
//import java.io.UnsupportedEncodingException;
//import javax.activation.DataHandler;
//import javax.mail.Multipart;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMultipart;
// [END multipart_includes]

//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

/**Service for email generation and sending*/
//@Service("htmlEmail")
public class HTMLMail {

//    private JavaMailSender mailSender;
    private String fromAddress;
    private String fromText;


    public void sendEmail(String userName, String userPassword, String userEmail, int type){

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromAddress, fromText));

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail, ""));

            msg.setSubject( generateTitle(type));

//            String htmlBody = generateMessage(userName, userPassword, type);
//            Multipart mp = new MimeMultipart();
//            MimeBodyPart body = new MimeBodyPart();
//            body.setContent(htmlBody, "text/html");
//            mp.addBodyPart(body);

//            String htmlBody = generateMessage(userName, userPassword, type);
            Multipart    multiPartMessage = new MimeMultipart();
            MimeBodyPart bodyOfMessage    = new MimeBodyPart();

            bodyOfMessage.setContent( generateMessage(userName, userPassword, type), "text/html");

            multiPartMessage.addBodyPart( bodyOfMessage);

            msg.setContent(multiPartMessage);
//            msg.setText( generateMessage(userName, userPassword, type));

            Transport.send(msg);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**Method sends email of specified type to specified recipient<br>
     * Types are stored into <i>Email</i> interface (welcome message, password reset message, user information updated message) */
//    public void sendEmail(String userName, String userPassword, String userEmail, int type){
//
//        String subject = generateTitle(type);
//        String msg     = generateMessage(userName, userPassword, type);
//        String from    = Email.FROM;
//        String to      = userEmail;
//
//        try {
//
//            MimeMessage message = mailSender.createMimeMessage();
//
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            message.setSubject(subject);
//
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setText(msg, true);
//
//            mailSender.send(message);
//
//        } catch (MessagingException ex) {
//            ex.printStackTrace();
//        }
//
//    }

    /**Generate message body based on message type<br>
     * Types are stored into <i>Email</i> interface (welcome message, password reset message, user information updated message) */
    private String generateMessage(String userName, String userPassword, int type){

        StringBuilder message = new StringBuilder();

        if (type == Email.WELCOME){

            message.append("Dear ").append(userName).append(",<br><br>").
                    append("Recently you have been registered on XO game server.<br>&nbsp;&nbsp;&nbsp;&nbsp;").
                    append("Your user name: <b>").append(userName).append("</b><br>&nbsp;&nbsp;&nbsp;&nbsp;").
                    append("Your  password: <b>").append(userPassword).append("</b><br><br>").
                    append("<i>Best Wishes,</i><br>").
                    append("<i>XO administration</i>");

            return message.toString();
        }

        if (type == Email.PASSWORD_RESET){

            message.append("Dear ").append(userName).append(",<br><br>").
                    append("Your password has been reset.<br>&nbsp;&nbsp;&nbsp;&nbsp;").
                    append("Your new password: <b>").append(userPassword).append("</b><br><br>").
                    append("<i>Best Wishes,</i><br>").
                    append("<i>XO administration</i>");

            return message.toString();
        }

        if (type == Email.UPDATE){

            message.append("Dear ").append(userName).append(",<br><br>").
                    append("Some information of your account was updated recently.<br>&nbsp;&nbsp;&nbsp;&nbsp;").
                    append("Your user name: <b>").append(userName).append("</b><br>&nbsp;&nbsp;&nbsp;&nbsp;").
                    append("Your  password: <b>").append(userPassword).append("</b><br><br>").
                    append("<i>Best Wishes,</i><br>").
                    append("<i>XO administration</i>");

            return message.toString();
        }

        return "";
    }

    /**Generate message title based on message type<br>
     * Types are stored into <i>Email</i> interface (welcome message, password reset message, user information updated message) */
    private String generateTitle(int type){

        if (type == Email.WELCOME)        return "[Game XO] Welcome";

        if (type == Email.PASSWORD_RESET) return "[Game XO] Password Reset";

        if (type == Email.UPDATE)         return "[Game XO] Information Update";

        return "";
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromText() {
        return fromText;
    }

    public void setFromText(String fromText) {
        this.fromText = fromText;
    }
}
