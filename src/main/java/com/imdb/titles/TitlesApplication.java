package com.imdb.titles;

import com.imdb.titles.entity.Actor;
import com.imdb.titles.entity.Rating;
import com.imdb.titles.entity.Title;
import com.imdb.titles.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@SpringBootApplication
public class TitlesApplication {

	@Autowired
	private TitleService titleService;

	@RequestMapping("/")
	Title test() {
		Actor actor = new Actor();
		actor.setActorId("actor1");
		actor.setPrimaryName("Tom Cruise");

		Rating rating = new Rating();
		rating.setTitleId("title1");
		rating.setAverageRating(5.5);
		rating.setNumVotes(100);

		Title title = new Title();
		title.setId("title1");
		title.setTitleType("movie");
		title.setRating(rating);
		title.setCast(Collections.singletonList(actor));

		titleService.save(title);

		return title;
	}

	public static void main(String[] args) {
		SpringApplication.run(TitlesApplication.class, args);

	}

}
