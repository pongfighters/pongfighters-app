package com.pongfighters.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String id;
    public String username;
    public String email;

    public User() {
    }
}
