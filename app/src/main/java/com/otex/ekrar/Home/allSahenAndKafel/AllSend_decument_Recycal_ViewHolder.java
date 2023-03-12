package com.otex.ekrar.Home.allSahenAndKafel;

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
import com.otex.ekrar.R;
import com.otex.ekrar.model.dataAllSendDucument.DataAllSendDucumentFinal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllSend_decument_Recycal_ViewHolder extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;

    List<DataFinalResponseAllsahed> List_Item;
    private Context context;
    String deirection;
    Activity activity;
    String type;



    public AllSend_decument_Recycal_ViewHolder(List<DataFinalResponseAllsahed> list_Item, Activity activity, Context context, String type) {
        List_Item = list_Item;
        this.context = context;
        this.deirection=deirection;
        this.activity=activity;
        this.type=type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View menu1 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_list_send_ducument, viewGroup, false);
        return new MenuItemViewHolder(menu1);
    }

    public void filterList(ArrayList<DataFinalResponseAllsahed> filteredList) {
        List_Item = filteredList;
        //recyclerView_dAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;


        try {
            menuItemHolder.txt_date_fin.setText("" + List_Item.get(position).getCreated_at().toString().substring(0, 10));
        }catch (Exception e){}

        try {
            menuItemHolder.txt_user_id.setText(
                    ""
//                    List_Item.get(position).getReceiver().getNationalId()
            );
        }catch (Exception e){}


        try {
            menuItemHolder.txt_name_fin.setText(
                    List_Item.get(position).getDocument_type()==1?" سند قبض عيني ":
                            List_Item.get(position).getDocument_type()==2?" سند قبض نقضي":
                                    List_Item.get(position).getDocument_type()==3?"عهده":
                                            List_Item.get(position).getDocument_type()==4?"اقرار نصي":""
            );



            menuItemHolder.txt_name_fin.setText(menuItemHolder.txt_name_fin.getText()+" من " +
                    List_Item.get(position).getSender().getName().toString()+" الي "+ List_Item.get(position).getReceiver().getName()

            );
        }catch (Exception e){}


//        Drawable color = new ColorDrawable(holder.txt_type.getContext().getResources().getColor(R.color.yellow));;

        Drawable colorss = new ColorDrawable();
        try{
        if(List_Item.get(position).getConfirmed() == 2) {
            menuItemHolder.statusImage.setImageResource(R.color.fabcolorxx);
        }
        else if(List_Item.get(position).getConfirmed() == 1) {
            if (List_Item.get(position).getStatus()==0)
            menuItemHolder.statusImage.setImageResource(R.color.colorPrimary);
            else if (List_Item.get(position).getStatus()==1)
            menuItemHolder.statusImage.setImageResource(R.color.green);
        }else{
            menuItemHolder.statusImage.setImageResource(R.color.yellow);
        }
        }catch (Exception e){}




        menuItemHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DetelesResvie_And_SendDcument.class);
                intent.putExtra("DOCUMENT_TYPE",List_Item.get(position).getDocument_type());
                intent.putExtra("DOCUMENT_ID",List_Item.get(position).getId());
                intent.putExtra("type","type");
                intent.putExtra("show","all");
                activity.startActivity(intent);



            }
        });

        previousPosition = position;


    }
    @Override
    public int getItemCount() {
        return (null != List_Item ? List_Item.size() : 0);
    }

    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        //        LinearLayout cardView;
//        ImageView imageView;
        TextView txt_type,txt_name_fin,txt_user_id,txt_date_fin;
        CircleImageView statusImage;
        CardView cardView;


        public MenuItemViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);

            statusImage =  view.findViewById(R.id.status);
            txt_name_fin = (TextView) view.findViewById(R.id.txt_name_fin);
            txt_user_id = (TextView) view.findViewById(R.id.txt_user_id);
//            txt_code_num = (TextView) view.findViewById(R.id.txt_code_num);
            txt_date_fin = (TextView) view.findViewById(R.id.txt_date_fin);


        }
    }




}
