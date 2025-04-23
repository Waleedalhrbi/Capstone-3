package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Model.*;
import com.example.warehouseplatform.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final StorageProviderRepository storageProviderRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierComplaintRepository supplierComplaintRepository;
    private final EmailNotificationService emailNotificationService;
    private final ProviderComplaintRepository providerComplaintRepository;


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


    public void validateStorageProviderLicense(Integer adminId, Integer providerId) {
        Admin admin = adminRepository.findAdminById(adminId);
        if (admin == null) {
            throw new ApiException("the admin is not found");
        }
        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if (provider == null) {
            throw new ApiException("the provider is not found");
        }


        provider.setIsActive(true);
        storageProviderRepository.save(provider);
    }





    public void riseSupplierComplaintCount(Integer id){
        Supplier supplier = supplierRepository.findSupplierById(id);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        supplier.setComplainCount(supplier.getComplainCount()+1);
        if (supplier.getComplainCount()>5){
            supplier.setIsBlackListed(true);

        }


    }


    public void approveStorageProviderComplaint(Integer adminId, Integer providerId, Integer complainId){
        Admin admin = adminRepository.findAdminById(adminId);
        if (admin == null) {
            throw new ApiException("the admin is not found");
        }


        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if (provider == null) {
            throw new ApiException("Provider not found");
        }

        ProviderComplaint complaint= providerComplaintRepository.getComplainBYComplainIdAndProviderId(complainId,providerId);
        if (complaint == null) {
            throw new ApiException("the complaint is not found");
        }

        complaint.setIsApproved(true);
        /// get supplier that made the request that has complaint
        Supplier supplier = supplierRepository.findSupplierById(complaint.getRequest().getSupplier().getId());
        riseSupplierComplaintCount(supplier.getId());
        supplierRepository.save(supplier);
        providerComplaintRepository.save(complaint);


    }

    public void riseProviderComplaintCount(Integer id){
        StorageProvider provider= storageProviderRepository.findStorageProviderById(id);
        provider.setComplainCount(provider.getComplainCount()+1);
        if (provider.getComplainCount()>5) {
            provider.setIsActive(false); // assign provider licence to false
        }

    }


    public void approveSupplierComplain(Integer adminId, Integer supplierId , Integer complainId){

        Admin admin = adminRepository.findAdminById(adminId);

        if (admin == null) {
            throw new ApiException("the admin is not found");
        }


        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        ///  check if the complaint is made by th supplier snd if it is not null
        SupplierComplaint  complaint= supplierComplaintRepository.getComplainBySupplierIdAndComplainId(supplierId,complainId);
        if (complaint == null) {
            throw new ApiException("the complaint is not found");
        }
        /// get providers that owns the warehouse that in the request that have complain
        StorageProvider provider =complaint.getRequest().getWareHouse().getStorageProvider();
        riseProviderComplaintCount(provider.getId());
        complaint.setIsApproved(true);
        storageProviderRepository.save(provider);
        supplierComplaintRepository.save(complaint);


    }



    public void approveRenewLicenceRequest(Integer adminId, Integer providerId){

        Admin admin = adminRepository.findAdminById(adminId);
        if (admin == null) {
            throw new ApiException("the admin is not found");
        }

        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if (provider == null) {
            throw new ApiException("Provider not found");
        }

        if (!provider.getRenewLicenceRequest()){
            throw new ApiException("Provider Licence is active ");
        }

        /// this condition only applies to providers with previous warehouses
        if(!provider.getWareHouses().isEmpty()) {
            if (provider.getComplainCount()>=5) {
                throw new ApiException("Provider Licence renew request is denied due to many complains ");
            }
        }


        //renew if all conditions are met
        provider.setIsActive(true);
        provider.setRenewLicenceRequest(false);
        provider.setLicenseDate(LocalDate.now());
        storageProviderRepository.save(provider);



    }












}



