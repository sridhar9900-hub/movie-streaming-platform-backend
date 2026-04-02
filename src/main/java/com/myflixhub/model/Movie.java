package com.myflixhub.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 100)
    private String genre;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    private Integer durationMinutes;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String streamingUrl;

    private String thumbnailUrl;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Movie() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ─── Getters ────────────────────────────────────────────────────
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
    public LocalDateTime getCreatedAt(){ return createdAt; }

    // ─── Setters ────────────────────────────────────────────────────
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
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ─── Builder ────────────────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private String genre;
        private Integer releaseYear;
        private Integer durationMinutes;
        private String director;
        private String streamingUrl;
        private String thumbnailUrl;
        private Double rating;

        public Builder id(Long id)                        { this.id = id; return this; }
        public Builder title(String title)                { this.title = title; return this; }
        public Builder description(String description)    { this.description = description; return this; }
        public Builder genre(String genre)                { this.genre = genre; return this; }
        public Builder releaseYear(Integer releaseYear)   { this.releaseYear = releaseYear; return this; }
        public Builder durationMinutes(Integer d)         { this.durationMinutes = d; return this; }
        public Builder director(String director)          { this.director = director; return this; }
        public Builder streamingUrl(String streamingUrl)  { this.streamingUrl = streamingUrl; return this; }
        public Builder thumbnailUrl(String thumbnailUrl)  { this.thumbnailUrl = thumbnailUrl; return this; }
        public Builder rating(Double rating)              { this.rating = rating; return this; }

        public Movie build() {
            Movie m = new Movie();
            m.id = this.id;
            m.title = this.title;
            m.description = this.description;
            m.genre = this.genre;
            m.releaseYear = this.releaseYear;
            m.durationMinutes = this.durationMinutes;
            m.director = this.director;
            m.streamingUrl = this.streamingUrl;
            m.thumbnailUrl = this.thumbnailUrl;
            m.rating = this.rating;
            return m;
        }
    }
}
