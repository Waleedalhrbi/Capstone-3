package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.*;
import com.example.warehouseplatform.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SupplierRepository supplierRepository;
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final StorageProviderRepository storageProviderRepository;
    private final WareHouseRepository wareHouseRepository;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Integer supplierId, Integer requestId, Review review) {

        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }


        Request request = requestService.getRequestById(requestId);
        if (request == null) {
            throw new ApiException("Request not found");
        }


        if (!request.getSupplier().getId().equals(supplierId)) {
            throw new ApiException("Supplier did not create this request");
        }


        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ApiException("Rating must be between 1 and 5");
        }


        if (LocalDate.now().isBefore(request.getStart_date())) {
            throw new ApiException("Cannot add a review before the rental period starts");
        }

        review.setReview_date(LocalDate.now());
        review.setSupplier(supplier);
        review.setRequest(request);


        reviewRepository.save(review);
    }


    public void updateReview(Integer id, Review review) {
        Review existingReview = reviewRepository.findReviewById(id);
        if (existingReview == null) {
            throw new ApiException("Review not found");
        }

        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setReview_date(review.getReview_date());

        reviewRepository.save(existingReview);
    }

    public void deleteReview(Integer id) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new ApiException("Review not found");
        }

        reviewRepository.delete(review);
    }

    public List<Review> getReviewsByWareHouseId(Integer wareHouseId) {

        List<Request> requests = requestRepository.findRequestsByWareHouseId(wareHouseId);
        if (requests.isEmpty()) {
            throw new ApiException("No requests found for this warehouse");
        }


        List<Review> reviews = reviewRepository.findAllByRequestIn(requests);

        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this warehouse");
        }

        return reviews;
    }


    public Double calculateAverageRatingForWarehouse(Integer wareHouseId) {

        List<Review> reviews = getReviewsByWareHouseId(wareHouseId);


        if (reviews.isEmpty()) {
            throw new ApiException("No reviews available for this warehouse to calculate the average.");
        }


        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }


        return totalRating / reviews.size();
    }


    public List<Request> getTopWarehousesByAverageRating() {

        List<Request> allRequests = requestRepository.findAll();
        Map<Integer, Double> warehouseRatings = new HashMap<>();


        for (Request request : allRequests) {
            Integer warehouseId = request.getWareHouse().getId();
            double averageRating = calculateAverageRatingForWarehouse(warehouseId);
            warehouseRatings.put(warehouseId, averageRating);
        }


        List<Request> sortedRequests = new ArrayList<>(allRequests);
        for (int i = 0; i < sortedRequests.size() - 1; i++) {
            for (int j = i + 1; j < sortedRequests.size(); j++) {
                Request request1 = sortedRequests.get(i);
                Request request2 = sortedRequests.get(j);

                double rating1 = warehouseRatings.get(request1.getWareHouse().getId());
                double rating2 = warehouseRatings.get(request2.getWareHouse().getId());


                if (rating1 < rating2) {

                    sortedRequests.set(i, request2);
                    sortedRequests.set(j, request1);
                }
            }
        }

        return sortedRequests;
    }

    public List<Review> getReviewsBySupplierId(Integer supplierId) {
        List<Review> reviews = reviewRepository.findAllBySupplier_Id(supplierId);

        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this supplier");
        }

        return reviews;
    }

    public List<Review> getReviewsByWarehouseIdSortedByDate(Integer wareHouseId) {

        List<Request> requests = requestRepository.findRequestsByWareHouseId(wareHouseId);

        if (requests.isEmpty()) {
            throw new ApiException("No requests found for this warehouse");
        }

         List<Review> reviews = reviewRepository.findAllByRequestIn(requests);

        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this warehouse");
        }


        reviews.sort((r1, r2) -> r2.getReview_date().compareTo(r1.getReview_date()));

        return reviews;
    }


    public Double providerAverageReview(Integer providerId){
        StorageProvider provider = storageProviderRepository.findStorageProviderById(providerId);
        if(provider == null){
            throw new ApiException("provider not found");
        }
        Double warehousesReviews=0.0;
        List<WareHouse> providerWareHouse = wareHouseRepository.getWareHouseByStorageProvider_Id(providerId);

        if (providerWareHouse.isEmpty()){
            throw new ApiException("provider does not have any warehouses added in the system ");

        }

        for (WareHouse w:providerWareHouse){
            warehousesReviews=warehousesReviews+calculateAverageRatingForWarehouse(w.getId());
        }

        if (warehousesReviews==0){
            throw new ApiException("provider warehouses average equal to 0 ");
        }

        return warehousesReviews/providerWareHouse.size();
    }







}