package com.NationalParks.ecoholiday.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.NationalParks.ecoholiday.R;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);




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
                trans.add(R.id.chartFrame,frag).commit();
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
                trans.add(R.id.chartFrame,frag).commit();
            }
        });
        CardView cardVic = (CardView)findViewById(R.id.cardVic);
        cardVic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                ChartFragment frag = new ChartFragment();
                Bundle toFragment = new Bundle();

                toFragment.putString("type","ausemissions");
                frag.setArguments(toFragment);
                trans.add(R.id.chartFrame,frag).commit();
            }
        });

    }
}
