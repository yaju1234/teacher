package com.mdgroup.teacher.interfaces.custom;


import com.mdgroup.teacher.schoolmodel.ForgotPasswordModal;
import com.mdgroup.teacher.schoolmodel.ModelUser;
import com.mdgroup.teacher.schoolmodel.ResAttendance;
import com.mdgroup.teacher.schoolmodel.ResClass;
import com.mdgroup.teacher.schoolmodel.ResSection;
import com.mdgroup.teacher.schoolmodel.ResSubject;
import com.mdgroup.teacher.schoolmodel.ResTestName;
import com.mdgroup.teacher.schoolmodel.ResponseAllChat;
import com.mdgroup.teacher.schoolmodel.ResponseLEave;
import com.mdgroup.teacher.schoolmodel.ResponseTeacher;
import com.mdgroup.teacher.schoolmodel.ResponseStudentChat;
import com.mdgroup.teacher.schoolmodel.SingleResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Gaurav Mangal
 */
public interface ApiIHandler {

    @FormUrlEncoded
    @POST("login.php")
    Call<ModelUser> getLoginAuthenticationStudent(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("forgot-password-otp.php")
    Call<ForgotPasswordModal> getFORGOTSTUDENT(@Field("STUDENT_FATHER_MOBILE_NO") String STUDENT_FATHER_MOBILE_NO);

    @FormUrlEncoded
    @POST("forgot-password-otp-match.php")
    Call<ForgotPasswordModal> getOTPMATCHAPI(@Field("STUDENT_ADMISSION_NO") String STUDENT_ADMISSION_NO, @Field("OTP") String OTP);

    @FormUrlEncoded
    @POST("forgot-password-reset.php")
    Call<ForgotPasswordModal> get_forgotpassword_reset(@Field("STUDENT_ADMISSION_NO") String STUDENT_ADMISSION_NO, @Field("password") String password, @Field("cpassword") String cpassword);

    @FormUrlEncoded
    @POST("change-password.php")
    Call<ForgotPasswordModal> get_change_password(@Field("STUDENT_ADMISSION_NO") String STUDENT_ADMISSION_NO, @Field("oldpassword") String oldpassword, @Field("password") String password, @Field("cpassword") String cpassword);

    @FormUrlEncoded
    @POST("update-profile.php")
    Call<ForgotPasswordModal> get_updates_profiledata(@Field("STUDENT_ADMISSION_NO") String STUDENT_ADMISSION_NO,
                                                      @Field("STUDENT_FATHER_MOBILE_NO") String STUDENT_FATHER_MOBILE_NO,
                                                      @Field("STUDENT_FATHER_EMAIL") String STUDENT_FATHER_EMAIL,
                                                      @Field("STUDENT_FATHER_ADDRESS1") String STUDENT_FATHER_ADDRESS1,
                                                      @Field("STUDENT_FATHER_ADDRESS2") String STUDENT_FATHER_ADDRESS2,
                                                      @Field("STUDENT_FIRST_NAME") String STUDENT_FIRST_NAME,
                                                      @Field("action") String action
    );


    @FormUrlEncoded
    @POST("getteachers.php")
    Call<ResponseTeacher>TeacherData(@Field("TEACHERS_CLASS_ID") String TEACHERS_CLASS_ID,
                                     @Field("TEACHERS_SECTION_ID") String TEACHERS_SECTION_ID);


    @POST("get-classes.php")
    Call<ResClass> getclass();

    @FormUrlEncoded
    @POST("get-sections.php")
    Call<ResSection>getsectiion(@Field("CLASS_ID") String CLASS_ID );

    @FormUrlEncoded
    @POST("get-subjects.php")
    Call<ResSubject>getsubject(@Field("CLASS_ID") String CLASS_ID);



    @FormUrlEncoded
    @POST("assign-homework.php")
    Call<SingleResponse>assignhw_upload(@Field("HOMEWORK_SUBJECT_ID") String HOMEWORK_SUBJECT_ID,
                                        @Field("HOMEWORK_CLASS_ID") String HOMEWORK_CLASS_ID,
                                        @Field("HOMEWORK_SECTION_ID") String HOMEWORK_SECTION_ID,
                                        @Field("HOMEWORK_TOPIC") String HOMEWORK_TOPIC,
                                        @Field("HOMEWORK_DESCRIPTION") String HOMEWORK_DESCRIPTION,
                                        @Field("HOMEWORK_DATE") String HOMEWORK_DATE);


    @FormUrlEncoded
    @POST("getstudentssectionwise.php")
    Call<ResAttendance>getstudentssectionwise_list(@Field("YEAR_NO") String YEAR_NO,
                                              @Field("SECTION_MASTER_NO") String SECTION_MASTER_NO
                                        );

    @FormUrlEncoded
    @POST("takeattendance.php")
    Call<SingleResponse>getstudentssectionwise(@Field("STUDENT_ADMISSION_NO") String STUDENT_ADMISSION_NO,
                                              @Field("ATTENDANCE_TYPE") String ATTENDANCE_TYPE,
                                           @Field("ATTENDANCE_DATE") String ATTENDANCE_DATE,
                                           @Field("ATTENDANCE_MONTH") String ATTENDANCE_MONTH
    );


    @FormUrlEncoded
    @POST("upload-class-tests.php")
    Call<SingleResponse>uploadclasstest(@Field("EXAMS_NAME") String EXAMS_NAME,
                                               @Field("EXAMS_DESCRIPTION") String EXAMS_DESCRIPTION,
                                               @Field("EXAMS_SUBJECT_ID") String EXAMS_SUBJECT_ID,
                                               @Field("EXAMS_CLASS_ID") String EXAMS_CLASS_ID,
                                        @Field("EXAMS_SECTIONS_ID") String EXAMS_SECTIONS_ID,
                                        @Field("EXAMS_DATE") String EXAMS_DATE,
                                        @Field("EXAMS_START_TIME") String EXAMS_START_TIME,
                                        @Field("EXAMS_END_TIME") String EXAMS_END_TIME
    );


    @FormUrlEncoded
    @POST("get-tests.php")
    Call<ResTestName>gettestresult(@Field("TEACHERS_ID") String TEACHERS_ID

    );

    @FormUrlEncoded
    @POST("get-chat-students.php")
    Call<ResponseStudentChat> chatStudentListdata(@Field("CHATS_TEACHER_ID") String CHATS_TEACHER_ID);  //7


    @FormUrlEncoded
    @POST("get-chats.php")
    Call<ResponseAllChat> getChatdata(@Field("CHATS_STUDENT_ID") String CHATS_STUDENT_ID, @Field("CHATS_TEACHER_ID") String CHATS_TEACHER_ID);

    @FormUrlEncoded
    @POST("send-chats.php")
    Call<ForgotPasswordModal> sendmessagedataobject(@Field("CHATS_MESSAGE") String CHATS_MESSAGE, @Field("CHATS_STUDENT_ID") String CHATS_STUDENT_ID,@Field("CHATS_TEACHER_ID") String CHATS_TEACHER_ID,@Field("CHATS_SIDE") String CHATS_SIDE);


    @FormUrlEncoded
    @POST("get-leaves.php")
    Call<ResponseLEave>getleave_list(@Field("YEAR_NO") String YEAR_NO,
                                     @Field("SECTION_MASTER_NO") String SECTION_MASTER_NO
    );


}
