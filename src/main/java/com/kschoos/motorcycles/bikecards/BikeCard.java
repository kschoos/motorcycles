package com.kschoos.motorcycles.bikecards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BikeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String make;
    private String model;
    private String engine;

    @Column(name="yuri")
    private String year;
    private String torque;
    private String power;
    private Integer displacement;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTorque() {
        return torque;
    }

    public void setTorque(String torque) {
        this.torque = torque;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public Integer getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        Pattern pattern = Pattern.compile("(\\d+).*");
        Matcher matcher = pattern.matcher(displacement);

        if (matcher.find()) {
            this.displacement = Integer.parseInt(matcher.group(1));
        }
    }

    public void setDisplacement(Integer displacement) {
        this.displacement = displacement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
