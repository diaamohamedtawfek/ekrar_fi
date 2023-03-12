package com.otex.ekrar.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otex.ekrar.R;
import com.otex.ekrar.model.DataSingelPayMentDetileal.PaidPayment;

import java.util.ArrayList;

public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.ViewHolder> {

    private ArrayList<PaidPayment> amounts;

    public AmountAdapter(ArrayList<PaidPayment> amounts) {
        this.amounts = amounts;
    }

    public void setData(ArrayList<PaidPayment> invoices){
        this.amounts = invoices;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AmountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.amount_list_item, parent, false);
        AmountAdapter.ViewHolder viewHolder = new AmountAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AmountAdapter.ViewHolder holder, int position) {
        final PaidPayment myListData = amounts.get(position);
        holder.txt_amount.setText(holder.txt_amount.getContext().getString(R.string.paymentName, amounts.get(position).getPayment()));
        holder.txt_date.setText(holder.txt_amount.getContext().getString(R.string.payment_number_text, amounts.get(position).getDate()));
//        holder.txt_date.setText(amounts.get(position).getDate());

    }
    @Override
    public int getItemCount() {
        return amounts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_amount, txt_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txt_amount=(TextView)itemView.findViewById(R.id.txt_amount);
            this.txt_date=itemView.findViewById(R.id.text_date);
        }

    }
}
