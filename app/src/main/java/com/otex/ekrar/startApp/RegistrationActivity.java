package com.otex.ekrar.startApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.startApp.model.DataSendSignUp;
import com.otex.ekrar.startApp.model.dataResponceSigUp.DataResponceSignUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {


    LinearLayout linearLayoutSigUp;
    ImageButton image_arrow;
    EditText name, idCard;
    EditText nationality, gender;
    EditText email, phone;
    EditText password, rePassword;
    Button signUp;

    private Spinner  spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        defineIds();
        actionButton();
    }




    private void actionButton() {


        linearLayoutSigUp.setOnClickListener(view -> finish());

        image_arrow.setOnClickListener(view -> finish());

        signUp.setOnClickListener(v -> {
            Log.e("sda",spinner2.getSelectedItem().toString());
            Log.e("sdasd", String.valueOf(spinner2.getSelectedItemId()));


            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                if (name.getText().toString().trim().isEmpty()){
                    name.setError("ادخل الاسم ");
                    name.setFocusableInTouchMode(true);
                    name.requestFocus();
                    name.setFocusableInTouchMode(true);
                }else if (idCard.getText().toString().trim().isEmpty()&&idCard.getText().toString().length()<10){
                    idCard.setError("رقم الهويه");
                    idCard.setFocusableInTouchMode(true);
                    idCard.requestFocus();
                    idCard.setFocusableInTouchMode(true);
                }else if (nationality.getText().toString().trim().isEmpty()){
                    nationality.setError("الجنسيه");
                    nationality.setFocusableInTouchMode(true);
                    nationality.requestFocus();
                    nationality.setFocusableInTouchMode(true);
                }
//                else if (gender.getText().toString().trim().isEmpty()){
//                    gender.setError("الجنس");
//                    gender.setFocusableInTouchMode(true);
//                    gender.requestFocus();
//                    gender.setFocusableInTouchMode(true);
//                }
                else if (email.getText().toString().trim().isEmpty()){
                    email.setError("ادخل اميل ");
                    email.setFocusableInTouchMode(true);
                    email.requestFocus();
                    email.setFocusableInTouchMode(true);
                }else if (phone.getText().toString().trim().isEmpty()){
                    phone.setError("ادخل رقم التليفون ");
                    phone.setFocusableInTouchMode(true);
                    phone.requestFocus();
                    phone.setFocusableInTouchMode(true);
                }else if (password.getText().toString().trim().isEmpty() || password.getText().toString().length()<8) {
                    password.setError("ادخل الرقم السري اكبر من ٨ احرف");
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                    password.setFocusableInTouchMode(true);
                }else if (rePassword.getText().toString().trim().isEmpty() || rePassword.getText().toString().length()<8){
                    rePassword.setError("ادخل الرقم السري اكبر من ٨ احرف");
                    rePassword.setFocusableInTouchMode(true);
                    rePassword.requestFocus();
                    rePassword.setFocusableInTouchMode(true);
                }else{
                    if (password.getText().toString().trim().equals(rePassword.getText().toString().trim())){
                        if (validateEmail()){
                            addReqestData();
                        }
                    }else{
                        password.setError("ادخل الرقم السري اكبر من ٨ احرف");
                        rePassword.setError("ادخل الرقم السري اكبر من ٨ احرف");
                    }

//                        startActivity(new Intent(getApplicationContext(),All_LocationAndService_Sico.class));
                }

            }else{
                Toast.makeText(RegistrationActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }

        });
    }



    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("ذكر");
        list.add("انثي");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    private void defineIds() {

        addItemsOnSpinner2();

        linearLayoutSigUp = findViewById(R.id.loginSignUp);
        image_arrow = findViewById(R.id.image_arrow);
        signUp = findViewById(R.id.signUp_SignUp);

        name = findViewById(R.id.id_name_SignUp);
        name.setFocusableInTouchMode(false);
        name.setOnClickListener(view -> {
            name.setFocusableInTouchMode(true);
            name.setFocusableInTouchMode(true);
            name.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);
        });

        idCard = findViewById(R.id.idCard_SignUp);
        idCard.setFocusableInTouchMode(false);
        idCard.setOnClickListener(view -> {
            idCard.setFocusableInTouchMode(true);
            idCard.setFocusableInTouchMode(true);
            idCard.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(idCard, InputMethodManager.SHOW_IMPLICIT);
        });

        // >>>>>>>>>>>>>>>>>>>
        nationality = findViewById(R.id.natonality_signUp);
        nationality.setFocusableInTouchMode(false);
        nationality.setOnClickListener(view -> {
            nationality.setFocusableInTouchMode(true);
            nationality.setFocusableInTouchMode(true);
            nationality.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(nationality, InputMethodManager.SHOW_IMPLICIT);
        });
//        gender = findViewById(R.id.gender_signUp);
//        gender.setFocusableInTouchMode(false);
//        gender.setOnClickListener(view -> {
////                nationality.setFocusableInTouchMode(true);
////                nationality.setFocusableInTouchMode(true);
////                nationality.requestFocus();
////                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////                imm.showSoftInput(nationality, InputMethodManager.SHOW_IMPLICIT);
//        });


        // >>>>>

        email = findViewById(R.id.email_signUp);
        email.setFocusableInTouchMode(false);
        email.setOnClickListener(view -> {
            email.setFocusableInTouchMode(true);
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
        });

        phone = findViewById(R.id.phone_signUp);
        phone.setFocusableInTouchMode(false);
        phone.setOnClickListener(view -> {
            phone.setFocusableInTouchMode(true);
            phone.setFocusableInTouchMode(true);
            phone.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(phone, InputMethodManager.SHOW_IMPLICIT);
        });


        password = findViewById(R.id.idPassword_SignUp);
        password.setFocusableInTouchMode(false);
        password.setOnClickListener(view -> {
            password.setFocusableInTouchMode(true);
            password.setFocusableInTouchMode(true);
            password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
        });


        rePassword = findViewById(R.id.rePassword_SignUp);
        rePassword.setFocusableInTouchMode(false);
        rePassword.setOnClickListener(view -> {
            rePassword.setFocusableInTouchMode(true);
            rePassword.setFocusableInTouchMode(true);
            rePassword.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(rePassword, InputMethodManager.SHOW_IMPLICIT);
        });
    }


    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            email.setFocusableInTouchMode(true);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setFocusableInTouchMode(true);
            email.requestFocus();
            email.setFocusableInTouchMode(true);
            email.setError(emailInput+"  Please enter a valid email email Like ex@gmail.com");
            return false;
        } else {
//            checkEmail_Is_Esxist(emailInput);
            email.setError(null);
            return true;
        }
    }



    private void addReqestData(){
        String myString = "1234";
        int foo = Integer.parseInt(myString);
        Log.e("???", String.valueOf(foo));
        String id=idCard.getText().toString().trim();
        int idfinal=Integer.parseInt(id);

        final ProgressDialog progressDialog=new ProgressDialog(RegistrationActivity.this);
        progressDialog.setMessage("انتظر قليلا...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        Gson gson = new Gson();

        DataSendSignUp contactsTop=new DataSendSignUp();
        contactsTop.setDeviceId("1");
        contactsTop.setEmail(email.getText().toString().trim());

        contactsTop.setGender(Integer.parseInt(String.valueOf(spinner2.getSelectedItemId()))+1);
        contactsTop.setMobile(phone.getText().toString().trim());

        contactsTop.setName(name.getText().toString().trim());
        contactsTop.setNationalId(idfinal);

        contactsTop.setNationality(nationality.getText().toString().trim());
        contactsTop.setPassword(password.getText().toString().trim());
        contactsTop.setPasswordConfirmation(rePassword.getText().toString().trim());

        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.signUp
                , jsonObject, response -> {
                    progressDialog.dismiss();
                    Log.e("response_booking",""+response);

                    JSONObject sys = response;//// result

                    Gson gson1 = new Gson();
                    DataResponceSignUp dataDelevery;
                    dataDelevery = gson1.fromJson(response.toString(), DataResponceSignUp.class);

                    if (!dataDelevery.getSuccess().equals(true)){
                        Log.e("error","not Re");
    //                    Gson gsonerror = new Gson();
    //                    DataResponseSignUpError dataDeleveryerror;
    //                    dataDeleveryerror = gsonerror.fromJson(response.toString(), DataResponseSignUpError.class);
    //                    email.setError(""+dataDeleveryerror.getErrorResponsePayloadList().get(0).getArabicMessage());

                    }else{
                        Toast.makeText(RegistrationActivity.this, "تم التسجيل بنجاح تم ارسال كود التفعيل الي الاميل الخاص بك", Toast.LENGTH_LONG).show();
                        SharedPrefManager sharedPrefManager = new SharedPrefManager(RegistrationActivity.this);
                        sharedPrefManager.userLogin(dataDelevery.getData().getToken(),
                                dataDelevery.getData().getUser().getId().toString(),
                                null,dataDelevery.getData().getUser().getName().toString());

                        Intent intent = new Intent(RegistrationActivity.this, ConfirmUserActivity.class);
                        intent.putExtra("token",dataDelevery.getData().getToken());
                        intent.putExtra("idCard",idCard.getText().toString().trim());
                        startActivity(intent);
//                        finish();
                    }


                }, error -> {

                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        //                            String body = new String(error.networkResponse.data,"UTF-8");
                        try {
                            Gson gson12 = new Gson();
                             DataResponceSignUp dataDelevery;
                            dataDelevery = gson12.fromJson(new String(error.networkResponse.data, "UTF-8"), DataResponceSignUp.class);

//                            Log.e("massage getDeviceId ",dataDelevery.getData().getDeviceId().toString());
//                            Log.e("massage getNationalId ",dataDelevery.getData().getNationalId().toString());
//                            Log.e("massage",dataDelevery.getData().toString());
                            if (dataDelevery.getData().getNationalId()!=null) {
                                Toast.makeText(getApplicationContext(), ""+dataDelevery.getData().getNationalId().toString(), Toast.LENGTH_LONG).show();
                                idCard.setError(dataDelevery.getData().getNationalId().toString());
                                idCard.setFocusableInTouchMode(true);
                                idCard.requestFocus();
                                idCard.setFocusableInTouchMode(true);
                            }else if (dataDelevery.getData().getDeviceId()!=null) {
                                email.setError("DeviceId");
                                email.setFocusableInTouchMode(true);
                                email.requestFocus();
                                email.setFocusableInTouchMode(true);
                            }
                            else if (dataDelevery.getData().getEmail()!=null) {
                                Toast.makeText(getApplicationContext(), ""+dataDelevery.getData().getEmail().toString(), Toast.LENGTH_LONG).show();
                                email.setError(dataDelevery.getData().getEmail().toString());
                                email.setFocusableInTouchMode(true);
                                email.requestFocus();
                                email.setFocusableInTouchMode(true);
                            }else if (dataDelevery.getData().getMobile()!=null) {
                                Toast.makeText(getApplicationContext(), ""+"رقم الجوال موجود لدينا بالفعل", Toast.LENGTH_LONG).show();
                                phone.setError("رقم الجوال موجود لدينا بالفعل");
                                phone.setFocusableInTouchMode(true);
                                phone.requestFocus();
                                phone.setFocusableInTouchMode(true);
                            }
//                                Toast.makeText(getApplicationContext(), ""+dataDelevery.getErrorResponsePayloadList().get(0).getArabicMessage(), Toast.LENGTH_LONG).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    progressDialog.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "Error Network Time Out", Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
    //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
    //                    finish();
                        Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();

                    } else if (error instanceof ServerError) {
//                        Toast.makeText(getApplicationContext(), "يرجي التاكد من ادخال البيانات", Toast.LENGTH_LONG).show();

                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();

                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();

                    }
                });
//        {
//            /** Passing some request headers* */
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization",
//                        SharedPrefManager_Login.getInstance(getApplicationContext()).getaccessType()+" "+
//                                SharedPrefManager_Login.getInstance(getApplicationContext()).getjwt());
//                return headers;
//            }
//        };;
        requestQueue.add(jsonObjectRequest);

    }

}