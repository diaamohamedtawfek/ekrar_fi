package com.otex.ekrar.Home.Send_Resived_Document;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.otex.ekrar.Adapter.Send_decument_Recycal_ViewHolder;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataSendFilter;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;
import com.otex.ekrar.model.dataAllSendDucument.DataResponseAllSentDocument;
import com.otex.ekrar.model.dataAllSendDucument.DataSendToFetchAllSendDucument;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecievedDocument extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    ImageButton imageButton;

    Button button_documentType,button_satesDoc;
    RecyclerView recycal_deliverys;
    private Send_decument_Recycal_ViewHolder recyclerView_dAdapter;
    public List<DataAllSendDucumentFinal> listItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    int REGISTER_URL=0;
    //    int REGISTER_URL_mareds;
//    Boolean isScrolling = false;
//    int currentItems, totalItems, scrollOutItems;
    SwipeRefreshLayout mSwipeRefreshLayout;

    int states=4;
    int dicumentType=0;

    ImageView fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_document);


        definUi();
        actionUi();
    }

    private void actionUi() {
        imageButton.setOnClickListener(view -> {
            finish();
        });

        button_documentType.setOnClickListener(view -> {
            showBottomSheetDialog();
        });

        button_satesDoc.setOnClickListener(view -> {
            showBottomSatesDoc();
        });
    }

    @Override
    public void onRefresh() {
        startUI();
    }


    private void definUi(){
        imageButton=findViewById(R.id.image_arrow);
        button_documentType=findViewById(R.id.button_documentType);
        button_satesDoc=findViewById(R.id.button_satesDoc);

         fab = (ImageView) findViewById(R.id.floatingActionButton_cash);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomsheetDialogCashDoc bottomsheetDialog = new BottomsheetDialogCashDoc(1);
                bottomsheetDialog.show(getSupportFragmentManager(), bottomsheetDialog.getTag());
            }
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
        recyclerView_dAdapter = new Send_decument_Recycal_ViewHolder(listItems,RecievedDocument.this, getApplicationContext(),"res");
        recycal_deliverys.setAdapter(recyclerView_dAdapter);

        getjson(dicumentType,states);

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


    private void getjson(final int docType,int states) {


        final RequestQueue requestQueue = Volley.newRequestQueue(RecievedDocument.this);
        Gson gson1 = new Gson();

        DataSendToFetchAllSendDucument contactsTop = new DataSendToFetchAllSendDucument();

        contactsTop.setDocumentType(docType);
        contactsTop.setStatus(states);
        Log.e("Volley:Response ", gson1.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson1.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.recieved_document
                , jsonObject, response -> {
            Log.e("response_booking", "" + response);

//            JSONObject sys = response;//// result

            mSwipeRefreshLayout.setRefreshing(false);
            try {
                Gson gson = new Gson();
                DataResponseAllSentDocument dataDelevery;
                dataDelevery = gson.fromJson(response.toString(), DataResponseAllSentDocument.class);


                Log.e("response_booking2", "" + dataDelevery.getData().getDocumentsSent().size());
//                if (dataDelevery.getSuccess()) {

                int lengt_for = dataDelevery.getData().getDocumentsSent().size();

                Log.e("lengt_for >>>>>>>>>>>>>", String.valueOf(lengt_for));

                if (lengt_for > 0) {

                    for (int I = 0; I < lengt_for; I++) {
                        Log.e(">>>>>>>>>>>>>", String.valueOf(listItems.size()));

                        listItems.add(new DataAllSendDucumentFinal(
                                        dataDelevery.getData().getDocumentsSent().get(I).getId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getSenderId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getReceiverId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCurrencyId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getProducts(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDescription(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getAddress(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDate(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getTotal(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getPayType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCode(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getConfirmed(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDocumentType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDocType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getStatus(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCreatedAt(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getUpdatedAt(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getReceiver(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getSender(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCurrency()
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

            }


        }, error -> {

            mSwipeRefreshLayout.setRefreshing(false);
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
        }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " +
                        SharedPrefManager.getInstance(getApplicationContext()).gettoken());
                return headers;
            }
        };
        ;
        requestQueue.add(jsonObjectRequest);
    }


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bootom_sheet_ducoment_type);

        ImageView closeimage=bottomSheetDialog.findViewById(R.id.closeimage);
        closeimage.setOnClickListener(view -> bottomSheetDialog.dismiss());

        TextView text_ekrar_nase=bottomSheetDialog.findViewById(R.id.t1);
        text_ekrar_nase.setOnClickListener(view -> {
            dicumentType=1;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView id_sand_eys=bottomSheetDialog.findViewById(R.id.t2);
        id_sand_eys.setOnClickListener(view -> {
            dicumentType=2;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView id_sand_payment=bottomSheetDialog.findViewById(R.id.t3);
        id_sand_payment.setOnClickListener(view -> {
            dicumentType=3;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView text_taslem=bottomSheetDialog.findViewById(R.id.t4);
        text_taslem.setOnClickListener(view -> {
            dicumentType=4;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView t5=bottomSheetDialog.findViewById(R.id.t5);
        t5.setOnClickListener(view -> {
            dicumentType=0;
            bottomSheetDialog.dismiss();
            showData();
        });



        bottomSheetDialog.show();
    }



    private void showBottomSatesDoc() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bootom_sheet_states_send_dec);

        ImageView closeimage=bottomSheetDialog.findViewById(R.id.closeimage);
        closeimage.setOnClickListener(view -> bottomSheetDialog.dismiss());

        TextView text_ekrar_nase=bottomSheetDialog.findViewById(R.id.t1);
        text_ekrar_nase.setOnClickListener(view -> {
            states=0;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView id_sand_eys=bottomSheetDialog.findViewById(R.id.t2);
        id_sand_eys.setOnClickListener(view -> {
            states=1;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView id_sand_payment=bottomSheetDialog.findViewById(R.id.t3);
        id_sand_payment.setOnClickListener(view -> {
            states=2;
            bottomSheetDialog.dismiss();
            showData();
        });

        TextView text_taslem=bottomSheetDialog.findViewById(R.id.t4);
        text_taslem.setOnClickListener(view -> {
            states=3;
            bottomSheetDialog.dismiss();
            showData();
        });


        TextView text_all=bottomSheetDialog.findViewById(R.id.t5);
        text_all.setOnClickListener(view -> {
            states=4;
            bottomSheetDialog.dismiss();
            showData();
        });



        bottomSheetDialog.show();
    }
    void showData(){
        listItems.clear();
        startUI();
    }




    /// * filtter -----


    public void applySearch(String name, String from, String to, String paytype,String code){

        mSwipeRefreshLayout.setRefreshing(true);
        listItems.clear();
        REGISTER_URL=0;
        recycal_deliverys = findViewById(R.id.recycal_allcatigry);
        recycal_deliverys.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recycal_deliverys.setLayoutManager(gridLayoutManager);
        recyclerView_dAdapter = new Send_decument_Recycal_ViewHolder(listItems,RecievedDocument.this, getApplicationContext(),"res");
        recycal_deliverys.setAdapter(recyclerView_dAdapter);

        final RequestQueue requestQueue = Volley.newRequestQueue(RecievedDocument.this);
        Gson gson1 = new Gson();

        DataSendFilter contactsTop = new DataSendFilter();

        if(!from.isEmpty())
        contactsTop.setFrom(from);

        if(!to.isEmpty())
        contactsTop.setTo(to);
        contactsTop.setDocumentType(dicumentType);

        try{
            if(!paytype.isEmpty()){
                contactsTop.setPayType(Integer.valueOf(paytype));
            }

        }catch (Exception e){}
        try{
            contactsTop.setCode(Integer.valueOf(code));
        }catch (Exception e){}

        if(!name.isEmpty())
        contactsTop.setNationalId(name);
        contactsTop.setType(1);

        Log.e("Volley:Response ", gson1.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson1.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.api_test+"filter"
                , jsonObject, response -> {
            Log.e("response_booking", "" + response);

//            JSONObject sys = response;//// result

            mSwipeRefreshLayout.setRefreshing(false);
            try {
                Gson gson = new Gson();
                DataResponseAllSentDocument dataDelevery;
                dataDelevery = gson.fromJson(response.toString(), DataResponseAllSentDocument.class);


                Log.e("response_booking2", "" + dataDelevery.getData().getDocumentsSent().size());
//                if (dataDelevery.getSuccess()) {

                int lengt_for = dataDelevery.getData().getDocumentsSent().size();

                Log.e("lengt_for >>>>>>>>>>>>>", String.valueOf(lengt_for));

                if (lengt_for > 0) {

                    for (int I = 0; I < lengt_for; I++) {
                        Log.e(">>>>>>>>>>>>>", String.valueOf(listItems.size()));

                        listItems.add(new DataAllSendDucumentFinal(
                                        dataDelevery.getData().getDocumentsSent().get(I).getId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getSenderId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getReceiverId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCurrencyId(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getProducts(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDescription(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getAddress(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDate(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getTotal(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getPayType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCode(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getConfirmed(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDocumentType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getDocType(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getStatus(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCreatedAt(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getUpdatedAt(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getReceiver(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getSender(),
                                        dataDelevery.getData().getDocumentsSent().get(I).getCurrency()
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

            }


        }, error -> {

            mSwipeRefreshLayout.setRefreshing(false);
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
        }) {
            /**
             * Passing some request headers*
             */
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", "Bearer " +
                        SharedPrefManager.getInstance(getApplicationContext()).gettoken());
                return headers;
            }
        };
        ;
        requestQueue.add(jsonObjectRequest);
    }
}