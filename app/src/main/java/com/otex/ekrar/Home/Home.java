package com.otex.ekrar.Home;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
//import android.support.design.widget.BottomSheetDialog;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.otex.ekrar.Home.add_document.Add_Financial_InvoiceNew;
import com.otex.ekrar.Home.add_document.EkrarNase;
import com.otex.ekrar.R;
import com.otex.ekrar.SharedPrefManager;
import com.otex.ekrar.model.addDataDoc.AddDataDocFromBootomSheet;

public class Home extends AppCompatActivity {



    SharedPrefManager sharedPrefManager ;
    Fragment newFragment;

    LinearLayout linearProfile,linearHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         defineUI();
         actionUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                showBottomSheetDialog();
            }
        });
    }


    private void defineUI() {
        sharedPrefManager = new SharedPrefManager(Home.this);

        newFragment = new HomeFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.frame_container, newFragment).commit();

        linearProfile=findViewById(R.id.linearProfile);
        linearHome=findViewById(R.id.linearHome);


    }


    private void actionUI() {
        linearProfile.setOnClickListener(view ->{
            newFragment = new ProfileFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, newFragment).commit();

        });
        linearHome.setOnClickListener(view ->{
            newFragment = new HomeFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container, newFragment).commit();

        });
    }


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bootom_sheet_home);

        ImageView closeimage=bottomSheetDialog.findViewById(R.id.closeimage);
        closeimage.setOnClickListener(view -> bottomSheetDialog.dismiss());

        TextView text_ekrar_nase=bottomSheetDialog.findViewById(R.id.text_ekrar_nase);
        text_ekrar_nase.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), EkrarNase.class));
        });

        TextView id_sand_eys=bottomSheetDialog.findViewById(R.id.id_sand_eys);
        id_sand_eys.setOnClickListener(view -> {
            Intent intent =  new Intent(getApplicationContext(), Add_Financial_InvoiceNew.class);
            intent.putExtra("DOCUMENT_TYPE", 1);
            startActivity(intent);
        });

        TextView id_sand_payment=bottomSheetDialog.findViewById(R.id.id_sand_payment);
        id_sand_payment.setOnClickListener(view -> {
            Intent intent =  new Intent(getApplicationContext(), Add_Financial_InvoiceNew.class);
            intent.putExtra("DOCUMENT_TYPE", 2);
            startActivity(intent);
        });

        TextView text_taslem=bottomSheetDialog.findViewById(R.id.text_taslem);
        text_taslem.setOnClickListener(view -> {
            Intent intent =  new Intent(getApplicationContext(), Add_Financial_InvoiceNew.class);
            intent.putExtra("DOCUMENT_TYPE", 3);
            startActivity(intent);
        });



        bottomSheetDialog.show();
    }

}