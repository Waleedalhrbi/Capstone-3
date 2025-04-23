package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse,Integer> {

    WareHouse findWareHouseById(Integer id);

    @Query("select w from WareHouse w where w.storeSize=?1")
    List<WareHouse>findWareHousesByStoreSize(String storeSize);

    @Query("select w from WareHouse w where w.storageProvider.id=?1")
    List<WareHouse> getWareHouseByStorageProvider_Id(Integer id);

}
