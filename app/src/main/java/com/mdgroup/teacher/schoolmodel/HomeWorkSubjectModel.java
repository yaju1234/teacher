package com.mdgroup.teacher.schoolmodel;

import java.io.Serializable;

public class HomeWorkSubjectModel implements Serializable {

	private static final long serialVersionUID = 1L;
	public String SUBJECTS_ID;
	public String SUBJECTS_NAME;


	public HomeWorkSubjectModel(){
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSUBJECTS_ID() {
		return SUBJECTS_ID;
	}

	public void setSUBJECTS_ID(String SUBJECTS_ID) {
		this.SUBJECTS_ID = SUBJECTS_ID;
	}

	public String getSUBJECTS_NAME() {
		return SUBJECTS_NAME;
	}

	public void setSUBJECTS_NAME(String SUBJECTS_NAME) {
		this.SUBJECTS_NAME = SUBJECTS_NAME;
	}
}
