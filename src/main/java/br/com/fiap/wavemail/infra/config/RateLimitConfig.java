package br.com.fiap.wavemail.infra.config;

import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket bucket() {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(10).refillGreedy(1, Duration.ofSeconds(3)))
                .build();
    }

}
