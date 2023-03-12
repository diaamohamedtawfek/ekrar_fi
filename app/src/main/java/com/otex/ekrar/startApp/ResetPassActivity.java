package com.otex.ekrar.startApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.otex.ekrar.startApp.model.DataChangePassword;
import com.otex.ekrar.startApp.model.DataResponseConfirmCode.DataResponceConfurmCode;
import com.otex.ekrar.startApp.model.DataSendConfirmCodeForgetPassword;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassActivity extends AppCompatActivity {

    ImageButton image_arrow;
    AlertDialog loadingDialog;
    EditText edtextreg_password,edtext_retry_password;
    Button btn_send;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        defineIds();
        actionButton();
    }


    private void defineIds(){
        btn_send = findViewById(R.id.btn_send);

        image_arrow = findViewById(R.id.image_arrow);


        edtextreg_password = findViewById(R.id.edtextreg_password);
        edtextreg_password.setFocusableInTouchMode(false);
        edtextreg_password.setOnClickListener(view -> {
            edtextreg_password.setFocusableInTouchMode(true);
            edtextreg_password.setFocusableInTouchMode(true);
            edtextreg_password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtextreg_password, InputMethodManager.SHOW_IMPLICIT);
        });

        edtext_retry_password = findViewById(R.id.edtext_retry_password);
        edtext_retry_password.setFocusableInTouchMode(false);
        edtext_retry_password.setOnClickListener(view -> {
            edtext_retry_password.setFocusableInTouchMode(true);
            edtext_retry_password.setFocusableInTouchMode(true);
            edtext_retry_password.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtext_retry_password, InputMethodManager.SHOW_IMPLICIT);
        });
    }


    private void actionButton() {
        image_arrow.setOnClickListener(view -> finish());


        btn_send.setOnClickListener(v -> {
            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                if (edtextreg_password.getText().toString().trim().isEmpty()){
                    edtextreg_password.setError("كلمة المرور اكبر من ٨");
                    edtextreg_password.setFocusableInTouchMode(true);
                    edtextreg_password.requestFocus();
                    edtextreg_password.setFocusableInTouchMode(true);
                }else if (edtextreg_password.getText().toString().length()<8){
                    edtextreg_password.setError("كلمة المرور اكبر من ٨");
                    edtextreg_password.setFocusableInTouchMode(true);
                    edtextreg_password.requestFocus();
                    edtextreg_password.setFocusableInTouchMode(true);
                }else if (edtext_retry_password.getText().toString().trim().isEmpty()){
                    edtext_retry_password.setError("اعادة كتابة كلمة المرور اكبر من ٨ ");
                    edtext_retry_password.setFocusableInTouchMode(true);
                    edtext_retry_password.requestFocus();
                    edtext_retry_password.setFocusableInTouchMode(true);
                }else if (edtext_retry_password.getText().toString().length()<8){
                    edtext_retry_password.setError("اعادة كتابة كلمة المرور اكبر من ٨ ");
                    edtext_retry_password.setFocusableInTouchMode(true);
                    edtext_retry_password.requestFocus();
                    edtext_retry_password.setFocusableInTouchMode(true);
                }else{
                    if(edtextreg_password.getText().toString().trim().equals(edtextreg_password.getText().toString().trim())){
                        addReqestData();
                    }else{
                        edtext_retry_password.setError("تاكد من كتابه الباسورد ");
                        edtext_retry_password.setFocusableInTouchMode(true);
                        edtext_retry_password.requestFocus();
                        edtext_retry_password.setFocusableInTouchMode(true);
                    }

                }

            }else{
                Toast.makeText(ResetPassActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(ResetPassActivity.this);
        progressDialog.setMessage("انتظر قليلا...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(ResetPassActivity.this);
        Gson gson = new Gson();

        DataChangePassword contactsTop=new DataChangePassword();
        contactsTop.setPassword(edtextreg_password.getText().toString().trim());
        contactsTop.setPassword_confirmation(edtext_retry_password.getText().toString().trim());
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.changePassword
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
                Toast.makeText(ResetPassActivity.this, "تم تغير الباسورد بنجاح", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(ResetPassActivity.this, Home.class);
////                intent.putExtra("token",dataDelevery.getData().getToken());
//                startActivity(intent);
//                finish();
                edtextreg_password.setText("");
                edtext_retry_password.setText("");
            }


        }, error -> {

            progressDialog.dismiss();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(getApplicationContext(), "Error Network Time Out", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                //                    finish();
                Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();

            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization","Bearer "+
                        SharedPrefManager.getInstance(getApplicationContext()).gettoken());
                return headers;
            }
        };;
        requestQueue.add(jsonObjectRequest);

    }

}