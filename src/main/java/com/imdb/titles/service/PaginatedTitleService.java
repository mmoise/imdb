package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaginatedTitleService {

    Page<Title> findAll(Pageable pageable);

    List<Title> findAll();

    List<Title> saveAll(List<Title> titles);

    Title findById(String titleId);
}
