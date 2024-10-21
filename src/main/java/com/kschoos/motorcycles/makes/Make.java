package com.kschoos.motorcycles.makes;

import jakarta.persistence.Id;

public class Make {
    @Id
    private Integer ID;
    private String name;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
