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

    public NewMatchDialogFragment() {

    }

    @SuppressLint("ValidFragment")
    public NewMatchDialogFragment(List<User> partners, List<User> opponents) {
        mPartners = partners;
        mOpponents = opponents;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //mSelectedItems = new ArrayList<>();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] values = new CharSequence[2];
        values[0] = "";
        values[1] = "";

        for (User user : mPartners) {
            values[0] = values[0] + user.getUsername();
        }

        for (User user : mOpponents) {
            values[1] = values[1] + user.getUsername();
        }

        // Set the dialog title
        builder.setTitle(R.string.select_winner)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(values, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                )
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        Match match = new Match();
                        for (User user : NewMatchDialogFragment.this.mPartners) {
                            match.getUsers().add(user.getId());
                        }
                        for (User user : mOpponents) {
                            match.getUsers().add(user.getId());
                        }
                        //match.setWinnerUserId(UserSession.getLoggedInUser().getId()); 
                        match.setDate(DateUtils.formatDate(new Date()));
                        String key = mDatabase.child(Match.DOCUMENT_NAME).push().getKey();
                        mDatabase.child(Match.DOCUMENT_NAME).child(key).setValue(match);
                    }
                });
        return builder.create();
    }
}
