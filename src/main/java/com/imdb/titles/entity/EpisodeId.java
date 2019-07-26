package com.imdb.titles.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EpisodeId implements Serializable {

    @Column(name="SEASON_NUMBER")
    private int seasonNumber;

    @Column(name="EPISODE_NUMBER")
    private int episodeNumber;

    @Column(name="TITLE_ID")
    private String titleId;


    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeId episodeId = (EpisodeId) o;

        if (getSeasonNumber() != episodeId.getSeasonNumber()) return false;
        if (getEpisodeNumber() != episodeId.getEpisodeNumber()) return false;
        return getTitleId().equals(episodeId.getTitleId());

    }

    @Override
    public int hashCode() {
        int result = getSeasonNumber();
        result = 31 * result + getEpisodeNumber();
        result = 31 * result + getTitleId().hashCode();
        return result;
    }
}
