package com.imdb.titles.service;


import com.imdb.titles.entity.Episode;
import com.imdb.titles.repository.EpisodeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EpisodeServiceImpl implements EpisodeService{

    private EpisodeRepository repository;

    @Override
    public Episode save(Episode episode) {
        return null;
    }
}