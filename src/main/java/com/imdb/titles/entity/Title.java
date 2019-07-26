package com.imdb.titles.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Title {

    @Id
    private String id;

    @Column(name="TITLE_TYPE")
    private String titleType;

    @Column(name="PRIMARY_TITLE")
    private String primaryTitle;

    @Column(name="ORIGINAL_TITLE")
    private String originalTitle;

    @Column(name="IS_ADULT")
    private boolean isAdult;

    @Column(name="START_YEAR")
    private Integer startYear;

    @Column(name="END_YEAR")
    private Integer endYear;

    @Column(name="RUNTIME_MINUTES")
    private Integer runTimeMinutes;

    @OneToOne
    private Rating rating;

    @OneToMany
    @JoinColumn(name="TITLE_ID")
    private List<Episode> episodes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name= "Cast", joinColumns = @JoinColumn(name = "TITLE_ID"), inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    private List<Actor> cast = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getPrimaryTitle() {
        return primaryTitle;
    }

    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getRunTimeMinutes() {
        return runTimeMinutes;
    }

    public void setRunTimeMinutes(Integer runTimeMinutes) {
        this.runTimeMinutes = runTimeMinutes;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}
