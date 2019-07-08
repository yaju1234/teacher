package com.mdgroup.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AttendanceMark implements Serializable {

    @SerializedName("STUDENT_USN_NUMBER")
    @Expose
    private String STUDENT_USN_NUMBER;

    @SerializedName("STUDENT_FIRST_NAME")
    @Expose
    private String STUDENT_FIRST_NAME;


    @SerializedName("STUDENT_LAST_NAME")
    @Expose
    private String STUDENT_LAST_NAME;


    @SerializedName("STUDENT_FATHER_NAME")
    @Expose
    private String STUDENT_FATHER_NAME;

    @SerializedName("STUDENT_ATTEND")
    @Expose
    private String STUDENT_ATTEND;


    public String getSTUDENT_USN_NUMBER() {
        return STUDENT_USN_NUMBER;
    }

    public void setSTUDENT_USN_NUMBER(String STUDENT_USN_NUMBER) {
        this.STUDENT_USN_NUMBER = STUDENT_USN_NUMBER;
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

    public String getSTUDENT_ATTEND() {
        return STUDENT_ATTEND;
    }

    public void setSTUDENT_ATTEND(String STUDENT_ATTEND) {
        this.STUDENT_ATTEND = STUDENT_ATTEND;
    }

    public String getSTUDENT_FATHER_NAME() {
        return STUDENT_FATHER_NAME;
    }

    public void setSTUDENT_FATHER_NAME(String STUDENT_FATHER_NAME) {
        this.STUDENT_FATHER_NAME = STUDENT_FATHER_NAME;
    }




    public AttendanceMark() {
    }
}
