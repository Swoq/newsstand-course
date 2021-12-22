package com.swoqe.newsstand;

import com.swoqe.newsstand.entities.GenreEntity;
import com.swoqe.newsstand.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "initialization", name = "mode", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class NewsstandInitializer implements CommandLineRunner {

    private final GenreService genreService;

    @Override
    public void run(String... args) throws Exception {
        List<GenreEntity> genreEntities = List.of(
                new GenreEntity("World"),
                new GenreEntity("U.S."),
                new GenreEntity("Technology"),
                new GenreEntity("Design"),
                new GenreEntity("Culture"),
                new GenreEntity("Business"),
                new GenreEntity("Politics"),
                new GenreEntity("Opinion"),
                new GenreEntity("Science"),
                new GenreEntity("Health"),
                new GenreEntity("Style"),
                new GenreEntity("Travel")
        );
        genreEntities.forEach(genreService::save);
        log.info("Initialization has been completed.");
    }
}
