package com.turtle.teacher.fragcontrol;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.turtle.teacher.R;
import com.turtle.teacher.adptercstm.adpter.chat.chatAdapter;
import com.turtle.teacher.api.urlsApimanage.ApiClient;
import com.turtle.teacher.interfaces.custom.ApiIHandler;
import com.turtle.teacher.prefControl.PrefManager;
import com.turtle.teacher.schoolmodel.ModelStudentlist;
import com.turtle.teacher.schoolmodel.ResponseStudentChat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @Gaurav Mangal
 */

public class ChatHandlerManager extends Fragment {
    private RecyclerView rv_chatdatarv;
    private chatAdapter adapter;
    private List<ModelStudentlist> albumList;
    RecyclerView.LayoutManager mLayoutManager;
    TextView chatdata_not_availabe;
    PrefManager prefManager;

    public ChatHandlerManager() {
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
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.chattingmanager, container, false);

        rv_chatdatarv = (RecyclerView) view.findViewById(R.id.rv_chatdatarv);
        chatdata_not_availabe = (TextView) view.findViewById(R.id.chatdata_not_availabe);
        albumList = new ArrayList<>();
        prefManager = new PrefManager(getActivity());

        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_chatdatarv.setVisibility(View.VISIBLE);
        chatdata_not_availabe.setVisibility(View.GONE);


        getchatStudentData(prefManager.getCheckUser_id());


       /* try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return view;
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

    public void getchatStudentData(String te_id) {
        final ApiIHandler apiService =
                ApiClient.getClient().create(ApiIHandler.class);

        final ProgressDialog pDialog = ProgressDialog.show(getActivity(), "", "Please wait ...", true);

        Call<ResponseStudentChat> call = apiService.chatStudentListdata(te_id);
        call.enqueue(new Callback<ResponseStudentChat>() {
            @Override
            public void onResponse(Call<ResponseStudentChat> call, retrofit2.Response<ResponseStudentChat> response) {
                pDialog.dismiss();
                if (response.code() == 200) {
                    ResponseStudentChat data = response.body();
                    pDialog.hide();
                    if(data.getMessage().equalsIgnoreCase("No records found."))
                    {
                        pDialog.hide();
                        chatdata_not_availabe.setVisibility(View.VISIBLE);
                        rv_chatdatarv.setVisibility(View.GONE);
                    }
                    else
                    {
                        if (data.getResult().size() > 0) {
                            rv_chatdatarv.setVisibility(View.VISIBLE);
                            chatdata_not_availabe.setVisibility(View.GONE);
                            albumList = data.getResult();
                            adapter = new chatAdapter(getActivity(), albumList);
                            rv_chatdatarv.setLayoutManager(mLayoutManager);
                            rv_chatdatarv.setItemAnimator(new DefaultItemAnimator());
                            rv_chatdatarv.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseStudentChat> call, Throwable t) {
                if (pDialog != null && pDialog.isShowing())
                    pDialog.hide();
                Toast.makeText(getActivity(), getResources().getString(R.string.error_tryagain), Toast.LENGTH_SHORT).show();
                chatdata_not_availabe.setVisibility(View.VISIBLE);
                rv_chatdatarv.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
