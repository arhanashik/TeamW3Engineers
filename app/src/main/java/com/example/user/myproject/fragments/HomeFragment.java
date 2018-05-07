package com.example.user.myproject.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.myproject.R;
import com.example.user.myproject.activities.ProfileActivity;
import com.example.user.myproject.adapter.DevDataAdapter;
import com.example.user.myproject.database.DBHelper;
import com.example.user.myproject.listeners.DevDataAdapterListener;
import com.example.user.myproject.models.Developer;
import com.example.user.myproject.others.FinalVariables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by USER on 2/15/2018.
 */

public class HomeFragment extends Fragment implements DevDataAdapterListener{
    private static View view;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DevDataAdapter mAdapter;

    private List<Developer> developers;
    private DBHelper dbHelper;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        developers = dbHelper.getDevelopers(FinalVariables.TABLE_DEVELOPERS);
        if(developers.size() > 1) shortDataByPosition(developers);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DevDataAdapter(getContext(), this, developers);
        mRecyclerView.setAdapter(mAdapter);
    }

    // Initiate Views
    private void initViews() {
        mRecyclerView = view.findViewById(R.id.rc_all_dev);

        developers = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public void onDevClick(int position, Developer developer) {
        //Toast.makeText(getContext(), "Hello " + developer.getName() + " :" + developer.getPosition(),
        //        Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("DATA", developer);
        startActivity(intent);
    }

    public void shortDataByPosition(List<Developer> developers){
        Collections.sort(developers, new Comparator<Developer>(){
            public int compare(Developer obj1, Developer obj2) {
                //Ascending order
                //return Integer.valueOf(obj1.getPosition()).compareTo(obj2.getPosition());

                //Descending order
                return Integer.valueOf(obj2.getPosition()).compareTo(obj1.getPosition());
            }
        });
    }
}
