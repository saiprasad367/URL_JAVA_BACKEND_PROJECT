package com.shortify.service;

import com.shortify.model.UrlRecord;
import com.shortify.repository.UrlRepository;
import com.shortify.utils.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public UrlRecord shortenUrl(String longUrl, String customAlias, LocalDateTime expiryDate) {
        String shortCode = customAlias;

        if (shortCode != null && !shortCode.isEmpty()) {
            if (urlRepository.findByShortCode(shortCode).isPresent()) {
                throw new IllegalArgumentException("Custom alias already exists");
            }
        } else {
            Long latestId = urlRepository.getMaxId();
            long nextId = latestId + 1;
            shortCode = Base62.encode(nextId);
            
            int attempts = 1;
            while (urlRepository.findByShortCode(shortCode).isPresent()) {
                shortCode = Base62.encode(nextId + attempts);
                attempts++;
            }
        }

        UrlRecord url = new UrlRecord();
        url.setLongUrl(longUrl);
        url.setShortCode(shortCode);
        url.setExpiryAt(expiryDate);
        return urlRepository.save(url);
    }

    public String resolveUrl(String shortCode) {
        UrlRecord url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));

        if (url.getExpiryAt() != null && url.getExpiryAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("URL has expired");
        }

        url.setClickCount(url.getClickCount() + 1);
        url.setLastAccessedAt(LocalDateTime.now());
        urlRepository.save(url);

        return url.getLongUrl();
    }

    public UrlRecord getAnalytics(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));
    }

    public void deleteUrl(String shortCode) {
        UrlRecord url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("URL not found"));
        urlRepository.delete(url);
    }

    public List<UrlRecord> getAllUrls() {
        return urlRepository.findAll();
    }
}
