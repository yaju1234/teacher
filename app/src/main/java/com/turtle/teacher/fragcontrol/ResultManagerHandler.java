package com.turtle.teacher.fragcontrol;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.turtle.teacher.R;
import com.turtle.teacher.adptercstm.adpter.chat.CallClassAdapter;
import com.turtle.teacher.adptercstm.adpter.chat.CallSectionAdapter;
import com.turtle.teacher.adptercstm.adpter.chat.CallSubjectAdapter;
import com.turtle.teacher.adptercstm.adpter.chat.CallTestingAdapter;
import com.turtle.teacher.adptercstm.adpter.chat.StudentNameAdapter;
import com.turtle.teacher.api.urlsApimanage.ApiClient;
import com.turtle.teacher.interfaces.custom.ApiIHandler;
import com.turtle.teacher.prefControl.PrefManager;
import com.turtle.teacher.schoolmodel.HomeWorkClassModel;
import com.turtle.teacher.schoolmodel.HomeWorkSectionModel;
import com.turtle.teacher.schoolmodel.HomeWorkSubjectModel;
import com.turtle.teacher.schoolmodel.ModelStudentlist;
import com.turtle.teacher.schoolmodel.ResClass;
import com.turtle.teacher.schoolmodel.ResSection;
import com.turtle.teacher.schoolmodel.ResSubject;
import com.turtle.teacher.schoolmodel.ResTestName;
import com.turtle.teacher.schoolmodel.ResponseStudentChat;
import com.turtle.teacher.schoolmodel.TestNameList;
import com.turtle.teacher.utilityschool.UtilityFunction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Gaurav Mangal
 */

public class ResultManagerHandler extends Fragment {

    EditText ettotal_marks,etoptainmark,etgrade;

    Button  btnstudent_name,class_photos, class_section,submit_result,test_load;

    Dialog classDialog, sectionDialog, studentDialog;
    String selected_class_id = "", selected_class_name = "";
    String selected_section_id = "", selected_section_name = "";
    ArrayList<TestNameList> arr_TestNameList;

    String selected_st_id="",selected_st_name="";

PrefManager prefManager;
    Dialog TestNameListDialog;
    String selected_testname_id = "", selected_testname = "";
    ArrayList<HomeWorkClassModel> arr_hw_classmodel;
    ArrayList<HomeWorkSectionModel> arr_hw_sectionmodel;
    ArrayList<ModelStudentlist> arr_st_namemodel;
    public ResultManagerHandler()
    {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.resultmanager, container, false);
        btnstudent_name=(Button)view.findViewById(R.id.student_name);

        test_load=(Button)view.findViewById(R.id.test_load);
        submit_result=(Button)view.findViewById(R.id.submit_result);
        ettotal_marks=(EditText)view.findViewById(R.id.ettotal_marks);
        etoptainmark=(EditText)view.findViewById(R.id.etoptainmark);
        etgrade=(EditText)view.findViewById(R.id.etgrade);
        //geteventDataFROMSERVER();

        class_photos = (Button) view.findViewById(R.id.class_photos);
        class_section = (Button) view.findViewById(R.id.class_section);
        prefManager=new PrefManager(getActivity());
        arr_TestNameList = new ArrayList<TestNameList>();
        arr_TestNameList = setarr_hw_test_add_array();
        arr_st_namemodel= setarr_st_name_add_array();
        test_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arr_TestNameList.size()>0)
                openTestNAmeSelector();
                else
                    Toast.makeText(getActivity(), "No test name available.", Toast.LENGTH_LONG).show();

            }
        });

        btnstudent_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arr_st_namemodel.size()>0)
                    openStudentNameSelector();
                else
                    Toast.makeText(getActivity(), "No student available.", Toast.LENGTH_LONG).show();

            }
        });


        submit_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String st_ettotal_marks="",st_etoptainmarks="",st_etgragemark="";
                st_ettotal_marks=ettotal_marks.getText().toString().trim();
                st_etoptainmarks=etoptainmark.getText().toString().trim();

                st_etgragemark=etgrade.getText().toString().trim();

                if(selected_testname_id.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please select test name", Toast.LENGTH_LONG).show();
                }
                else if (selected_class_id == null || selected_class_id.equals("")) {
                    UtilityFunction.showCenteredToast(getActivity(), "Please select class name");
                } else if (selected_section_id == null || selected_section_id.equals("")) {
                    UtilityFunction.showCenteredToast(getActivity(), "Please select section name");
                }
                else if (selected_st_id == null || selected_st_id.equals("")) {
                    UtilityFunction.showCenteredToast(getActivity(), "Please select student name");
                }
                else if(st_ettotal_marks.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter total mark", Toast.LENGTH_LONG).show();
                }
                else  if(st_etoptainmarks.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter optain mark", Toast.LENGTH_LONG).show();
                }
                else  if(st_etgragemark.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter mark grade", Toast.LENGTH_LONG).show();
                }
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Sucess")
                            .setMessage("Result Uploaded Successfully.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    switchFragment(new ResultManagerHandler());
                                }
                            }).show();
                }
            }
        });
       /* try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        arr_hw_classmodel = new ArrayList<HomeWorkClassModel>();
        arr_hw_sectionmodel = new ArrayList<HomeWorkSectionModel>();


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






        return view;
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



    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*public void geteventDataFROMSERVER() {
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);

        Call<ResponseEvent> call = apiService.FetchEventData();
        call.enqueue(new Callback<ResponseEvent>() {
            @Override
            public void onResponse(Call<ResponseEvent> call, retrofit2.Response<ResponseEvent> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    ResponseEvent data = response.body();
                    pDialog.hide();
                    if (data.getResult().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        newsdata_not_availabe.setVisibility(View.GONE);
                        albumList = data.getResult();
                        adapter = new EventsAdapter(getActivity(), albumList);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    } else {
                        pDialog.hide();
                        newsdata_not_availabe.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseEvent> call, Throwable t) {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_tryagain), Toast.LENGTH_SHORT).show();
                newsdata_not_availabe.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }
*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    public ArrayList<ModelStudentlist> setarr_st_name_add_array() {
        final ArrayList<ModelStudentlist> arr_st_name = new ArrayList<ModelStudentlist>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResponseStudentChat> call = apiService.chatStudentListdata(prefManager.getCheckUser_id());
        call.enqueue(new Callback<ResponseStudentChat>() {
            @Override
            public void onResponse(Call<ResponseStudentChat> call, Response<ResponseStudentChat> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    arr_st_namemodel = response.body().getResult();
                }
            }
            @Override
            public void onFailure(Call<ResponseStudentChat> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return arr_st_namemodel;
    }



    public ArrayList<TestNameList> setarr_hw_test_add_array() {
        ArrayList<TestNameList> HomeWorkClassModel = new ArrayList<TestNameList>();
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);
        Call<ResTestName> call = apiService.gettestresult(prefManager.getCheckUser_id());
        call.enqueue(new Callback<ResTestName>() {
            @Override
            public void onResponse(Call<ResTestName> call, Response<ResTestName> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    arr_TestNameList = response.body().getResult();
                }
            }
            @Override
            public void onFailure(Call<ResTestName> call, Throwable t) {
                // Log error here since request failed
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();

            }
        });

        return HomeWorkClassModel;
    }
    private void openTestNAmeSelector() {

        try {
            TestNameListDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
            TestNameListDialog
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            TestNameListDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            TestNameListDialog.setCancelable(false);


            View screen_api = LayoutInflater.from(getActivity()).inflate(
                    R.layout.screen_popup, null);
            TestNameListDialog.setContentView(screen_api);

            final ListView listviewitem_list = (ListView) TestNameListDialog
                    .findViewById(R.id.listviewitem);

            TextView header_title = (TextView) TestNameListDialog
                    .findViewById(R.id.header_title);

            header_title.setText("Select Test Name");


            final CallTestingAdapter api_type = new CallTestingAdapter(
                    getActivity(), R.layout.row_type, arr_TestNameList, selected_testname_id);

            // set the adapter
            listviewitem_list.setAdapter(api_type);
            TextView btn_ok = (TextView) TestNameListDialog
                    .findViewById(R.id.btn_ok);
            TextView btn_cancel = (TextView) TestNameListDialog
                    .findViewById(R.id.btn_cancel);

            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (api_type != null) {
                        if (api_type.getSelectedPosition() > -1) {
                            String classes_id_id_value = arr_TestNameList.get(api_type.getSelectedPosition()).getEXAMS_ID();

                            System.out.println("Value :" + classes_id_id_value);
                            // temp
                            selected_testname_id = classes_id_id_value;
                            selected_testname = arr_TestNameList.get(api_type.getSelectedPosition()).getEXAMS_NAME();
                            test_load.setText(selected_testname);

                            TestNameListDialog.dismiss();
                        } else {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select test name");
                        }
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TestNameListDialog.dismiss();

                }
            });

            TestNameListDialog.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    private void openStudentNameSelector() {

        try {
            studentDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
            studentDialog
                    .requestWindowFeature(Window.FEATURE_NO_TITLE);
            studentDialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            studentDialog.setCancelable(false);


            View screen_api = LayoutInflater.from(getActivity()).inflate(
                    R.layout.screen_popup, null);
            studentDialog.setContentView(screen_api);

            final ListView listviewitem_list = (ListView) studentDialog
                    .findViewById(R.id.listviewitem);

            TextView header_title = (TextView) studentDialog
                    .findViewById(R.id.header_title);

            header_title.setText("Select Student Name");


            final StudentNameAdapter api_type = new StudentNameAdapter(
                    getActivity(), R.layout.row_type, arr_st_namemodel, selected_st_id);

            // set the adapter
            listviewitem_list.setAdapter(api_type);
            TextView btn_ok = (TextView) studentDialog
                    .findViewById(R.id.btn_ok);
            TextView btn_cancel = (TextView) studentDialog
                    .findViewById(R.id.btn_cancel);

            btn_ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (api_type != null) {
                        if (api_type.getSelectedPosition() > -1) {
                            String stid_value = arr_st_namemodel.get(api_type.getSelectedPosition()).getSTUDENT_ADMISSION_NO();

                            System.out.println("Value :" + stid_value);
                            // temp
                            selected_st_id = stid_value;
                            selected_st_name = arr_st_namemodel.get(api_type.getSelectedPosition()).getSTUDENT_FIRST_NAME();
                            btnstudent_name.setText(selected_st_name);

                            studentDialog.dismiss();
                        } else {
                            UtilityFunction.showCenteredToast(getActivity(), "Please select student name");
                        }
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    studentDialog.dismiss();

                }
            });

            studentDialog.show();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }





    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
    }

    @Override
    public void unregisterForContextMenu(View view) {
        super.unregisterForContextMenu(view);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    @Override
    public void setExitSharedElementCallback(SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
    }

    @Override
    public void setEnterTransition(Object transition) {
        super.setEnterTransition(transition);
    }

    @Override
    public Object getEnterTransition() {
        return super.getEnterTransition();
    }

    @Override
    public void setReturnTransition(Object transition) {
        super.setReturnTransition(transition);
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



}
