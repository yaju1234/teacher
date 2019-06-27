package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ModelTimeTable implements Serializable {
    @SerializedName("TIME_TABLE_PERIOD_NO")
    @Expose
    private String TIME_TABLE_PERIOD_NO;

    @SerializedName("TIME_TABLE_PERIOD_START_TIME")
    @Expose
    private String TIME_TABLE_PERIOD_START_TIME;


    @SerializedName("TIME_TABLE_PERIOD_END_TIME")
    @Expose
    private String TIME_TABLE_PERIOD_END_TIME;


    @SerializedName("SUBJECTS_NAME")
    @Expose
    private String SUBJECTS_NAME;

    public String getTIME_TABLE_PERIOD_NO() {
        return TIME_TABLE_PERIOD_NO;
    }

    public void setTIME_TABLE_PERIOD_NO(String TIME_TABLE_PERIOD_NO) {
        this.TIME_TABLE_PERIOD_NO = TIME_TABLE_PERIOD_NO;
    }

    public String getTIME_TABLE_PERIOD_START_TIME() {
        return TIME_TABLE_PERIOD_START_TIME;
    }

    public void setTIME_TABLE_PERIOD_START_TIME(String TIME_TABLE_PERIOD_START_TIME) {
        this.TIME_TABLE_PERIOD_START_TIME = TIME_TABLE_PERIOD_START_TIME;
    }

    public String getTIME_TABLE_PERIOD_END_TIME() {
        return TIME_TABLE_PERIOD_END_TIME;
    }

    public void setTIME_TABLE_PERIOD_END_TIME(String TIME_TABLE_PERIOD_END_TIME) {
        this.TIME_TABLE_PERIOD_END_TIME = TIME_TABLE_PERIOD_END_TIME;
    }

    public String getSUBJECTS_NAME() {
        return SUBJECTS_NAME;
    }

    public void setSUBJECTS_NAME(String SUBJECTS_NAME) {
        this.SUBJECTS_NAME = SUBJECTS_NAME;
    }
}
