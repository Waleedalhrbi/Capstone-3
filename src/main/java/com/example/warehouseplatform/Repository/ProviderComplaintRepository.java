package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.ProviderComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderComplaintRepository extends JpaRepository<ProviderComplaint, Integer> {

    ProviderComplaint findComplaintById(Integer id);

    List<ProviderComplaint> findProviderComplaintByStorageProvider_Id(Integer storageProviderId);


    @Query("select c from ProviderComplaint c where c.storageProvider.id=?1 And c.isApproved=true")
    List<ProviderComplaint> approvedProviderComplaintMadeByProvider(Integer id);

    @Query("select c from ProviderComplaint c where c.id =?1 And c.storageProvider.id=?2")
    ProviderComplaint getComplainBYComplainIdAndProviderId(Integer complaintId,Integer providerId);


}
