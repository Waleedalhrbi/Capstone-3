package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Service.StorageProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class StorageProviderController {

    private final StorageProviderService providerService;

    @GetMapping("/get")
    public ResponseEntity getAllProviders() {
       return ResponseEntity.ok().body(providerService.getAllProviders());
    }


    @PostMapping("/add")
    public ResponseEntity addProvider(@Valid @RequestBody StorageProvider provider) {
        providerService.addProvider(provider);
        return ResponseEntity.ok().body(new ApiResponse("provider added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProvider(@PathVariable Integer id, @Valid @RequestBody StorageProvider provider) {
        providerService.updateProvider(id, provider);
        return ResponseEntity.ok().body(new ApiResponse("provider updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        providerService.deleteProvider(id);
        return ResponseEntity.ok().body(new ApiResponse("provider deleted"));
    }


    // Ex endpoint
    /// made by Sahar :9
    /// an endpoint to file a licence renew request
    /// the admin will approve or deny the request based on certain criteria
    @PutMapping("renew-licence/{id}")
    public ResponseEntity renewLicenceRequest(@PathVariable Integer id) {
        providerService.renewLicenceRequest(id);
        return ResponseEntity.ok().body(new ApiResponse("Provider request is send successfully "));
    }


    // Ex endpoint
    /// made by Sahar :10
    /// this endpoint allow SP to view all their approved complaints
    @GetMapping("approved-complaints/{id}")
    public ResponseEntity approvedComplaints(@PathVariable Integer id) {
        return ResponseEntity.ok().body(providerService.getAllApprovedComplain(id));


    }



    // Ex endpoint
    /// made by Sahar :13
    /// returning all the complaints that the provider  with their status
    @GetMapping("get-all-complains/{id}")
    public ResponseEntity getAllComplaintsMadeByProvider(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new ApiResponse("All the complains made by supplier :"+providerService.getAllComplaintsMadeByProvider(id)));
    }


}
