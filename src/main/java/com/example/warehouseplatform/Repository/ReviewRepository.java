package com.example.warehouseplatform.Repository;

import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

    Review findReviewById(Integer id);

    List<Review> findAllByRequestIn(List<Request> requests);

    List<Review> findAllBySupplier_Id(Integer supplierId);


}
