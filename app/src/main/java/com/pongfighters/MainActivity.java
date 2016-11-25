package com.pongfighters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pongfighters.models.User;
import com.pongfighters.viewholder.RankingViewHolder;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private RecyclerView mRecycler;
    private FirebaseRecyclerAdapter<User, RankingViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {


        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = (RecyclerView) findViewById(R.id.ranking);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = mDatabase.child("users");

        mAdapter = new FirebaseRecyclerAdapter<User, RankingViewHolder>(User.class, R.layout.item_ranking,
                RankingViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RankingViewHolder viewHolder, final User model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToPost(model);
            }
        };
        mRecycler.setAdapter(mAdapter);

    }
}
