package com.uvsl.urlShortener.controllers;

import com.uvsl.urlShortener.entities.Url;
import com.uvsl.urlShortener.services.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/shorter")
    public ResponseEntity<Url> generateShortUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        Integer durationDays = request.containsKey("durationDays") ?
                Integer.valueOf(request.get("durationDays")) : null;
        Url url = service.shortenUrl(originalUrl, durationDays);
        String generateUserRedirectUrl = "http://localhost:8080/" + url.getShortUrl();

        Url response = new Url(url.getId(), url.getLongUrl(), generateUserRedirectUrl, url.getCreatedAt(), url.getDurationDays());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortUrl}")
    public void redirectLink(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        Url url = service.getOriginalUrl(shortUrl);

        if (url == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (url.isExpired()) {
            response.sendError(HttpServletResponse.SC_GONE, "URL expired");
            return;
        }

        response.sendRedirect(url.getLongUrl());;

    }
}
