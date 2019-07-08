package com.mdgroup.teacher.fragcontrol;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdgroup.teacher.R;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallClassAdapter;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallSectionAdapter;
import com.mdgroup.teacher.api.urlsApimanage.ApiClient;
import com.mdgroup.teacher.interfaces.custom.ApiIHandler;
import com.mdgroup.teacher.prefControl.PrefManager;
import com.mdgroup.teacher.schoolmodel.AttendanceMark;
import com.mdgroup.teacher.schoolmodel.HomeWorkClassModel;
import com.mdgroup.teacher.schoolmodel.HomeWorkSectionModel;
import com.mdgroup.teacher.schoolmodel.ResAttendance;
import com.mdgroup.teacher.schoolmodel.ResClass;
import com.mdgroup.teacher.schoolmodel.ResSection;
import com.mdgroup.teacher.schoolmodel.SingleResponse;
import com.mdgroup.teacher.utilityschool.UtilityFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Gaurav Mangal
 */
public class Attendacence_StudentManager extends Fragment {
    private Button select_submit, class_photos;
    Button class_section;
    PrefManager prefManager;
    private RecyclerView recyclerView;
    private List<AttendanceMark> attendanceMarks;
    RecyclerView.LayoutManager mLayoutManager;
    private AdapterStudentList adapterStudentList;
    public List<AttendanceMark> albumList;
    public Attendacence_StudentManager() {
        // Required empty public constructor
    }
    ArrayList<HomeWorkClassModel> arr_hw_classmodel;
    ArrayList<HomeWorkSectionModel> arr_hw_sectionmodel;

    TextView student_not_availabe;
    Button date_pic,submit_attendance;
    String selected_dateattend = "";

    Dialog classDialog, sectionDialog;
    String selected_class_id = "", selected_class_name = "";
    String selected_section_id = "", selected_section_name = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.studentattendacenmanager, container, false);
        prefManager = new PrefManager(getActivity());
        class_section = (Button) view.findViewById(R.id.class_section);
        date_pic = (Button) view.findViewById(R.id.date_pic);
        student_not_availabe = (TextView) view.findViewById(R.id.student_not_availabe);

        select_submit = (Button) view.findViewById(R.id.select_submit);
        class_photos = (Button) view.findViewById(R.id.class_photos);
        attendanceMarks = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_liststudentattendace);

        mLayoutManager = new LinearLayoutManager(getActivity());

        submit_attendance= (Button) view.findViewById(R.id.submit_attendance_at);
        arr_hw_classmodel = new ArrayList<HomeWorkClassModel>();
        arr_hw_sectionmodel = new ArrayList<HomeWorkSectionModel>();


        arr_hw_classmodel = setarr_hw_classmodel_add_array();

        submit_attendance.setVisibility(View.GONE);
        select_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                // uploadonServerDataWithimages();
                if (selected_dateattend.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select attendance date", Toast.LENGTH_LONG).show();
                } else if (selected_class_id.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select class name", Toast.LENGTH_LONG).show();
                }
                else if (selected_section_id.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select section name", Toast.LENGTH_LONG).show();
                }
                else {
                    getstudentDataFROMSERVER();
                }
            }
        });
        submit_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    System.out.println("Type Att -albumList size-:"+albumList.size());
                    if(albumList.size()>0)
                    {
                        for(int i=0;i<albumList.size();i++)
                        {
                            System.out.println("Type Att --:"+albumList.get(i).getSTUDENT_ATTEND());
                            System.out.println("Type selected_dateattend --:"+selected_dateattend);
                            System.out.println("User Id --:"+prefManager.getCheckUser_id());
                            sendAttendanceToServer(i,albumList.get(i).getSTUDENT_USN_NUMBER(),albumList.get(i).getSTUDENT_ATTEND(),selected_dateattend,"February");
                            //sendAttendanceToServer(i,prefManager.getCheckUser_id(),albumList.get(i).getSTUDENT_ATTEND(),selected_dateattend,"January");
                        }
                    }
                    else
                    {
                        UtilityFunction.showCenteredToast(getActivity(), "Please first to get student data.");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        class_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClassNAmeSelector();
            }
        });

        class_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selected_class_id.equalsIgnoreCase("")) {
                    UtilityFunction.showCenteredToast(getActivity(), "Please select class name using section.");
                } else {
                    openSEctionNAmeSelector();
                }
            }
        });
        date_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new DATEPICSELECTEDFRAGMENT();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        return view;
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
    }


    @SuppressLint("ValidFragment")
    public class DATEPICSELECTEDFRAGMENT extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm + 1, dd);
        }

        public void populateSetDate(int year, int month, int day) {
            selected_dateattend = year + "/" + month + "/" + day;
            String selected_buttondate = day + "-" + month + "-" + year;
            date_pic.setText(selected_buttondate);
        }
    }
    public ArrayList<ResAttendance> getstudentDataFROMSERVER() {
        ArrayList<ResAttendance> HomeWorkClassModel = new ArrayList<ResAttendance>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResAttendance> call = apiService.getstudentssectionwise_list(selected_class_id,selected_section_id);
        call.enqueue(new Callback<ResAttendance>() {
            @Override
            public void onResponse(Call<ResAttendance> call, Response<ResAttendance> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    attendanceMarks = response.body().getResult();
                    System.out.println("size data selected_class_id :"+selected_class_id);
                    System.out.println("size data selected_section_id :"+selected_section_id);
                    if(attendanceMarks !=null && attendanceMarks.size()>0)
                    {
                        adapterStudentList = new AdapterStudentList(getActivity(), attendanceMarks);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapterStudentList);
                        submit_attendance.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        UtilityFunction.showCenteredToast(getActivity(),"No Student found.");
                    }
                }
            }
            @Override
            public void onFailure(Call<ResAttendance> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return HomeWorkClassModel;
    }


    /*public void getstudentDataFROMSERVER() {
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                // what to do before background task
                dialog.setTitle("Loading Student...");
                dialog.setMessage("Please wait.");
                dialog.setIndeterminate(true);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                // do your background operation here
                try {
                    Thread.sleep(2000);

                } catch (Exception e)

                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // what to do when background task is completed
                dialog.dismiss();



            }

            ;

            @Override
            protected void onCancelled() {
                dialog.dismiss();
                super.onCancelled();
            }
        };
        updateTask.execute((Void[]) null);
    }*/
   /* @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    if(bitmap !=null)
                    {
                        Toast.makeText(getActivity(), "One Photo Selected",
                                Toast.LENGTH_LONG).show();
                        btnCapturePicture.setText(String.valueOf("1").concat(" Photos Selected"));
                    }
                    // Log.d(TAG, String.valueOf(bitmap));

                  *//*  ImageView imageView = (ImageView) findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        //imgPreview.setImageBitmap(bitmap);
    }

    */

    /**
     * Display image from a path to ImageView
     *//*
    private void previewCapturedImage() {
        try {
            //imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            //imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
*/
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

   /* public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }*/

   /* private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }*/

    class AdapterStudentList extends RecyclerView.Adapter<AdapterStudentList.MyViewHolder> {

        private Context mContext;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name_st,sentserver;
            public ImageView image_request;
            public ProgressBar pb_image_request;
            RelativeLayout row_news;
            RadioGroup rg;
            RadioButton rb1,rb2,rb3;

            public MyViewHolder(View view) {
                super(view);
                name_st = (TextView) view.findViewById(R.id.name_st);
                sentserver = (TextView) view.findViewById(R.id.sentserver);
                image_request = (ImageView) view.findViewById(R.id.image_request);
                pb_image_request = (ProgressBar) view.findViewById(R.id.pb_image_request);
                row_news = (RelativeLayout) view.findViewById(R.id.row_news);
                rg=(RadioGroup)view.findViewById(R.id.viewrg);
                rb1 = (RadioButton) view.findViewById(R.id.radio_present);
                rb2 = (RadioButton) view.findViewById(R.id.radio_absent);
                rb3 = (RadioButton) view.findViewById(R.id.radio_Leave);
            }
        }

        public AdapterStudentList(Context mContext, List<AttendanceMark> albumL) {
            this.mContext = mContext;
            albumList = albumL;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.attendance_row_tick, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {



            final AttendanceMark album = albumList.get(position);

            holder.name_st.setText(album.getSTUDENT_FIRST_NAME());
            album.setSTUDENT_ATTEND("P");
//            final String value = ((RadioButton) holder.rg.findViewById(holder.rg.getCheckedRadioButtonId() )).getText().toString();
//
//            holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//            {
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                    Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
//                }
//            });


            //holder.rb1.setChecked(true);
            holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   /* int checkedId_sec = group.getCheckedRadioButtonId();

                    //Text of that id
                    if(checkedId > 0) {  //condition to check that at radio button is selected.
                        String text = holder.rb1.getText().toString();
                        Toast.makeText(getActivity(), "CALL:"+checkedId, Toast.LENGTH_SHORT).show();
                    }*/
                    switch(checkedId){
                        case R.id.radio_present:
                            // do operations specific to this selection
                            AttendanceMark am  = new AttendanceMark();
                            am.setSTUDENT_ATTEND("P");
                            albumList.set(position, am);
                            holder.rb1.setChecked(true);
                            holder.rb2.setChecked(false);
                            holder.rb3.setChecked(false);
                            break;
                        case R.id.radio_absent:
                            // do operations specific to this selection
                            AttendanceMark am1  = new AttendanceMark();
                            am1.setSTUDENT_ATTEND("A");
                            albumList.set(position, am1);
                            holder.rb1.setChecked(false);
                            holder.rb2.setChecked(true);
                            holder.rb3.setChecked(false);
                            break;

                        case R.id.radio_Leave:
                            // do operations specific to this selection
                            AttendanceMark am3  = new AttendanceMark();
                            am3.setSTUDENT_ATTEND("L");
                            albumList.set(position, am3);
                            holder.rb1.setChecked(false);
                            holder.rb2.setChecked(false);
                            holder.rb3.setChecked(true);
                            break;
                    }
                }
            });


        }

        /**
         * Showing popup menu when tapping on 3 dots
         */

        /**
         * Click listener for popup menu items
         */
        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

            public MyMenuItemClickListener() {
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
              /*  case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;*/
                    default:
                }
                return false;
            }
        }

        @Override
        public int getItemCount() {
            return albumList.size();
        }
    }

    private void openClassNAmeSelector() {

        try {
            classDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
            classDialog
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            classDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            classDialog.setCancelable(false);


            View screen_api = LayoutInflater.from(getActivity()).inflate(
                    R.layout.screen_popup, null);
            classDialog.setContentView(screen_api);

            final ListView listviewitem_list = (ListView) classDialog
                    .findViewById(R.id.listviewitem);

            TextView header_title = (TextView) classDialog
                    .findViewById(R.id.header_title);

            header_title.setText("Select Class Type");


            final CallClassAdapter api_type = new CallClassAdapter(
                    getActivity(), R.layout.row_type, arr_hw_classmodel, selected_class_id);

            // set the adapter
            listviewitem_list.setAdapter(api_type);
            TextView btn_ok = (TextView) classDialog
                    .findViewById(R.id.btn_ok);
            TextView btn_cancel = (TextView) classDialog
                    .findViewById(R.id.btn_cancel);

            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (api_type != null) {
                        if (api_type.getSelectedPosition() > -1) {
                            String classes_id_id_value = arr_hw_classmodel.get(api_type.getSelectedPosition()).getCLASSES_ID();

                            System.out.println("Value :" + classes_id_id_value);
                            // temp
                            selected_class_id = classes_id_id_value;
                            selected_class_name = arr_hw_classmodel.get(api_type.getSelectedPosition()).getCLASSES_NAME();
                            class_photos.setText(selected_class_name);

                            selected_section_id = "";
                            selected_section_name = "";

                            class_section.setText("Select Section");
                            classDialog.dismiss();
                            arr_hw_sectionmodel = setarr_hw_sectionmodel_add_array(selected_class_id);
                        } else {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select class name");
                        }
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    classDialog.dismiss();

                }
            });

            classDialog.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void openSEctionNAmeSelector() {

        try {
            sectionDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
            sectionDialog
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            sectionDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            sectionDialog.setCancelable(false);


            View screen_api = LayoutInflater.from(getActivity()).inflate(
                    R.layout.screen_popup, null);
            sectionDialog.setContentView(screen_api);

            final ListView listviewitem_list = (ListView) sectionDialog
                    .findViewById(R.id.listviewitem);

            TextView header_title = (TextView) sectionDialog
                    .findViewById(R.id.header_title);

            header_title.setText("Select Section");

            final CallSectionAdapter api_type = new CallSectionAdapter(
                    getActivity(), R.layout.row_type, arr_hw_sectionmodel, selected_section_id);

            // set the adapter
            listviewitem_list.setAdapter(api_type);
            TextView btn_ok = (TextView) sectionDialog
                    .findViewById(R.id.btn_ok);
            TextView btn_cancel = (TextView) sectionDialog.findViewById(R.id.btn_cancel);
            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (api_type != null) {
                        if (api_type.getSelectedPosition() > -1) {
                            String sectionid_id_value = arr_hw_sectionmodel.get(api_type.getSelectedPosition()).getSECTIONS_ID();
                            System.out.println("Value :" + sectionid_id_value);
                            selected_section_id = sectionid_id_value;
                            selected_section_name = arr_hw_sectionmodel.get(api_type.getSelectedPosition()).getSECTIONS_NAME();
                            class_section.setText(selected_section_name);
                            sectionDialog.dismiss();
                        } else {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select section name");
                        }
                    }
                }
            });
            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sectionDialog.dismiss();

                }
            });

            sectionDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<HomeWorkClassModel> setarr_hw_classmodel_add_array() {
        ArrayList<HomeWorkClassModel> HomeWorkClassModel = new ArrayList<HomeWorkClassModel>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResClass> call = apiService.getclass();
        call.enqueue(new Callback<ResClass>() {
            @Override
            public void onResponse(Call<ResClass> call, Response<ResClass> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    arr_hw_classmodel = response.body().getResult();
                }
            }

            @Override
            public void onFailure(Call<ResClass> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return HomeWorkClassModel;
    }

    public ArrayList<HomeWorkSectionModel> setarr_hw_sectionmodel_add_array(String class_id) {
        ArrayList<HomeWorkSectionModel> HomeWorkClassModel = new ArrayList<HomeWorkSectionModel>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResSection> call = apiService.getsectiion(class_id);
        call.enqueue(new Callback<ResSection>() {
            @Override
            public void onResponse(Call<ResSection> call, Response<ResSection> response) {
                pDialog.dismiss();
                if (response.code() == 200) {

                    arr_hw_sectionmodel = response.body().getResult();
                }
            }

            @Override
            public void onFailure(Call<ResSection> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return HomeWorkClassModel;
    }
    public ArrayList<HomeWorkSectionModel> sendAttendanceToServer(final int i,String STUDENT_ADMISSION_NO,String ATTENDANCE_TYPE,String ATTENDANCE_DATE,String ATTENDANCE_MONTH) {
        ArrayList<HomeWorkSectionModel> HomeWorkClassModel = new ArrayList<HomeWorkSectionModel>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<SingleResponse> call = apiService.getstudentssectionwise(STUDENT_ADMISSION_NO,ATTENDANCE_TYPE,ATTENDANCE_DATE,ATTENDANCE_MONTH);
        call.enqueue(new Callback<SingleResponse>() {
            @Override
            public void onResponse(Call<SingleResponse> call, Response<SingleResponse> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    //UtilityFunction.showCenteredToast(getActivity(),"Attendance done successfully.");
                    System.out.println("Call attendance :");
                    if(i==albumList.size()-1)
                    {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Sucess")
                                .setMessage("Attendance Uploaded Successfully.")
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        switchFragment(new Attendacence_StudentManager());
                                    }
                                }).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SingleResponse> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });
        return HomeWorkClassModel;
    }



}
