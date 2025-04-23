package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.SupplierComplaint;
import com.example.warehouseplatform.Service.SupplierComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier-complaint")
@RequiredArgsConstructor
public class SupplierComplaintController {

    private final SupplierComplaintService supplierComplaintService;


    @GetMapping("get")
    public ResponseEntity getAllClientComplaintService() {
        return ResponseEntity.ok().body(supplierComplaintService.getAllComplaint());
    }



    // Ex endpoint
    /// made by Sahar :4
    /// an endpoint to allow suppliers to file a complaint on a request (cooperated items,lack of professionalism etc...)
    /// admin will review complain and approve it
    @PutMapping("file-complain-on-provider/{supplierId}/{requestId}")
    public ResponseEntity fileComplaintOnProvider(@PathVariable Integer supplierId,@PathVariable Integer requestId, @Valid @RequestBody SupplierComplaint complaint) {
        supplierComplaintService.fileComplaintOnProvider(supplierId,requestId,complaint);
        return ResponseEntity.ok().body(new ApiResponse("The compliant has been filed successfully"));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateClientComplaint(@PathVariable Integer id, @RequestBody SupplierComplaint complaint) {
        supplierComplaintService.updateClientComplaint(id, complaint);
        return ResponseEntity.ok().body(new ApiResponse("Complaint updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteClientComplaint(@PathVariable Integer id) {
        supplierComplaintService.deleteClientComplaint(id);
        return ResponseEntity.ok().body(new ApiResponse("Complaint deleted"));
    }



}
