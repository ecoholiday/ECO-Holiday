package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.NationalParks.ecoholiday.R;

public class LandingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        ImageButton imgGetStarted = (ImageButton) findViewById(R.id.imgGetStarted);
        imgGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGetStarted = new Intent(LandingPage.this,MainActivity.class);
                startActivity(intentGetStarted);

            }
        });

    }
}
