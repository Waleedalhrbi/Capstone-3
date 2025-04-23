package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.DTO.StorageProviderDTO;
import com.example.warehouseplatform.Model.ProviderComplaint;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Repository.ProviderComplaintRepository;
import com.example.warehouseplatform.Repository.StorageProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageProviderService {

    private final StorageProviderRepository storageProviderRepository;
    private final ProviderComplaintRepository providerComplaintRepository;

    public List<StorageProvider> getAllProviders() {
        return storageProviderRepository.findAll();
    }

    public List<StorageProviderDTO> getAll() {
        List<StorageProvider> providers = storageProviderRepository.findAll();
        List<StorageProviderDTO> providerDTOList = new ArrayList<>();
        for (StorageProvider s : providers) {
            StorageProviderDTO providerDTO = new StorageProviderDTO(s.getUsername(), s.getEmail(), s.getPhoneNumber(), s.getIsActive());
            providerDTOList.add(providerDTO);
        }
        return providerDTOList;
    }


    public void addProvider(StorageProvider storageProvider) {
        storageProvider.setRenewLicenceRequest(false);
        storageProvider.setIsActive(false);
        storageProvider.setLicenseDate(LocalDate.now());
        storageProviderRepository.save(storageProvider);
    }

    public void updateProvider(Integer id, StorageProvider provider) {
        StorageProvider oldProvider = storageProviderRepository.findStorageProviderById(id);

        if (oldProvider == null) throw new ApiException("provider not found");

        oldProvider.setUsername(provider.getUsername());
        oldProvider.setPassword(provider.getPassword());
        oldProvider.setEmail(provider.getEmail());
        oldProvider.setPhoneNumber(provider.getPhoneNumber());
        oldProvider.setIsActive(provider.getIsActive());
        oldProvider.setLicense(provider.getLicense());

        storageProviderRepository.save(provider);
    }

    public void deleteProvider(Integer id) {
        StorageProvider provider = storageProviderRepository.findStorageProviderById(id);
        if (provider == null) throw new ApiException("provider not found");

        storageProviderRepository.deleteById(id);
    }




    public void renewLicenceRequest(Integer providerId){
        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if(provider == null) throw new ApiException("provider not found");

        if (provider.getIsActive()){
            throw new ApiException("provider licence is active");

        }
        provider.setRenewLicenceRequest(true);

    }



    public List<ProviderComplaint> getAllApprovedComplain(Integer id){
        StorageProvider provider = storageProviderRepository.findStorageProviderById(id);
        if(provider == null) throw new ApiException("provider not found");

        return providerComplaintRepository.approvedProviderComplaintMadeByProvider(id);

    }



}
