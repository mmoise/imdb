package com.imdb.titles.repository;


import com.imdb.titles.entity.Title;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginatedTitleRepository extends PagingAndSortingRepository<Title, String> {
}
