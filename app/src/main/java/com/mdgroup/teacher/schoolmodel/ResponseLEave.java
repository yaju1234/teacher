package com.mdgroup.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Gaurav on 13/02/2017.
 */
public class ResponseLEave {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    ArrayList<SchoolLEave> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SchoolLEave> getResult() {
        return result;
    }

    public void setResult(ArrayList<SchoolLEave> result) {
        this.result = result;
    }
}
