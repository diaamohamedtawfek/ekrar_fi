package com.otex.ekrar.Adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.otex.ekrar.R;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPaymentDoc extends Dialog {
    EditText edittext_value;
    TextView edittext_date;
    Context context;
    final Calendar c = Calendar.getInstance();
    final int year = c.get(Calendar.YEAR);
    final int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);
    int N = 10;
    AlertDialog loadingDialog;
    Button done;
    String date;
    DataChenges dataChenges;

    int id;


    @SuppressLint("NewApi")
    public AddPaymentDoc(Context context, int id, DataChenges dataChenges) {
        super(context);
        this.context = context;
        this.id = id;
        this.dataChenges = dataChenges;
//        invoicesService = ApiUtils.getInvoicesService();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_password);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.loading_dialog_layout);
        loadingDialog = builder.create();
        edittext_date = findViewById(R.id.edittext_date);
        edittext_value = findViewById(R.id.edittext_value);
        done = findViewById(R.id.done);
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
                setPay();
            }
        });
        edittext_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edittext_date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        date = year + "-" + month + "-" + dayOfMonth;

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    //
    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(layoutParams);

    }


    public void setPay() {
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

}