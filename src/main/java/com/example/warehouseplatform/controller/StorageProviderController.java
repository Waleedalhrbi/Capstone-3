package com.example.warehouseplatform.controller;

import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.api.ApiResponse;
import com.example.warehouseplatform.service.StorageProviderService;
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
    public ResponseEntity addProvider(@RequestBody StorageProvider provider) {
        providerService.addProvider(provider);
        return ResponseEntity.ok().body(new ApiResponse("provider added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProvider(@PathVariable Integer id, @RequestBody StorageProvider provider) {
        providerService.updateProvider(id, provider);
        return ResponseEntity.ok().body(new ApiResponse("provider updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        providerService.deleteProvider(id);
        return ResponseEntity.ok().body(new ApiResponse("provider deleted"));
    }
}
