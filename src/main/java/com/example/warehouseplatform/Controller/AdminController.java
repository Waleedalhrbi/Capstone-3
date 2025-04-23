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

    // Ex endpoint
    // made by Khaled
    @PostMapping("/notify/{providerId}/{adminId}")
    public ResponseEntity notifyExpiredLicense(@PathVariable Integer providerId, @PathVariable Integer adminId) {
        adminService.notifyExpiredLicense(providerId, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("Notification sent successfully"));
    }



    // Ex endpoint
    /// made by Sahar :3
    /// an endpoint to validate Storage Provider License and assign them as
    ///  active to start using the system
    @PutMapping("validate-licence/{adminId}/{providerId}")
    public ResponseEntity validateStorageProviderLicense (@PathVariable Integer adminId, @PathVariable Integer providerId){
        adminService.validateStorageProviderLicense(adminId,providerId);
        return ResponseEntity.status(200).body(new ApiResponse("The storage provider with id :"+providerId+" is confirmed successfully"));
    }


    // Ex endpoint
    /// made by Sahar :6
    /// an endpoint to approve a supplier complain on a storage provider\
    /// the complaint is approved ,SP will be inactive after more than 5 complains
    @PutMapping("approve-supplier-complain/{adminId}/{supplierId}/{requestId}")
    public ResponseEntity approveSupplierComplain (@PathVariable Integer adminId, @PathVariable Integer supplierId,@PathVariable Integer requestId){
        adminService.approveSupplierComplain(adminId,supplierId,requestId);
        return ResponseEntity.status(200).body(new ApiResponse("The complain is approved successfully"));
    }



    // Ex endpoint
    /// made by Sahar :8
    /// an endpoint to approve a provider complain on a storage provider
    /// the complaint is approved ,suppliers will be blackListed after 5 complains when black listed they will not be able to add new request
    @PutMapping("approve-supplier-complain/{adminId}/{providerId}/{requestId}")
    public ResponseEntity approveStorageProviderComplaint (@PathVariable Integer adminId, @PathVariable Integer providerId,@PathVariable Integer requestId){
        adminService.approveStorageProviderComplaint(adminId,providerId,requestId);
        return ResponseEntity.status(200).body(new ApiResponse("The complain is approved successfully"));
    }




    // Ex endpoint
    /// made by Sahar :12
    /// this endpoint allow admin to renew licence based on conditions
    @PutMapping("approve-supplier-licence-renew/{adminId}/{providerId}/{requestId}")
    public ResponseEntity approveRenewLicenceRequest (@PathVariable Integer adminId, @PathVariable Integer providerId){
        adminService.approveRenewLicenceRequest(adminId,providerId);
        return ResponseEntity.status(200).body(new ApiResponse("The licence renew request is approved successfully"));
    }


}
