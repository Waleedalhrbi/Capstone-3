package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse,Integer> {

    WareHouse findWareHouseById(Integer id);

}
