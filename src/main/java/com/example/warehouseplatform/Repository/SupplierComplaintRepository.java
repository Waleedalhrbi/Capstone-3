package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.SupplierComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierComplaintRepository extends JpaRepository<SupplierComplaint, Integer> {

    SupplierComplaint findComplaintById(Integer id);

    List<SupplierComplaint> findSupplierComplaintBySupplier_Id(Integer supplierId);

    SupplierComplaint findSupplierComplaintBySupplier_IdAndRequest_IdAndIsApproved(Integer supplierId, Integer requestId, Boolean isApproved);

    @Query("select c from SupplierComplaint c where c.supplier.id=?1 And c.isApproved=true")
    List<SupplierComplaint> approvedSupplierComplaintMadeBySupplier(Integer id);


    @Query("select c from SupplierComplaint c where c.supplier.id=?1 And c.id=?2")
    SupplierComplaint getComplainBySupplierIdAndComplainId(Integer supplierId, Integer complaintId);
}
