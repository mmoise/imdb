package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class DataLoadServiceImpl implements DataLoadService {

    @Autowired
    private TitleService titleService;

    @Override
    public void LoadTitles() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018titles.tsv"));
        String row;

        while ((row = tsvReader.readLine()) != null) {

            String[] data = row.split("\t");
            Title title = new Title();
            if (!data[0].equals("\\N"))
                title.setId(data[0]);
            if (!data[1].equals("\\N"))
                title.setTitleType(data[1]);
            if (!data[2].equals("\\N"))
                title.setPrimaryTitle(data[2]);
            if (!data[3].equals("\\N"))
                title.setOriginalTitle(data[3]);
            if (!data[4].equals("\\N")) {
                boolean isAdult = Integer.parseInt(data[4]) == 0 ? false : true;
                title.setIsAdult(isAdult);
            }
            if (!data[5].equals("\\N"))
                title.setStartYear(Integer.parseInt(data[5]));
            if (!data[6].equals("\\N"))
                title.setEndYear(Integer.parseInt(data[6]));
            if (!data[7].equals("\\N"))
                title.setRunTimeMinutes(Integer.parseInt(data[7]));

            titleService.save(title);
        }
        tsvReader.close();
    }

    @Override
    public void LoadEpisodes() {

    }

    @Override
    public void LoadRatings() {

    }

    @Override
    public void LoadCast() {

    }

    @Override
    public void LoadActors() {

    }
}
