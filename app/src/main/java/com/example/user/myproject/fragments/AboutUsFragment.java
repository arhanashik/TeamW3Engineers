package com.example.user.myproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.myproject.R;

/**
 * Created by USER on 2/15/2018.
 */

public class AboutUsFragment extends Fragment {
    private static View view;

    public AboutUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        //initViews();
        return view;
    }
}
