package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.ProviderComplaint;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Repository.ProviderComplaintRepository;
import com.example.warehouseplatform.Repository.RequestRepository;
import com.example.warehouseplatform.Repository.StorageProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderComplaintService {

    private final ProviderComplaintRepository providerComplaintRepository;
    private final RequestRepository requestRepository;
    private final StorageProviderRepository storageProviderRepository;

    public List<ProviderComplaint> getAllComplaint() {
        return providerComplaintRepository.findAll();
    }



    public void fileComplaintOnClient(Integer providerId, Integer requestId, ProviderComplaint complaint) {

        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if (provider == null) throw new ApiException("provider not found");

        Request request=requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found");


        complaint.setRequest(request);
        complaint.setStorageProvider(provider);
        complaint.setIsApproved(false);
        providerComplaintRepository.save(complaint);


    }




    public void updateProviderComplaint(Integer id, ProviderComplaint complaint) {
        ProviderComplaint complaint1 = providerComplaintRepository.findComplaintById(id);

        if (complaint1 == null) throw new ApiException("complaint not found");

        complaint1.setComplaintMessage(complaint.getComplaintMessage());
        providerComplaintRepository.save(complaint1);
    }




    public void deleteProviderComplaint(Integer id) {

        ProviderComplaint complaint = providerComplaintRepository.findComplaintById(id);

        if (complaint == null) throw new ApiException("complaint not found");
        if (complaint.getIsApproved()) throw new ApiException("complaint is already approved");
        providerComplaintRepository.deleteById(id);
    }







}
