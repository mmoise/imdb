package com.imdb.titles.rest;


import com.imdb.titles.entity.Title;
import com.imdb.titles.service.PaginatedTitleService;
import com.imdb.titles.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TitleController {

    @Autowired
    private TitleService titleService;

    @Autowired
    private PaginatedTitleService paginatedTitleService;

    /*
    @RequestMapping(value = "/titles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Title> getAllTitles() {

        return titleService.findAll();
    }
    */

    @RequestMapping(value = "/titles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Title> getAllTitles(Pageable pageable) {

        return paginatedTitleService.findAll(pageable);
    }

    @RequestMapping(value = "/titles/{titleId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Title getTitle(@PathVariable String titleId) {
        return titleService.findById(titleId);
    }

}
