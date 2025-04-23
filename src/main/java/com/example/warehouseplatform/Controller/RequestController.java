package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    @GetMapping("/get")
    public ResponseEntity getAllRequests() {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.getAllRequests());
    }

    // Ex endpoint
    @PostMapping("/add/{supplierId}/{warehouseId}")
    public ResponseEntity addRequest(@PathVariable Integer supplierId, @PathVariable Integer warehouseId, @Valid @RequestBody Request request) {
        requestService.addRequest(request, supplierId,warehouseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Request added successfully"));
    }

    // Ex endpoint
    @PutMapping("/update/{id}")
    public ResponseEntity updateRequest(@PathVariable Integer id, @Valid @RequestBody Request request) {
        requestService.updateRequest(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Request updated successfully"));
    }

    // Ex endpoint
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequest(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Request deleted successfully"));
    }


    // Ex endpoint
    @GetMapping("/search-by-date/{startDate}/{endDate}")
    public ResponseEntity getRequestsByDateRange(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.getRequestsByDateRange(startDate, endDate));
    }

    // Ex endpoint
    @PutMapping("/check-availability/{startDate}/{endDate}")
    public ResponseEntity checkDateAvailability(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {

        requestService.checkDateAvailability(startDate, endDate);

        return ResponseEntity.ok().body(new ApiResponse("The selected dates are available for booking."));
    }

    // Ex endpoint
    @GetMapping("/get-type/{type}")
    public ResponseEntity getRequestsByStoreType(@PathVariable String type) {
        return ResponseEntity.ok(requestService.getRequestsByStoreType(type));
    }


}
