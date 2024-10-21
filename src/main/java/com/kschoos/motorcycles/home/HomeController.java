package com.kschoos.motorcycles.bikecards;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kschoos.motorcycles.makes.Make;
import com.kschoos.motorcycles.makes.MakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private final BikeCardService bikeCardService;

    @Autowired
    private final MakeRepository makeRepository;

    public HomeController(BikeCardService bikeCardService, MakeRepository makeRepository) {
        this.bikeCardService = bikeCardService;
        this.makeRepository = makeRepository;
    }

    @RequestMapping("/")
    public Mono<String> getMotorcycles(@ModelAttribute BikeCardFilter bikeFilter, Model model) {
        Mono<List<BikeCard>> bikeCards = bikeCardService.getBikeCards(bikeFilter);
        Mono<List<Make>> makes = makeRepository.getMakes();

        return bikeCards.flatMap(lst -> makes.map(
                makeList -> {
                    model.addAttribute("bikelist", lst);
                    model.addAttribute("makelist", makeList);
                    model.addAttribute("bikeFilter", bikeFilter);

                    return "index";
                })
        );
    }
}
