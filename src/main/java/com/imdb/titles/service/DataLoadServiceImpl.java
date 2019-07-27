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
    private PaginatedTitleService titleService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private ActorService actorService;

    private Map<String, Double> ratingsMap;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(DataLoadServiceImpl.class);

    @Override
    public void LoadTitles(String pathToFile) throws IOException {
        // Read the file containing the title information
        BufferedReader tsvReader = new BufferedReader(new FileReader(pathToFile));
        String row;
        List<Title> titles = new ArrayList<>();
        logger.info("Loading Titles");
        Long startTime = System.currentTimeMillis();
        // Iterate through each line of the file
        while ((row = tsvReader.readLine()) != null) {
            // split each line of the tab delimited file to a string array
            String[] data = row.split("\t");
            Title title = new Title();
            // create a title object based on the column placement of the values in the input file
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

            // use the map containing the ratings data to retrieve the rating for this specific title
            title.setRating(ratingsMap.get(title.getId()));
            titles.add(title);
        }
        // save all of the titles
        titleService.saveAll(titles);
        Long duration = System.currentTimeMillis() - startTime;
        tsvReader.close();
        logger.info("It took " + duration + " milliseconds to load titles");
    }

    @Override
    public void LoadEpisodes(String pathToFile) throws IOException {
        // Read the file containing the episode information
        BufferedReader tsvReader = new BufferedReader(new FileReader(pathToFile));
        String row;
        List<Episode> episodes = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        logger.info("loading episodes");
        // Iterate through each line of the file
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            Episode episode = new Episode();
            EpisodeId id = new EpisodeId();
            // create an episode object based on the column placement of the values in the input file
            if (!data[0].equals("\\N"))
                episode.setEpisodeId(data[0]);
            if (!data[1].equals("\\N"))
                id.setTitleId(data[1]);
            if (!data[2].equals("\\N"))
                id.setSeasonNumber(Integer.parseInt(data[2]));
            if (!data[3].equals("\\N"))
                id.setEpisodeNumber(Integer.parseInt(data[3]));

            episode.setId(id);
            // use the map containing the ratings data to retrieve the rating for this specific episode
            Double rating = ratingsMap.get(episode.getEpisodeId());
            episode.setRating(rating);
            episodes.add(episode);
        }

        tsvReader.close();
        // save all of the episodes
        episodeService.saveAll(episodes);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load episodes");
    }

    @Override
    public void LoadRatings(String pathToFile) throws IOException {
        // Read the file containing the ratings information
        BufferedReader tsvReader = new BufferedReader(new FileReader(pathToFile));
        String row;
        ratingsMap = new HashMap<>();
        logger.info("Loading Ratings");
        Long startTime = System.currentTimeMillis();
        // Iterate through each line of the file
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            // create a map of titleId to rating. This map will be used later in order to populate ratings on titles and episodes
            // without needing an additional call to the database
            if (!data[0].equals("\\N") && !data[1].equals("\\N")) {
                ratingsMap.put(data[0], Double.parseDouble(data[1]));
            }
        }
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load ratings");
    }

    public void LoadCast(String pathToFile) throws IOException {
        // Read the file containing the cast information
        BufferedReader tsvReader = new BufferedReader(new FileReader(pathToFile));
        String row;
        Map<String, List<String>> castMap = new HashMap<>();
        Long startTime = System.currentTimeMillis();
        logger.info("Loading Cast");
        // Iterate through each line of the file
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            String titleId = data[0];
            String actorId = data[2];

            if (!titleId.equals("\\N") && !actorId.equals("\\N")) {
                // create a map of titleIds to actorIds
                if (castMap.containsKey(titleId)) {
                    castMap.get(titleId).add(actorId);
                }
                else {
                    castMap.put(titleId, new ArrayList<>(Arrays.asList(actorId)));
                }
            }
        }
        // retrieve all actors and titles
        List<Actor> actors = actorService.findAll();
        List<Title> titleList = titleService.findAll();

        // create a map of ActorIds to Actors. This will be used in order to avoid making another DB call
        Map<String, Actor> actorMap = new HashMap<>();
        for (Actor actor: actors) {
            actorMap.put(actor.getActorId(), actor);
        }

        // iterate through the list of titles and use the cast map to get a list of all of the associated actors with that title.
        // Use the list of actorIds and the actorMap to get the actor objects and add it to the cast list on the title
        for (Title title: titleList) {
            List<String> mappedActors = castMap.get(title.getId());
            if (mappedActors != null && !mappedActors.isEmpty()) {
                for (String actorId : mappedActors) {
                    Actor actor = actorMap.get(actorId);
                    title.getCast().add(actor);
                }
            }
        }
        // save all of the titles
        titleService.saveAll(titleList);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load cast");
    }

    @Override
    public void LoadActors(String pathToFile) throws IOException {
        // Read the file containing the actor information
        BufferedReader tsvReader = new BufferedReader(new FileReader(pathToFile));
        String row;
        List<Actor> actors = new ArrayList<>();
        Long startTime = System.currentTimeMillis();
        logger.info("loading actors");
        // Iterate through each line of the file
        while ((row = tsvReader.readLine()) != null) {
            String[] data = row.split("\t");
            Actor actor = new Actor();
            // create an actor object based on the column placement of the values in the input file
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
        // save all of the actors
        actorService.saveAll(actors);
        Long duration = System.currentTimeMillis() - startTime;
        logger.info("It took " + duration + " milliseconds to load actors");
    }
}
