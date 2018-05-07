package com.example.user.myproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.myproject.models.Developer;
import com.example.user.myproject.others.FinalVariables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/15/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private Context context;

    private static final String DATABASE_NAME = "teamw3engineers.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FinalVariables.CREATE_TABLE_DEVELOPERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + FinalVariables.TABLE_DEVELOPERS);
        // Creating tables again
        onCreate(db);
    }

    // Checking table has data or not
    public int getRowCount(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = -1;
        Cursor cursor  = db.rawQuery("SELECT * FROM " + tableName, null);

        count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    // Checking table has the given data or not
    public boolean isInserted(String tableName, String index, String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = -1;
        Cursor cursor  = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + index + "=\"" + value.trim()+"\";", null);

        count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0) return true;

        return false;
    }

    public long insertDeveloper(Developer developer, String tableName){
        //int curr_row = getRowCount(FinalVariables.TABLE_CHAT_LIST);
        boolean isInserted = isInserted(tableName, FinalVariables.KEY_EMAIL, developer.getEmail());

        SQLiteDatabase db = this.getWritableDatabase();

        long res = 0;
        ContentValues values = new ContentValues();
        values.put(FinalVariables.KEY_NAME, developer.getName());
        values.put(FinalVariables.KEY_POSITION, developer.getPosition());
        values.put(FinalVariables.KEY_JOINING_DATE, developer.getJoiningDate());
        values.put(FinalVariables.KEY_DEVELOPMENT_FIELD, developer.getField());
        values.put(FinalVariables.KEY_EMAIL, developer.getEmail());
        values.put(FinalVariables.KEY_ABOUT_ME, developer.getAboutMe());
        values.put(FinalVariables.KEY_IMAGE_PATH, developer.getImgPath());

        if(!isInserted){
            // Inserting Row
            res = db.insert(tableName, null, values);
            Log.d("Developer inserted: ",developer.getName());
        }else{
            res = -1;
            Log.d("Developer exists: ",developer.getName());
        }

        // Closing database connection
        db.close();

        return res;
    }

    public boolean updateImagePath(String tableName, int id, String imagePath){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FinalVariables.KEY_IMAGE_PATH, imagePath);

        int updated = db.update(tableName, values, FinalVariables.KEY_ID + "=?",
                new String[] { String.valueOf(id) });

        // Closing database connection
        db.close();

        return updated > 0;
    }

    // Getting chat list
    public List<Developer> getDevelopers(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Developer> developers = new ArrayList<>();
        Developer developer;

        String query = "SELECT * FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null){
            if(cursor.moveToFirst()){
                do {
                    developer = new Developer();
                    developer.setId(cursor.getInt(cursor.getColumnIndex(FinalVariables.KEY_ID)));
                    developer.setName(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_NAME)));
                    developer.setPosition(cursor.getInt(cursor.getColumnIndex(FinalVariables.KEY_POSITION)));
                    developer.setJoiningDate(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_JOINING_DATE)));
                    developer.setField(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_DEVELOPMENT_FIELD)));
                    developer.setEmail(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_EMAIL)));
                    developer.setAboutMe(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_ABOUT_ME)));
                    developer.setImgPath(cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_IMAGE_PATH)));

                    Log.d("Developer retrieving: ",developer.getName());
                    developers.add(developer);

                }while (cursor.moveToNext());

            }
        }

        // return chat list
        cursor.close();
        db.close();
        return developers;
    }

    public String getTeamLeader(String tableName, String team){
        SQLiteDatabase db = this.getReadableDatabase();
        String teamLeader = "";

        String query = "SELECT " + FinalVariables.KEY_NAME + " FROM " + tableName
                + " WHERE "+ FinalVariables.KEY_DEVELOPMENT_FIELD + "='" + team
                + "' AND " + FinalVariables.KEY_POSITION + "=" + FinalVariables.TEAM_LEADER +";";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null){
            if(cursor.moveToFirst()){
                teamLeader = cursor.getString(cursor.getColumnIndex(FinalVariables.KEY_NAME));
            }
        }

        // return chat list
        cursor.close();
        db.close();

        return teamLeader;
    }

    public boolean deleteDeveloper(String tableName, String id){
        SQLiteDatabase db = this.getWritableDatabase();

        int deleted = db.delete(tableName, FinalVariables.KEY_ID + " = ?",
                new String[] { id });

        // Closing database connection
        db.close();

        return deleted > 0;
    }
}
