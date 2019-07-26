package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaginatedTitleService {

    Page<Title> findAll(Pageable pageable);
}
