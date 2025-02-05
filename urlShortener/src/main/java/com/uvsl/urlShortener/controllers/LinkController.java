package com.uvsl.urlShortener.controllers;

import com.uvsl.urlShortener.entities.Link;
import com.uvsl.urlShortener.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LinkController {

    @Autowired
    private LinkService service;

    @PostMapping("/shorter")
    public ResponseEntity<Link> generateShortUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        Link link = service.shortenUrl(originalUrl);

        String generateUserRedirectUrl = "http://localhost:8080/r/" + link.getShortUrl();

        Link response = new Link(link.getId(), link.getLongUrl(), generateUserRedirectUrl, link.getCreatedIn());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
