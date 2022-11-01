package edu.northeastern.numad22fa_team15.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private List<String> friends;
    /**
     * An empty constructor is required when using Firebase Realtime Database.
     */
    public User() {}

    public User(String username) {
        this.username = username;
        this.friends = new ArrayList<String>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
