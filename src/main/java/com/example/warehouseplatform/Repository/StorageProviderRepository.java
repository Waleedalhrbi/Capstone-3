package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.StorageProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageProviderRepository extends JpaRepository<StorageProvider, Integer> {

    StorageProvider findStorageProviderById(Integer id);
}
