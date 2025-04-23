package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.Admin;
import com.example.warehouseplatform.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {




    private final AdminService adminService;




    @GetMapping("get")
    public ResponseEntity getLAll (){
        return  ResponseEntity.status(200).body(adminService.getAll());
    }



    @PostMapping("add")
    public ResponseEntity addAdmin (@Valid @RequestBody Admin admin){
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("The Admin added successfully"));
    }



    @PutMapping("update/{id}")
    public ResponseEntity updateAdmin (@Valid @RequestBody Admin admin, @PathVariable Integer id){
        adminService.updateAdmin(admin,id);
        return ResponseEntity.status(200).body(new ApiResponse("The Admin updated successfully"));
    }




    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("The Admin deleted successfully"));
    }

    @PostMapping("/notify/{providerId}/{adminId}")
    public ResponseEntity notifyExpiredLicense(@PathVariable Integer providerId, @PathVariable Integer adminId) {
        adminService.notifyExpiredLicense(providerId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Notification sent successfully"));
    }






}
