package com.kschoos.motorcycles.makes;

import reactor.core.publisher.Mono;

import java.util.List;

public interface MakeRepository {
    public Mono<List<Make>> getMakes();
}
