package com.imdb.titles.service;


import com.imdb.titles.entity.Episode;

import java.util.List;

public interface EpisodeService {

    List<Episode> saveAll(List<Episode> episodes);
}
