package com.example.user.myproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.user.myproject.R;
import com.example.user.myproject.fragments.AboutUsFragment;
import com.example.user.myproject.fragments.JoinUsFragment;
import com.example.user.myproject.fragments.HomeFragment;
import com.example.user.myproject.others.FinalVariables;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;

    public static FragmentManager fragmentManager;
    public static final int left = 0;
    public static final int right = 1;
    public static int nextTransitionDirection = left;

    private static final int TIME_INTERVAL = 2500;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                .beginTransaction()
                .replace(R.id.container, new HomeFragment(),
                        FinalVariables.HomeFragment).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(getResources().getString(R.string.app_name));
                    nextTransitionDirection = left;
                    replaceFragment(new HomeFragment(),
                            FinalVariables.HomeFragment);
                    return true;
                case R.id.navigation_create_account:
                    setTitle("Join Us");
                    nextTransitionDirection = left;
                    replaceFragment(new JoinUsFragment(),
                            FinalVariables.GalleryFragment);
                    return true;
                case R.id.navigation_dev:
                    setTitle("About us");
                    nextTransitionDirection = left;
                    replaceFragment(new AboutUsFragment(),
                            FinalVariables.AboutUsFragment);
                    return true;
            }
            return false;
        }
    };

    public void replaceFragment(Fragment fragment, String tag) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment,
                    tag).commit();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            //super.onBackPressed();
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();
            return;
        }
        else {
            Snackbar.make(navigation, "Click again to exit", Snackbar.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

}
