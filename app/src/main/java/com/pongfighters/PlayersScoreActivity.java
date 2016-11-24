package com.pongfighters;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.pongfighters.tools.UserSession;
import com.pongfighters.tools.BaseActivity;
import com.pongfighters.tools.DateUtils;
import com.pongfighters.viewholder.RankingViewHolder;

public class PlayersScoreActivity extends BaseActivity {
    DatabaseReference mDatabase;

    RecyclerView mRecycler;
    FirebaseRecyclerAdapter<User, RankingViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query postsQuery = mDatabase.child(User.DOCUMENT_NAME);

        mRecycler = (RecyclerView) findViewById(R.id.ranking);
        Query postsQuery = mDatabase.child(User.DOCUMENT_NAME);
        mAdapter = new FirebaseRecyclerAdapter<User, RankingViewHolder>(User.class, R.layout.item_ranking,
                RankingViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RankingViewHolder viewHolder, final User model, final int position) {
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showProgressDialog();
                        Match match = new Match();
                        match.getUsers().add(UserSession.getLoggedInUser().getId());
                        match.getUsers().add(model.getId());
                        match.setWinnerUserId(UserSession.getLoggedInUser().getId());
                        match.setDate(DateUtils.formatDate(new java.util.Date()));

                        String key = mDatabase.child(Match.DOCUMENT_NAME).push().getKey();
                        mDatabase.child(Match.DOCUMENT_NAME).child(key).setValue(match).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                hideProgressDialog();
                                Toast.makeText(PlayersScoreActivity.this, "Partida guardada", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }
}
