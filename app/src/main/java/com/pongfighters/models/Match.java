package com.pongfighters.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Match {
    public static final String DOCUMENT_NAME = "matches";

    private String id;
    private List<String> users = new ArrayList<>();
    private String winnerUserId;
    private String date;

    public Match() {
    }

    public static Match newInstance(DataSnapshot dataSnapshot) {
        Match result = new Match();
        result.setId(dataSnapshot.getKey());
        result.setUsers((List) dataSnapshot.child("users").getValue());
        result.setWinnerUserId((String) dataSnapshot.child("winnerUserId").getValue());
        result.setDate((String) dataSnapshot.child("date").getValue());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getWinnerUserId() {
        return winnerUserId;
    }

    public void setWinnerUserId(String winnerUserId) {
        this.winnerUserId = winnerUserId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
