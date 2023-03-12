package com.otex.ekrar.Home.add_document;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.otex.ekrar.Home.Home;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataCurrency.DataCurrency;
import com.otex.ekrar.model.addDataDoc.AddDataDocFromBootomSheet;
import com.otex.ekrar.startApp.LoginActivity;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_Financial_InvoiceNew extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ImageButton imageButton;
    int documentType;
    TextView txtTitle,text_product;

    EditText sendto,totalMoney,product,description;


    Spinner spinner_paytype,currencySpinner,spinner1_doc_type;
    String[] pay_type = {"نقدي", "اجل"};
    String[] doc_type = {"سند تسليم عهده نقديه", "سند تسليم عهده عيني"};
    int[] doc_typeInt = {2, 1};


    Button btn_send, btn_payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__financial__invoice_new);

        definUi();
        actionUi();
//        listCurrencies();
        getjson();
    }

    private void definUi() {
        imageButton=findViewById(R.id.image_arrow);
        txtTitle = findViewById(R.id.txt_docu);
        documentType = getIntent().getIntExtra("DOCUMENT_TYPE", 1);




        // * EditText

        sendto=findViewById(R.id.edtext_sendto_bonds);
//        address=findViewById(R.id.edt_address_bonds);
        totalMoney=findViewById(R.id.edt_total_money_product_bonds);
        product=findViewById(R.id.edt_product);
        text_product=findViewById(R.id.txt_product);
        product.setVisibility((documentType == 1 ? View.VISIBLE:View.GONE));
        text_product.setVisibility((documentType == 1 ? View.VISIBLE:View.GONE));
        description=findViewById(R.id.text_description);


        // * sppiner

        currencySpinner = (Spinner) findViewById(R.id.spinner1_currency);

        spinner_paytype = (Spinner) findViewById(R.id.spinner_paytype);
        spinner_paytype.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, pay_type);
        spinner_paytype.setAdapter(aa);


        spinner1_doc_type = (Spinner) findViewById(R.id.spinner1_doc_type);
        spinner1_doc_type.setOnItemSelectedListener(this);
        ArrayAdapter aa2 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, doc_type);
        spinner1_doc_type.setAdapter(aa2);
        spinner1_doc_type.setVisibility(documentType==3?View.VISIBLE:View.GONE);



        /// ** ------------------------------------------------------------------------------------
        txtTitle.setText(documentType == 1 ? "سند قبض عيني " :documentType == 3 ? "سند تسليم عهده " : "سند قبض نقدي");

        // *  Button

        btn_send = (Button) findViewById(R.id.btn_send_to);
        btn_send.setOnClickListener(view -> {

            ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null &&
                    conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(SignUp.this, "true", Toast.LENGTH_SHORT).show();

                //sendto,,,
//                if (description.getText().toString().trim().isEmpty()) {
//                    description.setError("    ");
//                    description.setFocusableInTouchMode(true);
//                    description.requestFocus();
//                    description.setFocusableInTouchMode(true);
//                }  else
                    if (totalMoney.getText().toString().trim().isEmpty()) {
                    totalMoney.setError("   ");
                    totalMoney.setFocusableInTouchMode(true);
                    totalMoney.requestFocus();
                    totalMoney.setFocusableInTouchMode(true);
                } else if (idCurrency==0) {
                    Toast.makeText(this, "يجب اختيار العمله", Toast.LENGTH_SHORT).show();
                }
//                    else if (address.getText().toString().trim().isEmpty()) {
//                    address.setError("  ");
//                    address.setFocusableInTouchMode(true);
//                    address.requestFocus();
//                    address.setFocusableInTouchMode(true);
//                }
                else if (sendto.getText().toString().trim().isEmpty()) {
                    sendto.setError("   ");
                    sendto.setFocusableInTouchMode(true);
                    sendto.requestFocus();
                    sendto.setFocusableInTouchMode(true);
                } else {
                    addReqestData();
//                    Log.e(">>???", String.valueOf(CategorysId[(int) currencySpinner.getSelectedItemId()]));
                }

            } else {
                Toast.makeText(Add_Financial_InvoiceNew.this, "Check Your Network", Toast.LENGTH_SHORT).show();
            }
        });

        btn_payments = (Button) findViewById(R.id.button_addpayments);

    }


    private void actionUi() {
        imageButton.setOnClickListener(view -> {
            finish();
        });

        btn_payments.setOnClickListener(view -> {
            Log.e("currencySpinner>>>>>>", String.valueOf(currencySpinner.getSelectedItemId()));
            Log.e("CategorysId>>>>>>", String.valueOf(CategorysId[(int) currencySpinner.getSelectedItemId()]));
            if (Validation()) {
                    Intent intent = new Intent(Add_Financial_InvoiceNew.this, CashReceiptsActivity.class);

                    intent.putExtra("description", description.getText().toString());
                    intent.putExtra("total", totalMoney.getText().toString());
                    intent.putExtra("nationality", sendto.getText().toString());
                    intent.putExtra("currency_id", "" + CategorysId[(int) currencySpinner.getSelectedItemId()]);
//                    intent.putExtra("address", address.getText().toString());
                    intent.putExtra("products", product.getText().toString());
                    intent.putExtra("DocType", doc_typeInt[(int) spinner1_doc_type.getSelectedItemId()]);
                    intent.putExtra("DOCUMENT_TYPE", documentType);
                    startActivity(intent);
//                    finish();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinner_paytype.getSelectedItem().equals("اجل")) {
            Log.e("spinner_paytype", String.valueOf(spinner_paytype.getSelectedItemId()));
            btn_payments.setVisibility(View.VISIBLE);
            btn_send.setVisibility(View.GONE);
        } else
            btn_send.setVisibility(View.VISIBLE);

        if (spinner_paytype.getSelectedItem().equals("نقدي")) {
            Log.e("spinner_paytype", String.valueOf(spinner_paytype.getSelectedItemId()));
            btn_send.setVisibility(View.VISIBLE);
            btn_payments.setVisibility(View.GONE);
        } else
            btn_send.setVisibility(View.GONE);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public boolean Validation()
    {
        if (TextUtils.isEmpty(sendto.getText().toString().trim()))
        {
            sendto.setError("يجب عليك كتابة رقم الهوية او الإقامة للشخص المرسل الية");
            return false;
        }
//        if (TextUtils.isEmpty(address.getText().toString().trim()))
//        {
//            address.setError("يجب عليك كتابة عنوان الشخص المرسل الية");
//
//            return false;
//        }
        if (TextUtils.isEmpty(totalMoney.getText().toString().trim()))
        {
            totalMoney.setError("يجب عليك كتابة القيمة الكلية المرسلة");

            return false;
        }

        return true;
    }



    int[] CategorysId;
    int idCurrency=0;
    private void getjson() {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        Urls.currency,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);

                                if (response.length() > 0) {
                                    try {
                                        Gson gson = new Gson();
                                        DataCurrency dataDelevery;
                                        dataDelevery = gson.fromJson(response.toString(), DataCurrency.class);


                                        if (dataDelevery.getSuccess().equals(true)) {

                                            String[] Categorys = new String[dataDelevery.getData().getCurrency().size()];
                                            CategorysId = new int[dataDelevery.getData().getCurrency().size()];

                                            for(int i=0; i< dataDelevery.getData().getCurrency().size(); i++){
                                                idCurrency=dataDelevery.getData().getCurrency().get(0).getId();
                                                Categorys[i]= dataDelevery.getData().getCurrency().get(i).getName();
                                                CategorysId[i]= dataDelevery.getData().getCurrency().get(i).getId();

                                                ArrayAdapter<String> spinnerArrayAdapter =
                                                        new ArrayAdapter<String>(Add_Financial_InvoiceNew.this,
                                                                android.R.layout.simple_spinner_item, Categorys);

                                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                currencySpinner.setAdapter(spinnerArrayAdapter);
                                            }

                                            currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                                            {
                                                @Override
                                                public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                                                    // TODO Auto-generated method stub
//                                                    idCurrency= Integer.parseInt(Categorys[position]);
//                                                    Toast.makeText(getBaseContext(), idCurrency, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> arg0) {
                                                    // TODO Auto-generated method stub
                                                }
                                            });

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


    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(Add_Financial_InvoiceNew.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(Add_Financial_InvoiceNew.this);
        Gson gson = new Gson();

        AddDataDocFromBootomSheet contactsTop=new AddDataDocFromBootomSheet();
        contactsTop.setDocumentType(documentType);
        contactsTop.setCurrencyId(CategorysId[(int) currencySpinner.getSelectedItemId()]);
        contactsTop.setNationalId(sendto.getText().toString().trim());
//        contactsTop.setAddress(address.getText().toString().trim());
        contactsTop.setTotal(Integer.valueOf(totalMoney.getText().toString().trim()));
        contactsTop.setDescription(description.getText().toString().trim());
        contactsTop.setPayType(0);
        if(documentType==1){
            contactsTop.setProducts(product.getText().toString().trim());
        }else if(documentType==3){
            Log.e("documentTypess ", "?????????????");
            contactsTop.setDocType(doc_typeInt[(int) spinner1_doc_type.getSelectedItemId()]);
        }

        Log.e("Volley:Response ", gson.toJson(contactsTop));
        Log.e("documentType ", String.valueOf(documentType));


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
                Toast.makeText(getApplicationContext(), "تم الارسال بنجاح ", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Add_Financial_InvoiceNew.this, Home.class);
                intent.putExtra("token",dataDelevery.getData().getToken());
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