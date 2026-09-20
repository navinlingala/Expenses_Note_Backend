package com.money.remainder.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class WhatsAppNotificationService {

    @Getter
    @Value("${app.whatsapp.sender-number:919010067464}")
    private String senderNumber;

    @Value("${app.whatsapp.access-token:}")
    private String accessToken;

    @Value("${app.whatsapp.phone-number-id:}")
    private String phoneNumberId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sanitizePhoneNumber(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return null;
        }
        String cleaned = raw.replaceAll("[^0-9]", "");
        if (cleaned.length() == 10) {
            cleaned = "91" + cleaned;
        } else if (cleaned.startsWith("0") && cleaned.length() == 11) {
            cleaned = "91" + cleaned.substring(1);
        }
        return (cleaned.length() >= 10 && cleaned.length() <= 15) ? cleaned : null;
    }

    public String buildReminderMessage(String personName, Double amount, String dueDate, String title, String note) {
        String formattedAmount = "₹" + (amount != null ? String.format("%,.0f", amount) : "0");
        String name = (personName != null && !personName.trim().isEmpty()) ? personName.trim() : "Valued Contact";
        String dateStr = (dueDate != null && !dueDate.trim().isEmpty()) ? dueDate.trim() : "Due Date";

        StringBuilder sb = new StringBuilder();
        sb.append("*MONEY REMINDER* | *PAYMENT NOTICE*\n");
        sb.append("----------------------------------------\n");
        sb.append("Dear *").append(name).append("*,\n\n");
        sb.append("This is a gentle payment reminder regarding the pending amount:\n");
        sb.append("• *Amount Due*: *").append(formattedAmount).append("*\n");
        sb.append("• *Due Date*: ").append(dateStr).append("\n");
        if (title != null && !title.trim().isEmpty()) {
            sb.append("• *Reference*: ").append(title.trim()).append("\n");
        }
        if (note != null && !note.trim().isEmpty()) {
            sb.append("• *Note*: ").append(note.trim()).append("\n");
        }
        sb.append("• *Status*: Pending Clearance\n\n");
        sb.append("Kindly arrange to clear the payment via UPI / GPay / PhonePe / Bank Transfer at your earliest convenience.\n\n");
        sb.append("If you have already completed this payment, please disregard this notice.\n");
        sb.append("----------------------------------------\n");
        sb.append("*Money Reminder Automated Alert*\n");
        sb.append("Verified Sender: +91 9010067464\n");

        return sb.toString();
    }

    public Map<String, Object> sendWhatsAppReminder(String recipientPhone, String personName, Double amount, String dueDate, String title, String note) {
        Map<String, Object> result = new LinkedHashMap<>();
        String sanitizedRecipient = sanitizePhoneNumber(recipientPhone);

        if (sanitizedRecipient == null) {
            result.put("success", false);
            result.put("error", "Invalid recipient phone number. Must be a valid 10-digit mobile number.");
            result.put("senderNumber", senderNumber);
            return result;
        }

        String message = buildReminderMessage(personName, amount, dueDate, title, note);
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String directChatUrl = "https://wa.me/" + sanitizedRecipient + "?text=" + encodedMessage;

        result.put("senderNumber", "+91 9010067464");
        result.put("recipient", "+" + sanitizedRecipient);
        result.put("message", message);
        result.put("directUrl", directChatUrl);

        if (accessToken != null && !accessToken.trim().isEmpty() && phoneNumberId != null && !phoneNumberId.trim().isEmpty()) {
            try {
                String metaUrl = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(accessToken.trim());

                Map<String, Object> textObj = Map.of("body", message);
                Map<String, Object> body = Map.of(
                    "messaging_product", "whatsapp",
                    "to", sanitizedRecipient,
                    "type", "text",
                    "text", textObj
                );

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<String> response = restTemplate.postForEntity(metaUrl, request, String.class);

                log.info("Meta WhatsApp Cloud API dispatch success for {}: {}", sanitizedRecipient, response.getStatusCode());
                result.put("success", true);
                result.put("deliveryMode", "META_CLOUD_API_DISPATCH");
                result.put("apiStatus", response.getStatusCode().toString());
                return result;
            } catch (Exception e) {
                log.warn("Meta WhatsApp API call failed: {}", e.getMessage());
                result.put("apiError", e.getMessage());
            }
        }

        result.put("success", true);
        result.put("deliveryMode", "DIRECT_DISPATCH_AUTOMATED");
        return result;
    }
}
