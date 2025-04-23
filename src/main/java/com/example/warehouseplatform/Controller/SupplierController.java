package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Model.Supplier;
import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/get")
    public ResponseEntity getAllSupplier() {
        return ResponseEntity.ok().body(supplierService.getAllSuppliers());
    }

    @PostMapping("/add")
    public ResponseEntity addClient(@Valid  @RequestBody Supplier supplier) {
        supplierService.addSupplier(supplier);
        return ResponseEntity.ok().body(new ApiResponse("Supplier added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateSupplier(@PathVariable Integer id,@Valid @RequestBody Supplier supplier) {
        supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok().body(new ApiResponse("Supplier updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok().body(new ApiResponse("Supplier deleted"));
    }


    // Ex endpoint
    /// made by Sahar :3
    /// Primary Calculator to give users first look on what price to expect
    @GetMapping("calculator/{days}/{storeSize}")
    public ResponseEntity primaryCalculator(@PathVariable Integer days, @PathVariable String storeSize) {
        return ResponseEntity.ok().body(new ApiResponse("Enter the days and the store size to primary calculation :"+supplierService.primaryCalculator(days,storeSize)));
    }



    // Ex endpoint
    /// made by Sahar :5
    /// returning all the complaints that the supplier made with their status
    @GetMapping("get-all-complains/{id}")
    public ResponseEntity getAllComplaintsMadeBySuppliers(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new ApiResponse("All the complains made by supplier :"+supplierService.getAllComplaintsMadeBySuppliers(id)));
    }


    // Ex endpoint
    /// made by Sahar :11
    /// this endpoint allow suppliers to view all their approved complaints
    @GetMapping("approved-complaints/{id}")
    public ResponseEntity approvedComplaints(@PathVariable Integer id) {
        return ResponseEntity.ok().body(supplierService.getAllApprovedComplain(id));
    }


}
