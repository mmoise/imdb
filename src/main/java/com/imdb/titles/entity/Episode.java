package com.imdb.titles.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Episode {

    @EmbeddedId
    private EpisodeId id;

    @Column(name="EPISODE_ID")
    private String episodeId;

    @OneToOne
    private Rating rating;

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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
