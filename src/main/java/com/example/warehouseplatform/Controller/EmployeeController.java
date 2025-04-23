package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Model.Employee;
import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/get")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.ok().body(new ApiResponse("employee added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @Valid @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok().body(new ApiResponse("employee updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body(new ApiResponse("employee deleted"));
    }

    // Ex endpoint
    // made by Khaled
    @PutMapping("/assign/{employeeId}/{providerId}")
    public ResponseEntity assignEmployeeToProvider(@PathVariable Integer employeeId, @PathVariable Integer providerId) {
        employeeService.assignEmployeeToProvider(employeeId, providerId);
        return ResponseEntity.ok().body(new ApiResponse("employee assigned to the provider"));
    }
}
