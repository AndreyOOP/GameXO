package jfiles.service;

import jfiles.Constants.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**Service for email generation and sending*/
@Service("htmlEmail")
public class HTMLMail {

    private JavaMailSender mailSender;

//    @Autowired
//    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }

    /**Method sends email of specified type to specified recipient<br>
     * Types are stored into <i>Email</i> interface (welcome message, password reset message, user information updated message) */
    public void sendEmail(String userName, String userPassword, String userEmail, int type){

        String subject = generateTitle(type);
        String msg     = generateMessage(userName, userPassword, type);
        String from    = Email.FROM;
        String to      = userEmail;

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            message.setSubject(subject);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(msg, true);

            mailSender.send(message);

        } catch (MessagingException ex) {
            ex.printStackTrace();
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
}
