package com.otex.ekrar.Home.add_document;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
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
import com.otex.ekrar.Home.Home;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.addDataDoc.AddDataDocFromBootomSheet;
import com.otex.ekrar.startApp.LoginActivity;
import com.otex.ekrar.startApp.model.DataSendLogin;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EkrarNase extends AppCompatActivity {

    // * اقرار نصي

    ImageButton imageButton;
    EditText nationalId_ekrarNace,address_ekrarNace,type_ekrarNace;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekrar_nase);

        defineUi();
        actionUi();
    }

    private void defineUi() {
        imageButton=findViewById(R.id.image_arrow);
        nationalId_ekrarNace=findViewById(R.id.nationalId_ekrarNace);
        address_ekrarNace=findViewById(R.id.address_ekrarNace);
        type_ekrarNace=findViewById(R.id.type_ekrarNace);
        type_ekrarNace=findViewById(R.id.type_ekrarNace);
        send=findViewById(R.id.btn_add_ekrarNase);
    }



   private void actionUi(){
        imageButton.setOnClickListener(view -> {finish();});

       send.setOnClickListener(view -> {

           ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
           if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
               //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

               if (nationalId_ekrarNace.getText().toString().trim().isEmpty()){
                   nationalId_ekrarNace.setError("    ");
                   nationalId_ekrarNace.setFocusableInTouchMode(true);
                   nationalId_ekrarNace.requestFocus();
                   nationalId_ekrarNace.setFocusableInTouchMode(true);
               }else if (address_ekrarNace.getText().toString().trim().isEmpty()){
                   address_ekrarNace.setError("  ");
                   address_ekrarNace.setFocusableInTouchMode(true);
                   address_ekrarNace.requestFocus();
                   address_ekrarNace.setFocusableInTouchMode(true);
               }else if (type_ekrarNace.getText().toString().trim().isEmpty()){
                   type_ekrarNace.setError(  "   ");
                   type_ekrarNace.setFocusableInTouchMode(true);
                   type_ekrarNace.requestFocus();
                   type_ekrarNace.setFocusableInTouchMode(true);
               } else{
                   addReqestData();
//                        startActivity(new Intent(getApplicationContext(),All_LocationAndService_Sico.class));
               }

           }else{
               Toast.makeText(EkrarNase.this, "Check Your Network", Toast.LENGTH_SHORT).show();
           }

       });
    }




    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(EkrarNase.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(EkrarNase.this);
        Gson gson = new Gson();

        AddDataDocFromBootomSheet contactsTop=new AddDataDocFromBootomSheet();
        contactsTop.setDocumentType(4);
        contactsTop.setNationalId(nationalId_ekrarNace.getText().toString().trim());
        contactsTop.setAddress(address_ekrarNace.getText().toString().trim());
        contactsTop.setDescription(type_ekrarNace.getText().toString().trim());

        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.add_document
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
                Toast.makeText(EkrarNase.this, "تم الارسال بنجاح ", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(EkrarNase.this, Home.class);
                intent.putExtra("token",dataDelevery.getData().getToken());
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
        };
        requestQueue.add(jsonObjectRequest);
    }

}