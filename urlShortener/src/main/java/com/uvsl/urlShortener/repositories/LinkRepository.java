package com.uvsl.urlShortener.repositories;

import com.uvsl.urlShortener.entities.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository <Link, Long> {

    Link findByLongUrl(String shortUrl);
}
