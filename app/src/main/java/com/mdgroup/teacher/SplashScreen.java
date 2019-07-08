package com.mdgroup.teacher;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.mdgroup.teacher.prefControl.PrefManager;

/**
 * Gaurav Mangal
 */
public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private String user_id="";
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        prefManager=new PrefManager(SplashScreen.this);

        String login_data=prefManager.getChecklogindata();
        user_id=prefManager.getCheckUser_id();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if(user_id.equalsIgnoreCase(""))
                {
                    Intent i = new Intent(SplashScreen.this, LoginSlider.class);
                    startActivity(i);
                }else
                {
                    Intent i = new Intent(SplashScreen.this, DrawerActivity.class);
                    startActivity(i);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }




}
