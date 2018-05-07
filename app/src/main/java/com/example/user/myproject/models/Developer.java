package com.example.user.myproject.models;

import java.io.Serializable;

/**
 * Created by USER on 2/15/2018.
 */

public class Developer implements Serializable {
    private int id;
    private String name;
    private int position;
    private String joiningDate;
    private String field;
    private String email;
    private String aboutMe;
    private String imgPath;

    public Developer() {
        id = 0;
        name = "";
        position = 0;
        joiningDate = "";
        field = "";
        email = "";
        aboutMe = "";
        imgPath = "";
    }

    public Developer(String name, int position, String joiningDate, String field, String email, String aboutMe, String imgPath) {
        this.name = name;
        this.position = position;
        this.joiningDate = joiningDate;
        this.field = field;
        this.email = email;
        this.aboutMe = aboutMe;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public static boolean isAllDataThere(Developer developer){
        if(developer == null) return false;

        if(developer.getName().isEmpty() || developer.getPosition() == 0 || developer.getJoiningDate().isEmpty() ||
                developer.getField().isEmpty() || developer.getAboutMe().isEmpty() || developer.getEmail().isEmpty() ||
                developer.getImgPath().isEmpty()){

            return false;
        }

        return true;
    }
}
