package com.mdgroup.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Gaurav on 03/05/2017.
 */


public class SchoolLEave implements Serializable {

    @SerializedName("STUDENT_ADMISSION_NO")
    @Expose
    private String STUDENT_ADMISSION_NO;

    @SerializedName("STUDENT_FIRST_NAME")
    @Expose
    private String STUDENT_FIRST_NAME;


    @SerializedName("STUDENT_LAST_NAME")
    @Expose
    private String STUDENT_LAST_NAME;


    @SerializedName("LEAVES_RECEIVER")
    @Expose
    private String LEAVES_RECEIVER;


    @SerializedName("LEAVES_SUBJECT")
    @Expose
    private String LEAVES_SUBJECT;


    @SerializedName("LEAVES_FROMDATE")
    @Expose
    private String LEAVES_FROMDATE;




    @SerializedName("LEAVES_TODATE")
    @Expose
    private String LEAVES_TODATE;



    @SerializedName("LEAVES_BODY")
    @Expose
    private String LEAVES_BODY;


    @SerializedName("LEAVES_SINGLEDAY")
    @Expose
    private String LEAVES_SINGLEDAY;

    public String getSTUDENT_ADMISSION_NO() {
        return STUDENT_ADMISSION_NO;
    }

    public void setSTUDENT_ADMISSION_NO(String STUDENT_ADMISSION_NO) {
        this.STUDENT_ADMISSION_NO = STUDENT_ADMISSION_NO;
    }

    public String getSTUDENT_FIRST_NAME() {
        return STUDENT_FIRST_NAME;
    }

    public void setSTUDENT_FIRST_NAME(String STUDENT_FIRST_NAME) {
        this.STUDENT_FIRST_NAME = STUDENT_FIRST_NAME;
    }

    public String getSTUDENT_LAST_NAME() {
        return STUDENT_LAST_NAME;
    }

    public void setSTUDENT_LAST_NAME(String STUDENT_LAST_NAME) {
        this.STUDENT_LAST_NAME = STUDENT_LAST_NAME;
    }

    public String getLEAVES_RECEIVER() {
        return LEAVES_RECEIVER;
    }

    public void setLEAVES_RECEIVER(String LEAVES_RECEIVER) {
        this.LEAVES_RECEIVER = LEAVES_RECEIVER;
    }

    public String getLEAVES_SUBJECT() {
        return LEAVES_SUBJECT;
    }

    public void setLEAVES_SUBJECT(String LEAVES_SUBJECT) {
        this.LEAVES_SUBJECT = LEAVES_SUBJECT;
    }

    public String getLEAVES_FROMDATE() {
        return LEAVES_FROMDATE;
    }

    public void setLEAVES_FROMDATE(String LEAVES_FROMDATE) {
        this.LEAVES_FROMDATE = LEAVES_FROMDATE;
    }

    public String getLEAVES_TODATE() {
        return LEAVES_TODATE;
    }

    public void setLEAVES_TODATE(String LEAVES_TODATE) {
        this.LEAVES_TODATE = LEAVES_TODATE;
    }

    public String getLEAVES_BODY() {
        return LEAVES_BODY;
    }

    public void setLEAVES_BODY(String LEAVES_BODY) {
        this.LEAVES_BODY = LEAVES_BODY;
    }

    public String getLEAVES_SINGLEDAY() {
        return LEAVES_SINGLEDAY;
    }

    public void setLEAVES_SINGLEDAY(String LEAVES_SINGLEDAY) {
        this.LEAVES_SINGLEDAY = LEAVES_SINGLEDAY;
    }

    public SchoolLEave() {
    }
}
