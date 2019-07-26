package com.imdb.titles;

import com.imdb.titles.entity.Actor;
import com.imdb.titles.entity.Title;
import com.imdb.titles.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
@RestController
public class TitlesApplication {

	@Autowired
	private TitleService titleService;

	@RequestMapping(value = "/titles")
	Title test() {
		Actor actor = new Actor();
		actor.setActorId("actor1");
		actor.setPrimaryName("Tom Cruise");

		Title title = new Title();
		title.setId("title1");
		title.setTitleType("movie");
		title.setRating(9.5);
		title.setCast(Collections.singletonList(actor));

		titleService.save(title);

		return title;
	}

	public static void main(String[] args) {
		SpringApplication.run(TitlesApplication.class, args);

	}

}
