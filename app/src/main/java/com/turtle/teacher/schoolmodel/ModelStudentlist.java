package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav on 05/02/2017.
 */

public class ModelStudentlist implements Serializable {
    @SerializedName("STUDENT_ADMISSION_NO")
    @Expose
    private String STUDENT_ADMISSION_NO;

    @SerializedName("STUDENT_FIRST_NAME")
    @Expose
    private String STUDENT_FIRST_NAME;

    @SerializedName("CLASSES_NAME")
    @Expose
    private String CLASSES_NAME;

    @SerializedName("SECTIONS_NAME")
    @Expose
    private String SECTIONS_NAME;


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

    public String getCLASSES_NAME() {
        return CLASSES_NAME;
    }

    public void setCLASSES_NAME(String CLASSES_NAME) {
        this.CLASSES_NAME = CLASSES_NAME;
    }

    public String getSECTIONS_NAME() {
        return SECTIONS_NAME;
    }

    public void setSECTIONS_NAME(String SECTIONS_NAME) {
        this.SECTIONS_NAME = SECTIONS_NAME;
    }
}
