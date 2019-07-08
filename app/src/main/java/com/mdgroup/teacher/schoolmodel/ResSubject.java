package com.mdgroup.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResSubject {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<HomeWorkSubjectModel> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<HomeWorkSubjectModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<HomeWorkSubjectModel> result) {
        this.result = result;
    }
}
