package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.NationalParks.ecoholiday.R;

public class Statistics extends AppCompatActivity {
    FrameLayout worldFrame;
    FrameLayout ausFrame;
    FrameLayout vicFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        worldFrame = (FrameLayout) findViewById(R.id.worldFrame);
        ausFrame = (FrameLayout) findViewById(R.id.ausFrame);
        vicFrame = (FrameLayout) findViewById(R.id.vicFrame);

        ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setImageResource(R.mipmap.stats_clicked);


        final ImageButton imgExplore = (ImageButton) findViewById(R.id.imgExplore);
        imgExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExplore = new Intent(Statistics.this, Home.class);
                startActivity(intentExplore);

            }
        });

        final ImageButton imgHelp = (ImageButton) findViewById(R.id.imgHelp);
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgHelp = new Intent(Statistics.this, Help.class);
                startActivity(imgHelp);

            }
        });

        final ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgChecklist = new Intent(Statistics.this, CheckList.class);
                startActivity(imgChecklist);

            }
        });




        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        ChartFragment frag = new ChartFragment();
        Bundle toFragment = new Bundle();

        toFragment.putString("type","world");
        frag.setArguments(toFragment);
        trans.add(R.id.worldFrame,frag).commit();
        worldFrame.setVisibility(View.VISIBLE);

        final CardView cardWorld = (CardView)findViewById(R.id.cardWorld);
        final CardView cardAus = (CardView)findViewById(R.id.cardAus);
        final CardView cardVic = (CardView)findViewById(R.id.cardVic);

        cardWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardWorld.setBackgroundColor(Color.parseColor("#FFAE76"));
                cardAus.setBackgroundColor(Color.parseColor("#ffffff"));
                cardVic.setBackgroundColor(Color.parseColor("#ffffff"));

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                ChartFragment frag = new ChartFragment();
                Bundle toFragment = new Bundle();

                toFragment.putString("type","world");
                frag.setArguments(toFragment);
                trans.add(R.id.worldFrame,frag).commit();

                worldFrame.setVisibility(View.VISIBLE);
                ausFrame.setVisibility(View.INVISIBLE);
                vicFrame.setVisibility(View.INVISIBLE);


            }
        });

        cardAus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardAus.setBackgroundColor(Color.parseColor("#FFAE76"));
                cardWorld.setBackgroundColor(Color.parseColor("#ffffff"));
                cardVic.setBackgroundColor(Color.parseColor("#ffffff"));

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                ChartFragment frag = new ChartFragment();
                Bundle toFragment = new Bundle();

                toFragment.putString("type","piechart");
                frag.setArguments(toFragment);
                trans.add(R.id.vicFrame,frag).commit();

                ausFrame.setVisibility(View.VISIBLE);
                worldFrame.setVisibility(View.INVISIBLE);
                vicFrame.setVisibility(View.INVISIBLE);
            }
        });

        // on clicking victoria card

        cardVic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardVic.setBackgroundColor(Color.parseColor("#FFAE76"));
                cardAus.setBackgroundColor(Color.parseColor("#ffffff"));
                cardWorld.setBackgroundColor(Color.parseColor("#ffffff"));

                // create a fragment and append the chart to the victoria frame
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                ChartFragment frag = new ChartFragment();
                Bundle toFragment = new Bundle();

                toFragment.putString("type","ausemissions");
                frag.setArguments(toFragment);
                trans.add(R.id.ausFrame,frag).commit();

                vicFrame.setVisibility(View.VISIBLE);
                ausFrame.setVisibility(View.INVISIBLE);
                worldFrame.setVisibility(View.INVISIBLE);

            }
        });

    }
}
