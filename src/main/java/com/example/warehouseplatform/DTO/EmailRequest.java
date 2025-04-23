package com.example.warehouseplatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {

    // recipient , message , subject
    private String recipient;
    private String message;
    private String subject;


}
