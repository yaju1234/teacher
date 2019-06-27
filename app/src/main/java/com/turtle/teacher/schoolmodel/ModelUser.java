package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ModelUser {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("TEACHERS_ID")
    @Expose
    private String TEACHERS_ID;

    @SerializedName("TEACHERS_NAME")
    @Expose
    private String TEACHERS_NAME;

    @SerializedName("TEACHERS_FATHER_NAME")
    @Expose
    private String TEACHERS_FATHER_NAME;

    @SerializedName("TEACHERS_MOBILE_NO")
    @Expose
    private String TEACHERS_MOBILE_NO;

    @SerializedName("TEACHERS_EMAIL")
    @Expose
    private String TEACHERS_EMAIL;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("TEACHERS_ADDRESS")
    @Expose
    private String TEACHERS_ADDRESS;

    @SerializedName("TEACHERS_QUALIFICATION")
    @Expose
    private String TEACHERS_QUALIFICATION;


    @SerializedName("TEACHERS_EXPERIENCE")
    @Expose
    private String TEACHERS_EXPERIENCE;

    @SerializedName("TEACHERS_FILE_PATH")
    @Expose
    private String TEACHERS_FILE_PATH;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTEACHERS_ID() {
        return TEACHERS_ID;
    }

    public void setTEACHERS_ID(String TEACHERS_ID) {
        this.TEACHERS_ID = TEACHERS_ID;
    }

    public String getTEACHERS_NAME() {
        return TEACHERS_NAME;
    }

    public void setTEACHERS_NAME(String TEACHERS_NAME) {
        this.TEACHERS_NAME = TEACHERS_NAME;
    }

    public String getTEACHERS_FATHER_NAME() {
        return TEACHERS_FATHER_NAME;
    }

    public void setTEACHERS_FATHER_NAME(String TEACHERS_FATHER_NAME) {
        this.TEACHERS_FATHER_NAME = TEACHERS_FATHER_NAME;
    }

    public String getTEACHERS_MOBILE_NO() {
        return TEACHERS_MOBILE_NO;
    }

    public void setTEACHERS_MOBILE_NO(String TEACHERS_MOBILE_NO) {
        this.TEACHERS_MOBILE_NO = TEACHERS_MOBILE_NO;
    }

    public String getTEACHERS_EMAIL() {
        return TEACHERS_EMAIL;
    }

    public void setTEACHERS_EMAIL(String TEACHERS_EMAIL) {
        this.TEACHERS_EMAIL = TEACHERS_EMAIL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTEACHERS_ADDRESS() {
        return TEACHERS_ADDRESS;
    }

    public void setTEACHERS_ADDRESS(String TEACHERS_ADDRESS) {
        this.TEACHERS_ADDRESS = TEACHERS_ADDRESS;
    }

    public String getTEACHERS_QUALIFICATION() {
        return TEACHERS_QUALIFICATION;
    }

    public void setTEACHERS_QUALIFICATION(String TEACHERS_QUALIFICATION) {
        this.TEACHERS_QUALIFICATION = TEACHERS_QUALIFICATION;
    }

    public String getTEACHERS_EXPERIENCE() {
        return TEACHERS_EXPERIENCE;
    }

    public void setTEACHERS_EXPERIENCE(String TEACHERS_EXPERIENCE) {
        this.TEACHERS_EXPERIENCE = TEACHERS_EXPERIENCE;
    }

    public String getTEACHERS_FILE_PATH() {
        return TEACHERS_FILE_PATH;
    }

    public void setTEACHERS_FILE_PATH(String TEACHERS_FILE_PATH) {
        this.TEACHERS_FILE_PATH = TEACHERS_FILE_PATH;
    }

    public ModelUser() {
    }
}
