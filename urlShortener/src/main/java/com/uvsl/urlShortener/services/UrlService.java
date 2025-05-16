package com.uvsl.urlShortener.services;

import com.uvsl.urlShortener.entities.Url;
import com.uvsl.urlShortener.exceptions.GreaterThanExpectedException;
import com.uvsl.urlShortener.exceptions.RestErrorMessage;
import com.uvsl.urlShortener.repositories.UrlRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String generateRandomUrl() {
        int length = ThreadLocalRandom.current().nextInt(5, 10);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public Url shortenUrl(String originalUrl, Integer durationDays) {
        Optional<Url> existingUrl = urlRepository.findByLongUrl(originalUrl);

        if (existingUrl.isPresent()) {
            return existingUrl.get();
        }

        Url url = new Url();
        url.setLongUrl(originalUrl);
        url.setShortUrl(generateRandomUrl());
        url.setCreatedAt(LocalDateTime.now());
        if (durationDays != null && durationDays > 90) {
            throw new GreaterThanExpectedException(RestErrorMessage.EXPIRATION_LIMIT_EXCEEDED);
        }
        url.setDurationDays(durationDays != null ? durationDays : 7);
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