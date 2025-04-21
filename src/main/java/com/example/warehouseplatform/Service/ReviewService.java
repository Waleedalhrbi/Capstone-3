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
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClientService clientService;
    private final RequestService requestService;

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
}