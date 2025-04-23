package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.DTO.StorageProviderDTO;
import com.example.warehouseplatform.DTO.WareHouseDTO;
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

        if(storageProvider == null) throw new ApiException("provider not found");

        if(!storageProvider.getIsActive()) throw new ApiException("Storage provider is not active") ;

        wareHouse.setStorageProvider(storageProvider);
        wareHouseRepository.save(wareHouse);
    }

    public void updateWareHouse(WareHouse wareHouse, Integer id) {

        WareHouse wareHouse1 = wareHouseRepository.findWareHouseById(id);

        if (wareHouse1 == null) {
            throw new ApiException("the wareHouse is not found");
        }
        wareHouse1.setStorageArea(wareHouse.getStorageArea());
        wareHouse1.setStoreSize(wareHouse.getStoreSize());
        wareHouse1.setStoreType(wareHouse.getStoreType());
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




    public WareHouseDTO findMostUsedWareHousesByStoreSize(String storeSize){

    List<WareHouse> wareHousesSameSize= wareHouseRepository.findWareHousesByStoreSize(storeSize);
    if (wareHousesSameSize.isEmpty()){
        throw new ApiException("there are no warehouses in this size , available sizes : small , medium , large");
    }
    WareHouse mostUsed=wareHousesSameSize.get(0);
    for (WareHouse w:wareHousesSameSize ){
        if (w.getUsageCount()>mostUsed.getUsageCount()){
            mostUsed=w;
        }
    }


        StorageProvider sP = mostUsed.getStorageProvider();
        StorageProviderDTO storageProviderDTO = new StorageProviderDTO(sP.getUsername(), sP.getEmail(), sP.getPhoneNumber(), sP.getIsActive());

        return new WareHouseDTO(storageProviderDTO, storeSize, mostUsed.getLocation(), mostUsed.getStorageArea(), mostUsed.getPrice());




    }



}
