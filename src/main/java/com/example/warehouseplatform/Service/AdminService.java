package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Admin;
import com.example.warehouseplatform.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

private final AdminRepository adminRepository;


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





}
