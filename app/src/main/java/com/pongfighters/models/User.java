package com.pongfighters.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public static final String DOCUMENT_NAME = "users";

    public String id;
    public String username;
    public String email;

    public User() {
    }

    public static User newInstance(DataSnapshot dataSnapshot) {
        User result = new User();
        result.id = dataSnapshot.getKey();
        result.email = (String) dataSnapshot.child("email").getValue();
        result.username = (String) dataSnapshot.child("username").getValue();
        return result;
    }
}
