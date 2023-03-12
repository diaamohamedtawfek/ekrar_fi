package com.otex.ekrar.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.otex.ekrar.Home.activtys.DetelesResvie_And_SendDcument;
import com.otex.ekrar.Home.activtys.MModel.SahedAnd_Kafel_SingelId.Sahed_Kafile_Final;
import com.otex.ekrar.R;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Sahed_Kafel_SingelIdRecyxal extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;

    List<Sahed_Kafile_Final> List_Item;
    private Context context;
    String deirection;
    Activity activity;
    String type;



    public Sahed_Kafel_SingelIdRecyxal(List<Sahed_Kafile_Final> list_Item, Activity activity, Context context, String type) {
        List_Item = list_Item;
        this.context = context;
        this.deirection=deirection;
        this.activity=activity;
        this.type=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_sahed_kafel_ducument, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    public void filterList(ArrayList<Sahed_Kafile_Final> filteredList) {
        List_Item = filteredList;
        //recyclerView_dAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;


        try {
            menuItemHolder.txt_date_fin.setText("" + List_Item.get(position).getNId().toString());
        }catch (Exception e){}



        try {

            menuItemHolder.txt_name_fin.setText( List_Item.get(position).getName().toString());
        }catch (Exception e){}

        previousPosition = position;


    }
    @Override
    public int getItemCount() {
        return (null != List_Item ? List_Item.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView txt_name_fin,txt_date_fin;



        public MenuItemViewHolder(View view) {
            super(view);

            txt_name_fin = (TextView) view.findViewById(R.id.txt_name_fin);
            txt_date_fin = (TextView) view.findViewById(R.id.txt_date_fin);


        }
    }




}
