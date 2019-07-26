package com.imdb.titles.repository;


import com.imdb.titles.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, String> {
}
