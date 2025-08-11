package com.communityrideshare.rating.web;

import com.communityrideshare.rating.domain.Rating;
import com.communityrideshare.rating.service.RatingService;
import com.communityrideshare.rating.web.dtos.RatingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@Valid @RequestBody RatingRequest request) {
        Rating rating = ratingService.createRating(request);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }
}
