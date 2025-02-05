package com.uvsl.urlShortener.services;

import com.uvsl.urlShortener.entities.Link;
import com.uvsl.urlShortener.repositories.LinkRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public String generateRandomUrl() {
        int length = ThreadLocalRandom.current().nextInt(5, 11);
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public Link shortenUrl(String originalUrl) {
        Link link = new Link();
        link.setLongUrl(originalUrl);
        link.setShortUrl(generateRandomUrl());
        link.setCreatedIn(LocalDateTime.now());
        return linkRepository.save(link);
    }
    public Link getOriginalUrl(String shortUrl) {
        try {
            return linkRepository.findByLongUrl(shortUrl);
        } catch (Exception e) {
            throw new RuntimeException("Url doesn't exist. " + e);
        }
    }
}