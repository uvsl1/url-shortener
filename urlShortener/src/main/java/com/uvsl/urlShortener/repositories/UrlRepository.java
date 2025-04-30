package com.uvsl.urlShortener.repositories;

import com.uvsl.urlShortener.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByShortUrl(String shortUrl);
    Optional<Url> findByLongUrl(String longUrl);

}