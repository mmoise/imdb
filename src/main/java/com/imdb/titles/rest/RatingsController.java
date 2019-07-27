package com.imdb.titles.rest;


import com.imdb.titles.service.RatingRandomizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingsController {

    @Autowired
    private RatingRandomizerService randomizer;

    @RequestMapping(value = "/ratings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String generateRatings() {
        randomizer.randomizeRatings();
        String response = "Ratings have been updated";

        return response;
    }
}
