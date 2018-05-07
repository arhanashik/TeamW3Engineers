package com.example.user.myproject.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.graphics.Palette;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by USER on 2/16/2018.
 */

public class ImageUtils {

    public ImageUtils() {
    }

    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    public static void storeCameraPhotoInSDCard(Bitmap bitmap, String imageName){
        File outputFile = new File(Environment.getExternalStorageDirectory(), imageName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + "/" + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    public static Palette.Swatch checkVibrantSwatch(Palette p) {
        Palette.Swatch vibrant = p.getVibrantSwatch();
        if (vibrant != null) {
            return vibrant;
        }

        return null;
    }
}
