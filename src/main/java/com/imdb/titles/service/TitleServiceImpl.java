package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import com.imdb.titles.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleRepository repository;

    @Override
    public Title save(Title title) {
        return repository.save(title);
    }

    @Override
    public List<Title> saveAll(List<Title> titles) {
        return repository.saveAll(titles);
    }

    @Override
    public List<Title> findAll() {
        return repository.findAll();
    }

    @Override
    public Title findById(String titleId) {
        return repository.getOne(titleId);
    }
}
