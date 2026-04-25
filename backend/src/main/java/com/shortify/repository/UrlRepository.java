package com.shortify.repository;

import com.shortify.model.UrlRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlRecord, Long> {
    Optional<UrlRecord> findByShortCode(String shortCode);

    @Query(value = "SELECT COALESCE(MAX(id), 0) FROM urls", nativeQuery = true)
    Long getMaxId();
}
