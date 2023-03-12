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
import android.view.View;
import android.widget.TextView;
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
import com.otex.ekrar.Adapter.DataChenges;
import com.otex.ekrar.Adapter.Send_decument_Recycal_ViewHolder;
import com.otex.ekrar.Adapter.ViewInvoiceNewPaymentAdapter;
import com.otex.ekrar.Home.Send_Resived_Document.SentDocument;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataShowSingelDocumentsources.DataShowSingelDocument;
import com.otex.ekrar.model.DataSingelPayMentDetileal.Data;
import com.otex.ekrar.model.DataSingelPayMentDetileal.DataSingelPayMentDetileal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.DataSingelPayMentDetilealFinal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.PaidPayment;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;
import com.otex.ekrar.startApp.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class AddNewPayment extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , DataChenges {


    int documentId,statusOrder;
    String type;


    public List<DataSingelPayMentDetilealFinal> listItems = new ArrayList<>();
    RecyclerView recycal_deliverys;
    private ViewInvoiceNewPaymentAdapter recyclerView_dAdapter;
    private GridLayoutManager gridLayoutManager;

    TextView text_noData;
    int REGISTER_URL=0;
//    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payment);


        documentId = getIntent().getIntExtra("id",0);
        statusOrder = getIntent().getIntExtra("statusOrder",0);
        type = getIntent().getStringExtra("type");

        definUi();

//        getjson();

    }

    @Override
    public void onRefresh() {
        startUI();
    }

    private void definUi(){
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        mSwipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                mSwipeRefreshLayout.setRefreshing(true);
//                // Fetching data from server
//                startUI();
//            }
//        });

        text_noData=findViewById(R.id.text_noData);
        startUI();
    }



    private void startUI() {
//        mSwipeRefreshLayout.setRefreshing(true);
        listItems.clear();
        REGISTER_URL=0;
        recycal_deliverys = findViewById(R.id.products_recycler_view);
        recycal_deliverys.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recycal_deliverys.setLayoutManager(gridLayoutManager);
        recyclerView_dAdapter = new ViewInvoiceNewPaymentAdapter(listItems, AddNewPayment.this,
                getApplicationContext(),type,this,statusOrder);
        recycal_deliverys.setAdapter(recyclerView_dAdapter);

        getjson();

//        recycal_deliverys.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                isScrolling=true;
//                currentItems = gridLayoutManager.getChildCount();
//                totalItems = gridLayoutManager.getItemCount();
//                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
//                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == listItems.size() - 1) {
//                    if (REGISTER_URL<=REGISTER_URL_mareds) {
//                        isScrolling = false;
//                        getjson(REGISTER_URL);
//                    }
//
//                }
//                //
//
//            }
//        });
    }



    private void getjson() {
        Log.e("ofers json>>>>>>>>   ",Urls.show_payment_document+documentId);
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.show_payment_document+documentId,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);

                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DataSingelPayMentDetileal dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DataSingelPayMentDetileal.class);

                                        if(dataDelevery.getData().getFinancialPayments().size()>0){
                                            text_noData.setVisibility(View.GONE);
                                            text_noData.setText("لا يوجد بيانات");
                                            recycal_deliverys.setVisibility(View.VISIBLE);
                                        }else{
                                            text_noData.setVisibility(View.VISIBLE);
                                            recycal_deliverys.setVisibility(View.GONE);
                                        }

                                        for (int i = 0; i < dataDelevery.getData().getFinancialPayments().size(); i++) {

                                        if (dataDelevery.getSuccess().equals(true)) {
                                            listItems.add(new DataSingelPayMentDetilealFinal(
                                                  dataDelevery.getData().getFinancialPayments().get(i).getId(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getFinancialDocumentId() ,
                                                    dataDelevery.getData().getFinancialPayments().get(i).getPayment(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getDate(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getDocumentType(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getStatus(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getCreatedAt(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getUpdatedAt() ,
                                                    dataDelevery.getData().getFinancialPayments().get(i).getPaid(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getRest(),
                                                    dataDelevery.getData().getFinancialPayments().get(i).getPaidPayments()
                                            ));
                                            recyclerView_dAdapter.notifyDataSetChanged();
                                        }
                                            recyclerView_dAdapter.notifyDataSetChanged();
                                        }
                                        recyclerView_dAdapter.notifyDataSetChanged();
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

    @Override
    public void notifyDataChanged() {
        startUI();
    }
}