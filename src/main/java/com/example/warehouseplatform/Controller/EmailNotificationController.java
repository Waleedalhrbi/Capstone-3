package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailRequest emailRequest){
        emailNotificationService.sendEmail(emailRequest);
        return ResponseEntity.status(200).body(new ApiResponse("Email sent successfully"));
    }
}
