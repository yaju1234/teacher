package com.mdgroup.teacher.prefControl;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {

    public static boolean flag = false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "school_manage";
    private String checkUser_id = "Check_User_Id";
    private String checklogindata = "Login_data";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getCheckUser_id() {
        return pref.getString(checkUser_id, "");
    }

    public String getChecklogindata() {
        return pref.getString(checklogindata, "");
    }

    public void setChecklogindata(String checklogidata) {
        editor.putString(checklogindata, checklogidata);
        editor.commit();
    }

    public void setCheckUser_id(String checkUserid) {
        editor.putString(checkUser_id, checkUserid);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}