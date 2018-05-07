package com.example.user.myproject.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.myproject.R;


public class WelcomeActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHideAppBarRunnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mGotoActivityRunnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        }
    };

    private ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mContentView = findViewById(R.id.fullscreen_content);
        loading = findViewById(R.id.iv_loading);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().getBooleanExtra("EXIT", false)){
            finish();
        }
        else {
            Glide.with(this).load(R.drawable.loading).into(loading);
            hide();
            delayedActivity(2500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHideHandler.removeCallbacks(mGotoActivityRunnable);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHideAppBarRunnable, UI_ANIMATION_DELAY);
    }

    private void delayedActivity(int delayMillis) {
        mHideHandler.removeCallbacks(mGotoActivityRunnable);
        mHideHandler.postDelayed(mGotoActivityRunnable, delayMillis);
    }
}
