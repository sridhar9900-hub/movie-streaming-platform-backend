package com.myflixhub.dto;

import jakarta.validation.constraints.*;

public class MovieDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;

    private String description;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Release year is required")
    @Min(value = 1888, message = "Release year must be valid")
    private Integer releaseYear;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    @NotBlank(message = "Director is required")
    private String director;

    @NotBlank(message = "Streaming URL is required")
    private String streamingUrl;

    private String thumbnailUrl;

    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 10")
    @DecimalMax(value = "10.0", message = "Rating must be between 0 and 10")
    private Double rating;

    public MovieDTO() {}

    public Long getId()               { return id; }
    public String getTitle()          { return title; }
    public String getDescription()    { return description; }
    public String getGenre()          { return genre; }
    public Integer getReleaseYear()   { return releaseYear; }
    public Integer getDurationMinutes(){ return durationMinutes; }
    public String getDirector()       { return director; }
    public String getStreamingUrl()   { return streamingUrl; }
    public String getThumbnailUrl()   { return thumbnailUrl; }
    public Double getRating()         { return rating; }

    public void setId(Long id)                        { this.id = id; }
    public void setTitle(String title)                { this.title = title; }
    public void setDescription(String description)    { this.description = description; }
    public void setGenre(String genre)                { this.genre = genre; }
    public void setReleaseYear(Integer releaseYear)   { this.releaseYear = releaseYear; }
    public void setDurationMinutes(Integer d)         { this.durationMinutes = d; }
    public void setDirector(String director)          { this.director = director; }
    public void setStreamingUrl(String streamingUrl)  { this.streamingUrl = streamingUrl; }
    public void setThumbnailUrl(String thumbnailUrl)  { this.thumbnailUrl = thumbnailUrl; }
    public void setRating(Double rating)              { this.rating = rating; }
}
