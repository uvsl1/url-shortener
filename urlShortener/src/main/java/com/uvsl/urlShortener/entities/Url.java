package com.uvsl.urlShortener.entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="links")
public class Url implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private int durationDays;

    public Url() {
    }

    public Url(Long id, String longUrl, String shortUrl, LocalDateTime createdAt, int durationDays) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
        this.durationDays = durationDays;
    }

    public Url(String longUrl, int durationDays) {
        this.longUrl = longUrl;
        this.durationDays = durationDays;
    }

    public boolean isExpired() {
        return createdAt.plusDays(durationDays).isBefore(LocalDateTime.now());
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }
}
