package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import com.imdb.titles.repository.PaginatedTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginatedTitleServiceImpl implements PaginatedTitleService {

    @Autowired
    private PaginatedTitleRepository repository;

    @Override
    public Page<Title> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
