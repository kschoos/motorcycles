package com.kschoos.motorcycles.bikecards;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Primary
public class BikeCardOriginRepository implements BikeCardRepository {
    @Value("${motorcycle_api.endpoint}")
    String motorcycleAPI_URI;

    @Value("${motorcycle_api.key}")
    String motorcycleAPI_KEY;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<List<BikeCard>> getBikeCards(BikeCardFilter filter) {
        ObjectMapper objectMapper = new ObjectMapper();
        WebClient webClient = webClientBuilder.build();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.motorcycleAPI_URI);

        stringBuilder.append("?");

        System.out.println(filter);

        if (!filter.makes.isEmpty()) {
            stringBuilder.append("make=");
            stringBuilder.append(filter.makes.get(0));
        }

        System.out.println(filter);

        System.out.println(stringBuilder.toString());

        return webClient.get()
                .uri(stringBuilder.toString())
                .header("X-Api-Key", this.motorcycleAPI_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(json -> {
                    try {
                        // Convert JSON string to List<BikeCard> using ObjectMapper
                        List<BikeCard> bikeCards = objectMapper.readValue(json, new TypeReference<List<BikeCard>>() {});
                        return Mono.just(bikeCards);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Mono.error(new RuntimeException("Failed to parse JSON"));
                    }
                });
    }
}
