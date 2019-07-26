package com.imdb.titles.service;


import com.imdb.titles.entity.Actor;
import com.imdb.titles.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository repository;

    @Override
    public Actor save(Actor actor) {
        return repository.save(actor);
    }
}
