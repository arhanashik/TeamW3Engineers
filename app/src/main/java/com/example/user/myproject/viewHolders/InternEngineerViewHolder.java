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

public class InternEngineerViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout container;
    public ImageView internImg;
    public TextView internName, internJoiningDate, header;

    public InternEngineerViewHolder(View itemView) {
        super(itemView);

        container = itemView.findViewById(R.id.container);
        internImg = itemView.findViewById(R.id.intern_img);
        internName = itemView.findViewById(R.id.intern_name);
        internJoiningDate = itemView.findViewById(R.id.intern_joining_date);
        header = itemView.findViewById(R.id.tv_header);
    }
}
