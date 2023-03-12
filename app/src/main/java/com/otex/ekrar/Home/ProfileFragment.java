package com.otex.ekrar.Home;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.startApp.ConfirmationActivity;
import com.otex.ekrar.startApp.LoginActivity;
import com.otex.ekrar.startApp.ResetPassActivity;
import com.otex.ekrar.startApp.model.DataResponseConfirmCode.DataResponceConfurmCode;
import com.otex.ekrar.startApp.model.DataSendConfirmCodeForgetPassword;
import com.otex.ekrar.startApp.model.DataSendUpdateProfile;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
    }


    TextView txt_logout;
    EditText edtext_name, edtext_mail, edtext_phone, text_national_id,nationalty;

    View view;
    TextView txt_changePassword;
    Button btn_update;
    int gender=0;
    int stats=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_profile, container, false);

        defineUi();
        actionUI();
        getjsonProfile();

        return view ;
    }




    private void defineUi() {

        txt_changePassword=view.findViewById(R.id.txt_changePassword);

        nationalty =  view.findViewById(R.id.nationalty);
        edtext_name =  view.findViewById(R.id.text_name_profile);
        edtext_mail =  view.findViewById(R.id.edtext_mail_profile);
        edtext_phone =  view.findViewById(R.id.edtext_phone_profile);
        text_national_id =  view.findViewById(R.id.national_id);
        txt_logout=view.findViewById(R.id.txt_logout);

        btn_update =  view.findViewById(R.id.btn_save);
    }

    private void actionUI() {
        txt_changePassword.setOnClickListener(view1 -> startActivity(new Intent(getActivity(), ResetPassActivity.class)));
        txt_logout.setOnClickListener(view1 -> {
            SharedPrefManager.getInstance(getActivity()).logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        btn_update.setOnClickListener(view1 ->
        {
            if (stats==0){
                stats=1;
                nationalty.setEnabled(true);
                edtext_name.setEnabled(true);
                edtext_mail.setEnabled(true);
                edtext_phone.setEnabled(true);
                btn_update.setText("حفظ");
            }else if(stats==1){
                if (gender!=0){
                    addReqestData();
                    stats=0;
                }
            }
        });


    }



    private void getjsonProfile() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.profile,
                        response -> {
                         Log.e("ofers json>>>>>>>>   ",response);

                            if (response.length() > 0) {
                                //progressDialog.dismiss();
                                try {
                                    Gson gson = new Gson();
                                    DataResponceLogin dataDelevery;
                                    dataDelevery = gson.fromJson(response.toString(), DataResponceLogin.class);


                                    if (dataDelevery.getSuccess().equals(true)) {

                                        edtext_name.setText(dataDelevery.getData().getUser().getName());
                                        nationalty.setText(dataDelevery.getData().getUser().getNationality());
                                        edtext_mail.setText(dataDelevery.getData().getUser().getEmail());
                                        edtext_phone.setText(dataDelevery.getData().getUser().getMobile());
                                        text_national_id.setText(dataDelevery.getData().getUser().getNationalId());
                                        gender=dataDelevery.getData().getUser().getGender();
                                    }
                                }catch (Exception e){

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                try {


                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(getActivity(), "Error Network Time Out", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                        SharedPrefManager.getInstance(getActivity()).logout();
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                        getActivity().finish();
                                        //startActivity(new Intent(getApplicationContext(),Log_In.class));
                                    } else if (error instanceof ServerError) {
//                                        Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(getActivity(), "NetworkError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getActivity(), "ParseError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    }
                                }catch (Exception e){

                                }
                            }
                        }
                )        {
                    //            /** Passing some request headers* */
                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Authorization","Bearer "+
                                SharedPrefManager.getInstance(getActivity()).gettoken());
                        return headers;
                    }
                };;
                MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            } else {
            }
        }catch (Exception r){

        }
    }


    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Gson gson = new Gson();

        DataSendUpdateProfile contactsTop=new DataSendUpdateProfile();
        contactsTop.setEmail(edtext_mail.getText().toString().trim());
        contactsTop.setGender(gender);
        contactsTop.setMobile(edtext_phone.getText().toString().trim());
        contactsTop.setName(edtext_name.getText().toString().trim());
        contactsTop.setNationality(nationalty.getText().toString().trim());
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.edit_profile
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Gson gson1 = new Gson();
            DataResponceLogin dataDelevery;
            dataDelevery = gson1.fromJson(response.toString(), DataResponceLogin.class);

            if (!dataDelevery.getSuccess().equals(true)){
                Log.e("error","not Re");
                Toast.makeText(getActivity(), dataDelevery.getMessage(), Toast.LENGTH_LONG).show();
                getjsonProfile();


            }else{
//                Toast.makeText(getActivity(), "تم التسجيل بنجاح", Toast.LENGTH_LONG).show();
////                SharedPrefManager sharedPrefManager = new SharedPrefManager(ConfirmUserActivity.this);
////                sharedPrefManager.userLogin(dataDelevery.getData().getToken(),dataDelevery.getData().getUser().getId().toString(),null);
//
//                Intent intent = new Intent(getActivity(), Home.class);
////                intent.putExtra("token",dataDelevery.getData().getToken());
//                startActivity(intent);
//                finish();
            }


        }, error -> {

            progressDialog.dismiss();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(getActivity(), "Error Network Time Out", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                //                    finish();
                Toast.makeText(getActivity(), "AuthFailureError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_LONG).show();

            } else if (error instanceof NetworkError) {
                Toast.makeText(getActivity(), "NetworkError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(getActivity(), "ParseError", Toast.LENGTH_LONG).show();

            }
        })
        {
            /** Passing some request headers* */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization","Bearer "+
                        SharedPrefManager.getInstance(getActivity()).gettoken());
                return headers;
            }
        };;
        requestQueue.add(jsonObjectRequest);

    }
}