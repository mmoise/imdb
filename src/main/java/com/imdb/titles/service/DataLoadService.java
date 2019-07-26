package com.imdb.titles.service;


import java.io.IOException;

public interface DataLoadService {

    void LoadTitles() throws IOException;

    void LoadEpisodes() throws IOException;

    void LoadRatings() throws IOException;

    void LoadCast() throws IOException;

    void LoadActors() throws IOException;
}
