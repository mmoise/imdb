package com.imdb.titles.service;


import com.imdb.titles.entity.Actor;
import com.imdb.titles.entity.Episode;
import com.imdb.titles.entity.EpisodeId;
import com.imdb.titles.entity.Title;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class DataLoadServiceImpl implements DataLoadService {

    @Autowired
    private TitleService titleService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private ActorService actorService;

    private Map<String, Double> ratingsMap;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(DataLoadServiceImpl.class);

    @Override
    public void LoadTitles() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018titles.tsv"));
        String row;
        List<Title> titles = new ArrayList<>();
        logger.info("Loading Titles");
        Long startTime = System.currentTimeMillis();
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

            title.setRating(ratingsMap.get(title.getId()));
            titles.add(title);
        }
        titleService.saveAll(titles);
        Long duration = System.currentTimeMillis() - startTime;
        tsvReader.close();
        logger.info("It took " + duration + " milliseconds to load titles");
    }

    @Override
    public void LoadEpisodes() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018episodes.tsv"));
        String row;

        List<Episode> episodes = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        logger.info("loading episodes");
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            Episode episode = new Episode();
            EpisodeId id = new EpisodeId();
            if (!data[0].equals("\\N"))
                episode.setEpisodeId(data[0]);
            if (!data[1].equals("\\N"))
                id.setTitleId(data[1]);
            if (!data[2].equals("\\N"))
                id.setSeasonNumber(Integer.parseInt(data[2]));
            if (!data[3].equals("\\N"))
                id.setEpisodeNumber(Integer.parseInt(data[3]));

            episode.setId(id);
            Double rating = ratingsMap.get(episode.getEpisodeId());
            episode.setRating(rating);
            episodes.add(episode);
        }

        tsvReader.close();
        episodeService.saveAll(episodes);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load episodes");
    }

    @Override
    public void LoadRatings() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018ratings.tsv"));
        String row;
        ratingsMap = new HashMap<>();
        logger.info("Loading Ratings");
        Long startTime = System.currentTimeMillis();
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            if (!data[0].equals("\\N") && !data[1].equals("\\N")) {
                ratingsMap.put(data[0], Double.parseDouble(data[1]));
            }
        }
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load ratings");
    }

    public void LoadCast() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018cast.tsv"));
        String row;
        Map<String, List<String>> castMap = new HashMap<>();
        Long startTime = System.currentTimeMillis();
        logger.info("Loading Cast");
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            String titleId = data[0];
            String actorId = data[2];

            if (!titleId.equals("\\N") && !actorId.equals("\\N")) {
                if (castMap.containsKey(titleId)) {
                    castMap.get(titleId).add(actorId);
                }
                else {
                    castMap.put(titleId, new ArrayList<>(Arrays.asList(actorId)));
                }
            }
        }
        List<Actor> actors = actorService.findAll();
        List<Title> titleList = titleService.findAll();

        Map<String, Actor> actorMap = new HashMap<>();
        for (Actor actor: actors) {
            actorMap.put(actor.getActorId(), actor);
        }

        for (Title title: titleList) {
            List<String> mappedActors = castMap.get(title.getId());
            if (mappedActors != null && !mappedActors.isEmpty()) {
                for (String actorId : mappedActors) {
                    Actor actor = actorMap.get(actorId);
                    title.getCast().add(actor);
                }
            }
        }
        titleService.saveAll(titleList);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load cast");
    }

    @Override
    public void LoadActors() throws IOException {
        BufferedReader tsvReader = new BufferedReader(new FileReader("src/main/resources/imdb/filtered/2018actors.tsv"));
        String row;
        List<Actor> actors = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        logger.info("loading actors");
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            Actor actor = new Actor();
            if (!data[0].equals("\\N"))
                actor.setActorId(data[0]);
            if (!data[1].equals("\\N"))
                actor.setPrimaryName(data[1]);
            if (!data[2].equals("\\N"))
                actor.setBirthYear(Integer.parseInt(data[2]));
            if (!data[3].equals("\\N"))
                actor.setDeathYear(Integer.parseInt(data[3]));

            actors.add(actor);
        }
        actorService.saveAll(actors);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load actors");
    }
}
