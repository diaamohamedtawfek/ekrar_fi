package com.otex.ekrar.Home.activtys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.otex.ekrar.Home.add_document.Add_Financial_InvoiceNew;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataAddSponsorWitness;
import com.otex.ekrar.model.DataCurrency.DataCurrency;
import com.otex.ekrar.model.DataResponseAll;
import com.otex.ekrar.model.DataSendConfirmReceved;
import com.otex.ekrar.model.DataSendNewPay;
import com.otex.ekrar.model.DataShowSingelDocumentsources.DataShowSingelDocument;
import com.otex.ekrar.model.dataAllSendDucument.DataResponseAllSentDocument;
import com.otex.ekrar.startApp.ConfirmationActivity;
import com.otex.ekrar.startApp.LoginActivity;
import com.otex.ekrar.startApp.model.DataReSendCodeConfirmm;
import com.otex.ekrar.startApp.model.dataResponseForgetPassword.DataResponceForgetPassword;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Url;

public class DetelesResvie_And_SendDcument extends AppCompatActivity {


    int documentType;
    int documentId,status,statusOrder;
    String type,show;
    ImageButton imageButton;

    TextView national_id_sender,amount,prudect,p,currency;
//    TextView address_resved;
    TextView spinner_paytype,description,id_saned,id_states;
    // * to show payMeant
    Button button_addpayments;
    Button button_accept,button_reject;
    Button btn_shahed2,btn_kafel2;


    Button showShod,showKafel;

    LinearLayout linearsend,linearResvied;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteles_resvie__and__send_dcument);


        imageButton=findViewById(R.id.image_arrow);
        imageButton.setOnClickListener(view -> {
            finish();
        });

        documentType = getIntent().getIntExtra("DOCUMENT_TYPE", 1);
        documentId = getIntent().getIntExtra("DOCUMENT_ID",0);
        show = getIntent().getStringExtra("show");
        type = getIntent().getStringExtra("type");
        status = getIntent().getIntExtra("status",0);

//        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();


        defineUi();
        getjson();

        if(show!=null) {
            btn_shahed2.setVisibility(View.GONE);
            btn_kafel2.setVisibility(View.GONE);

            button_addpayments.setVisibility(View.GONE);
            button_accept.setVisibility(View.GONE);
            button_reject.setVisibility(View.GONE);

            showShod.setVisibility(View.GONE);
            showKafel.setVisibility(View.GONE);
        }
    }

    void defineUi(){
        id_states=findViewById(R.id.id_states);
        national_id_sender=findViewById(R.id.national_id_resved);
//        address_resved=findViewById(R.id.address_resved);
        amount=findViewById(R.id.amount);
        prudect=findViewById(R.id.prudect);
        p=findViewById(R.id.p);
        prudect.setVisibility(documentType==1?View.VISIBLE:View.GONE);
        p.setVisibility(documentType==1?View.VISIBLE:View.GONE);
        currency=findViewById(R.id.currency);
        spinner_paytype=findViewById(R.id.spinner_paytype);
        description=findViewById(R.id.description);
        id_saned=findViewById(R.id.id_saned);
        button_addpayments=findViewById(R.id.button_addpayments);
//        button_addpayments.setVisibility(!type.equals("send") ?View.VISIBLE:View.GONE);
        button_addpayments.setOnClickListener(view -> {
            Intent intent=new Intent(getApplicationContext(), AddNewPayment.class);
            intent.putExtra("type",type);
            intent.putExtra("id",documentId);
            intent.putExtra("statusOrder",statusOrder);
            startActivity(intent);
        });

        showShod=findViewById(R.id.btn_shahed);
        showShod.setOnClickListener(view -> {

            Intent intent=new Intent(getApplicationContext(), Sahed_Kafel_SingelId.class);
            intent.putExtra("type","sahed");
            intent.putExtra("id",documentId);
            startActivity(intent);
        });
        showKafel=findViewById(R.id.btn_kafel);
        showKafel.setOnClickListener(view -> {

            Intent intent=new Intent(getApplicationContext(), Sahed_Kafel_SingelId.class);
            intent.putExtra("type","kafel");
            intent.putExtra("id",documentId);
            startActivity(intent);
        });




        btn_shahed2=findViewById(R.id.btn_shahed2);
        btn_shahed2.setOnClickListener(view -> {
            sentToSahed("ارسال الي شاهد", Urls.add_witness);
        });


        btn_kafel2=findViewById(R.id.btn_kafel2);
        btn_kafel2.setOnClickListener(view -> {
            sentToSahed("ارسال الي كفيل", Urls.add_sponsor);
        });


        button_accept=findViewById(R.id.accept);
        button_accept.setOnClickListener(view -> {
            new AlertDialog.Builder(DetelesResvie_And_SendDcument.this)
                    .setTitle("")
                    .setMessage("هل تريد استكمال")
                    .setCancelable(false)
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirmRecved(1);
                        }
                    }).setNegativeButton("لا",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            }).show();
        });


        button_reject=findViewById(R.id.reject);
        button_reject.setOnClickListener(view -> {
            new AlertDialog.Builder(DetelesResvie_And_SendDcument.this)
                    .setTitle("")
                    .setMessage("هل تريد استكمال")
                    .setCancelable(false)
                    .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirmRecved(2);
                        }
                    }).setNegativeButton("لا",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            }).show();
        });


        linearsend=findViewById(R.id.send);
        linearResvied=findViewById(R.id.resved);

        if(type.equals("send")){
            linearsend.setVisibility(View.VISIBLE);
            linearResvied.setVisibility(View.GONE);
        }else{
            linearsend.setVisibility(View.GONE);
            linearResvied.setVisibility(View.VISIBLE);
        }



        if(show!=null&& !show.equals("all")) {
            btn_shahed2.setVisibility(View.GONE);
            btn_kafel2.setVisibility(View.GONE);

            button_addpayments.setVisibility(View.GONE);
            button_accept.setVisibility(View.GONE);
            button_reject.setVisibility(View.GONE);

            showShod.setVisibility(View.GONE);
            showKafel.setVisibility(View.GONE);
        }

        try{
            if(show.equals("all")){
                showShod.setVisibility(View.VISIBLE);
                showKafel.setVisibility(View.VISIBLE);
            }

            Log.e("show ??? ",show);
        }catch (Exception ignored){}

        if(status!=1) {
            Log.e("::::::::::::<<<<",status+"");
            btn_shahed2.setVisibility(View.GONE);
            btn_kafel2.setVisibility(View.GONE);
        }
        if(status!=0) {
            Log.e("::::::::::::<<<<",status+"");
            button_accept.setVisibility(View.GONE);
            button_reject.setVisibility(View.GONE);
        }

        Log.e("::::::::::::",status+"");
    }


    private void getjson() {

        if(status!=1) {
            Log.e("::::::::::::<<<<",status+"");
            btn_shahed2.setVisibility(View.GONE);
            btn_kafel2.setVisibility(View.GONE);
        }

        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.show_document+documentType+"/"+documentId,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);

                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DataShowSingelDocument dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DataShowSingelDocument.class);


                                        if (dataDelevery.getSuccess().equals(true)) {
                                            id_saned.setText(dataDelevery.getData().getDocument().getCode().toString());
                                            national_id_sender.setText(dataDelevery.getData().getDocument().getReceiver().getNationalId());
//                                            address_resved.setText(dataDelevery.getData().getDocument().getAddress());
                                            amount.setText(dataDelevery.getData().getDocument().getTotal());
                                            prudect.setText(dataDelevery.getData().getDocument().getProducts());
                                            currency.setText(dataDelevery.getData().getDocument().getCurrency().getName());
                                            spinner_paytype.setText(dataDelevery.getData().getDocument().getPayType()==0?"نقدي":"اجل");
                                            description.setText(dataDelevery.getData().getDocument().getDescription());

                                            if(show==null){
                                                button_addpayments.setVisibility(dataDelevery.getData().getDocument().getPayType()==0?View.GONE:View.VISIBLE);
                                            }


                                            if(type.equals("send")){
                                                linearsend.setVisibility(View.VISIBLE);
                                                linearResvied.setVisibility(View.GONE);
                                            }else{
                                                linearsend.setVisibility(View.GONE);
                                                linearResvied.setVisibility(View.VISIBLE);

                                             if ((dataDelevery.getData().getDocument().getConfirmed()!=0)){
                                                 linearResvied.setVisibility(View.GONE);
                                             }else{

                                                 id_states.setVisibility(View.GONE);
                                             }
                                            }

                                            if(dataDelevery.getData().getDocument().getStatus()==2){
                                                btn_shahed2.setVisibility(View.GONE);
                                                btn_kafel2.setVisibility(View.GONE);
                                            }else{
                                                btn_shahed2.setVisibility(View.VISIBLE);
                                                btn_kafel2.setVisibility(View.VISIBLE);
                                            }

                                            Log.e("getConfirmed()",dataDelevery.getData().getDocument().getConfirmed()+"");
                                            Log.e("getStatus()",dataDelevery.getData().getDocument().getStatus()+"");

                                            statusOrder=dataDelevery.getData().getDocument().getStatus();
                                            if(dataDelevery.getData().getDocument().getConfirmed()==2){
                                                id_states.setText("تم رفض الطلب ");
                                            }else{
                                                id_states.setText(
                                                        dataDelevery.getData().getDocument().getConfirmed()==0?"الطلب في الانتظار":
                                                                dataDelevery.getData().getDocument().getConfirmed()==1&&dataDelevery.getData().getDocument().getStatus()==0?"الطلب مفتوح":

                                                                dataDelevery.getData().getDocument().getConfirmed()==1&&
                                                                        dataDelevery.getData().getDocument().getStatus()==1?"الطلب مغلق":
                                                                        dataDelevery.getData().getDocument().getConfirmed()==2?"تم رفض الطلب":
                                                                                dataDelevery.getData().getDocument().getConfirmed()==3?"تم رفض الطلب ":""

                                                );
                                            }

                                        }



                                        if(status!=1) {
                                            Log.e("::::::::::::<<<<",status+"");
                                            btn_shahed2.setVisibility(View.GONE);
                                            btn_kafel2.setVisibility(View.GONE);
                                        }
                                        if(status!=0) {
                                            Log.e("::::::::::::<<<<",status+"");
                                            button_accept.setVisibility(View.GONE);
                                            button_reject.setVisibility(View.GONE);
                                        }

                                        try{
                                            if(show.equals("all")){
                                                showShod.setVisibility(View.VISIBLE);
                                                showKafel.setVisibility(View.VISIBLE);
                                            }

                                            Log.e("show ??? ",show);
                                        }catch (Exception ignored){}


                                        if(dataDelevery.getData().getDocument().getConfirmed()==1&&
                                                dataDelevery.getData().getDocument().getStatus()==0){
                                            statusOrder=0;
                                            btn_shahed2.setVisibility(View.VISIBLE);
                                            btn_kafel2.setVisibility(View.VISIBLE);
                                        }else{
                                            statusOrder=1;
                                            btn_shahed2.setVisibility(View.GONE);
                                            btn_kafel2.setVisibility(View.GONE);
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


    private void confirmRecved(int i){
        final ProgressDialog progressDialog=new ProgressDialog(DetelesResvie_And_SendDcument.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(DetelesResvie_And_SendDcument.this);
        Gson gson = new Gson();

        DataSendConfirmReceved contactsTop=new DataSendConfirmReceved();
        contactsTop.setDocumentId(documentId);
        contactsTop.setStatus(i);
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.confirm_document
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            Toast.makeText(DetelesResvie_And_SendDcument.this, "تم الارسال", Toast.LENGTH_LONG).show();

            getjson();


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


  void  sentToSahed(String name,String url){
      final Dialog dialog = new Dialog(DetelesResvie_And_SendDcument.this);
      //We have added a title in the custom layout. So let's disable the default title.
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setCancelable(true);
      //Mention the name of the layout of your custom dialog.
      dialog.setContentView(R.layout.dialog_send_sahed);

      //Initializing the views of the dialog.
      Button done = dialog.findViewById(R.id.done);;
      TextView txt_name = dialog.findViewById(R.id.txt_name);
      txt_name.setText(name);
      EditText edittext_value = dialog.findViewById(R.id.edittext_value);



      done.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (TextUtils.isEmpty(edittext_value.getText().toString())) {
                  edittext_value.setError("قم بكتابة قيمة الدفع");
                  return;
              }

              addReqestData(edittext_value.getText().toString(),url,dialog);

          }
      });

      dialog.show();

    }




    private void addReqestData(String name, String url, Dialog dialog){
        final ProgressDialog progressDialog=new ProgressDialog(DetelesResvie_And_SendDcument.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(DetelesResvie_And_SendDcument.this);
        Gson gson = new Gson();

        DataAddSponsorWitness contactsTop=new DataAddSponsorWitness();
        contactsTop.setDocumentId(documentId);
        contactsTop.setNationalId(Integer.parseInt(name));

        Log.e("Volley:Url ", url);
        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);


            Gson gsons = new Gson();
            DataResponseAll dataDelevery;
            dataDelevery = gsons.fromJson(response.toString(), DataResponseAll.class);
            if(dataDelevery.getSuccess().equals(true)){
                Toast.makeText(DetelesResvie_And_SendDcument.this, "تم اللاضافه بنجاح", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }else{
                Toast.makeText(DetelesResvie_And_SendDcument.this, "يرجي التاكد من ادخال البيانات", Toast.LENGTH_LONG).show();
            }




        }, error -> {

            progressDialog.dismiss();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(DetelesResvie_And_SendDcument.this, "Error Network Time Out", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                //                    finish();
                Toast.makeText(DetelesResvie_And_SendDcument.this, "AuthFailureError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Toast.makeText(DetelesResvie_And_SendDcument.this, "يرجي التاكد من ادخال البيانات", Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(DetelesResvie_And_SendDcument.this, "NetworkError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(DetelesResvie_And_SendDcument.this, "ParseError", Toast.LENGTH_LONG).show();

            }
        }) {
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