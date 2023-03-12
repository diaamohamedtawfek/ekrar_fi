package com.otex.ekrar.Home.add_document;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.otex.ekrar.Adapter.CashListAdapter;
import com.otex.ekrar.Home.Home;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.Rowitem_cash;
import com.otex.ekrar.model.addDataDoc.AddDataDocFromBootomSheet;
import com.otex.ekrar.model.addDataDoc.Payment;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashReceiptsActivity extends AppCompatActivity {


    ImageView image_arrow;
    EditText editText_value;
    TextView editText_date, txtTitle;
    Button btn_add_payment, btn_send;
    LinearLayout container;
    RecyclerView recyclerView;
    CashListAdapter adapter;
    AlertDialog loadingDialog;
    int documentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_receipts);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.loading_dialog_layout);
        loadingDialog = builder.create();

        editText_value = (EditText) findViewById(R.id.edittext_value);
        btn_add_payment = (Button) findViewById(R.id.btn_payments);
        txtTitle = findViewById(R.id.txt_docu_bond);
        txtTitle.setText(documentType == 1 ? "سند قبض عيني " :documentType == 3 ? "سند تسليم عهده " : "سند قبض نقدي");


        documentType = getIntent().getIntExtra("DOCUMENT_TYPE", 1);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation())
                    addFinicailaInvoiceDoc();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.cash_recycler);
        adapter = new CashListAdapter(new ArrayList<Rowitem_cash>());
        adapter.addPayment(new Rowitem_cash());
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        container = (LinearLayout) findViewById(R.id.container);

        editText_date = findViewById(R.id.edittext_date);

        btn_add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addPayment(new Rowitem_cash());
                if (adapter.getItemCount() > 11)
                    btn_add_payment.setVisibility(View.INVISIBLE);
                else
                    btn_add_payment.setVisibility(View.VISIBLE);
            }
        });
        image_arrow = (ImageView) findViewById(R.id.image_arrow);
        image_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }




    public  boolean Validation()
    {

        ArrayList<Rowitem_cash> products = adapter.getPaymentList();
        Rowitem_cash product = null;
        int x=0;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);

            if (product.getPayment() != null) {
                if (!product.getPayment().equals("")){
                    x= x+Integer.parseInt( product.getPayment());
                }

            }
        }
        if (x!=Integer.parseInt(getIntent().getStringExtra("total")))
        {
            Toast.makeText(this, "تأكد من ان الدفات تساوي القيمة الكليه وهي تساوي " +getIntent().getStringExtra("total"), Toast.LENGTH_SHORT).show();
            return false;
        }
        return  true;
    }


    private Map<String, String> getPaymentsAsMap() {
        HashMap<String, String> productsMap = new HashMap<>();
        ArrayList<Rowitem_cash> products = adapter.getPaymentList();
        Rowitem_cash product = null;
        int x=0;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);

            if (product.getPayment() != null) {
                String statusKey = "payments[" + i + "][payment]";
                productsMap.put(statusKey, product.getPayment());
                x= x+Integer.parseInt( product.getPayment());
            }
            if (product.getDate() != null) {
                String statusKey = "payments[" + i + "][date]";
                productsMap.put(statusKey, product.getDate());
            }



        }
        if (x!=Integer.parseInt(getIntent().getStringExtra("total")))
        {
            Toast.makeText(this, "تأكد من ان الدفات تساوي القيمة الكليه وهي تساوي " +getIntent().getStringExtra("total"), Toast.LENGTH_SHORT).show();
            return null;
        }

        return productsMap;
    }


    public void addFinicailaInvoiceDoc() {
        addReqestData();
    }

    private void addReqestData(){
        final ProgressDialog progressDialog=new ProgressDialog(CashReceiptsActivity.this);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(CashReceiptsActivity.this);
        Gson gson = new Gson();

        AddDataDocFromBootomSheet contactsTop=new AddDataDocFromBootomSheet();
        contactsTop.setDocumentType(documentType);
        contactsTop.setCurrencyId(Integer.valueOf(getIntent().getStringExtra("currency_id")));
        contactsTop.setNationalId(getIntent().getStringExtra("nationality"));
        contactsTop.setAddress(getIntent().getStringExtra("address"));
        contactsTop.setTotal(Integer.valueOf(getIntent().getStringExtra("total")));
        contactsTop.setDescription(getIntent().getStringExtra("description"));
        contactsTop.setPayType(1);

        Payment payment=new Payment();
//        ArrayList<Payment> paymentfinal = null;
        List<Payment> paymentfinal = new ArrayList<Payment>();

        ArrayList<Rowitem_cash> products = adapter.getPaymentList();
        Rowitem_cash product = null;
        int x=0;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);

            if (product.getPayment() != null) {
            if (!product.getPayment().equals("")) {
                String statusKey = "payments[" + i + "][payment]";
                x= x+Integer.parseInt( product.getPayment());
            }
            }
            if (product.getDate() != null) {
            if (!product.getDate().equals("")) {
                String statusKey = "payments[" + i + "][date]";
            }
            }

            if (products.get(i).getDate()!=null) {
            if (products.get(i).getPayment()!=null) {
            if (!products.get(i).getDate().equals("")) {
                payment=new Payment();
                payment.setDate(products.get(i).getDate());
                payment.setPayment(Integer.valueOf(products.get(i).getPayment()));
                paymentfinal.add(payment);
                payment=new Payment();
            }
            }
            }

        }

        contactsTop.setPayments(paymentfinal);



        if(documentType==1){
            contactsTop.setProducts(getIntent().getStringExtra("products"));
        }else if(documentType==3){
            Log.e("documentTypess ", "?????????????");
            contactsTop.setDocType(Integer.valueOf(getIntent().getStringExtra("DocType")));
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
                Intent intent = new Intent(CashReceiptsActivity.this, Home.class);
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