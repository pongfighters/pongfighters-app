package com.pongfighters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pongfighters.models.Match;
import com.pongfighters.models.User;
import com.pongfighters.models.UserSession;
import com.pongfighters.tools.DateUtils;
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
                        match.winnerUserId = UserSession.getLoggedInUser().id;
                        match.date = DateUtils.formatDate(new java.util.Date());

                        String key = mDatabase.child(Match.DOCUMENT_NAME).push().getKey();
                        mDatabase.child(Match.DOCUMENT_NAME).child(key).setValue(match).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Partida guardada", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }
}
