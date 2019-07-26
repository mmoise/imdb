package com.imdb.titles.service;


import com.imdb.titles.entity.Episode;
import com.imdb.titles.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EpisodeServiceImpl implements EpisodeService{

    @Autowired
    private EpisodeRepository repository;

    @Override
    public Episode save(Episode episode) {
        return repository.save(episode);
    }

    @Override
    public List<Episode> saveAll(List<Episode> episodes) {
        return repository.saveAll(episodes);
    }
}
