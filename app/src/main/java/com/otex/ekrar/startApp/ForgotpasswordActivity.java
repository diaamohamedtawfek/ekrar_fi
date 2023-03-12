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
import android.widget.ImageButton;
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
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.startApp.model.SendForgetPassord;
import com.otex.ekrar.startApp.model.dataResponseForgetPassword.DataResponceForgetPassword;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotpasswordActivity extends AppCompatActivity {

    ImageButton image_arrow;
    EditText idCard;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        defineIds();
        actionButton();
    }

    private void defineIds() {

        btn_send=findViewById(R.id.btn_send);

        image_arrow=findViewById(R.id.image_arrow);

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

    }


    private void actionButton() {
        image_arrow.setOnClickListener((View.OnClickListener) view -> finish());


        btn_send.setOnClickListener(v -> {
            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                if (idCard.getText().toString().trim().isEmpty()){
                    idCard.setError("رقم الهويه");
                    idCard.setFocusableInTouchMode(true);
                    idCard.requestFocus();
                    idCard.setFocusableInTouchMode(true);
                }else{
                    addReqestData();
//                        startActivity(new Intent(getApplicationContext(),All_LocationAndService_Sico.class));
                }

            }else{
                Toast.makeText(ForgotpasswordActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }

        });
    }



    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(ForgotpasswordActivity.this);
        progressDialog.setMessage("انتظر قليلا ...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(ForgotpasswordActivity.this);
        Gson gson = new Gson();

        SendForgetPassord contactsTop=new SendForgetPassord();
        contactsTop.setNational_id(idCard.getText().toString().trim());

        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.forgetPassword
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Gson gson1 = new Gson();
            DataResponceForgetPassword  dataDelevery;
            dataDelevery = gson1.fromJson(response.toString(), DataResponceForgetPassword.class);

            if (!dataDelevery.getSuccess().equals(true)){
                Log.e("error","not Re");

            }else{
//                Toast.makeText(ForgotpasswordActivity.this, "تم التسجيل بنجاح ", Toast.LENGTH_LONG).show();
                SharedPrefManager sharedPrefManager = new SharedPrefManager(ForgotpasswordActivity.this);
                sharedPrefManager.userLogin(dataDelevery.getData().getToken(),"",null,"");

                Intent intent = new Intent(ForgotpasswordActivity.this, ConfirmationActivity.class);
                intent.putExtra("idCard",idCard.getText().toString().trim());
                intent.putExtra("token",dataDelevery.getData().getToken());
                intent.putExtra("forget","forget");
                startActivity(intent);
                finish();
            }


        }, error -> {

            //get status code here
            String statusCode = String.valueOf(error.networkResponse.statusCode);
            //get response body and parse with appropriate encoding
            if(error.networkResponse.data!=null) {
            }

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