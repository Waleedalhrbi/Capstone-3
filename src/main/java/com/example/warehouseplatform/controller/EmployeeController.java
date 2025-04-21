package com.example.warehouseplatform.controller;

import com.example.warehouseplatform.Model.Employee;
import com.example.warehouseplatform.api.ApiResponse;
import com.example.warehouseplatform.service.EmployeeService;
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
    public ResponseEntity addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return ResponseEntity.ok().body(new ApiResponse("employee added"));
    }

    @PutMapping("/assign/{employeeId}/{providerId}")
    public ResponseEntity assignEmployeeToProvider(@PathVariable Integer employeeId, @PathVariable Integer providerId) {
        employeeService.assignEmployeeToProvider(employeeId, providerId);
        return ResponseEntity.ok().body("employee assigned to the provider");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok().body(new ApiResponse("employee updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body(new ApiResponse("employee deleted"));
    }
}
