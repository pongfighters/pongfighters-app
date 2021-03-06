package com.pongfighters.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public static final String DOCUMENT_NAME = "users";

    private String id;
    private String username;
    private String email;
    private String icon;
    private long points;

    public User() {
    }

    public static User newInstance(DataSnapshot dataSnapshot) {
        User result = new User();
        result.setId(dataSnapshot.getKey());
        result.setEmail((String) dataSnapshot.child("email").getValue());
        result.setUsername((String) dataSnapshot.child("username").getValue());
        result.setIcon((String) dataSnapshot.child("icon").getValue());
        result.setPoints((long)dataSnapshot.child("points").getValue());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }


}
