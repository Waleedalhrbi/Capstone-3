package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    @GetMapping("/get")
    public ResponseEntity getAllRequests() {
        return ResponseEntity.status(HttpStatus.OK).body(requestService.getAllRequests());
    }

    @PostMapping("/add/{clientId}")
    public ResponseEntity addRequest(@PathVariable Integer clientId, @Valid @RequestBody Request request) {
        requestService.addRequest(request, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Request added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateRequest(@PathVariable Integer id, @Valid @RequestBody Request request) {
        requestService.updateRequest(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Request updated successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteRequest(@PathVariable Integer id) {
        requestService.deleteRequest(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Request deleted successfully"));
    }

}
