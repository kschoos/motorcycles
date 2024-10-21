package com.kschoos.motorcycles.bikecards;

import com.kschoos.motorcycles.makes.Make;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BikeCardService {
    @Autowired
    private final BikeCardRepositoryCache bikeCardRepositoryCache;
    private final BikeCardRepository bikeCardRepository;

    public BikeCardService(BikeCardRepositoryCache bikeCardRepositoryCache, BikeCardRepository bikeCardRepository) {
        this.bikeCardRepositoryCache = bikeCardRepositoryCache;
        this.bikeCardRepository = bikeCardRepository;
    }

    public Mono<List<BikeCard>> getBikeCards(BikeCardFilter filter) {
        if (filter.makes.isEmpty() && filter.models.isEmpty()) {
            filter = BikeCardFilter.getDefault();
        }

        List<String> makes = new ArrayList(filter.getMakes());
        List<BikeCard> allCards = new ArrayList<>();


        for (String make: filter.getMakes()) {
            List<BikeCard> bikeCards = bikeCardRepositoryCache.findByMake(make);
            if (bikeCards.isEmpty()) {
                continue;
            }

            makes.remove(make);
            allCards.addAll(bikeCards);
        }

        Mono<List<BikeCard>> allCardsMono = Mono.just(allCards);

        for (String make: makes) {
            BikeCardFilter bikeFilter = new BikeCardFilter(Collections.singletonList(make));
            Mono<List<BikeCard>> bikeCards = bikeCardRepository.getBikeCards(bikeFilter);

            bikeCards.publishOn(Schedulers.boundedElastic()).map(bikeCardRepositoryCache::saveAll).subscribe();

            allCardsMono = Mono.zip(allCardsMono, bikeCards)
                    .map(tuple -> Stream.concat(tuple.getT1().stream(), tuple.getT2().stream()).toList());
        }

        return allCardsMono;
    }
}
