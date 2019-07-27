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

    private static final Logger LOGGER = LoggerFactory.getLogger(InitiateDatabaseService.class);

    @Override
    /**
     * This method will load titles, ratings, episodes, actors, and cast data upon start up of the application. The data
     * is stored local to the application
     */
    public void run(String... strings) throws Exception {

        LOGGER.info("Loading files");
        Long startTime = System.currentTimeMillis();

        dataLoadService.LoadRatings("src/main/resources/imdb/filtered/2018ratings.tsv");
        dataLoadService.LoadTitles("src/main/resources/imdb/filtered/2018titles.tsv");
        dataLoadService.LoadEpisodes("src/main/resources/imdb/filtered/2018episodes.tsv");
        dataLoadService.LoadActors("src/main/resources/imdb/filtered/2018actors.tsv");
        dataLoadService.LoadCast("src/main/resources/imdb/filtered/2018cast.tsv");

        Long duration = System.currentTimeMillis() - startTime;
        LOGGER.info("It took " + duration + " milliseconds to load all files");
    }
}
