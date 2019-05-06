package com.NationalParks.ecoholiday.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.NationalParks.ecoholiday.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ImageButton imgHelp = (ImageButton) findViewById(R.id.imgHelp);
        imgHelp.setImageResource(R.mipmap.help_clicked);


        final ImageButton imgExplore = (ImageButton) findViewById(R.id.imgExplore);
        imgExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogueShowClose();
                Intent intentExplore = new Intent(Help.this, Home.class);
                startActivity(intentExplore);

            }
        });

        final ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogueShowClose();
                Intent imgHelp = new Intent(Help.this, Statistics.class);
                startActivity(imgHelp);
            }
        });

        final ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogueShowClose();
                Intent imgChecklist = new Intent(Help.this, CheckList.class);
                startActivity(imgChecklist);

            }
        });
    }

    public void progressDialogueShowClose(){
        final ProgressDialog progressDialog = ProgressDialog.show(Help.this,
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
