package com.imdb.titles.service;


import com.imdb.titles.entity.Title;

import java.util.List;

public interface TitleService {

    Title save(Title title);

    List<Title> saveAll(List<Title> titles);
}
