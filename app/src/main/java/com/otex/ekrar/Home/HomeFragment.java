package com.otex.ekrar.Home;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.otex.ekrar.Home.Send_Resived_Document.RecievedDocument;
import com.otex.ekrar.Home.Send_Resived_Document.SentDocument;
import com.otex.ekrar.Home.activtys.AllNotfiction;
import com.otex.ekrar.Home.activtys.DetelesResvie_And_SendDcument;
import com.otex.ekrar.Home.allSahenAndKafel.AllSahedAndKafel;
import com.otex.ekrar.Home.allSahenAndKafel.DataFinalResponseAllsahed;
import com.otex.ekrar.Home.allSahenAndKafel.model.DatawitnessDocument;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.countNotification.DataCountAllNotifications;
import com.otex.ekrar.startApp.LoginActivity;

import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }



    TextView textNameUser;
    View view;
    CardView idCard_SendDecument,idCard_RecevedDecument;
    CardView cardSahed,sponsor_document;
    SharedPrefManager sharedPrefManager ;

    CardView fabNotification;
    RelativeLayout re_no;
    TextView textNuNotification;

    @Override
    public void onResume() {
        super.onResume();

        getjson();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        re_no=view.findViewById(R.id.re_no);
        textNuNotification=view.findViewById(R.id.textNuNotification);
        textNuNotification.setText("");
        fabNotification=view.findViewById(R.id.fabNotification);
        fabNotification.setOnClickListener(view1 -> {
            getActivity().startActivity(new Intent(getActivity(), AllNotfiction.class));
        });



        idCard_SendDecument=view.findViewById(R.id.idCard_SendDecument);
        idCard_SendDecument.setOnClickListener(view1 -> {
            getActivity().startActivity(new Intent(getActivity(), SentDocument.class));
        });

        idCard_RecevedDecument=view.findViewById(R.id.idCard_RecevedDecument);
        idCard_RecevedDecument.setOnClickListener(view1 -> {
            getActivity().startActivity(new Intent(getActivity(), RecievedDocument.class));
        });


        cardSahed=view.findViewById(R.id.cardSahed);
        cardSahed.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), AllSahedAndKafel.class);
            intent.putExtra("url", Urls.witness_document);
            intent.putExtra("name", "الشهود");
            startActivity(intent);
        });


        sponsor_document=view.findViewById(R.id.sponsor_document);
        sponsor_document.setOnClickListener(view1 -> {
            Intent intent=new Intent(getActivity(), AllSahedAndKafel.class);
            intent.putExtra("url", Urls.sponsor_document);
            intent.putExtra("name", "الكفلاء");
            startActivity(intent);
        });


        textNameUser=view.findViewById(R.id.textNameUser);
        textNameUser.setText("مرحبا بك  "+sharedPrefManager.getName());

        getjson();

        return view ;
    }


    private void getjson() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.api_test+"unread_notification",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);
                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DataCountAllNotifications dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DataCountAllNotifications.class);


                                        if (dataDelevery.getSuccess().equals(true)){
                                            if (dataDelevery.getData().getNotifications()>9){
                                                textNuNotification.setText("9+");
                                            }else
                                            textNuNotification.setText(dataDelevery.getData().getNotifications().toString());
                                        }else{
                                            textNuNotification.setText("");
                                            re_no.setVerticalGravity(View.GONE);
                                        }

                                        if(dataDelevery.getData().getNotifications()<1){
                                            re_no.setVerticalGravity(View.GONE);
                                        }


                                    }catch (Exception e){
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                try {

                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(getActivity(), "Error Network Time Out", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
//                                        SharedPrefManager.getInstance(getApplicationContext()).logout();
//                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                                        finish();
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
}