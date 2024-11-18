package com.ist.signature.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;

    private static final String EMAIL_TEMPLATE = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    .email-container {
                        max-width: 600px;
                        margin: 0 auto;
                        font-family: Arial, sans-serif;
                        padding: 20px;
                    }
                    .button {
                        display: inline-block;
                        padding: 10px 20px;
                        background-color: #007bff;
                        color: white;
                        text-decoration: none;
                        border-radius: 5px;
                        margin: 20px 0;
                    }
                    .footer {
                        font-size: 12px;
                        color: #666;
                        margin-top: 20px;
                        border-top: 1px solid #eee;
                        padding-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="email-container">
                    %s
                    <div class="footer">
                        <p>Need help? Contact us at support@yourdomain.com</p>
                    </div>
                </div>
            </body>
            </html>
            """;

    public void sendVerificationEmail(String to, String verificationToken) {
        String verificationLink = "http://localhost:8080/api/v1/auth/verify?token=" + verificationToken;

        String content = String.format(EMAIL_TEMPLATE, String.format("""
                <h2>Verify Your Email Address</h2>
                <p>Please click the button below to verify your email address:</p>
                <a href="%s" class="button" style="color: white;">Verify Email</a>
                <p>Or copy and paste this link in your browser:</p>
                <p>%s</p>
                <p>If you didn't create an account, please ignore this email.</p>
                """, verificationLink, verificationLink));

        sendHtmlEmail(to, "Verify Your Email Address", content);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@ist.com");
            helper.setTo("kira.wiegand15@ethereal.email");
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            emailSender.send(message);
            log.info("Email sent successfully to: {}", to);

        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}