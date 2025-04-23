package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.Supplier;
import com.example.warehouseplatform.Model.SupplierComplaint;
import com.example.warehouseplatform.Repository.RequestRepository;
import com.example.warehouseplatform.Repository.SupplierComplaintRepository;
import com.example.warehouseplatform.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierComplaintService {

    private final SupplierComplaintRepository supplierComplaintRepository;
    private final SupplierRepository supplierRepository;
    private final RequestRepository requestRepository;

    public List<SupplierComplaint> getAllComplaint() {
        return supplierComplaintRepository.findAll();
    }



    public void fileComplaintOnProvider(Integer supplierId, Integer requestId, SupplierComplaint complaint) {

        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) throw new ApiException("supplier not found");

        Request request=requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found");


        if (supplierComplaintRepository.findSupplierComplaintBySupplier_IdAndRequest_IdAndIsApproved(supplierId,requestId,true)!=null){
            throw  new ApiException("there is already one complain made by supplier on the same request waiting for approval waiting for approval");
        }

        complaint.setRequest(request);
        complaint.setSupplier(supplier);
        complaint.setIsApproved(false);
        supplierComplaintRepository.save(complaint);


    }




    public void updateClientComplaint(Integer id, SupplierComplaint complaint) {
        SupplierComplaint complaint1 = supplierComplaintRepository.findComplaintById(id);

        if (complaint1 == null) throw new ApiException("complaint not found");

        complaint1.setComplaintMessage(complaint.getComplaintMessage());
        supplierComplaintRepository.save(complaint1);
    }




    public void deleteClientComplaint(Integer id) {

        SupplierComplaint complaint = supplierComplaintRepository.findComplaintById(id);

        if (complaint == null) throw new ApiException("complaint not found");
        if (complaint.getIsApproved()) throw new ApiException("complaint is already approved");
        supplierComplaintRepository.deleteById(id);
    }







}
