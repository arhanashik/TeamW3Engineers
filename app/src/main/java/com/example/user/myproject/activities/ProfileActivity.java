package com.example.user.myproject.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myproject.R;
import com.example.user.myproject.database.DBHelper;
import com.example.user.myproject.models.Developer;
import com.example.user.myproject.others.FinalVariables;
import com.example.user.myproject.utils.ImageUtils;

public class ProfileActivity extends AppCompatActivity {
    private ImageView devImg;
    private TextView devName, devPosition, devJoiningDate, devField, devEmail, devAboutMe;

    private Developer developer;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        developer  = (Developer) getIntent().getSerializableExtra("DATA");
        dbHelper = new DBHelper(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeDevImage();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        devImg = findViewById(R.id.dev_img);
        devName = findViewById(R.id.dev_name);
        devPosition = findViewById(R.id.dev_position);
        devJoiningDate = findViewById(R.id.dev_joining_date);
        devField = findViewById(R.id.dev_field);
        devEmail = findViewById(R.id.dev_email);
        devAboutMe = findViewById(R.id.dev_about);

        if(developer != null){
            setTitle(developer.getName());
            devName.setText("Name: " + developer.getName());
            devPosition.setText("Position: " + developer.getPosition()+"");
            devJoiningDate.setText(developer.getJoiningDate());
            devField.setText("Development Field: " + developer.getField());
            devEmail.setText("Email: " + developer.getEmail());
            devAboutMe.setText("Description: " + developer.getAboutMe());

            Bitmap bitmap = ImageUtils.getImageFileFromSDCard(developer.getImgPath());
            if(bitmap != null) {
                devImg.setImageBitmap(bitmap);
            }
            else Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeDevImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, FinalVariables.REQUEST_CODE_CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case FinalVariables.REQUEST_CODE_CAPTURE_IMAGE:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    if(bitmap != null){
                        String storeFilename = developer.getImgPath();
                        if(storeFilename == null || storeFilename.isEmpty()) storeFilename = "photo_" + ImageUtils.currentDateFormat() + ".jpg";
                        ImageUtils.storeCameraPhotoInSDCard(bitmap, storeFilename);
                        devImg.setImageBitmap(bitmap);
                        dbHelper.updateImagePath(FinalVariables.TABLE_DEVELOPERS, developer.getId(), storeFilename);
                        Toast.makeText(this, "Image changed!", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(this, "Image not captured!", Toast.LENGTH_SHORT).show();

                break;

            default:
                break;
        }
    }
}
