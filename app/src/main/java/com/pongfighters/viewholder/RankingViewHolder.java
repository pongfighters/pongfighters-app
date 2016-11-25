package com.pongfighters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pongfighters.R;
import com.pongfighters.models.User;

public class RankingViewHolder extends RecyclerView.ViewHolder {
    ImageView mMatchIcon;
    TextView mUserNameView;
    TextView mUserPointsView;

    public RankingViewHolder(View itemView) {
        super(itemView);
        mUserNameView = (TextView) itemView.findViewById(R.id.ranking_username);
        mUserPointsView = (TextView) itemView.findViewById(R.id.ranking_user_points);
        mMatchIcon = (ImageView) itemView.findViewById(R.id.match);
    }

    public void bindToPost(User user, View.OnClickListener clickListener) {
        mUserNameView.setText(user.getUsername());
        mUserPointsView.setText("1234");
        mMatchIcon.setOnClickListener(clickListener);
    }
}
