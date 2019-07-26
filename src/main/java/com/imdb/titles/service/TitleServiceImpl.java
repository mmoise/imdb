package com.imdb.titles.service;


import com.imdb.titles.entity.Title;
import com.imdb.titles.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TitleServiceImpl implements TitleService {

    @Autowired
    private TitleRepository repository;

    @Override
    public Title save(Title title) {
        return repository.save(title);
    }
}
