package com.turtle.teacher.schoolmodel;

import java.io.Serializable;

public class HomeWorkClassModel implements Serializable {

	private static final long serialVersionUID = 1L;
	public String CLASSES_ID;
	public String CLASSES_NAME;



	public HomeWorkClassModel(){
    }


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getCLASSES_ID() {
		return CLASSES_ID;
	}

	public void setCLASSES_ID(String CLASSES_ID) {
		this.CLASSES_ID = CLASSES_ID;
	}

	public String getCLASSES_NAME() {
		return CLASSES_NAME;
	}

	public void setCLASSES_NAME(String CLASSES_NAME) {
		this.CLASSES_NAME = CLASSES_NAME;
	}
}
