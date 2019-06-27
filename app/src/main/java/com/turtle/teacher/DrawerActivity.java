package com.turtle.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.turtle.teacher.fragcontrol.Attendacence_StudentManager;
import com.turtle.teacher.fragcontrol.ChatHandlerManager;
import com.turtle.teacher.fragcontrol.FragmentUploadPhotos;
import com.turtle.teacher.fragcontrol.HomeFragmentContoller;
import com.turtle.teacher.fragcontrol.HomeworkUpload;
import com.turtle.teacher.fragcontrol.ResultManagerHandler;
import com.turtle.teacher.fragcontrol.StudentPhoto;
import com.turtle.teacher.prefControl.PrefManager;
import com.turtle.teacher.schoolmodel.ModelUser;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    PrefManager prefManager;
    TextView tv_headerScreenname, tv_headerScreendetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefManager = new PrefManager(DrawerActivity.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);

        tv_headerScreenname = (TextView) header.findViewById(R.id.tv_un);
        tv_headerScreendetails = (TextView) header.findViewById(R.id.textView);

        LinearLayout clickheader = (LinearLayout) header.findViewById(R.id.clickheader);

        clickheader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DrawerActivity.this, EditActivity.class);
                startActivity(i);
            }
        });
        String data = prefManager.getChecklogindata();

        String login_data = prefManager.getChecklogindata();

        System.out.println("login_data :" + login_data);

        Gson gson = new Gson();
        ModelUser modelstudent = gson.fromJson(data, ModelUser.class);
        tv_headerScreenname.setText(modelstudent.getTEACHERS_NAME());
        tv_headerScreendetails.setText(modelstudent.getTEACHERS_QUALIFICATION());

        switchFragment(new HomeFragmentContoller());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {

            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    @Override
    public boolean supportRequestWindowFeature(int featureId) {
        return super.supportRequestWindowFeature(featureId);
    }

    @Override
    public void supportInvalidateOptionsMenu() {
        super.supportInvalidateOptionsMenu();
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        super.onSupportActionModeStarted(mode);
    }
    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        super.onSupportActionModeFinished(mode);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.T_XX_X_1) {
            setTitle("Dashboard");
            switchFragment(new HomeFragmentContoller());
        }
      if (id == R.id.T_XX_X_2) {
            setTitle("Class Event");
            switchFragment(new FragmentUploadPhotos());
        }
       /* if (id == R.id.T_XX_X_PIC) {
            setTitle("Student Photo");
            switchFragment(new StudentPhoto());
        }*/
        if (id == R.id.T_XX_XX_3) {
            setTitle("Attendance Register");
            switchFragment(new Attendacence_StudentManager());
        }
        if (id == R.id.T_XX_XX__X_4) {
            setTitle("Assign HomeWork");
            switchFragment(new HomeworkUpload());
        }
        if (id == R.id.T_XX_XX__X_5) {
            setTitle("Class Test");
            switchFragment(new TestUploadingManager());
        }
        if (id == R.id.T_XX_XX__X_44) {
            setTitle("Tests Results");
            switchFragment(new ResultManagerHandler());
        }
        if (id == R.id.T_XX_XX__X_t_chat) {
            setTitle("Chat");
            switchFragment(new ChatHandlerManager());
        }
        if (id == R.id.T_XX_XX__X_leave) {
            setTitle("Leave");
            switchFragment(new ManagerLeaveHandler());
        }
        else if (id == R.id.id_XX1010) {
            prefManager.setChecklogindata("");
            prefManager.setCheckUser_id("");
            finish();
            Intent i = new Intent(DrawerActivity.this, LoginSlider.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
    }


}
