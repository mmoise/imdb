package com.imdb.titles;

import com.imdb.titles.entity.Actor;
import com.imdb.titles.entity.Episode;
import com.imdb.titles.entity.EpisodeId;
import com.imdb.titles.entity.Title;
import com.imdb.titles.service.ActorService;
import com.imdb.titles.service.EpisodeService;
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

	@Autowired
	private ActorService actorService;

	@Autowired
	private EpisodeService episodeService;

	@RequestMapping(value = "/titles")
	Title test() {

		Actor actor = new Actor();
		actor.setActorId("actor1");
		actor.setPrimaryName("Tom Cruise");
		actorService.save(actor);

		EpisodeId episodeId = new EpisodeId();
		episodeId.setTitleId("title1");
		episodeId.setEpisodeNumber(2);
		episodeId.setSeasonNumber(1);

		Episode episode = new Episode();
		episode.setEpisodeId("episode1");
		episode.setRating(3.5);
		episode.setId(episodeId);
		episodeService.save(episode);

		Title title = new Title();
		title.setId("title1");
		title.setTitleType("tvSeries");
		title.setRating(9.5);
		title.setCast(Collections.singletonList(actor));
		title.setEpisodes(Collections.singletonList(episode));

		title = titleService.save(title);

		return title;
	}

	public static void main(String[] args) {
		SpringApplication.run(TitlesApplication.class, args);

	}

}
