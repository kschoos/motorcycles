package com.kschoos.motorcycles.bikecards;

import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BikeCardRepository {
    public Mono<List<BikeCard>> getBikeCards(BikeCardFilter filter);

}
