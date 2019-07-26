package com.imdb.titles.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Episode {

    @EmbeddedId
    private EpisodeId id;

    @Column(name="EPISODE_ID")
    private String episodeId;

    @Column(name="RATING")
    private Double rating;

    public EpisodeId getId() {
        return id;
    }

    public void setId(EpisodeId id) {
        this.id = id;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

}
