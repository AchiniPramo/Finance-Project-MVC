package lk.ijse.Micro_Finance_Management_System.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    private static final String SENDER_EMAIL = "chathuhiru45@gmail.com";
    private static final String PASSWORD = "vhmz gqaj hmvf mjpa";

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    public static boolean sendCodeByEmail(String verificationCode) {
        String recipientEmail = "achinipramodhya4@gmail.com";

        String subject = "Verification Code";
        String body = "Your verification code is: " + verificationCode;

        return sendEmail(recipientEmail, subject, body);
    }

    public static boolean sendEmail(String recipientEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}