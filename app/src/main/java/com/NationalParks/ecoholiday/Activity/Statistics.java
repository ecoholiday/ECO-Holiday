package com.NationalParks.ecoholiday.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

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


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        ChartFragment frag = new ChartFragment();
        Bundle toFragment = new Bundle();

        toFragment.putString("type","world");
        frag.setArguments(toFragment);
        trans.add(R.id.worldFrame,frag).commit();
        worldFrame.setVisibility(View.VISIBLE);

        CardView cardWorld = (CardView)findViewById(R.id.cardWorld);
        cardWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        CardView cardAus = (CardView)findViewById(R.id.cardAus);
        cardAus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        CardView cardVic = (CardView)findViewById(R.id.cardVic);
        cardVic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
