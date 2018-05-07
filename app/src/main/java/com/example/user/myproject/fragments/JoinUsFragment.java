package com.example.user.myproject.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.myproject.R;
import com.example.user.myproject.database.DBHelper;
import com.example.user.myproject.models.Developer;
import com.example.user.myproject.others.FinalVariables;
import com.example.user.myproject.utils.ImageUtils;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by USER on 2/15/2018.
 */

public class JoinUsFragment extends Fragment implements View.OnClickListener{
    private static View view;
    private EditText imageName, joiningDate, name, developmentField, email, aboutMe;
    private Spinner position;
    private Button joinNow;
    private ImageView selectImg, selectDate;

    private DatePickerDialog datePickerDialog;

    private Developer developer;
    private Calendar calendar;
    private Palette palette;

    private DBHelper dbHelper;

    //private String strName, strJoiningDate, strDevelopmentField, strEmail, strAboutMe, strImageName;

    public JoinUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_join_us, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        dbHelper = new DBHelper(getContext());

        imageName = view.findViewById(R.id.image);
        name = view.findViewById(R.id.fullName);
        joiningDate = view.findViewById(R.id.joining_date);
        developmentField = view.findViewById(R.id.development_field);
        email = view.findViewById(R.id.email);
        aboutMe = view.findViewById(R.id.about_me);
        position = view.findViewById(R.id.select_position);
        joinNow = view.findViewById(R.id.add_developer);
        selectImg = view.findViewById(R.id.img_select);
        selectDate = view.findViewById(R.id.img_calender);

        joinNow.setOnClickListener(this);
        selectImg.setOnClickListener(this);
        selectDate.setOnClickListener(this);

        developer = new Developer();
    }

    @Override
    public void onClick(View v) {
        if(v==joinNow){
            addDeveloper();
        }
        else if(v==selectImg){
            selectImg();
        }
        else if(v==selectDate){
            selectDate();
        }
        else {

        }
    }

    private void addDeveloper() {
        developer.setName(name.getText().toString());
        developer.setPosition(position.getSelectedItemPosition());
        //developer.setJoiningDate(joiningDate.getText().toString());
        developer.setField(developmentField.getText().toString());
        developer.setEmail(email.getText().toString());
        developer.setAboutMe(aboutMe.getText().toString());
        //developer.setImgPath(imageName.getText().toString());

        if(Developer.isAllDataThere(developer)) {
            dbHelper.insertDeveloper(developer, FinalVariables.TABLE_DEVELOPERS);
            Toast.makeText(getContext(), "Developer inserted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImg(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, FinalVariables.REQUEST_CODE_CAPTURE_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case FinalVariables.REQUEST_CODE_CAPTURE_IMAGE:
                if(resultCode == RESULT_OK){
                    Bitmap bitmap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    if(bitmap != null){
                        palette = ImageUtils.createPaletteSync(bitmap);
                        Palette.Swatch vibrantSwatch = ImageUtils.checkVibrantSwatch(palette);
                        if(vibrantSwatch != null) joinNow.setBackgroundColor(vibrantSwatch.getRgb());

                        String storeFilename = "photo_" + ImageUtils.currentDateFormat() + ".jpg";
                        ImageUtils.storeCameraPhotoInSDCard(bitmap, storeFilename);
                        imageName.setText(storeFilename);
                        developer.setImgPath(storeFilename);

                        // display the image from SD Card to ImageView Control
                        //Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
                        //imageHolder.setImageBitmap(mBitmap);
                    }
                }
                else imageName.setText("Image not captured!");

                break;

            default:
                break;
        }
    }

    private void selectDate(){
        calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        joiningDate.setText("Your Joining Date: "+date);
                        developer.setJoiningDate(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
