package jfiles.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import jfiles.Constants.Email;


/**Service for email generation and sending*/
public class HTMLMail {

    private String fromAddress;
    private String fromText;

    /**Method sends email of specified type to specified recipient<br>
     * Types are stored into <i>Email</i> interface (welcome message, password reset message, user information updated message) */
    public void sendEmail(String userName, String userPassword, String userEmail, int type){

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(fromAddress, fromText));

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail, ""));

            msg.setSubject( generateTitle(type));

            Multipart    multiPartMessage = new MimeMultipart();
            MimeBodyPart bodyOfMessage    = new MimeBodyPart();

            bodyOfMessage.setContent( generateMessage(userName, userPassword, type), "text/html");

            multiPartMessage.addBodyPart( bodyOfMessage);

            msg.setContent(multiPartMessage);

            Transport.send(msg);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


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
