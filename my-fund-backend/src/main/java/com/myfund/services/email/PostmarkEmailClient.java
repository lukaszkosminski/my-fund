package com.myfund.services.email;


import com.myfund.exceptions.EmailThrottleException;
import com.myfund.models.DTOs.UserDTO;
import com.myfund.models.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@Primary
public class PostmarkEmailClient implements EmailSender {

    @Value("${postmark.apikey}")
    private String apiKey;

    @Value("${postmark.api.url}")
    private String apiUrl;

    @Value("${email.sender}")
    private String emailSender;

    @Value("${change.password.url}")
    private String changePasswordUrl;

    private final EmailThrottleService emailThrottleService;

    public PostmarkEmailClient(EmailThrottleService emailThrottleService) {
        this.emailThrottleService = emailThrottleService;
    }

    @Override
    public void sendWelcomeEmail(User user) throws IOException {

        log.info("Starting to send email to: {}", user.getEmail());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(apiUrl);

            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("X-Postmark-Server-Token", apiKey);
            String json = String.format("{\"From\": \"%s\", \"To\": \"%s\", \"TemplateId\": 35874742, \"TemplateModel\": {\"name\": \"%s\", \"product_name\": \"my fund\"}}", emailSender, user.getEmail(), user.getUsername());
            post.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("Email sent successfully. Server response: {}", responseString);
                return;
            }
            log.error("Failed to send email. Server response: {}", responseString);

        } catch (IOException e) {
            log.error("Failed to send email to: {}", user.getEmail(), e);
            throw e;
        }
    }

    @Override
    public void sendPasswordResetEmail(User user, String resetToken) throws IOException {
        log.info("Starting to send password reset email to: {}", user.getEmail());

        if (!emailThrottleService.canSendEmail(user.getEmail())) {
            log.error("Email limit exceeded for: {}", user.getEmail());
            throw new EmailThrottleException("Email limit exceeded");
        }

        String changePasswordUrlWithParams = changePasswordUrl + resetToken + "&email=" + user.getEmail();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(apiUrl);

            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("X-Postmark-Server-Token", apiKey);
            String json = String.format("{\"From\": \"%s\", \"To\": \"%s\", \"TemplateId\": 35917746, \"TemplateModel\": {\"name\": \"%s\", \"action_url\": \"%s\"}}", emailSender, user.getEmail(), user.getUsername(), changePasswordUrlWithParams);
            post.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            emailThrottleService.incrementEmailCount(user.getEmail());

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("Password reset email sent successfully. Server response: {}", responseString);
                return;
            }
            log.error("Failed to send password reset email. Server response: {}", responseString);

        } catch (IOException e) {
            log.error("Failed to send password reset email to: {}", user.getEmail(), e);
            throw e;
        }
    }
}