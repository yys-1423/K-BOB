package com.SPISE.k_bob;// Profile.java
// Profile.java
import java.util.UUID;

public class Profile {
    private String id;
    private String name;

    // Constructor to create a new profile with a random UUID
    public Profile(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    // Constructor to restore an existing profile with a given ID
    public Profile(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

