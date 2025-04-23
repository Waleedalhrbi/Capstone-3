package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.ProviderComplaint;
import com.example.warehouseplatform.Service.ProviderComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider-complaint")
@RequiredArgsConstructor
public class ProviderComplaintController {

    private final ProviderComplaintService providerComplaintService;


    @GetMapping("get")
    public ResponseEntity getAllProviderComplaintService() {
        return ResponseEntity.ok().body(providerComplaintService.getAllComplaint());
    }



    // Ex endpoint
    /// made by Sahar :7
    /// an endpoint to allow storage providers to file a complaint on a request (cooperated warehouses,lack of professionalism ,lack of responses etc...)
     /// admin will review complain and approve it
    @PutMapping("file-complain-on-client/{clientId}/{requestId}")
    public ResponseEntity fileComplaintOnClient(@PathVariable Integer clientId,@PathVariable Integer requestId, @Valid @RequestBody ProviderComplaint complaint) {
        providerComplaintService.fileComplaintOnClient(clientId,requestId,complaint);
        return ResponseEntity.ok().body(new ApiResponse("The compliant has been filed successfully"));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateClientComplaint(@PathVariable Integer id, @RequestBody ProviderComplaint complaint) {
        providerComplaintService.updateProviderComplaint(id, complaint);
        return ResponseEntity.ok().body(new ApiResponse("Complaint updated"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteClientComplaint(@PathVariable Integer id) {
        providerComplaintService.deleteProviderComplaint(id);
        return ResponseEntity.ok().body(new ApiResponse("Complaint deleted"));
    }






}
