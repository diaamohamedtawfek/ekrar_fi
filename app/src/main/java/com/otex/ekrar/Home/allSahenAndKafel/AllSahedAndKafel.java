package com.otex.ekrar.Home.allSahenAndKafel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.otex.ekrar.Adapter.Send_decument_Recycal_ViewHolder;
import com.otex.ekrar.Home.Send_Resived_Document.BottomsheetDialogCashDoc;
import com.otex.ekrar.Home.Send_Resived_Document.SentDocument;
import com.otex.ekrar.Home.allSahenAndKafel.model.DatawitnessDocument;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataShowSingelDocumentsources.DataShowSingelDocument;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;
import com.otex.ekrar.model.dataAllSendDucument.DataResponseAllSentDocument;
import com.otex.ekrar.model.dataAllSendDucument.DataSendToFetchAllSendDucument;
import com.otex.ekrar.startApp.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllSahedAndKafel extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    ImageButton imageButton;

    RecyclerView recycal_deliverys;
    private AllSend_decument_Recycal_ViewHolder recyclerView_dAdapter;
    public List<DataFinalResponseAllsahed> listItems = new ArrayList<>();
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

TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sahed_and_kafel);
        url = getIntent().getStringExtra("url");

        name=findViewById(R.id.txt_docu);
        name.setText(getIntent().getStringExtra("name"));
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
        recyclerView_dAdapter = new AllSend_decument_Recycal_ViewHolder(listItems, AllSahedAndKafel.this, getApplicationContext(),"send");
        recycal_deliverys.setAdapter(recyclerView_dAdapter);

        getjson();

    }

    private void getjson() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);
                                mSwipeRefreshLayout.setRefreshing(false);
                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DatawitnessDocument dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DatawitnessDocument.class);


                                        Log.e("response_booking2", "" + dataDelevery.getData().getDocuments().size());
//                if (dataDelevery.getSuccess()) {

                                        int lengt_for = dataDelevery.getData().getDocuments().size();

                                        Log.e("lengt_for >>>>>>>>>>>>>", String.valueOf(lengt_for));

                                        if (lengt_for > 0) {

                                            for (int I = 0; I < lengt_for; I++) {
                                                Log.e(">>>>>>>>>>>>>", String.valueOf(listItems.size()));

                                                listItems.add(new DataFinalResponseAllsahed(
                                                        dataDelevery.getData().getDocuments().get(I).getCreatedAt(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getId(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getSenderId(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getReceiverId(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getCurrencyId(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getConfirmed() ,
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getDocumentType(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getDocType(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getStatus(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getPayType(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getProducts(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getDescription(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getAddress(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getDate(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getTotal(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getCode(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getReceiver(),
                                                        dataDelevery.getData().getDocuments().get(I).getDocument().getSender()
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

}