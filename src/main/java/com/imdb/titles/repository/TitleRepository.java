package com.imdb.titles.repository;


import com.imdb.titles.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, String> {
}
