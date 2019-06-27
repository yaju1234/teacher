package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gaurav on 05/02/2017.
 */
public class ResponseStudentChat {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<ModelStudentlist> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ModelStudentlist> getResult() {
        return result;
    }

    public void setResult(ArrayList<ModelStudentlist> result) {
        this.result = result;
    }
}
