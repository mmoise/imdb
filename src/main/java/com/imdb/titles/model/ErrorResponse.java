package com.imdb.titles.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonIgnore
    public static final String GENERAL_ERROR_TEXT = "An error has occurred";

    @JsonProperty
    private String status = GENERAL_ERROR_TEXT;

    @JsonProperty
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
