package com.mdgroup.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ResTestName {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<TestNameList> result;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public ArrayList<TestNameList> getResult() {
        return result;
    }

    public void setResult(ArrayList<TestNameList> result) {
        this.result = result;
    }
}
