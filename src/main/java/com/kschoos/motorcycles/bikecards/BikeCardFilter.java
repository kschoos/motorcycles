package com.kschoos.motorcycles.bikecards;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BikeCardFilter {
    List<String> makes;
    List<String> models;

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public BikeCardFilter(List<String> makes) {
        this.makes = makes;
    }

    public BikeCardFilter() {
        this.makes = new ArrayList<>();
        this.models = new ArrayList<>();
    }

    public List<String> getMakes() {
        return makes;
    }

    public void setMakes(List<String> makes) {
        this.makes = makes;
    }

    @Override
    public String toString() {
        return "BikeCardFilter{" +
                "makes=" + makes +
                '}';
    }

    public static BikeCardFilter getDefault() {
        return new BikeCardFilter(Collections.singletonList("Yamaha"));
    }
}
