package com.NationalParks.ecoholiday.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.NationalParks.ecoholiday.R;

public class CheckList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgChecklist.setImageResource(R.mipmap.check_list_clicked);


        final ImageButton imgExplore = (ImageButton) findViewById(R.id.imgExplore);
        imgExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogueShowClose();
                Intent intentExplore = new Intent(CheckList.this, Home.class);
                startActivity(intentExplore);

            }
        });

        final ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogueShowClose();
                Intent imgHelp = new Intent(CheckList.this, Statistics.class);
                startActivity(imgHelp);
            }
        });

        final ImageButton imgHelp = (ImageButton) findViewById(R.id.imgHelp);
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog.show(CheckList.this, "Loading", "Wait while loading...");
                Intent imgChecklist = new Intent(CheckList.this, Help.class);
                startActivity(imgChecklist);

            }
        });
    }
    public void progressDialogueShowClose(){
        final ProgressDialog progressDialog = ProgressDialog.show(CheckList.this,
                "Loading","Please Wait...");
        progressDialog.setCancelable(true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();

            }
        }, 500);

    }
}
