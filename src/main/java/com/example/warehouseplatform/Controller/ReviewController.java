package com.example.warehouseplatform.Controller;

import com.example.warehouseplatform.Api.ApiResponse;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.Review;
import com.example.warehouseplatform.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/get")
    public ResponseEntity getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity addReview(@Valid @RequestBody Review review) {
        reviewService.addReview(review);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id, @Valid @RequestBody Review review) {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Review deleted successfully"));
    }


    // Ex endpoint
    @GetMapping("/get-reviews-by-warehouse/{wareHouseId}")
    public ResponseEntity getReviewsByWareHouseId(@PathVariable Integer wareHouseId) {
        List<Review> reviews = reviewService.getReviewsByWareHouseId(wareHouseId);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    // Ex endpoint
    @GetMapping("/average-rating/{wareHouseId}")
    public ResponseEntity getAverageRatingForWarehouse(@PathVariable Integer wareHouseId) {
        return ResponseEntity.status(HttpStatus.OK).body((reviewService.calculateAverageRatingForWarehouse(wareHouseId)));
    }

    // Ex endpoint
    @GetMapping("/top-warehouses-by-rating")
    public ResponseEntity getTopWarehousesByRating() {
        List<Request> topWarehouses = reviewService.getTopWarehousesByAverageRating();
        return ResponseEntity.status(HttpStatus.OK).body(topWarehouses);
    }

    // Ex endpoint
    @GetMapping("/get-reviews-by-client/{clientId}")
    public ResponseEntity getReviewsByClientId(@PathVariable Integer clientId) {
        List<Review> reviews = reviewService.getReviewsByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    // Ex endpoint
    @GetMapping("/get-reviews-by-warehouse-sorted/{wareHouseId}")
    public ResponseEntity getReviewsByWarehouseIdSortedByDate(@PathVariable Integer wareHouseId) {
        List<Review> reviews = reviewService.getReviewsByWarehouseIdSortedByDate(wareHouseId);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }
}
