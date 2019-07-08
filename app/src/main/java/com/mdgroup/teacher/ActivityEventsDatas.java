package com.mdgroup.teacher;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mdgroup.teacher.schoolmodel.ModelEvents;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityEventsDatas extends AppCompatActivity {
    ImageView image_sub;
    static int i = 0;
    private Timer myTimer;
    ModelEvents news_data_receive;
    Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_datas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = getApplicationContext();

        CollapsingToolbarLayout toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        TextView news_details = (TextView) findViewById(R.id.news_details);
        //receive
        news_data_receive = (ModelEvents) getIntent().getSerializableExtra("events_data");
        image_sub = (ImageView) findViewById(R.id.image_sub);
        if (news_data_receive != null) {
            setTitle(news_data_receive.getEVENTS_NAME());
            news_details.setText(news_data_receive.getEVENTS_LOCATION());
            Glide.with(ActivityEventsDatas.this).load(news_data_receive.getEVENTS_SUB_IMG1()).into(image_sub);
        }
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 500, 5000);
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            System.out.println("the value id call:" + i);
            if (i < 3) {
                System.out.println("the value id:" + i);
                if (i == 0) {
                    try {
                        if (news_data_receive != null) {
                            Glide.with(mcontext).load(news_data_receive.getEVENTS_SUB_IMG1()).into(image_sub);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (i == 1) {
                    if (news_data_receive != null) {
                        Glide.with(mcontext).load(news_data_receive.getEVENTS_SUB_IMG2()).into(image_sub);
                    }
                }
                if (i == 2) {
                    try {
                        if (news_data_receive != null) {
                            Glide.with(mcontext).load(news_data_receive.getEVENTS_SUB_IMG3()).into(image_sub);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                i = 0;
                try {
                    if (news_data_receive != null) {
                        Glide.with(mcontext).load(news_data_receive.getEVENTS_SUB_IMG1()).into(image_sub);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
