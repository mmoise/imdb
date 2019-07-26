package com.imdb.titles.service;


import java.io.IOException;

public interface DataLoadService {

    void LoadTitles(String pathToFile) throws IOException;

    void LoadEpisodes(String pathToFile) throws IOException;

    void LoadRatings(String pathToFile) throws IOException;

    void LoadCast(String pathToFile) throws IOException;

    void LoadActors(String pathToFile) throws IOException;
}
