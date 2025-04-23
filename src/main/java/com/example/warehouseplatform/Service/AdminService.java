package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Model.Admin;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Repository.AdminRepository;
import com.example.warehouseplatform.Repository.StorageProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

private final AdminRepository adminRepository;
private final StorageProviderRepository storageProviderRepository;
private final EmailNotificationService emailNotificationService;


public List<Admin> getAll(){
    return adminRepository.findAll();
}



    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }



    public void updateAdmin(Admin admin, Integer id) {

        Admin admin1 = adminRepository.findAdminById(id);

        if (admin1 == null) {
            throw new ApiException("the admin is not found");
        }

        admin1.setUsername(admin.getUsername());
        admin1.setPassword(admin.getPassword());
        adminRepository.save(admin1);


    }




    public void deleteAdmin(Integer id){

        Admin admin= adminRepository.findAdminById(id);

        if (admin==null){
            throw new ApiException("admin is not found");
        }

        adminRepository.delete(admin);


    }


    public void notifyExpiredLicense(Integer providerId, Integer adminId) {
        StorageProvider storageProvider = storageProviderRepository.findStorageProviderById(providerId);

        if(storageProvider == null) throw new ApiException("storage provider no found");

        Admin admin = adminRepository.findAdminById(adminId);
        if(admin == null) throw new ApiException("admin not found");

        String subject = "License Expiration Notice";
        String message = "Dear " + storageProvider.getUsername() + ",\n\n" +
                "Your warehouse license has expired. Please renew it to continue using our platform.\n\n" +
                "Regards,\n" + admin.getUsername();

        EmailRequest request = new EmailRequest(storageProvider.getEmail(), message, subject);
        emailNotificationService.sendEmail(request);
    }
}



