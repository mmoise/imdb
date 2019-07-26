package com.imdb.titles.repository;


import com.imdb.titles.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, String>  {
}
