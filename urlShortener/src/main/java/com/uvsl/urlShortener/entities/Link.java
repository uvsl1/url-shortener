package com.uvsl.urlShortener.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortUrl;
    private LocalDateTime createdIn;

    public Link() {
    }

    public Link(Long id, String longUrl, String shortUrl, LocalDateTime createdIn) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.createdIn = createdIn;
    }

    public Long getId() {
        return id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String bigUrl) {
        this.longUrl = bigUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public LocalDateTime getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(LocalDateTime createdIn) {
        this.createdIn = createdIn;
    }
}
