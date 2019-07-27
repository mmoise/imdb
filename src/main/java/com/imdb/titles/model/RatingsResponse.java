package com.imdb.titles.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingsResponse {

    @JsonProperty
    private String status = "Ratings have been updated";
}
