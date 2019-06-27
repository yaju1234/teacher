package com.turtle.teacher;


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

import com.turtle.teacher.adptercstm.adpter.chat.CallClassAdapter;
import com.turtle.teacher.adptercstm.adpter.chat.CallSectionAdapter;
import com.turtle.teacher.api.urlsApimanage.ApiClient;
import com.turtle.teacher.fragcontrol.ResultManagerHandler;
import com.turtle.teacher.interfaces.custom.ApiIHandler;
import com.turtle.teacher.prefControl.PrefManager;
import com.turtle.teacher.schoolmodel.AttendanceMark;
import com.turtle.teacher.schoolmodel.HomeWorkClassModel;
import com.turtle.teacher.schoolmodel.HomeWorkSectionModel;
import com.turtle.teacher.schoolmodel.ResAttendance;
import com.turtle.teacher.schoolmodel.ResClass;
import com.turtle.teacher.schoolmodel.ResSection;
import com.turtle.teacher.schoolmodel.ResponseLEave;
import com.turtle.teacher.schoolmodel.SchoolLEave;
import com.turtle.teacher.schoolmodel.SingleResponse;
import com.turtle.teacher.utilityschool.UtilityFunction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Gaurav Mangal
 */
public class ManagerLeaveHandler extends Fragment {
    private Button select_submit, class_photos;
    Button class_section;
    PrefManager prefManager;
    private RecyclerView recyclerView;
    private List<SchoolLEave> leave_all;
    RecyclerView.LayoutManager mLayoutManager;
    private AdapterLEaveList adapterLEaveList;
    public List<SchoolLEave> albumList;
    public ManagerLeaveHandler() {
        // Required empty public constructor
    }

    ArrayList<HomeWorkClassModel> arr_hw_classmodel;
    ArrayList<HomeWorkSectionModel> arr_hw_sectionmodel;

    TextView student_not_availabe;
    Button submit_attendance;


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
        View view = inflater.inflate(R.layout.leavemanager, container, false);
        prefManager = new PrefManager(getActivity());
        class_section = (Button) view.findViewById(R.id.class_section);
        student_not_availabe = (TextView) view.findViewById(R.id.student_not_availabe);

        select_submit = (Button) view.findViewById(R.id.select_submit);
        class_photos = (Button) view.findViewById(R.id.class_photos);
        leave_all = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_liststudentattendace);

        mLayoutManager = new LinearLayoutManager(getActivity());



        submit_attendance= (Button) view.findViewById(R.id.submit_attendance);
        arr_hw_classmodel = new ArrayList<HomeWorkClassModel>();
        arr_hw_sectionmodel = new ArrayList<HomeWorkSectionModel>();


        arr_hw_classmodel = setarr_hw_classmodel_add_array();

        submit_attendance.setVisibility(View.GONE);
        select_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                // uploadonServerDataWithimages();
               if (selected_class_id.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select class name", Toast.LENGTH_LONG).show();
                }
                else if (selected_section_id.equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), "Please select section name", Toast.LENGTH_LONG).show();
                }
                else {
                    getleaveDataFROMSERVER();
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

                    if(arr_hw_sectionmodel.size()>0)
                    openSEctionNAmeSelector();
                    else
                    UtilityFunction.showCenteredToast(getActivity(), "No section name available.");
                }
            }
        });

        return view;
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
    }



    public ArrayList<ResAttendance> getleaveDataFROMSERVER() {
        ArrayList<ResAttendance> HomeWorkClassModel = new ArrayList<ResAttendance>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResponseLEave> call = apiService.getleave_list(selected_class_id,selected_section_id);
        call.enqueue(new Callback<ResponseLEave>() {
            @Override
            public void onResponse(Call<ResponseLEave> call, Response<ResponseLEave> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    leave_all = response.body().getResult();
                    System.out.println("size data selected_class_id :"+selected_class_id);
                    System.out.println("size data selected_section_id :"+selected_section_id);
                    if(leave_all !=null && leave_all.size()>0)
                    {
                        adapterLEaveList = new AdapterLEaveList(getActivity(), leave_all);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapterLEaveList);
                    }
                    else
                    {
                        UtilityFunction.showCenteredToast(getActivity(),"No Leave available.");
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseLEave> call, Throwable t) {
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

    class AdapterLEaveList extends RecyclerView.Adapter<AdapterLEaveList.MyViewHolder> {

        private Context mContext;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name_st,sentserver,leavesubject,leavebody,singleday,starttime,endtime;
            public ImageView image_request;
            public ProgressBar pb_image_request;
            RelativeLayout row_news,v2timemultiple;

            public MyViewHolder(View view) {
                super(view);
                name_st = (TextView) view.findViewById(R.id.name_st);
                sentserver = (TextView) view.findViewById(R.id.sentserver);
                image_request = (ImageView) view.findViewById(R.id.image_request);
                pb_image_request = (ProgressBar) view.findViewById(R.id.pb_image_request);
                row_news = (RelativeLayout) view.findViewById(R.id.row_news);

                v2timemultiple = (RelativeLayout) view.findViewById(R.id.v2timemultiple);
                leavesubject = (TextView) view.findViewById(R.id.leavesubject);
              //  leavebody = (TextView) view.findViewById(R.id.leavebody);
                singleday = (TextView) view.findViewById(R.id.singleday);
                starttime = (TextView) view.findViewById(R.id.starttime);
                endtime = (TextView) view.findViewById(R.id.endtime);


            }
        }

        public AdapterLEaveList(Context mContext, List<SchoolLEave> albumL) {
            this.mContext = mContext;
            albumList = albumL;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.leave_row_tick, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


          final SchoolLEave album = albumList.get(position);

            holder.name_st.setText(album.getSTUDENT_FIRST_NAME());

            holder.leavesubject.setText(album.getLEAVES_SUBJECT());
          //  holder.leavebody.setText(album.getLEAVES_BODY());

            String single_dayleave=album.getLEAVES_SINGLEDAY();

            if(single_dayleave.equalsIgnoreCase("0000-00-00"))
            {
                holder.singleday.setVisibility(View.GONE);
                holder.v2timemultiple.setVisibility(View.VISIBLE);

                String getLEAVES_FROMDATE = UtilityFunction.dateConvert(album.getLEAVES_FROMDATE());
                holder.starttime.setText(getString(R.string.from)+" "+getLEAVES_FROMDATE);
                String getLEAVES_TODATE = UtilityFunction.dateConvert(album.getLEAVES_TODATE());
                holder.endtime.setText(getString(R.string.to)+" "+getLEAVES_TODATE);
            }
            else
            {
                String single_dayleaveconvert = UtilityFunction.dateConvert(single_dayleave);
                holder.singleday.setText(single_dayleaveconvert);
                holder.v2timemultiple.setVisibility(View.GONE);
            }



            holder.row_news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    new AlertDialog.Builder(getActivity())
                            .setTitle("Leave Message")
                            .setMessage(album.getLEAVES_BODY())
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
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
            TextView btn_cancel = (TextView) sectionDialog
                    .findViewById(R.id.btn_cancel);

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
                                        switchFragment(new ManagerLeaveHandler());
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
