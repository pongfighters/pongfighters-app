package com.pongfighters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pongfighters.R;
import com.pongfighters.models.User;

public class RankingViewHolder extends RecyclerView.ViewHolder {

    public TextView userNameView;
    public TextView userPointsView;

    public RankingViewHolder(View itemView) {
        super(itemView);
        userNameView = (TextView) itemView.findViewById(R.id.ranking_username);
        userPointsView = (TextView) itemView.findViewById(R.id.ranking_user_points);
    }

    public void bindToPost(User user) {
        userNameView.setText(user.username);
        userPointsView.setText("1234");
    }
}
