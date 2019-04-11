package com.NationalParks.ecoholiday.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.os.Handler;
import android.widget.Toast;

import com.NationalParks.ecoholiday.GPS.GPSTracker;
import com.NationalParks.ecoholiday.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.games.stats.Stats;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.round;

public class PlanHoliday extends AppCompatActivity {

    ImageView nationalPark;
    //Button btnHoliday;
    SQLiteDatabase mDatabase;
    public static final String DATABASE_NAME = "myecodatabase";
    String z;
    List<String> NationalParks = new ArrayList<String>();
    List<String> Area = new ArrayList<String>();
    List<String> Latitude = new ArrayList<String>();
    List<String> Longitude = new ArrayList<String>();
    List<String> Distance = new ArrayList<String>();
    List<String> Latitude1 = new ArrayList<String>();
    List<String> Longitude1 = new ArrayList<String>();
    List<String> Distance1 = new ArrayList<String>();
    //String[] showdistance = new String[20];
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    private static final int START_PLACE_PICKER_REQUEST = 1;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    double latitude, longitude;
    String address = "";
    Double la, ln, la1, ln1;
    String Lat, lat1, lon, lon1;
    String current_address;
    boolean doubleBackToExitPressedOnce = false;
    //GoogleApiClient mGoogleApiClient;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_holiday);

        nationalPark = findViewById(R.id.imgNationalPark);

        NationalParks = Arrays.asList(getResources().getStringArray(R.array.NationalParks));
        Area = Arrays.asList(getResources().getStringArray(R.array.Area));
        Latitude = Arrays.asList(getResources().getStringArray(R.array.Latitude));
        Longitude = Arrays.asList(getResources().getStringArray(R.array.Longitude));
        Distance = Arrays.asList(getResources().getStringArray(R.array.Distance));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        if (createEcoParkTables()) {
            new UploadData().execute();
            //Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Error has occured. Please try again", Toast.LENGTH_LONG).show();

        }



        nationalPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,DistanceActivity.class));

                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            nationalPark.setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }, 500);
                    nationalPark.setBackgroundColor(Color.parseColor("#25368A"));

                    //intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                    Intent intent = intentBuilder.build(PlanHoliday.this);
                    startActivityForResult(intent, START_PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException
                        | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });


        /* final ImageView imgNationalPark = (ImageView)findViewById(R.id.imgNationalPark);
        imgNationalPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgNationalPark.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgNationalPark.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentNP = new Intent(PlanHoliday.this, MainActivity.class);
                startActivity(intentNP);
            }
        }); */

        final ImageView imgStats = (ImageView)findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgStats.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgStats.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentStats = new Intent(PlanHoliday.this, Statistics.class);
                startActivity(intentStats);
            }
        });


        final ImageView imgHiking = (ImageView)findViewById(R.id.imgHiking);
        imgHiking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgHiking.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgHiking.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentHik = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentHik);
            }
        });

        final ImageView imgCycling = (ImageView)findViewById(R.id.imgCycling);
        imgCycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgCycling.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);
                imgCycling.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentCyc = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentCyc);
            }
        });

        final ImageView imgCamping = (ImageView)findViewById(R.id.imgCamping);
        imgCamping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        imgCamping.setBackgroundColor(Color.parseColor("#ffffff"));
                    }
                }, 500);

                imgCamping.setBackgroundColor(Color.parseColor("#25368A"));
                Intent intentCamp = new Intent(PlanHoliday.this, Cycling.class);
                startActivity(intentCamp);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (requestCode == START_PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(this, data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            String attributions = (String) place.getAttributions();
            final LatLng latLng = place.getLatLng();
            la = latLng.latitude;
            Lat = la.toString();
            Lat = Lat.substring(0,10);
            ln = latLng.longitude;
            lon = ln.toString();
            lon=lon.substring(0,10);

            current_address =(String)address;
            Toast.makeText(getApplicationContext(),la+" : "+ln,Toast.LENGTH_LONG).show();
            Distance1.clear();
            Latitude1.clear();
            Longitude1.clear();
            if(Latitude.size()>0 && Longitude.size()>0 && Latitude.size() == Longitude.size()){

                for(int i=0;i<Longitude.size();i++){
                    Location locationA = new Location("point A");

                    locationA.setLatitude(la);
                    locationA.setLongitude(ln);

                    Location locationB = new Location("point B");

                    locationB.setLatitude(Double.parseDouble(Latitude.get(i)));
                    locationB.setLongitude(Double.parseDouble(Longitude.get(i)));

                    float dist = locationA.distanceTo(locationB);
                    double ditKM = round(dist/1000.0);
                    Distance1.add(ditKM+"");
                    Latitude1.add(Latitude.get(i));
                    Longitude1.add(Longitude.get(i));
                }


                if(Distance1.size() == Latitude.size()){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Latitude", la.toString());
                    editor.putString("Longitude", ln.toString());
                    editor.putString("Address",current_address);
                    editor.commit();
                    new PlanHoliday.UpdateDistance().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Problem in Uploading Distance. Contact to our Support Team",Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Problem in Uploading Distance. Contact to our Support Team",Toast.LENGTH_LONG).show();
            }



//            Second Method

//            float dist = distance(la,ln,Double.parseDouble(Latitude.get(1)),Double.parseDouble(Longitude.get(1)));
//
//            double lngDst = round(dist/1000.0);

            //Toast.makeText(getApplicationContext(),ditKM+":"+lngDst,Toast.LENGTH_LONG).show();


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public static float distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    private boolean createEcoParkTables() {
        boolean isCreated = false;
        try{
            mDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS tbl_NationalParksList (\n" +
                            "   [NPID] INTEGER NOT NULL CONSTRAINT PK_tbl_NationalParksList PRIMARY KEY AUTOINCREMENT,\n" +
                            "   [NationalPark] [varchar](1000),\n" +
                            "   [Area] [varchar](100),\n" +
                            "   [Latitude] [varchar](500),\n" +
                            "   [Longitude] [varchar](500),\n" +
                            "   [Distance] [REAL],\n" +
                            "   [Status] [varchar](100) ,\n" +
                            "   [CreatedDateTime] [smalldatetime] \n" +
                            ");"
            );

//            pDialog.hide();
            isCreated =true;

        }catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage()+"",Toast.LENGTH_LONG).show();
            isCreated= false;
        }

        return isCreated;
    }


    private class UploadData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this," Data is Loading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {

            if(NationalParks.size()>0){
                Calendar cal = Calendar.getInstance();
                String dateTime = sdf.format(cal.getTime());
                z="SUCCESS";
                for(int i=0;i<NationalParks.size();i++){
                    if(CheckIsDataAlreadyInDBorNot("tbl_NationalParksList",
                            "NationalPark",NationalParks.get(i),
                            "Area",Area.get(i))){
                        try{
                            z="SUCCESS";
                            String insertSQL = "INSERT INTO tbl_NationalParksList \n" +
                                    "(NationalPark, Area, Latitude, Longitude,Distance,Status,CreatedDateTime)\n" +
                                    "VALUES \n" +
                                    "('"+NationalParks.get(i)+"'," +
                                    " '"+Area.get(i)+"'," +
                                    " '"+Latitude.get(i)+"'," +
                                    " '"+Longitude.get(i)+"'," +
                                    " "+Double.parseDouble(Distance.get(i))+"," +
                                    " 'Active'," +
                                    " '"+dateTime+"'" +
                                    ");";


                            mDatabase.execSQL(insertSQL);
                        }catch (SQLException se){
                            Toast.makeText(getApplicationContext(),"National Parks Loading Problem :"+se.getMessage(),Toast.LENGTH_LONG).show();
                            z="FAIL";
                        }
                    }
                }
            }


            return z;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(z=="SUCCESS"){
                //Toast.makeText(getApplicationContext(),"Data Loading Completed.",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Data Loading Fail. Please try again",Toast.LENGTH_LONG).show();
            }



        }
    }

    private class UpdateDistance extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this," Data is Loading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {

            if(Distance1.size()>0){
                z="SUCCESS";
                for(int i=0;i<Distance1.size();i++){
                    if(CheckIsDataAlreadyInDBorNot1("tbl_NationalParksList",
                            "Latitude",Latitude1.get(i),
                            "Longitude",Longitude1.get(i),
                            "Distance",Double.parseDouble(Distance1.get(i)))){
                        try{
                            z="SUCCESS";

                            String updateQuery = "UPDATE tbl_NationalParksList " +
                                    "set Distance = "+Double.parseDouble(Distance1.get(i))+" " +
                                    "Where Latitude = '"+Latitude1.get(i)+"' and Longitude = '"+Longitude1.get(i)+"'";
                            mDatabase.execSQL(updateQuery);



                        }catch (SQLException se){
                            Toast.makeText(getApplicationContext(),"National Parks Loading Problem :"+se.getMessage(),Toast.LENGTH_LONG).show();
                            z="FAIL";
                        }
                    }
                }
            }


            return z;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(z=="SUCCESS"){
                Toast.makeText(getApplicationContext(),"Distance Updated.",Toast.LENGTH_LONG).show();


                startActivity( new Intent(PlanHoliday.this,DistanceActivity.class));
            }else{
                Toast.makeText(getApplicationContext(),"Distance Update Fail",Toast.LENGTH_LONG).show();
            }



        }
    }


    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield,
                                               String fieldValue,
                                               String dbfield1,
                                               String fieldValue1)
    {
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue+"' and " + dbfield1 + " = '" + fieldValue1+"'";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if(cursor.getCount() > 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean CheckIsDataAlreadyInDBorNot1(String TableName,
                                                String dbfield,
                                                String fieldValue,
                                                String dbfield1,
                                                String fieldValue1,
                                                String dbfield2,
                                                double fieldValue2)
    {
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue+"' and " + dbfield1 + " = '" + fieldValue1+"' and " + dbfield2 + " = " + fieldValue2+"";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        if(cursor.getCount() > 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press quick back again to exit", Toast.LENGTH_SHORT).show();

    }

}
