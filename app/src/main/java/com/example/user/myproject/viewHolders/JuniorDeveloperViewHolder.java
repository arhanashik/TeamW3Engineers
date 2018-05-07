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

public class JuniorDeveloperViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout container;
    public ImageView jdevImg;
    public TextView jdevName, jdevField, header;

    public JuniorDeveloperViewHolder(View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        jdevImg = itemView.findViewById(R.id.jdev_img);
        jdevName = itemView.findViewById(R.id.jdev_name);
        jdevField = itemView.findViewById(R.id.jdev_field);
        header = itemView.findViewById(R.id.tv_header);
    }
}
