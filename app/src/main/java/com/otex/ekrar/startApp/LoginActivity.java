package com.otex.ekrar.startApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.otex.ekrar.Home.Home;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.startApp.model.DataSendLogin;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    LinearLayout linearLayoutSigUp,bacground_image;
    EditText idCard,password;
    Button login;
    TextView forgetPassword_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        defineIds();
        actionButton();
    }



    private void defineIds() {
        login=findViewById(R.id.login_login);
        forgetPassword_Login=findViewById(R.id.forgetPassword_Login);
        bacground_image=findViewById(R.id.bacground_image);
//        bacground_image.setBackground(R.drawable.image_login);
        linearLayoutSigUp=findViewById(R.id.linearLayoutSignUp_Login);

        idCard=findViewById(R.id.idCardLogin);
        idCard.setFocusableInTouchMode(false);
        idCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCard.setFocusableInTouchMode(true);
                idCard.setFocusableInTouchMode(true);
                idCard.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(idCard, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        password=findViewById(R.id.idPasswordLogin);
        password.setFocusableInTouchMode(false);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password.setFocusableInTouchMode(true);
                password.setFocusableInTouchMode(true);
                password.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }



    private void actionButton() {
        linearLayoutSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        forgetPassword_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotpasswordActivity.class));
            }
        });



        login.setOnClickListener(v -> {
            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                if (idCard.getText().toString().trim().isEmpty()){
                    idCard.setError("رقم الهويه");
                    idCard.setFocusableInTouchMode(true);
                    idCard.requestFocus();
                    idCard.setFocusableInTouchMode(true);
                }else if (password.getText().toString().trim().isEmpty()&&idCard.getText().toString().length()<10){
                    password.setError("كلمه المرور");
                    password.setFocusableInTouchMode(true);
                    password.requestFocus();
                    password.setFocusableInTouchMode(true);
                } else{
                    addReqestData();
//                        startActivity(new Intent(getApplicationContext(),All_LocationAndService_Sico.class));
                }

            }else{
                Toast.makeText(LoginActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }

        });
    }



    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("انتظر قليلا ...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        Gson gson = new Gson();

        DataSendLogin contactsTop=new DataSendLogin();
        contactsTop.setDevice_id("1");
        contactsTop.setNational_id(idCard.getText().toString().trim());
        contactsTop.setPassword(password.getText().toString().trim());

        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.login
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Gson gson1 = new Gson();
            DataResponceLogin dataDelevery;
            dataDelevery = gson1.fromJson(response.toString(), DataResponceLogin.class);

            if (!dataDelevery.getSuccess().equals(true)){
                Log.e("error","not Re");

            }else{
//                Toast.makeText(LoginActivity.this, "تم التسجيل بنجاح ", Toast.LENGTH_SHORT).show();
                SharedPrefManager sharedPrefManager = new SharedPrefManager(LoginActivity.this);
                sharedPrefManager.userLogin(
                        dataDelevery.getData().getToken(),
                        dataDelevery.getData().getUser().getId().toString(),
                        "login",
                        dataDelevery.getData().getUser().getName());

                Intent intent = new Intent(LoginActivity.this, Home.class);
                intent.putExtra("token",dataDelevery.getData().getToken());
                startActivity(intent);
                finish();
            }


        }, error -> {

            //get status code here
//            String statusCode = String.valueOf(error.networkResponse.statusCode);
            //get response body and parse with appropriate encoding


            progressDialog.dismiss();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(getApplicationContext(), "Error Network Time Out", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                //                    finish();
                Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "يرجي التاكد من ادخال البيانات", Toast.LENGTH_LONG).show();
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