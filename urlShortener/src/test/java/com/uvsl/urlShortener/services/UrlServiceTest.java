package com.uvsl.urlShortener.services;

import com.uvsl.urlShortener.entities.Url;
import com.uvsl.urlShortener.exceptions.GreaterThanExpectedException;
import com.uvsl.urlShortener.exceptions.RestErrorMessage;
import com.uvsl.urlShortener.repositories.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateRandomUrlLenghth() {
        for(int i = 0; i < 10; i++) {
            String randomUrl = urlService.generateRandomUrl();
            assertTrue(randomUrl.length() >= 5 && randomUrl.length() <= 10);
        }
    }

    @Test
    void testShrotenUrlAlreadyExists() {
        String originalUrl = "https://test.com";
        Url existingUrl = new Url();
        existingUrl.setLongUrl(originalUrl);
        existingUrl.setShortUrl("abc123");

        when(urlRepository.findByLongUrl(originalUrl)).thenReturn(Optional.of(existingUrl));
        Url result = urlService.shortenUrl(originalUrl, 10);

        assertEquals("abc123", result.getShortUrl());
        verify(urlRepository, never()).save(any());
    }

    @Test
    void testShortenUrlWithDurationGreaterThan90ThrowsException() {
        String originalUrl = "https://test.com";
        Integer durationDays = 91;

        assertThrows(GreaterThanExpectedException.class, () -> {
            urlService.shortenUrl(originalUrl, durationDays);
        });

        verify(urlRepository, never()).save(any());
    }

    @Test
    void testShortenUrlWithNullDuration() {
        String originalUrl = "https://test.com";

        when(urlRepository.findByLongUrl(originalUrl)).thenReturn(Optional.empty());
        when(urlRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Url result = urlService.shortenUrl(originalUrl, null);

        assertEquals(7, result.getDurationDays());
    }

    @Test
    void testGetOriginalUrlSuccess() {
        String shortUrl = "abc123";
        Url url = new Url();
        url.setLongUrl("https://test.com");
        url.setShortUrl(shortUrl);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(url);
        Url result = urlService.getOriginalUrl(shortUrl);
        assertEquals("https://test.com", result.getLongUrl());
    }
}