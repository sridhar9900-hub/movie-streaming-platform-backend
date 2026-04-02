package com.myflixhub.service;

import com.myflixhub.dto.MovieDTO;
import com.myflixhub.exception.CustomException;
import com.myflixhub.model.Movie;
import com.myflixhub.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new CustomException("Movie not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public List<MovieDTO> searchMovies(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO createMovie(MovieDTO dto) {
        Movie movie = toEntity(dto);
        return toDTO(movieRepository.save(movie));
    }

    public MovieDTO updateMovie(Long id, MovieDTO dto) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new CustomException("Movie not found with id: " + id, HttpStatus.NOT_FOUND));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setGenre(dto.getGenre());
        existing.setReleaseYear(dto.getReleaseYear());
        existing.setDurationMinutes(dto.getDurationMinutes());
        existing.setDirector(dto.getDirector());
        existing.setStreamingUrl(dto.getStreamingUrl());
        existing.setThumbnailUrl(dto.getThumbnailUrl());
        existing.setRating(dto.getRating());

        return toDTO(movieRepository.save(existing));
    }

    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new CustomException("Movie not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        movieRepository.deleteById(id);
    }

    private MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setGenre(movie.getGenre());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDurationMinutes(movie.getDurationMinutes());
        dto.setDirector(movie.getDirector());
        dto.setStreamingUrl(movie.getStreamingUrl());
        dto.setThumbnailUrl(movie.getThumbnailUrl());
        dto.setRating(movie.getRating());
        return dto;
    }

    private Movie toEntity(MovieDTO dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .releaseYear(dto.getReleaseYear())
                .durationMinutes(dto.getDurationMinutes())
                .director(dto.getDirector())
                .streamingUrl(dto.getStreamingUrl())
                .thumbnailUrl(dto.getThumbnailUrl())
                .rating(dto.getRating() != null ? dto.getRating() : 0.0)
                .build();
    }
}
