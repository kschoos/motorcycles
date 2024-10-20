package com.kschoos.motorcycles.bikecards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class BikeCardLocalRepository implements BikeCardRepository {
    @Value("classpath:bikes.json")
    String motorcycleAPI_URI;

    private final ResourceLoader resourceLoader;

    public BikeCardLocalRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Mono<List<BikeCard>> getBikeCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream;

        try {
            Resource resource = this.resourceLoader.getResource(this.motorcycleAPI_URI);
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            System.out.println("Could not read from resource...");
            e.printStackTrace();
            inputStream = null;
        }

        if (inputStream == null) {
            return null;
        }

        List<BikeCard> bikeCards;
        try {
            bikeCards = objectMapper.readValue(inputStream, new TypeReference<List<BikeCard>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            bikeCards = null;
        }

        return Mono.just(bikeCards);
    }
}
