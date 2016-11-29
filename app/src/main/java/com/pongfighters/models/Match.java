package com.pongfighters.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Match {
    public static final String DOCUMENT_NAME = "matches";

    private String id;
    private List<String> winners = new ArrayList<>();
    private List<String> losers = new ArrayList<>();
    private String date;

    public Match() {
    }

    public static Match newInstance(DataSnapshot dataSnapshot) {
        Match result = new Match();
        result.setId(dataSnapshot.getKey());
        result.setWinners((List) dataSnapshot.child("winners").getValue());
        result.setLosers((List) dataSnapshot.child("losers").getValue());
        result.setDate((String) dataSnapshot.child("date").getValue());
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getWinners() {
        return winners;
    }

    public void setWinners(List<String> winners) {
        this.winners = winners;
    }

    public List<String> getLosers() {
        return losers;
    }

    public void setLosers(List<String> losers) {
        this.losers = losers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
