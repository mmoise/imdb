package com.imdb.titles.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitiateDatabaseService implements CommandLineRunner {

    @Autowired
    private DataLoadService dataLoadService;

    @Override
    public void run(String... strings) throws Exception {
        dataLoadService.LoadTitles();
    }
}
