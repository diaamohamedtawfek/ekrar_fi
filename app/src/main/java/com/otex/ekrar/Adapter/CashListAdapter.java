package com.otex.ekrar.Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otex.ekrar.R;
import com.otex.ekrar.model.Rowitem_cash;

import java.util.ArrayList;
import java.util.Calendar;

public class CashListAdapter extends RecyclerView.Adapter<CashListAdapter.ViewHolder> {
    private ArrayList<Rowitem_cash> listdata_cash;
    Context context;

    public CashListAdapter(ArrayList<Rowitem_cash> listdata_cash) {
        this.listdata_cash = listdata_cash;
    }

    public void setData(ArrayList<Rowitem_cash> invoices) {
        this.listdata_cash = invoices;
        notifyDataSetChanged();
    }

    public void addPayment(Rowitem_cash payment) {
        this.listdata_cash.add(payment);
        notifyDataSetChanged();
    }

    public ArrayList<Rowitem_cash> getPaymentList() {
        return this.listdata_cash;
    }

    @NonNull
    @Override
    public CashListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_cash_receipts, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CashListAdapter.ViewHolder holder, int position) {
        final Rowitem_cash myListData = listdata_cash.get(position);
        holder.paymentEditTextListener.updatePosition(position);
        holder.edt_date.setText(myListData.getDate() == null ? "" : myListData.getDate());
        holder.edt_payment.setText(listdata_cash.get(position).getPayment());
//        holder.edt_date.setText(listdata_cash.get(position).getDate());

    }

    @Override
    public int getItemCount() {

        return listdata_cash.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText edt_payment;
        TextView txt_payment, txt_date, edt_date;
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH) ;
        final int day = c.get(Calendar.DAY_OF_MONTH);
        private DatePickerDialog datePicker;
        public EditTextListener paymentEditTextListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.edt_date = itemView.findViewById(R.id.edittext_date);
            this.edt_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePicker = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                    edt_date.setText(year + "/" + month + "/" + dayOfMonth);
                            listdata_cash.get(ViewHolder.this.getAdapterPosition()).setDate(year + "-" + (month+1) + "-" + dayOfMonth);
                            notifyItemChanged(ViewHolder.this.getAdapterPosition());

                        }
                    }, year, month, day);
//                            datePicker.setTitle("Choose Date");
                    datePicker.show();
                }
            });
            this.edt_payment = (EditText) itemView.findViewById(R.id.edittext_value);
            this.paymentEditTextListener = new EditTextListener();
            this.edt_payment.addTextChangedListener(paymentEditTextListener);

//            this.txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            this.txt_payment = (TextView) itemView.findViewById(R.id.txt_value);

        }
    }

    private class EditTextListener implements TextWatcher {
        private int position;

//        public EditTextListener(int updatedField) {
//            this.updatedField = updatedField;
//        }

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            listdata_cash.get(position).setPayment(editable.toString());
            // no op
        }
    }
}



