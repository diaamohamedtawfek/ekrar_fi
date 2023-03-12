package com.otex.ekrar.Home.activtys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.google.gson.Gson;
import com.otex.ekrar.Adapter.AllNotificationAdapter;
import com.otex.ekrar.Home.allSahenAndKafel.AllSahedAndKafel;
import com.otex.ekrar.Home.allSahenAndKafel.AllSend_decument_Recycal_ViewHolder;
import com.otex.ekrar.Home.allSahenAndKafel.DataFinalResponseAllsahed;
import com.otex.ekrar.Home.allSahenAndKafel.model.DatawitnessDocument;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.AllNotification.DataAllNotifications;
import com.otex.ekrar.model.DataAllNotificationFinal;
import com.otex.ekrar.startApp.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllNotfiction extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    ImageButton imageButton;

    RecyclerView recycal_deliverys;
    private AllNotificationAdapter recyclerView_dAdapter;
    public List<DataAllNotificationFinal> listItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    int REGISTER_URL=0;
    //    int REGISTER_URL_mareds;
//    Boolean isScrolling = false;
//    int currentItems, totalItems, scrollOutItems;
    SwipeRefreshLayout mSwipeRefreshLayout;


    ImageView fab;

    int states=4;
    int dicumentType=0;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notfiction);
        url = getIntent().getStringExtra("url");

        definUi();
    }


    @Override
    public void onRefresh() {
        startUI();
    }



    private void definUi(){
        imageButton=findViewById(R.id.image_arrow);
        imageButton.setOnClickListener(view -> {
            finish();
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                startUI();
            }
        });
    }



    private void startUI() {
        mSwipeRefreshLayout.setRefreshing(true);
        listItems.clear();
        REGISTER_URL=0;
        recycal_deliverys = findViewById(R.id.recycal_allcatigry);
        recycal_deliverys.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recycal_deliverys.setLayoutManager(gridLayoutManager);
        recyclerView_dAdapter = new AllNotificationAdapter(listItems,getApplicationContext(),AllNotfiction.this);
        recycal_deliverys.setAdapter(recyclerView_dAdapter);

        getjson();

    }

    private void getjson() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.api_test+"notification",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);
                                mSwipeRefreshLayout.setRefreshing(false);
                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DataAllNotifications dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DataAllNotifications.class);


                                        Log.e("response_booking2", "" + dataDelevery.getData().getNotifications().size());
//                if (dataDelevery.getSuccess()) {

                                        int lengt_for = dataDelevery.getData().getNotifications().size();

                                        Log.e("lengt_for >>>>>>>>>>>>>", String.valueOf(lengt_for));

                                        if (lengt_for > 0) {

                                            for (int I = 0; I < lengt_for; I++) {
                                                Log.e(">>>>>>>>>>>>>", String.valueOf(listItems.size()));

                                                listItems.add(new DataAllNotificationFinal(
                                                                dataDelevery.getData().getNotifications().get(I).getId(),
                                                        dataDelevery.getData().getNotifications().get(I).getUserId(),
                                                        dataDelevery.getData().getNotifications().get(I).getMsg(),
                                                        dataDelevery.getData().getNotifications().get(I).getDocumentType(),
                                                        dataDelevery.getData().getNotifications().get(I).getDocumentId(),
                                                        dataDelevery.getData().getNotifications().get(I).getStatus(),
                                                        dataDelevery.getData().getNotifications().get(I).getCreatedAt(),
                                                        dataDelevery.getData().getNotifications().get(I).getUpdatedAt()
                                                        )
                                                );

                                                Log.e(">>>>>>>>>>>>>", String.valueOf(listItems.size()));
                                                recyclerView_dAdapter.notifyDataSetChanged();
                                            }
                                            recyclerView_dAdapter.notifyDataSetChanged();
                                        } else {
                                            mSwipeRefreshLayout.setRefreshing(false);
                                            // progressDialog.dismiss();
                                            Log.e("test", "no");
                                        }
//                }
                                        getjsonread();
                                    }catch (Exception e){
                                        mSwipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                try {

                                    mSwipeRefreshLayout.setRefreshing(false);
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(getApplicationContext(), "Error Network Time Out", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                        //startActivity(new Intent(getApplicationContext(),Log_In.class));
                                    } else if (error instanceof ServerError) {
//                                        Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();
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
                                SharedPrefManager.getInstance(getApplicationContext()).gettoken());
                        return headers;
                    }
                };;
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            } else {
            }
        }catch (Exception r){

        }


    }



    private void getjsonread() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.api_test+"read_notification",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);
                                mSwipeRefreshLayout.setRefreshing(false);
                                if (response.length() > 0) {

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                try {

                                    mSwipeRefreshLayout.setRefreshing(false);
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(getApplicationContext(), "Error Network Time Out", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
                                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                        //startActivity(new Intent(getApplicationContext(),Log_In.class));
                                    } else if (error instanceof ServerError) {
//                                        Toast.makeText(getActivity(), "ServerError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();
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
                                SharedPrefManager.getInstance(getApplicationContext()).gettoken());
                        return headers;
                    }
                };;
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            } else {
            }
        }catch (Exception r){

        }


    }

}