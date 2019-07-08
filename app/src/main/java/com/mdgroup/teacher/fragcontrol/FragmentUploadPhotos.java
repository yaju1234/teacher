package com.mdgroup.teacher.fragcontrol;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.mdgroup.teacher.R;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallClassAdapter;
import com.mdgroup.teacher.adptercstm.adpter.chat.CallSectionAdapter;
import com.mdgroup.teacher.api.urlsApimanage.ApiClient;
import com.mdgroup.teacher.interfaces.custom.ApiIHandler;
import com.mdgroup.teacher.interfaces.custom.UploadImageInterface;
import com.mdgroup.teacher.prefControl.PrefManager;
import com.mdgroup.teacher.schoolmodel.HomeWorkClassModel;
import com.mdgroup.teacher.schoolmodel.HomeWorkSectionModel;
import com.mdgroup.teacher.schoolmodel.ResClass;
import com.mdgroup.teacher.schoolmodel.ResSection;
import com.mdgroup.teacher.schoolmodel.UploadObject;
import com.mdgroup.teacher.utilityschool.UtilityFunction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Gaurav Mangal
 */
public class FragmentUploadPhotos extends Fragment {

    String selectedclassValue="1st class";
    ArrayList<Uri> mArrayUri;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final String SERVER_FILE_IMAGE = "http://www.gauravdeveloper.com";
    public static final int MEDIA_TYPE_IMAGE = 1;
    Bitmap bitmap;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Turtle";
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri fileUri;

    int value=0;
    // private ImageView imgPreview;
    private Button btnCapturePicture, select_submit,class_photos;
    ArrayList<HomeWorkClassModel> arr_hw_classmodel;
    PrefManager prefManager;

    Button date_pic,class_section;
    String selected_datepic = "";
    EditText st_name;


    Dialog classDialog, sectionDialog;
    String selected_class_id = "", selected_class_name = "";
    String selected_section_id = "", selected_section_name = "";
    ArrayList<HomeWorkSectionModel> arr_hw_sectionmodel;

    public FragmentUploadPhotos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.schemauploadphotos, container, false);
        prefManager = new PrefManager(getActivity());

        class_section=(Button) view.findViewById(R.id.class_section);
        btnCapturePicture = (Button) view.findViewById(R.id.select_image);
        date_pic = (Button) view.findViewById(R.id.date_pic);
        select_submit = (Button) view.findViewById(R.id.select_submit);
        class_photos = (Button)view.findViewById(R.id.class_photos);
        st_name= (EditText) view.findViewById(R.id.st_name);

        arr_hw_classmodel = new ArrayList<HomeWorkClassModel>();
        arr_hw_sectionmodel = new ArrayList<HomeWorkSectionModel>();


        arr_hw_classmodel = setarr_hw_classmodel_add_array();

        // imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                5);
                    } else
                    {
                        captureImage();
                    }
                } else {
                    captureImage();
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
		
        select_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call
                // uploadonServerDataWithimages();
                String st_name_str="";
                st_name_str=st_name.getText().toString().trim();
                if(st_name_str.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter event name", Toast.LENGTH_LONG).show();
                }
                else if (selected_class_id == null || selected_class_id.equals("")) {
                    UtilityFunction.showCenteredToast(getActivity(), "Please select class name");
                }
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Sucess")
                            .setMessage("Photos Uploaded Successfully.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    getActivity().setTitle("Upload Photos");
                                    switchFragment(new FragmentUploadPhotos());
                                }
                            }).show();
                }

            }
        });
        return view;
    }
    private void captureImage() {
        // Create the Intent for Image Gallery.
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.setAction(Intent.ACTION_GET_CONTENT);
        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit();
    }

    public void uploadonServerDataWithimages() {

        File file = new File(fileUri.getPath());
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_FILE_IMAGE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UploadImageInterface uploadImage = retrofit.create(UploadImageInterface.class);
        Call<UploadObject> fileUpload = uploadImage.uploadFile(fileToUpload, filename);
        fileUpload.enqueue(new Callback<UploadObject>() {
            @Override
            public void onResponse(Call<UploadObject> call, Response<UploadObject> response) {
                Toast.makeText(getActivity(), "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UploadObject> call, Throwable t) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            } else {
                UtilityFunction.showCenteredToast(getActivity(), "Please must to allow permission");
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            List<String> imagesEncodedList;
            String imageEncoded;
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    Toast.makeText(getActivity(), "Please select atleast two and maximum 5 images",
                            Toast.LENGTH_LONG).show();

                }else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                        }
                        if(mArrayUri.size()>=2 && mArrayUri.size()<=5)
                        {
                            //Toast.makeText(getActivity(), "done "+mArrayUri.size(),Toast.LENGTH_LONG).show();
                            btnCapturePicture.setText(String.valueOf(mArrayUri.size()).concat(" Photos Selected"));
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please select atleast two and maximum 5 images",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Please select atleast two and maximum 5 images",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        //imgPreview.setImageBitmap(bitmap);
    }

    /**
     * Display image from a path to ImageView
     */
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

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

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
            selected_datepic = year + "-" + month + "-" + day;
            String selected_buttondate = day + "-" + month + "-" + year;
            date_pic.setText(selected_buttondate);
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



}
