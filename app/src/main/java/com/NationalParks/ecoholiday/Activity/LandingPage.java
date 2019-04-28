package com.NationalParks.ecoholiday.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.NationalParks.ecoholiday.R;

public class LandingPage extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler mhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /*SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("DELETE FROM tablename"); //delete all rows in a table
        db.close();*/

        Thread mythead = new Thread(){
            @Override
            public void run() {
                try {
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(25);
                        }
                    });
                    sleep(1500);
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(75);
                        }
                    });
                    sleep(1500);
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(1000);
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        mythead.start();




    }
}
