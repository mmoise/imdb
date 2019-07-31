package com.imdb.titles.rest;


import com.imdb.titles.entity.Title;
import com.imdb.titles.service.PaginatedTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Retrieve 2018 Titles", description = "Returns details related to titles created in 2018")
public class TitleController {


    @Autowired
    private PaginatedTitleService paginatedTitleService;

    @RequestMapping(value = "/titles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N).", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "20")})
    public ResponseEntity<Page<Title>> getAllTitles(Pageable pageable) {

        Page<Title> titles = paginatedTitleService.findAll(pageable);
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    @RequestMapping(value = "/titles/{titleId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Title> getTitle(@ApiParam(name = "titleId", value = "Alphanumeric Unique Identifier of a Title", required = true) @PathVariable String titleId) {
        Title tite = paginatedTitleService.findById(titleId);
        return new ResponseEntity<>(tite, HttpStatus.OK);
    }

}
