package com.imdb.titles.rest;


import com.imdb.titles.model.RatingsResponse;
import com.imdb.titles.service.RatingRandomizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingsController {

    @Autowired
    private RatingRandomizerService randomizer;

    @RequestMapping(value = "/ratings",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingsResponse> generateRatings() {
        randomizer.randomizeRatings();

        return new ResponseEntity<>(new RatingsResponse(), HttpStatus.OK);
    }
}
