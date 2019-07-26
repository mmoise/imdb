package com.imdb.titles.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Rating {

    @Id
    @Column(name = "TITLE_ID")
    private String titleId;

    @Column(name = "AVERAGE_RATING")
    private Double averageRating;

    @Column(name = "NUM_VOTES")
    private Integer numVotes;

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(Integer numVotes) {
        this.numVotes = numVotes;
    }
}
