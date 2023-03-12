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
import com.otex.ekrar.startApp.model.DataReSendCodeConfirmm;
import com.otex.ekrar.startApp.model.DataResponseConfirmCode.DataResponceConfurmCode;
import com.otex.ekrar.startApp.model.DataSendConfirmCond;
import com.otex.ekrar.startApp.model.dataResponseForgetPassword.DataResponceForgetPassword;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmUserActivity extends AppCompatActivity {


    ImageButton image_arrow;
    EditText codeConfirm;
    private String token;
    private String idCard;
    Button sendConfirmButton;
    TextView resend_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_user);

        token=getIntent().getStringExtra("token");
        idCard=getIntent().getStringExtra("idCard");

        defineIds();
        actionButton();
    }




    private void defineIds(){
        sendConfirmButton = findViewById(R.id.signUp_ConfirmCode);
        resend_text = findViewById(R.id.resend_text);

        image_arrow = findViewById(R.id.image_arrow);

        codeConfirm = findViewById(R.id.edtext_add_email);
        codeConfirm.setFocusableInTouchMode(false);
        codeConfirm.setOnClickListener(view -> {
            codeConfirm.setFocusableInTouchMode(true);
            codeConfirm.setFocusableInTouchMode(true);
            codeConfirm.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(codeConfirm, InputMethodManager.SHOW_IMPLICIT);
        });




    }


    private void actionButton() {
        image_arrow.setOnClickListener(view -> finish());


        resend_text.setOnClickListener(view -> resendCOde());

        sendConfirmButton.setOnClickListener(v -> {
             ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                if (codeConfirm.getText().toString().trim().isEmpty()&&codeConfirm.getText().toString().length()<4){
                    codeConfirm.setError("كود التأكيد");
                    codeConfirm.setFocusableInTouchMode(true);
                    codeConfirm.requestFocus();
                    codeConfirm.setFocusableInTouchMode(true);
                }else{
                            addReqestData();
                }

            }else{
                Toast.makeText(ConfirmUserActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(ConfirmUserActivity.this);
        progressDialog.setMessage("انتظر قليلا...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(ConfirmUserActivity.this);
        Gson gson = new Gson();

        DataSendConfirmCond contactsTop=new DataSendConfirmCond();
        contactsTop.setCode(codeConfirm.getText().toString().trim());
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.accountVerifySignUp
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Gson gson1 = new Gson();
            DataResponceConfurmCode dataDelevery;
            dataDelevery = gson1.fromJson(response.toString(), DataResponceConfurmCode.class);

            if (!dataDelevery.getSuccess().equals(true)){
                Log.e("error","not Re");

            }else{
                Toast.makeText(ConfirmUserActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_LONG).show();
//                SharedPrefManager sharedPrefManager = new SharedPrefManager(ConfirmUserActivity.this);
//                sharedPrefManager.userLogin(dataDelevery.getData().getToken(),dataDelevery.getData().getUser().getId().toString(),null);

                Intent intent = new Intent(ConfirmUserActivity.this, Home.class);
//                intent.putExtra("token",dataDelevery.getData().getToken());
                startActivity(intent);
                        finish();
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




    private void resendCOde(){
        final ProgressDialog progressDialog=new ProgressDialog(ConfirmUserActivity.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(ConfirmUserActivity.this);
        Gson gson = new Gson();

        DataReSendCodeConfirmm contactsTop=new DataReSendCodeConfirmm();
        contactsTop.setNational_id(idCard);
        contactsTop.setStatus(1);
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.resendCode
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Gson gson1 = new Gson();
            DataResponceForgetPassword dataDelevery;
            dataDelevery = gson1.fromJson(response.toString(), DataResponceForgetPassword.class);

            if (!dataDelevery.getSuccess().equals(true)){
                Log.e("error","not Re");

            }else{
                Toast.makeText(ConfirmUserActivity.this, "تم الارسال", Toast.LENGTH_LONG).show();

                SharedPrefManager sharedPrefManager = new SharedPrefManager(ConfirmUserActivity.this);
                sharedPrefManager.userLogin(dataDelevery.getData().getToken(),"",null,"");

//                Intent intent = new Intent(ConfirmationActivity.this, Home.class);
//                intent.putExtra("token",dataDelevery.getData().getToken());
//                startActivity(intent);
//                finish();
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