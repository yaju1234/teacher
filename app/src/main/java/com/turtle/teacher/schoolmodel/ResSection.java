package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResSection {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<HomeWorkSectionModel> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<HomeWorkSectionModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<HomeWorkSectionModel> result) {
        this.result = result;
    }
}
