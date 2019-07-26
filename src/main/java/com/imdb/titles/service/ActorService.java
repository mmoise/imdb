package com.imdb.titles.service;


import com.imdb.titles.entity.Actor;

import java.util.List;

public interface ActorService {

    Actor save(Actor actor);

    List<Actor> saveAll(List<Actor> actors);

    List<Actor> findAll();
}
