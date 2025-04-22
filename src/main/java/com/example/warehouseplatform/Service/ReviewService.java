package com.example.warehouseplatform.Service;

import com.example.warehouseplatform.Api.ApiException;
import com.example.warehouseplatform.Model.Client;
import com.example.warehouseplatform.Model.Request;
import com.example.warehouseplatform.Model.Review;
import com.example.warehouseplatform.Repository.ClientRepository;
import com.example.warehouseplatform.Repository.RequestRepository;
import com.example.warehouseplatform.Repository.ReviewRepository;
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
    private final ClientService clientService;
    private final RequestService requestService;
    private final RequestRepository requestRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void addReview(Review review) {
        Client client = clientService.getClientById(review.getClient().getId());
        if (client == null) {
            throw new ApiException("Client not found");
        }

        Request request = requestService.getRequestById(review.getRequest().getId());
        if (request == null) {
            throw new ApiException("Request not found");
        }

        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ApiException("Rating must be between 1 and 5");
        }

        review.setReview_date(LocalDate.now());
        review.setClient(client);
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

    public List<Review> getReviewsByClientId(Integer clientId) {
        List<Review> reviews = reviewRepository.findAllByClientId(clientId);

        if (reviews.isEmpty()) {
            throw new ApiException("No reviews found for this client");
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
}