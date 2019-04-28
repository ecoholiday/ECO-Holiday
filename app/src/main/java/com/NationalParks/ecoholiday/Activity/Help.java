package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
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
                Intent intentExplore = new Intent(Help.this, Home.class);
                startActivity(intentExplore);

            }
        });

        final ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgHelp = new Intent(Help.this, Statistics.class);
                startActivity(imgHelp);
            }
        });

        final ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgChecklist = new Intent(Help.this, CheckList.class);
                startActivity(imgChecklist);

            }
        });
    }
}
