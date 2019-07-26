package com.imdb.titles.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitiateDatabaseService implements CommandLineRunner {

    @Autowired
    private DataLoadService dataLoadService;

    private Logger logger = LoggerFactory.getLogger(InitiateDatabaseService.class);

    @Override
    public void run(String... strings) throws Exception {

        logger.info("Loading files");
        Long startTime = System.currentTimeMillis();
        dataLoadService.LoadRatings();
        dataLoadService.LoadTitles();
        dataLoadService.LoadEpisodes();
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load all files");
    }
}
