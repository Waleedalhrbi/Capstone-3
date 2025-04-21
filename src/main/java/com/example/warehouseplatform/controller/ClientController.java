package com.example.warehouseplatform.controller;

import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.api.ApiResponse;
import com.example.warehouseplatform.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/get")
    public ResponseEntity getAllClients() {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @PostMapping("/add")
    public ResponseEntity addClient(@RequestBody Client client) {
        clientService.addClient(client);
        return ResponseEntity.ok().body(new ApiResponse("client added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClient(@PathVariable Integer id, @RequestBody Client client) {
        clientService.updateClient(id, client);
        return ResponseEntity.ok().body(new ApiResponse("client updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClient(@PathVariable Integer id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().body(new ApiResponse("client deleted"));
    }
}
