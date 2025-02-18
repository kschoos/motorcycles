package com.kschoos.motorcycles.bikecards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class BikeCardsController {
    @Autowired
    private final BikeCardService bikeCardService;
    private final BikeCardRepository bikeCardRepository;

    public BikeCardsController(BikeCardRepository bikeCardRepository, BikeCardService bikeCardService) {
        this.bikeCardService = bikeCardService;
        this.bikeCardRepository = bikeCardRepository;
    }

    @RequestMapping("/bikecards")
    public String getMotorcycles(@RequestBody String body, Model model) {
        String make = "";
        BikeCardFilter filter = new BikeCardFilter(Collections.singletonList(make));
        Mono<List<BikeCard>> bikeCards = bikeCardService.getBikeCards(filter);

        model.addAttribute("bikelist", bikeCards);

        return "bikelist";
    }
}
