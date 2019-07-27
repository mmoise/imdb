package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingRandomizerService {

    @Autowired
    private PaginatedTitleService titleService;

    /**
     * This method generates a random and saves it as a rating for all Titles and their correspondong episodes
     */
    public void randomizeRatings() {

        // Retrieve all titles
        List<Title> titleList = titleService.findAll();
        for (Title title: titleList) {
            // Update the movie/series level rating
            title.setRating(getRandomNumber());
            if (!title.getEpisodes().isEmpty()) {
                // Update the rating for each episode
                title.getEpisodes().stream()
                        .forEach(episode -> episode.setRating(getRandomNumber()));
            }
        }
        // save the changes
        titleService.saveAll(titleList);
    }

    /**
     * This method generates a random number between 1-10 with the precision of a single decimal place
     */
    public Double getRandomNumber() {

        double randomNum = (Math.random()*((10-1)+1))+1;
        double scale = Math.pow(10, 1);
        return Math.round(randomNum * scale) / scale;
    }
}
