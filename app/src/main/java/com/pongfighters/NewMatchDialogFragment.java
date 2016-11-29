package com.pongfighters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pongfighters.models.Match;
import com.pongfighters.models.User;
import com.pongfighters.tools.DateUtils;
import com.pongfighters.tools.UserSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewMatchDialogFragment extends DialogFragment {

    List<User> mPartners;
    List<User> mOpponents;
    List<User> mWinners = new ArrayList<User>();
    List<User> mLosers = new ArrayList<User>();

    Match match = new Match();

    public NewMatchDialogFragment() {

    }

    @SuppressLint("ValidFragment")
    public NewMatchDialogFragment(List<User> partners, List<User> opponents) {
        mPartners = partners;
        mOpponents = opponents;
    }

    private void selectWinner(int index) {
        mWinners.clear();
        mLosers.clear();
        if(index == 0) {
            mWinners.addAll(mPartners);
            mLosers.addAll(mOpponents);
        } else {
            mWinners.addAll(mOpponents);
            mLosers.addAll(mPartners);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //mSelectedItems = new ArrayList<>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] values = new CharSequence[2];
        values[0] = mPartners.get(0).getUsername();
        values[1] = mOpponents.get(0).getUsername();
        if(mPartners.size() > 1) {
            values[0] = values[0] + " & " + mPartners.get(1).getUsername();
        }
        if(mOpponents.size() > 1) {
            values[0] = values[0] + " & " + mOpponents.get(1).getUsername();
        }

        // select default winner, first one
        selectWinner(0);

        // Set the dialog title
        builder.setTitle(R.string.select_winner)
                .setSingleChoiceItems(values, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int index) {
                                selectWinner(index);
                            }
                        }

                )
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        match.setDate(DateUtils.formatDate(new Date()));
                        for (User user : NewMatchDialogFragment.this.mWinners) {
                            match.getWinners().add(user.getId());
                            user.setPoints(user.getPoints() + 5);
                            mDatabase.child(User.DOCUMENT_NAME).child(user.getId()).setValue(user);
                        }
                        for (User user : NewMatchDialogFragment.this.mLosers) {
                            match.getLosers().add(user.getId());
                            user.setPoints(user.getPoints() + 1);
                            mDatabase.child(User.DOCUMENT_NAME).child(user.getId()).setValue(user);
                        }
                        String key = mDatabase.child(Match.DOCUMENT_NAME).push().getKey();
                        mDatabase.child(Match.DOCUMENT_NAME).child(key).setValue(match);
                        mPartners.clear();
                        mOpponents.clear();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}
