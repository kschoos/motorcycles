package com.kschoos.motorcycles.makes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kschoos.motorcycles.bikecards.BikeCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class MakeLocalRepository implements MakeRepository {
    @Value("classpath:makes.json")
    private String repositoryURI;

    ResourceLoader resourceLoader;

    public MakeLocalRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public Mono<List<Make>> getMakes() {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream;

        try {
            Resource resource = this.resourceLoader.getResource(this.repositoryURI);
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            System.out.println("Could not read from resource...");
            e.printStackTrace();
            inputStream = null;
        }

        if (inputStream == null) {
            return null;
        }

        List<Make> makeNames;
        try {
            makeNames = objectMapper.readValue(inputStream, new TypeReference<List<Make>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            makeNames = null;
        }

        return Mono.just(makeNames);
    }
}
