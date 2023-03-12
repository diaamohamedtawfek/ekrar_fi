package com.otex.ekrar.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.cardview.widget.CardView;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.otex.ekrar.Home.Home;
import com.otex.ekrar.Home.activtys.DetelesResvie_And_SendDcument;
import com.otex.ekrar.MySingleton;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.Urls;
import com.otex.ekrar.model.DataSendNewPay;
import com.otex.ekrar.model.DataSingelPayMentDetileal.DataSingelPayMentDetileal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.DataSingelPayMentDetilealFinal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.PaidPayment;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;
import com.otex.ekrar.startApp.LoginActivity;
import com.otex.ekrar.startApp.model.DataSendLogin;
import com.otex.ekrar.startApp.model.dataResponceLogin.DataResponceLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewInvoiceNewPaymentAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataChenges {

    private int previousPosition = 0;
    private int statusOrder;
    String date;
    final Calendar c = Calendar.getInstance();
    final int year = c.get(Calendar.YEAR);
    final int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);



    List<DataSingelPayMentDetilealFinal> List_Item;
    private Context context;
    String deirection;
    Activity activity;
    String type;
    DataChenges dataChenges;



    public ViewInvoiceNewPaymentAdapter(List<DataSingelPayMentDetilealFinal> list_Item,
                                        Activity activity, Context context,String type,DataChenges dataChenges,int statusOrder) {
        List_Item = list_Item;
        this.context = context;
        this.deirection=deirection;
        this.activity=activity;
        this.type=type;
        this.dataChenges=dataChenges;
        this.statusOrder=statusOrder;
    }

    @Override
    public void notifyDataChanged() {
        dataChenges.notifyDataChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.view_payment_list_item, viewGroup, false);
        return new ViewInvoiceNewPaymentAdapter.MenuItemViewHolder(menu1);
    }

    public void filterList(ArrayList<DataSingelPayMentDetilealFinal> filteredList) {
        List_Item = filteredList;
        //recyclerView_dAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;


        menuItemHolder.amountAdapter.setData((ArrayList<PaidPayment>) List_Item.get(position).getPaidPayments());

        try {
            menuItemHolder.dateToggleButton.setText(" :" + List_Item.get(position).getDate());
            menuItemHolder.dateToggleButton.setTextOn("" + List_Item.get(position).getDate());
            menuItemHolder.dateToggleButton.setTextOff("" + List_Item.get(position).getDate());
        }catch (Exception ignored){}

        try {
            menuItemHolder.totalMoney_text_view.setText("القيمة " + List_Item.get(position).getPayment().toString());
        }catch (Exception ignored){}

        try {
            menuItemHolder.amount_text_view.setText("القيمة المدفوعة  " + List_Item.get(position).getPaid());
        }catch (Exception ignored){}
        try {
            menuItemHolder.amount.setText("الباقي  " + List_Item.get(position).getRest());
        }catch (Exception ignored){}



        if(!type.equals("send")){
            menuItemHolder.btn_confirm.setVisibility(View.GONE);
            menuItemHolder.btn_pay.setVisibility(View.GONE);
        }
        Log.e("...............", String.valueOf(statusOrder));
        if(statusOrder ==0 ){

            if (List_Item.get(position).getStatus() == 1) {
                menuItemHolder.btn_confirm.setVisibility(View.GONE);
                menuItemHolder.btn_pay.setVisibility(View.GONE);
                menuItemHolder.done.setVisibility(View.VISIBLE);
            } else if (List_Item.get(position).getStatus() == 0) {
                menuItemHolder.btn_confirm.setVisibility(View.VISIBLE);
                menuItemHolder.btn_pay.setVisibility(View.VISIBLE);
                menuItemHolder.done.setVisibility(View.GONE);
            }

        }else{
            menuItemHolder.btn_confirm.setVisibility(View.GONE);
            menuItemHolder.btn_pay.setVisibility(View.GONE);
            menuItemHolder.done.setVisibility(View.GONE);
        }



        if(type.equals("res")){
            menuItemHolder.btn_pay.setVisibility(View.GONE);
            menuItemHolder.btn_confirm.setVisibility(View.GONE);
        }

        menuItemHolder.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity)
                        .setTitle("")
                        .setMessage("هل تريد استكمال")
                        .setCancelable(false)
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirm_payment_document(List_Item.get(position).getId(),position);
                            }
                        }).setNegativeButton("لا",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });


        menuItemHolder.btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(activity);
                //We have added a title in the custom layout. So let's disable the default title.
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                //Mention the name of the layout of your custom dialog.
                dialog.setContentView(R.layout.dialog_change_password);

                //Initializing the views of the dialog.
                Button done = dialog.findViewById(R.id.done);;
                TextView edittext_date = dialog.findViewById(R.id.edittext_date);
                EditText edittext_value = dialog.findViewById(R.id.edittext_value);



                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(edittext_value.getText().toString())) {
                            edittext_value.setError("قم بكتابة قيمة الدفع");
                            return;
                        }
                        if (TextUtils.isEmpty(edittext_date.getText().toString())) {
                            edittext_date.setError("قم بكتابة تاريخ الدفع");
                            return;
                        }

                        setPay(List_Item.get(position).getId(),edittext_value.getText().toString().trim(),
                                edittext_date.getText().toString().trim());
                    }
                });

                edittext_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edittext_date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                date = year + "-" + month + "-" + dayOfMonth;

                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    }
                });

                dialog.show();
            }
        });

//
//        try {
//            menuItemHolder.txt_user_id.setText(
//                    List_Item.get(position).getReceiver().getNationalId()
//            );
//        }catch (Exception e){}





//        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(context, DetelesResvie_And_SendDcument.class);
//                intent.putExtra("DOCUMENT_TYPE",List_Item.get(position).getDocumentType());
//                intent.putExtra("DOCUMENT_ID",List_Item.get(position).getId());
//                intent.putExtra("type",type);
//                activity.startActivity(intent);
//
//
//
//            }
//        });

        previousPosition = position;


    }
    @Override
    public int getItemCount() {
        return (null != List_Item ? List_Item.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        //        LinearLayout cardView;
//        ImageView imageView;
        TextView totalMoney_text_view,amount_text_view,amount,done,txt_date_fin;
        ToggleButton dateToggleButton;

        Button btn_confirm, btn_pay;

        RecyclerView amounts_recycler_view;
        AmountAdapter amountAdapter;

        public MenuItemViewHolder(View view) {
            super(view);
//            cardView = (CardView) view.findViewById(R.id.card_view);
//
//            statusImage =  view.findViewById(R.id.status);
//            txt_name_fin = (TextView) view.findViewById(R.id.txt_name_fin);
            done = (TextView) view.findViewById(R.id.done);
            amount = (TextView) view.findViewById(R.id.amount);
            amount_text_view = (TextView) view.findViewById(R.id.amount_text_view);
            totalMoney_text_view = (TextView) view.findViewById(R.id.totalMoney_text_view);
            btn_confirm = view.findViewById(R.id.btn_confirm);
            btn_pay = view.findViewById(R.id.btn_pay);
            btn_pay.setVisibility(type.equals("send")?View.VISIBLE:View.GONE);
            btn_confirm.setVisibility(type.equals("send")?View.VISIBLE:View.GONE);

            dateToggleButton =  view.findViewById(R.id.dateToggleButton);

            this.amounts_recycler_view = itemView.findViewById(R.id.amounts_recycler_view);
            this.amounts_recycler_view.setLayoutManager(new LinearLayoutManager(dateToggleButton.getContext()));

            this.amountAdapter = new AmountAdapter(new ArrayList<>());
            this.amounts_recycler_view.setAdapter(this.amountAdapter);


        }
    }

     void confirm_payment_document( int documentId,int i) {
        Log.e("ofers json>>>>>>>>   ", Urls.confirm_payment_document+documentId);
        try {
            ConnectivityManager conMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {
                //Toast.makeText(context, "you don't have update "+REGISTER_URL, Toast.LENGTH_SHORT).show();
                final StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Urls.confirm_payment_document+documentId,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.e("ofers json>>>>>>>>   ",response);
                                List_Item.get(i).setStatus(1);
                                notifyDataSetChanged();
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //progressDialog.dismiss();
                                try {


                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        Toast.makeText(activity, "Error Network Time Out", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof AuthFailureError) {
//                                        SharedPrefManager.getInstance(activity).logout();
                                        //startActivity(new Intent(getApplicationContext(),Log_In.class));
                                        Toast.makeText(activity, "AuthFailureError", Toast.LENGTH_LONG).show();
                                    } else if (error instanceof ServerError) {
                                        Toast.makeText(activity, "ServerError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof NetworkError) {
                                        Toast.makeText(activity, "NetworkError", Toast.LENGTH_LONG).show();
                                        //TODO
                                    } else if (error instanceof ParseError) {
                                        Toast.makeText(activity, "ParseError", Toast.LENGTH_LONG).show();
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
                                SharedPrefManager.getInstance(activity).gettoken());
                        return headers;
                    }
                };;
                MySingleton.getInstance(activity).addToRequestQueue(stringRequest);
            } else {
            }
        }catch (Exception r){
        }


    }



    public void setPay(int documentId,String valu,String date) {

//        DataSendNewPay.java/

        addReqestData( documentId, valu, date);


//        Log.e("cccccccccccc", String.valueOf(id));
//        loadingDialog.show();
//        UserSharedPreferencesManager userSharedPreferencesManager = UserSharedPreferencesManager.getInstance(context.getApplicationContext());
//        String token = userSharedPreferencesManager.getToken();
//        Call call = invoicesService.add_paid_document("Bearer " + token,
//                id, edittext_date.getText().toString(),
//                edittext_value.getText().toString());
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                if (response.isSuccessful()) {
//                    ResObj<LoginData> resObj = (ResObj<LoginData>) response.body();
//                    if (resObj.getStatus().equals("success")) {
//                        dataChenges.notifyDataChanged();
//                        Toast.makeText(context, "تم الاستلام", Toast.LENGTH_LONG).show();
//                        loadingDialog.dismiss();
//                        dismiss();
//                    }
//                } else
//                    Toast.makeText(context.getApplicationContext(), "تأكد من تمن الدفعات", Toast.LENGTH_SHORT).show();
//                loadingDialog.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                loadingDialog.dismiss();
//                Toast.makeText(context, "error please try again!", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void addReqestData(int documentId,String valu,String date){
        final ProgressDialog progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Waite...");
        progressDialog.show();

        final RequestQueue requestQueue = Volley.newRequestQueue(activity);
        Gson gson = new Gson();

        DataSendNewPay contactsTop=new DataSendNewPay();
        contactsTop.setDate(date);
        contactsTop.setPayment(Integer.parseInt(valu));

        Log.e("Volley:Response ", gson.toJson(contactsTop));


        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(gson.toJson(contactsTop));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.add_paid_document+documentId
                , jsonObject, response -> {
            progressDialog.dismiss();
            Log.e("response_booking",""+response);

            JSONObject sys = response;//// result

            dataChenges.notifyDataChanged();



        }, error -> {

            progressDialog.dismiss();
            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                Toast.makeText(activity, "Error Network Time Out", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                //                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                //                    finish();
                Toast.makeText(activity, "AuthFailureError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ServerError) {
                Log.e("ServerError",error.toString());
                Toast.makeText(activity, "حدث خطأ في ارسال الطلب", Toast.LENGTH_LONG).show();
//                Toast.makeText(activity, "القيمه المدخله اكبر من القيمه المطلوبه", Toast.LENGTH_LONG).show();
            } else if (error instanceof NetworkError) {
                Toast.makeText(activity, "NetworkError", Toast.LENGTH_LONG).show();

            } else if (error instanceof ParseError) {
                Toast.makeText(activity, "ParseError", Toast.LENGTH_LONG).show();

            }
        });
//        {
//            /** Passing some request headers* */
//            @Override
//            public Map getHeaders() throws AuthFailureError {
//                HashMap headers = new HashMap();
//                headers.put("Authorization",
//                        SharedPrefManager_Login.getInstance(getApplicationContext()).getaccessType()+" "+
//                                SharedPrefManager_Login.getInstance(getApplicationContext()).getjwt());
//                return headers;
//            }
//        };;
        requestQueue.add(jsonObjectRequest);

    }

}
