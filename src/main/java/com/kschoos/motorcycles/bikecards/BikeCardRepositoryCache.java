package com.kschoos.motorcycles.bikecards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeCardRepositoryCache extends JpaRepository<BikeCard, Long> {

    List<BikeCard> findByMake(String make);
}
