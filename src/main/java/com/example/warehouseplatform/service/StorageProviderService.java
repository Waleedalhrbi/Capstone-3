package com.example.warehouseplatform.service;

import com.example.warehouseplatform.DTO.EmployeeDTO;
import com.example.warehouseplatform.DTO.StorageProviderDTO;
import com.example.warehouseplatform.Model.Employee;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.api.ApiException;
import com.example.warehouseplatform.repository.StorageProviderRepository;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageProviderService {

    private final StorageProviderRepository storageProviderRepository;

    public List<StorageProvider> getAllProviders() {
        return storageProviderRepository.findAll();
    }

    public List<StorageProviderDTO> getAll() {
        List<StorageProvider> providers = storageProviderRepository.findAll();
        List<StorageProviderDTO> providerDTOList = new ArrayList<>();
        for(StorageProvider s : providers) {
            StorageProviderDTO providerDTO = new StorageProviderDTO(s.getUsername(), s.getEmail(), s.getPhoneNumber(), s.getIsActive());
            providerDTOList.add(providerDTO);
        }
        return providerDTOList;
    }


    public void addProvider(StorageProvider storageProvider) {
       storageProviderRepository.save(storageProvider);
    }

    public void updateProvider(Integer id, StorageProvider provider) {
        StorageProvider oldProvider = storageProviderRepository.findStorageProviderById(id);

        if(oldProvider == null) throw new ApiException("provider not found");

        oldProvider.setUsername(provider.getUsername());
        oldProvider.setPassword(provider.getPassword());
        oldProvider.setEmail(provider.getEmail());
        oldProvider.setPhoneNumber(provider.getPhoneNumber());
        oldProvider.setIsActive(provider.getIsActive());
        oldProvider.setPropertyLicence(provider.getPropertyLicence());

        storageProviderRepository.save(provider);
    }

    public void deleteProvider(Integer id) {
        StorageProvider provider = storageProviderRepository.findStorageProviderById(id);
        if(provider == null) throw new ApiException("provider not found");

        storageProviderRepository.deleteById(id);
    }
}
