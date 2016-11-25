package com.pongfighters.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nmarsollier on 11/25/16.
 */

public class Match {
    public static final String DOCUMENT_NAME = "matches";

    public String id;
    public List<String> users = new ArrayList<>();

    public Match() {
    }

    public static Match newInstance(DataSnapshot dataSnapshot) {
        Match result = new Match();
        result.id = dataSnapshot.getKey();
        result.users = (List) dataSnapshot.child("users").getValue();
        return result;
    }
}
