package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import com.imdb.titles.repository.PaginatedTitleRepository;
import com.imdb.titles.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginatedTitleServiceImpl implements PaginatedTitleService {

    @Autowired
    private PaginatedTitleRepository paginatedRepository;

    @Autowired
    private TitleRepository repository;

    @Override
    public Page<Title> findAll(Pageable pageable) {
        return paginatedRepository.findAll(pageable);
    }

    @Override
    public List<Title> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Title> saveAll(List<Title> titles) {
        return repository.saveAll(titles);
    }

    @Override
    public Title findById(String titleId) {
        return repository.getOne(titleId);
    }
}
