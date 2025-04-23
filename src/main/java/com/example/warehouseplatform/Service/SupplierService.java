package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.DTO.SupplierDTO;
import com.example.warehouseplatform.DTO.EmailRequest;
import com.example.warehouseplatform.Model.*;
import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Repository.SupplierComplaintRepository;
import com.example.warehouseplatform.Repository.SupplierRepository;

import com.example.warehouseplatform.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final EmailNotificationService emailNotificationService;
    private final RequestRepository requestRepository;
    private final SupplierComplaintRepository supplierComplaintRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<SupplierDTO> getAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        for(Supplier c : suppliers) {
            SupplierDTO supplierDTO = new SupplierDTO(c.getUsername(),c.getEmail(),c.getPhoneNumber());
            supplierDTOList.add(supplierDTO);
        }
        return supplierDTOList;
    }

    public void addSupplier(Supplier supplier) {
        supplier.setIsBlackListed(false);
        supplierRepository.save(supplier);
    }

    public void updateSupplier(Integer id, Supplier updatedSupplier) {
        Supplier supplier = supplierRepository.findSupplierById(id);
        if(supplier == null) throw new ApiException("Client not found");


        supplier.setUsername(updatedSupplier.getUsername());
        supplier.setPassword(updatedSupplier.getPassword());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setPhoneNumber(updatedSupplier.getPhoneNumber());
        supplierRepository.save(supplier);
    }

    public void deleteSupplier(Integer id) {
        Supplier supplier = supplierRepository.findSupplierById(id);
        if(supplier == null) throw new ApiException("client not found");
        supplierRepository.deleteById(id);
    }


    public void notifyEndOfRequest(Integer requestId) {
        Request request = requestRepository.findRequestById(requestId); // assuming you have this method

        if (request == null) {
            throw new ApiException("Request not found");
        }

        LocalDate today = LocalDate.now();

        if (request.getEnd_date() != null && request.getEnd_date().isEqual(today)) {
            Supplier supplier = request.getSupplier();

            if (supplier == null) {
                throw new ApiException("Supplier not found for this request");
            }

            if (supplier.getEmail() == null || supplier.getUsername() == null) {
                throw new ApiException("Supplier email or username is missing");
            }

            String subject = "Your Warehouse Booking Has Ended";
            String message = "Dear " + supplier.getUsername() + ",\n\n" +
                    "Your warehouse booking has officially ended on " + today + ".\n\n" +
                    "We hope everything went smoothly. Feel free to leave a review or book again!\n\n" +
                    "Thank you";

            EmailRequest emailRequest = new EmailRequest(supplier.getEmail(),message, subject);
            emailNotificationService.sendEmail(emailRequest);
        } else {
            throw new ApiException("Today is not the request's end date");
        }
    }



    public Integer primaryCalculator(Integer days, String storeSize) {

        Integer price = 0;

        switch (storeSize.toLowerCase()) {

            case "small":
                price = 100 * days;
                break;

            case "medium":
                price = 500 * days;
                break;


            case "large":
                price = 1000 * days;
                break;
            default:
                throw new ApiException("uncorrected size ,the available sizes are (small , medium , large");

        }


        return price;
    }




    public List<SupplierComplaint> getAllComplaintsMadeBySuppliers(Integer supplierId){
        List<SupplierComplaint> suppliersComplaints=supplierComplaintRepository.findSupplierComplaintBySupplier_Id(supplierId);
        if (suppliersComplaints.isEmpty()){
            throw new ApiException("the supplier has no complains made yet");
        }
        return suppliersComplaints;

    }



    public List<SupplierComplaint> getAllApprovedComplain(Integer id){
        Supplier supplier = supplierRepository.findSupplierById(id);
        if(supplier == null) throw new ApiException("supplier not found");
        return supplierComplaintRepository.approvedSupplierComplaintMadeBySupplier(id);

    }

}
