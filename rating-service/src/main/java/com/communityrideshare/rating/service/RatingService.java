package com.communityrideshare.rating.service;

import com.communityrideshare.rating.domain.Rating;
import com.communityrideshare.rating.repository.RatingRepository;
import com.communityrideshare.rating.web.dtos.RatingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    @Transactional
    public Rating createRating(RatingRequest request) {
        Rating rating = new Rating();
        rating.setRideId(request.getRideId());
        rating.setRatedBy(request.getRatedBy());
        rating.setRatedUser(request.getRatedUser());
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        return ratingRepository.save(rating);
    }
}
