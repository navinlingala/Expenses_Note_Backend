package com.money.remainder.controller;

import com.money.remainder.service.WhatsAppNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications/whatsapp")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WhatsAppController {

    private final WhatsAppNotificationService whatsAppService;

    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> getConfig() {
        return ResponseEntity.ok(Map.of(
            "appWhatsAppNumber", whatsAppService.getSenderNumber(),
            "formattedNumber", "+91 9010067464",
            "status", "ACTIVE",
            "description", "Official Money Reminder WhatsApp Dispatch Channel"
        ));
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendReminder(@RequestBody Map<String, Object> payload) {
        String recipient = (String) payload.get("recipientPhone");
        String personName = (String) payload.get("personName");
        Double amount = payload.get("amount") != null ? Double.valueOf(payload.get("amount").toString()) : 0.0;
        String dueDate = (String) payload.get("dueDate");
        String title = (String) payload.get("title");
        String note = (String) payload.get("note");

        Map<String, Object> result = whatsAppService.sendWhatsAppReminder(
            recipient, personName, amount, dueDate, title, note
        );

        if (Boolean.TRUE.equals(result.get("success"))) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
