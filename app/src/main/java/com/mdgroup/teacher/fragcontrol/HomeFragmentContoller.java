package com.mdgroup.teacher.fragcontrol;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdgroup.teacher.ManagerLeaveHandler;
import com.mdgroup.teacher.R;
import com.mdgroup.teacher.TestUploadingManager;
import com.mdgroup.teacher.prefControl.PrefManager;
import com.mdgroup.teacher.utilityschool.UtilityFunction;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * @Gaurav Mangal
 */

public class HomeFragmentContoller extends Fragment {
    GridView list;
    public static String[] osNameList = {
            "Attendance",
            "Leaves",
            "Tests",
            "Result",
            "Message",
            "Time Table",
            "Events",
            "Homework",
    };
    public static int[] osImages = {
            R.drawable.at,
            R.drawable.at2,
            R.drawable.at3,
            R.drawable.at4,
            R.drawable.at5,
            R.drawable.at6,
            R.drawable.at1,
            R.drawable.at7,};
    View v;
    private ViewPager viewPager;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN = {R.drawable.slider_4, R.drawable.slider_5, R.drawable.slider_6, R.drawable.slider_7, R.drawable.slider_1};
    //private static final Integer[] XMEN = {R.drawable.ac1, R.drawable.ac7, R.drawable.ac4, R.drawable.ac5, R.drawable.ac6};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    Timer swipeTimer = new Timer();
    final Handler handler = new Handler();
     Runnable Update  = new Runnable() {
        public void run() {
            if (currentPage == XMEN.length) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);
        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_dash,container,false);
        list = (GridView) v.findViewById(R.id.gv);
        list.setAdapter(new CustomAdapter(getActivity(), osNameList, osImages));
       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    switchFragment(new Attendacence_StudentManager());
                }
                else if (position==1){
                    switchFragment(new ManagerLeaveHandler());
                }
                else if (position==2){
                    switchFragment(new TestUploadingManager());
                }
                else if (position==3){
                    switchFragment(new ResultManagerHandler());
                }
                else if (position==4){
                    switchFragment(new ChatHandlerManager());
                }
                else if (position==5){
                    UtilityFunction.showCenteredToast(getActivity(), "Postion 5");
                }
                else if (position==6){
                    switchFragment(new FragmentUploadPhotos());
                }
                else if (position==7){
                    switchFragment(new HomeworkUpload());
                }


            }
        });*/
        init();
        return v;
    }

    private void switchFragment(Fragment fragment) {
        handler.removeCallbacks(Update);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).addToBackStack(null).commit();
    }

    private void init() {
        XMENArray.clear();
        currentPage = 0;
        for (int i = 0; i < XMEN.length; i++)
            XMENArray.add(XMEN[i]);
        mPager = (ViewPager)v.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getActivity(), XMENArray));
        CircleIndicator indicator = (CircleIndicator)v.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager


        if(!PrefManager.flag){
            PrefManager.flag = true;
           // Toast.makeText(getActivity(), "AAAAAA", Toast.LENGTH_SHORT).show();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3500, 3500);

        }

    }
    public class MyAdapter extends PagerAdapter {
        private ArrayList<Integer> images;
        private LayoutInflater inflater;
        private Context context;

        public MyAdapter(Context context, ArrayList<Integer> images) {
            this.context = context;
            this.images = images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View myImageLayout = inflater.inflate(R.layout.slide, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.image);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }


    public class CustomAdapter extends BaseAdapter {
        String[] result;
        Context context;
        int [] imageId;
        private  LayoutInflater inflater=null;
        public CustomAdapter(FragmentActivity mainActivity, String[] osNameList, int[] osImages) {
            // TODO Auto-generated constructor stub
            result=osNameList;
            context=mainActivity;
            imageId=osImages;
            inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return result.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView os_text;
            ImageView os_img;
            CardView card_view;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.sample_gridlayout, null);
            holder.os_text =(TextView) rowView.findViewById(R.id.os_texts);
            holder.os_img =(ImageView) rowView.findViewById(R.id.os_images);
            holder.card_view =(CardView) rowView.findViewById(R.id.card_view);

            holder.os_text.setText(result[position]);
            holder.os_img.setImageResource(imageId[position]);

            holder.os_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position==0){
                        switchFragment(new Attendacence_StudentManager());
                    }
                    else if (position==1){
                        switchFragment(new ManagerLeaveHandler());
                    }
                    else if (position==2){
                        switchFragment(new TestUploadingManager());
                    }
                    else if (position==3){
                        switchFragment(new ResultManagerHandler());
                    }
                    else if (position==4){
                        switchFragment(new ChatHandlerManager());
                    }
                    else if (position==5){
                        UtilityFunction.showCenteredToast(getActivity(), "Postion 5");
                    }
                    else if (position==6){
                        switchFragment(new FragmentUploadPhotos());
                    }
                    else if (position==7){
                        switchFragment(new HomeworkUpload());
                    }
                }
            });

            holder.os_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position==0){
                        switchFragment(new Attendacence_StudentManager());
                    }
                    else if (position==1){
                        switchFragment(new ManagerLeaveHandler());
                    }
                    else if (position==2){
                        switchFragment(new TestUploadingManager());
                    }
                    else if (position==3){
                        switchFragment(new ResultManagerHandler());
                    }
                    else if (position==4){
                        switchFragment(new ChatHandlerManager());
                    }
                    else if (position==5){
                        UtilityFunction.showCenteredToast(getActivity(), "Postion 5");
                    }
                    else if (position==6){
                        switchFragment(new FragmentUploadPhotos());
                    }
                    else if (position==7){
                        switchFragment(new HomeworkUpload());
                    }
                }
            });

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position==0){
                        switchFragment(new Attendacence_StudentManager());
                    }
                    else if (position==1){
                        switchFragment(new ManagerLeaveHandler());
                    }
                    else if (position==2){
                        switchFragment(new TestUploadingManager());
                    }
                    else if (position==3){
                        switchFragment(new ResultManagerHandler());
                    }
                    else if (position==4){
                        switchFragment(new ChatHandlerManager());
                    }
                    else if (position==5){
                        UtilityFunction.showCenteredToast(getActivity(), "Postion 5");
                    }
                    else if (position==6){
                        switchFragment(new FragmentUploadPhotos());
                    }
                    else if (position==7){
                        switchFragment(new HomeworkUpload());
                    }
                }
            });

            return rowView;
        }

    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
*/
}
