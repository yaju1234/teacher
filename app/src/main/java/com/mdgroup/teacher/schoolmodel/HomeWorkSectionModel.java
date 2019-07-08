package com.mdgroup.teacher.schoolmodel;

import java.io.Serializable;

public class HomeWorkSectionModel implements Serializable {

	private static final long serialVersionUID = 1L;
	public String SECTIONS_ID;
	public String SECTIONS_NAME;


	public HomeWorkSectionModel(){
    }

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSECTIONS_ID() {
		return SECTIONS_ID;
	}

	public void setSECTIONS_ID(String SECTIONS_ID) {
		this.SECTIONS_ID = SECTIONS_ID;
	}

	public String getSECTIONS_NAME() {
		return SECTIONS_NAME;
	}

	public void setSECTIONS_NAME(String SECTIONS_NAME) {
		this.SECTIONS_NAME = SECTIONS_NAME;
	}
}
