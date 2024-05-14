package com.myfund.services.email;


import com.myfund.models.DTOs.UserDTO;
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
public class PostmarkService implements EmailSender {

    @Value("${postmark.apikey}")
    private String apiKey;

    @Value("${postmark.api.url}")
    private String apiUrl;

    @Value("${email.sender}")
    private String emailSender;

    @Override
    public void sendWelcomeEmail(UserDTO userDTO) throws IOException {

        log.info("Starting to send email to: {}", userDTO.getEmail());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(apiUrl);

            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");
            post.setHeader("X-Postmark-Server-Token", apiKey);
            String json = String.format("{\"From\": \"%s\", \"To\": \"%s\", \"TemplateId\": 35874742, \"TemplateModel\": {\"name\": \"%s\", \"product_name\": \"my fund\"}}", emailSender, userDTO.getEmail(), userDTO.getUsername());
            post.setEntity(new StringEntity(json));

            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("Email sent successfully. Server response: {}", responseString);
            } else {
                log.error("Failed to send email. Server response: {}", responseString);
            }
        } catch (IOException e) {
            log.error("Failed to send email to: {}", userDTO.getEmail(), e);
            throw e;
        }
    }
}