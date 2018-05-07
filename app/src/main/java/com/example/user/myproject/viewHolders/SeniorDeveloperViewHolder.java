package com.example.user.myproject.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.myproject.R;

/**
 * Created by USER on 2/15/2018.
 */

public class SeniorDeveloperViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout container;
    public ImageView sImg;
    public TextView sName, sTeam, sTeamLeader, header;

    public SeniorDeveloperViewHolder(View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        sImg = itemView.findViewById(R.id.sdev_img);
        sName = itemView.findViewById(R.id.sdev_name);
        sTeam = itemView.findViewById(R.id.sdev_team);
        sTeamLeader = itemView.findViewById(R.id.steam_leader);
        header = itemView.findViewById(R.id.tv_header);
    }
}
