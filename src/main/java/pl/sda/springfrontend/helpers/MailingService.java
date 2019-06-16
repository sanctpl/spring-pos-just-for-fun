package pl.sda.springfrontend.helpers;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public
class MailingService {
    private final
    JavaMailSender emailSender;

    public MailingService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String message) throws MessagingException {
//       SimpleMailMessage emailMessage = new SimpleMailMessage();
//        emailMessage.setFrom("authority@sanct.pl");
//        emailMessage.setTo(to);
//        emailMessage.setSubject(subject);
        // emailMessage.setText(message);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        mimeMessage.setFrom("authority@sanct.pl");
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject(subject);
        mimeMessage.setContent(message, "text/html; charset=utf-8");

        emailSender.send(mimeMessage);
    }
}
