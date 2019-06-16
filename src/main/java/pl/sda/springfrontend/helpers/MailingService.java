package pl.sda.springfrontend.helpers;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public
class MailingService {
    private final
    JavaMailSender emailSender;

    public MailingService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String message) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setFrom("authority@sanct.pl");
        emailMessage.setTo(to);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        emailSender.send(emailMessage);
    }
}
