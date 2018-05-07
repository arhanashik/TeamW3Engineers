package com.example.user.myproject.adapter;

/**
 * Created by USER on 2/15/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.myproject.R;
import com.example.user.myproject.database.DBHelper;
import com.example.user.myproject.listeners.DevDataAdapterListener;
import com.example.user.myproject.models.Developer;
import com.example.user.myproject.others.FinalVariables;
import com.example.user.myproject.utils.ImageUtils;
import com.example.user.myproject.viewHolders.InternEngineerViewHolder;
import com.example.user.myproject.viewHolders.JuniorDeveloperViewHolder;
import com.example.user.myproject.viewHolders.SeniorDeveloperViewHolder;
import com.example.user.myproject.viewHolders.TeamLeaderViewHolder;

import java.util.List;


public class DevDataAdapter extends RecyclerView.Adapter {

    private DBHelper dbHelper;
    private List<Developer> mAllDev;
    private Context mContext;

    public static DevDataAdapterListener mListener;

    private Palette palette;

    private int hpIntern = -1, hpJdev = -1, hpSdev = -1, hpTl = -1;

    public DevDataAdapter(Context context, DevDataAdapterListener listener, List<Developer> allDev) {
        dbHelper = new DBHelper(context);
        this.mContext = context;
        this.mListener = listener;
        this.mAllDev = allDev;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case FinalVariables.INTERN_ENGINEER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intern_engineer, parent, false);
                return new InternEngineerViewHolder(view);
            case FinalVariables.JUNIOR_DEVELOPER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_junior_developer, parent, false);
                return new JuniorDeveloperViewHolder(view);
            case FinalVariables.SENIOR_DEVELOPER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_senior_developer, parent, false);
                return new SeniorDeveloperViewHolder(view);
            case FinalVariables.TEAM_LEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_leader, parent, false);
                return new TeamLeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mAllDev.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (mAllDev.get(position).getPosition()){
            case FinalVariables.INTERN_ENGINEER:
                if(hpIntern == -1) hpIntern = position;
                break;

            case FinalVariables.JUNIOR_DEVELOPER:
                if(hpJdev == -1) hpJdev = position;
                break;

            case FinalVariables.SENIOR_DEVELOPER:
                if(hpSdev == -1) hpSdev = position;
                break;

            case FinalVariables.TEAM_LEADER:
                if(hpTl == -1) hpTl = position;
                break;

            default:
                break;

        }

        return mAllDev.get(position).getPosition();
    }

    private Developer getDeveloper(int position) {
        if (mAllDev != null && !mAllDev.isEmpty()) {
            if (position >= 0 && position < getItemCount()) {
                Developer dev = mAllDev.get(position);
                if (dev != null) {
                    return dev;
                }
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        if (holder == null) {
            return;
        }

        final Developer developer = getDeveloper(listPosition);
        if (developer != null) {
            TextView header = null;
            View container = null;
            ImageView imageView = null;
            switch (developer.getPosition()) {
                case FinalVariables.INTERN_ENGINEER:
                    InternEngineerViewHolder ivh = (InternEngineerViewHolder) holder;
                    if(developer.getName() != null) ivh.internName.setText(developer.getName());
                    if(developer.getJoiningDate() != null) ivh.internJoiningDate.setText("Joining date: " + developer.getJoiningDate());
                    container = ivh.container;
                    imageView = ivh.internImg;
                    header = ivh.header;
                    header.setText("Intern Engineers");

                    break;

                case FinalVariables.JUNIOR_DEVELOPER:
                    JuniorDeveloperViewHolder jdv = (JuniorDeveloperViewHolder) holder;
                    if(developer.getName() != null) jdv.jdevName.setText(developer.getName());
                    if(developer.getField() != null) jdv.jdevField.setText("Team: " + developer.getField());
                    container = jdv.container;
                    imageView = jdv.jdevImg;
                    header = jdv.header;
                    header.setText("Junior Developers");

                    break;

                case FinalVariables.SENIOR_DEVELOPER:
                    SeniorDeveloperViewHolder sdv = (SeniorDeveloperViewHolder) holder;
                    if(developer.getName() != null) sdv.sName.setText(developer.getName());
                    if(developer.getField() != null) {
                        sdv.sTeam.setText("Team: " + developer.getField());
                        String teamLeader = dbHelper.getTeamLeader(FinalVariables.TABLE_DEVELOPERS, developer.getField());
                        if(teamLeader == null || teamLeader.isEmpty()) teamLeader = "Not yet assigned";
                        sdv.sTeamLeader.setText("Team leader: " + teamLeader);
                    }

                    container = sdv.container;
                    imageView = sdv.sImg;
                    header = sdv.header;
                    header.setText("Senior Developers");

                    Bitmap bitmap = ImageUtils.getImageFileFromSDCard(developer.getImgPath());
                    if(bitmap != null){
                        palette = ImageUtils.createPaletteSync(bitmap);
                        Palette.Swatch vibrantSwatch = ImageUtils.checkVibrantSwatch(palette);
                        if(vibrantSwatch != null) sdv.sTeamLeader.setBackgroundColor(vibrantSwatch.getRgb());
                    }

                    break;

                case FinalVariables.TEAM_LEADER:
                    TeamLeaderViewHolder tlv = (TeamLeaderViewHolder) holder;
                    if(developer.getName() != null) tlv.tlName.setText(developer.getName());
                    if(developer.getField() != null) tlv.tlTeam.setText("Team: " + developer.getField());
                    if(developer.getField() != null) tlv.tlTotalTeamMember.setText("Total team member: " + listPosition);
                    container = tlv.container;
                    imageView = tlv.tlImg;
                    header = tlv.header;
                    header.setText("Team Leaders");

                    Bitmap bmp = ImageUtils.getImageFileFromSDCard(developer.getImgPath());
                    if(bmp != null){
                        palette = ImageUtils.createPaletteSync(bmp);
                        Palette.Swatch vibrantSwatch = ImageUtils.checkVibrantSwatch(palette);
                        if(vibrantSwatch != null) tlv.tlName.setBackgroundColor(vibrantSwatch.getRgb());
                    }

                    break;

                default:
                    break;
            }

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onDevClick(listPosition, developer);
                }
            });
            applyProfilePicture(imageView, developer.getImgPath());

            if(header != null ) {
                if(listPosition == hpIntern || listPosition == hpJdev || listPosition == hpSdev
                        || listPosition == hpTl) header.setVisibility(View.VISIBLE);
                else header.setVisibility(View.GONE);
            }
        }
    }

    private void applyProfilePicture(final ImageView imageView, String imgUrl) {
        if (imgUrl != null) {
            Bitmap bitmap = ImageUtils.getImageFileFromSDCard(imgUrl);
            if(bitmap != null){
                Glide.with(mContext)
                    .load(bitmap)
                    .into(imageView);
            }
        }
    }

}
