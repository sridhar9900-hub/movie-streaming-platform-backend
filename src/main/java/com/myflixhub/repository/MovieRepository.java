package com.myflixhub.repository;

import com.myflixhub.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByGenreIgnoreCase(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByReleaseYear(Integer year);
    List<Movie> findByRatingGreaterThanEqual(Double rating);
}
