package com.otex.ekrar.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.otex.ekrar.Home.activtys.DetelesResvie_And_SendDcument;
import com.otex.ekrar.R;
import com.otex.ekrar.model.DataAllNotificationFinal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.DataSingelPayMentDetilealFinal;
import com.otex.ekrar.model.DataSingelPayMentDetileal.PaidPayment;

import java.util.ArrayList;
import java.util.List;

public class AllNotificationAdapter extends RecyclerView.Adapter<AllNotificationAdapter.ViewHolder> {

//    private ArrayList<DataAllNotificationFinal> amounts;

    List<DataAllNotificationFinal> amounts;
    Context context;
    Activity activity;


    public AllNotificationAdapter(List<DataAllNotificationFinal> amounts,Context context,Activity activity) {
        this.amounts = amounts;
        this.context = context;
        this.activity = activity;
    }

    public void setData(List<DataAllNotificationFinal> invoices){
        this.amounts = invoices;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AllNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_list_all_notification, parent, false);
        AllNotificationAdapter.ViewHolder viewHolder = new AllNotificationAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllNotificationAdapter.ViewHolder holder, int position) {
        final DataAllNotificationFinal myListData = amounts.get(position);
        holder.txt_name_fin.setText(myListData.getMsg());
        holder.txt_date_fin.setText(myListData.getCreatedAt().substring(0,10));


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DetelesResvie_And_SendDcument.class);
                intent.putExtra("DOCUMENT_TYPE",(myListData.getDocumentType()));
                intent.putExtra("DOCUMENT_ID",myListData.getDocumentId());
                intent.putExtra("type","type");
//                intent.putExtra("show","0");
                activity.startActivity(intent);



            }
        });

    }
    @Override
    public int getItemCount() {
        return amounts.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name_fin, txt_date_fin;
        CardView card_view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.card_view=(CardView) itemView.findViewById(R.id.card_view);
            this.txt_name_fin=(TextView)itemView.findViewById(R.id.txt_name_fin);
            this.txt_date_fin=itemView.findViewById(R.id.txt_date_fin);
        }

    }
}
