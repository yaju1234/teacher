package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResClass {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<HomeWorkClassModel> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<HomeWorkClassModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<HomeWorkClassModel> result) {
        this.result = result;
    }
}
