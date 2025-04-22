package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findRequestById(Integer id);

    @Query("SELECT r FROM Request r WHERE r.request_date BETWEEN :start AND :end")
    List<Request> findRequestsBetweenDates(LocalDate start, LocalDate end);

    List<Request> findRequestsByWareHouseId(Integer id);
}
