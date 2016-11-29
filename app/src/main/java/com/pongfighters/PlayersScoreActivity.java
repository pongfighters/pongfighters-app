package com.pongfighters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pongfighters.models.User;
import com.pongfighters.tools.BaseActivity;
import com.pongfighters.viewholder.RankingViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PlayersScoreActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    DatabaseReference mDatabase;

    RecyclerView mRecycler;
    FloatingActionButton mFab;
    FirebaseRecyclerAdapter<User, RankingViewHolder> mAdapter;
    List<User> opponents = new ArrayList<>();
    List<User> partners = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query postsQuery = mDatabase.child(User.DOCUMENT_NAME);
        mRecycler = (RecyclerView) findViewById(R.id.ranking);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FirebaseRecyclerAdapter<User, RankingViewHolder>(User.class, R.layout.item_ranking,
                RankingViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RankingViewHolder viewHolder, final User model, final int position) {
                viewHolder.bindToPost(getApplicationContext(), model, partners, opponents, () -> {
                    if (opponents.size() == 1 && partners.size() == 1 || opponents.size() == 2 && partners.size() == 2) {
                        mFab.show();
                    } else {
                        mFab.hide();
                    }

                });
            }
        };
        mRecycler.setAdapter(mAdapter);
        mFab.setOnClickListener(view -> new NewMatchDialogFragment(partners, opponents).show(PlayersScoreActivity.this.getFragmentManager(), "new_score"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                signOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
            setResult(1);
            finish();
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
