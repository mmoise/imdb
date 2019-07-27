package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingRandomizerService {

    @Autowired
    private PaginatedTitleService titleService;

    public void randomizeRatings() {

        List<Title> titleList = titleService.findAll();
        for (Title title: titleList) {
            title.setRating(getRandomNumber());
            if (!title.getEpisodes().isEmpty()) {
                title.getEpisodes().stream()
                        .forEach(episode -> episode.setRating(getRandomNumber()));
            }
        }
        titleService.saveAll(titleList);
    }

    public Double getRandomNumber() {
        return (Math.random()*((10-1)+1))+1;
    }
}
