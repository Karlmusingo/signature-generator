package com.ist.signature.services;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

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

    @Value("${spring.host}")
    private String apiHost;

    @Value("${spring.mail.mailjet.api.key}")
    private String emailApiKey;

    @Value("${spring.mail.mailjet.api.secret}")
    private String emailApiSecret;

    @Value("${spring.mail.mailjet.from.address}")
    private String emailFromAddress;

    public void sendVerificationEmail(String to, String verificationToken) {
        String verificationLink = apiHost + "/api/v1/auth/verify?token=" + verificationToken;

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
            ClientOptions options = ClientOptions.builder()
                    .apiKey(emailApiKey)
                    .apiSecretKey(emailApiSecret)
                    .build();

            MailjetClient client = new MailjetClient(options);

            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", emailFromAddress)
                                            .put("Name", "Signature Generator"))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", to)))
                                    .put(Emailv31.Message.SUBJECT, subject)
                                    .put(Emailv31.Message.HTMLPART, htmlContent)));

            MailjetResponse response = client.post(request);

            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to: {}", to, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}