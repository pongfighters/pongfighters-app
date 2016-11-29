package com.pongfighters.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pongfighters.R;
import com.pongfighters.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RankingViewHolder extends RecyclerView.ViewHolder {
    ImageView mUserIcon;

    TextView mUserNameView;
    TextView mUserPointsView;
    CheckBox mPartnerView;
    CheckBox mOpponetView;
    TextView mPoints;

    public RankingViewHolder(View itemView) {
        super(itemView);
        mUserIcon   = (ImageView) itemView.findViewById(R.id.user_icon);
        mUserNameView = (TextView) itemView.findViewById(R.id.ranking_username);
        mUserPointsView = (TextView) itemView.findViewById(R.id.ranking_user_points);
        mPartnerView = (CheckBox) itemView.findViewById(R.id.partner);
        mOpponetView = (CheckBox) itemView.findViewById(R.id.opponent);
        mPoints = (TextView) itemView.findViewById(R.id.ranking_user_points);
    }

    private void updateCheckbox(User user, final List<User> partners, final List<User> opponents) {
        mPartnerView.setChecked(partners.contains(user));
        mOpponetView.setChecked(opponents.contains(user));
    }

    public void bindToPost(Context context, final User user, final List<User> partners, final List<User> opponents, final OnSelectionChange selectionChange) {
        mUserNameView.setText(user.getUsername());
        mUserPointsView.setText(String.valueOf(user.getPoints()));
        updateCheckbox(user, partners, opponents);
        Picasso.with(context).load(user.getIcon()).into(mUserIcon);
        mPartnerView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    partners.add(user);
                    if (opponents.contains(user)) {
                        opponents.remove(user);

                    }
                } else {
                    partners.remove(user);
                }
                selectionChange.selectionChange();
                updateCheckbox(user, partners, opponents);
            }
        });
        mOpponetView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    opponents.add(user);
                    if (partners.contains(user)) {
                        partners.remove(user);
                    }
                } else {
                    opponents.remove(user);
                }
                selectionChange.selectionChange();
                updateCheckbox(user, partners, opponents);
            }

        });
    }
}
