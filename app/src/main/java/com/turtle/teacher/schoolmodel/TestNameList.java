package com.turtle.teacher.schoolmodel;

import java.io.Serializable;

public class TestNameList implements Serializable {

	private static final long serialVersionUID = 1L;
	public String EXAMS_ID;
	public String EXAMS_NAME;


	public TestNameList(){
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getEXAMS_ID() {
		return EXAMS_ID;
	}

	public void setEXAMS_ID(String EXAMS_ID) {
		this.EXAMS_ID = EXAMS_ID;
	}

	public String getEXAMS_NAME() {
		return EXAMS_NAME;
	}

	public void setEXAMS_NAME(String EXAMS_NAME) {
		this.EXAMS_NAME = EXAMS_NAME;
	}
}
