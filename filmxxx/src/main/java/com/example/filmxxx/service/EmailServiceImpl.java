package com.example.filmxxx.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessage(String from, String to, String subject, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            String htmlContent = """
            <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                    <h2 style="color: #1976d2;">%s</h2>
                    <p style="font-size: 16px;">%s</p>
                    <p style="font-size: 16px;">
                        <a href="%s" style="color: #007BFF; text-decoration: none; font-weight: bold;">Click here to reset your password</a>
                    </p>
                    <p style="font-size: 14px; color: #555;">
                        If you didn’t request this, ignore this email dmm.
                    </p>
                </body>
            </html>
        """.formatted(subject, text, text);
            helper.setText(htmlContent, true);
        } catch (Exception ex) {
            throw new RuntimeException("Error while sending email: " + ex.getMessage());
        }
        emailSender.send(mimeMessage);
    }
}
