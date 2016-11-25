package com.pongfighters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pongfighters.models.Match;
import com.pongfighters.models.User;
import com.pongfighters.models.UserSession;
import com.pongfighters.viewholder.RankingViewHolder;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private RecyclerView mRecycler;
    private FirebaseRecyclerAdapter<User, RankingViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mRecycler = (RecyclerView) findViewById(R.id.ranking);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query postsQuery = mDatabase.child(User.DOCUMENT_NAME);

        mAdapter = new FirebaseRecyclerAdapter<User, RankingViewHolder>(User.class, R.layout.item_ranking,
                RankingViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RankingViewHolder viewHolder, final User model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Match match = new Match();
                        match.users.add(UserSession.getLoggedInUser().id);
                        match.users.add(model.id);

                        String key = mDatabase.child(Match.DOCUMENT_NAME).push().getKey();
                        mDatabase.child(Match.DOCUMENT_NAME).child(key).setValue(match);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);

    }
}
