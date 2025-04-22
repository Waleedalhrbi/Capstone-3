package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Employee;
import com.example.warehouseplatform.Model.StorageProvider;
import com.example.warehouseplatform.Model.WareHouse;
import com.example.warehouseplatform.Repository.StorageProviderRepository;
import com.example.warehouseplatform.Repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WareHouseService {

private final WareHouseRepository wareHouseRepository;
private final StorageProviderRepository storageProviderRepository;


public List<WareHouse> getAll(){
    return wareHouseRepository.findAll();
}

    public void addWareHouse(WareHouse wareHouse, Integer providerId) {
        StorageProvider storageProvider = storageProviderRepository.findStorageProviderById(providerId);

        if(storageProvider == null) {
            throw new ApiException("provider not found");
        }
        wareHouse.setStorageProvider(storageProvider);
        wareHouseRepository.save(wareHouse);
    }

    public void updateWareHouse(WareHouse wareHouse, Integer id) {

        WareHouse wareHouse1 = wareHouseRepository.findWareHouseById(id);

        if (wareHouse1 == null) {
            throw new ApiException("the wareHouse is not found");
        }
        wareHouse1.setStorageArea(wareHouse.getStorageArea());
        wareHouse1.setStore_size(wareHouse.getStore_size());
        wareHouse1.setStore_type(wareHouse.getStore_type());
        wareHouse1.setPrice(wareHouse.getPrice());
        wareHouse1.setLocation(wareHouse1.getLocation());
        wareHouseRepository.save(wareHouse1);


    }




    public void deleteWareHouse(Integer id){

        WareHouse wareHouse= wareHouseRepository.findWareHouseById(id);

        if (wareHouse==null){
            throw new ApiException("wareHouse is not found");
        }

        wareHouseRepository.delete(wareHouse);


    }








}
