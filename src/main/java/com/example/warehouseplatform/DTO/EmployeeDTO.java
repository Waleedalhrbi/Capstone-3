package com.example.warehouseplatform.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    @NotEmpty
    private String fullName;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String nationality;
}
