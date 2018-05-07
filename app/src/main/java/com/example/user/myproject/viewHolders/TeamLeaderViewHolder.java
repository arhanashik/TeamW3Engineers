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

public class TeamLeaderViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout container;
    public ImageView tlImg;
    public TextView tlName, tlTeam, tlTotalTeamMember, header;

    public TeamLeaderViewHolder(View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        tlImg = itemView.findViewById(R.id.tl_img);
        tlName = itemView.findViewById(R.id.tl_name);
        tlTeam = itemView.findViewById(R.id.tl_field);
        tlTotalTeamMember = itemView.findViewById(R.id.tl_team_member);
        header = itemView.findViewById(R.id.tv_header);
    }
}
