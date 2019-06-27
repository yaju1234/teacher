package com.turtle.teacher.schoolmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UploadObject {
    @SerializedName("message")
    @Expose
    private String success;

    public UploadObject(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }
}