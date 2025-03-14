package com.uvsl.urlShortener.services;

import com.uvsl.urlShortener.entities.Url;
import com.uvsl.urlShortener.repositories.UrlRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String generateRandomUrl() {
        int length = ThreadLocalRandom.current().nextInt(5, 11);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public Url shortenUrl(String originalUrl) {
        Url url = new Url();
        url.setLongUrl(originalUrl);
        url.setShortUrl(generateRandomUrl());
        url.setCreatedAt(LocalDateTime.now());
        return urlRepository.save(url);
    }

    public Url getOriginalUrl(String shortUrl) {
        try {
            return urlRepository.findByShortUrl(shortUrl);
        } catch (Exception e) {
            throw new RuntimeException("Url doesn't exist. " + e);
        }
    }
}