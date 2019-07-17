package com.mdgroup.teacher;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mdgroup.teacher.adptercstm.adpter.chat.CallClassAdapter;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallSectionAdapter;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallSubjectAdapter;
import com.mdgroup.teacher.api.urlsApimanage.ApiClient;
import com.mdgroup.teacher.interfaces.custom.ApiIHandler;
import com.mdgroup.teacher.schoolmodel.HomeWorkClassModel;
import com.mdgroup.teacher.schoolmodel.HomeWorkSectionModel;
import com.mdgroup.teacher.schoolmodel.HomeWorkSubjectModel;
import com.mdgroup.teacher.schoolmodel.ResClass;
import com.mdgroup.teacher.schoolmodel.ResSection;
import com.mdgroup.teacher.schoolmodel.ResSubject;
import com.mdgroup.teacher.schoolmodel.SingleResponse;
import com.mdgroup.teacher.utilityschool.UtilityFunction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestUploadingManager extends Fragment {

    static Button btn_date;
    Button class_photos, class_section, subject, select_submit,btnselect_st,btnselect_et;
    EditText  et_hw_heading;
    ArrayList<HomeWorkClassModel> arr_hw_classmodel;
    ArrayList<HomeWorkSectionModel> arr_hw_sectionmodel;
    ArrayList<HomeWorkSubjectModel> arr_hw_subjectmodel;


    Dialog classDialog, sectionDialog, subjectDialog;
    String selected_class_id = "", selected_class_name = "";
    String selected_section_id = "", selected_section_name = "";
    String selected_subject_id = "", selected_subject_name = "";
    String selected_st = "",selected_et = "";
    static String  selected_date = "";

    public TestUploadingManager() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.testuploadmanager, container, false);

        class_photos = (Button) view.findViewById(R.id.class_photos);
        class_section = (Button) view.findViewById(R.id.class_section);
        subject = (Button) view.findViewById(R.id.subject);
        btn_date = (Button) view.findViewById(R.id.select_date);
        btnselect_st= (Button) view.findViewById(R.id.select_st);
        btnselect_et= (Button) view.findViewById(R.id.select_et);
        btn_date = (Button) view.findViewById(R.id.select_date);

        selected_date = "";

        select_submit = (Button) view.findViewById(R.id.select_submit);


        et_hw_heading = (EditText) view.findViewById(R.id.et_hw_heading);


        arr_hw_classmodel = new ArrayList<HomeWorkClassModel>();
        arr_hw_sectionmodel = new ArrayList<HomeWorkSectionModel>();
        arr_hw_subjectmodel = new ArrayList<HomeWorkSubjectModel>();


        arr_hw_classmodel = setarr_hw_classmodel_add_array();
        //arr_hw_sectionmodel=setarr_hw_sectionmodel_add_array(selected);


        class_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openClassNAmeSelector();
            }
        });
        class_section.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (selected_class_id.equalsIgnoreCase("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select class name using section.");
                        } else {
                            if(arr_hw_sectionmodel.size()>0)
                                openSEctionNAmeSelector();
                            else
                                UtilityFunction.showCenteredToast(getActivity(), "No Section available selected class.");
                        }
                    }
                });

        subject.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (selected_class_id.equalsIgnoreCase("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select class name using subject.");
                        } else {
                            if(arr_hw_subjectmodel.size()>0)
                                openSubjectNAmeSelector();
                            else
                                UtilityFunction.showCenteredToast(getActivity(), "No Subject available.");
                        }

                    }
                });

        btn_date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment newFragment = new DATEFragment();
                        newFragment.show(getFragmentManager(), "DatePicker");
                    }
                });

        btnselect_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTimeFragmentStart();
            }
        });


        btnselect_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeFragmentEnd();

            }
        });



        select_submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String hw_heading = et_hw_heading.getText().toString().trim();
                        if (selected_class_id == null || selected_class_id.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select class name");
                        } else if (selected_section_id == null || selected_section_id.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select section name");
                        } else if (selected_subject_id == null || selected_subject_id.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select subject name");
                        } else if (hw_heading == null || hw_heading.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please enter test heading.");
                        }
                        else if (selected_date == null || selected_date.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select date.");
                        }
                        else if (selected_st == null || selected_st.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select start time.");
                        }
                        else if (selected_et == null || selected_et.equals("")) {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select end time.");
                        }
                        else {
                            sendHOmeWorkDAta(selected_subject_id,selected_class_id,selected_section_id,hw_heading,selected_date);
                        }

                    }
                });


        return view;
    }


    void openTimeFragmentStart() {
        TimePickerDialog mTimePicker;

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

			String time = selectedHour + ":" + selectedMinute;

                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(time );
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                selected_st=fmtOut.format(date);

                btnselect_st.setText(selected_st);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Start Time");
        mTimePicker.show();
    }

    void openTimeFragmentEnd() {
        TimePickerDialog mTimePicker;

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {



                String time = selectedHour + ":" + selectedMinute;

                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(time );
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                selected_et=fmtOut.format(date);

                btnselect_et.setText(selected_et);
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select End Time");
        mTimePicker.show();
    }
    public void sendHOmeWorkDAta(String selected_subject_id,String selected_class_id,String selected_section_id,String hw_heading,String selected_date) {
        final ApiIHandler apiService = ApiClient.getClient().create(ApiIHandler.class);
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "please wait ...", true);
        Call<SingleResponse> call = apiService.uploadclasstest(hw_heading,"",selected_subject_id,selected_class_id,selected_section_id,selected_date,selected_st,selected_et);
        call.enqueue(new Callback<SingleResponse>() {
            @Override
            public void onResponse(Call<SingleResponse> call, Response<SingleResponse> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    SingleResponse data = response.body();
                    pDialog.hide();
                    if (data.getResult().equalsIgnoreCase("1")) {
                        pDialog.hide();
                        UtilityFunction.showCenteredToast(getActivity(),data.getMessage());
                        switchFragment(new TestUploadingManager());
                    }
                    else
                    {
                        pDialog.hide();
                        UtilityFunction.showCenteredToast(getActivity(),data.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_tryagain), Toast.LENGTH_SHORT).show();
            }
        });
    }
    // switching fragment
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
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

    public ArrayList<HomeWorkSubjectModel> setarr_hw_subjectodel_add_array(String classid) {
        ArrayList<HomeWorkSubjectModel> HomeWorkClassModel = new ArrayList<HomeWorkSubjectModel>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResSubject> call = apiService.getsubject(classid);
        call.enqueue(new Callback<ResSubject>() {
            @Override
            public void onResponse(Call<ResSubject> call, Response<ResSubject> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    arr_hw_subjectmodel = response.body().getResult();
                }
            }

            @Override
            public void onFailure(Call<ResSubject> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return HomeWorkClassModel;
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


                            selected_subject_id = "";
                            selected_subject_name = "";

                            subject.setText("Select Subject");



                            classDialog.dismiss();
                            arr_hw_sectionmodel = setarr_hw_sectionmodel_add_array(selected_class_id);

                            arr_hw_subjectmodel = setarr_hw_subjectodel_add_array(selected_class_id);


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

    private void openSubjectNAmeSelector() {

        try {
            subjectDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
            subjectDialog
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            subjectDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            subjectDialog.setCancelable(false);


            View screen_api = LayoutInflater.from(getActivity()).inflate(
                    R.layout.screen_popup, null);
            subjectDialog.setContentView(screen_api);

            final ListView listviewitem_list = (ListView) subjectDialog
                    .findViewById(R.id.listviewitem);

            TextView header_title = (TextView) subjectDialog
                    .findViewById(R.id.header_title);

            header_title.setText("Select Subject");

            final CallSubjectAdapter api_type = new CallSubjectAdapter(
                    getActivity(), R.layout.row_type, arr_hw_subjectmodel, selected_subject_id);

            // set the adapter
            listviewitem_list.setAdapter(api_type);
            TextView btn_ok = (TextView) subjectDialog
                    .findViewById(R.id.btn_ok);
            TextView btn_cancel = (TextView) subjectDialog
                    .findViewById(R.id.btn_cancel);

            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (api_type != null) {
                        if (api_type.getSelectedPosition() > -1) {
                            String getSUBJECTS_ID = arr_hw_subjectmodel.get(api_type.getSelectedPosition()).getSUBJECTS_ID();
                            System.out.println("Value :" + getSUBJECTS_ID);
                            selected_subject_id = getSUBJECTS_ID;
                            selected_subject_name = arr_hw_subjectmodel.get(api_type.getSelectedPosition()).getSUBJECTS_NAME();
                            subject.setText(selected_subject_name);
                            subjectDialog.dismiss();
                        } else {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select subject name");
                        }
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    subjectDialog.dismiss();

                }
            });

            subjectDialog.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @SuppressLint("ValidFragment")
    public static class DATEFragment extends DialogFragment implements
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
            selected_date = year + "/" + month + "/" + day;
            String selected_buttondate = day + "-" + month + "-" + year;
            btn_date.setText(selected_buttondate);
        }
    }

}
