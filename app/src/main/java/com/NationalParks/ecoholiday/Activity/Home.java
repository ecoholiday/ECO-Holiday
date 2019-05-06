package com.NationalParks.ecoholiday.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.NationalParks.ecoholiday.Adapter.ParksAdapter;
import com.NationalParks.ecoholiday.GPS.GPSTracker;
import com.NationalParks.ecoholiday.Item.ParkFacilityListItem;
import com.NationalParks.ecoholiday.Item.ParkItems;
import com.NationalParks.ecoholiday.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.games.stats.Stats;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.Distance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.round;

public class Home extends AppCompatActivity {

    //from distnace activity

    SQLiteDatabase mDatabase;
    TextView distanceCount;
    TextView dayCount;
    ImageView imgSearch;
    SeekBar seekDistance;
    SeekBar seekDays;
    ListView viewData;
    int DistCount=1000;
    int MinDistCount = 0;
    int DaysCount=5;
    SharedPreferences sharedpreferences;
    private ProgressDialog pDialog;
    public static ArrayList<ParkItems> ParksList ;

    // search bar
    AutoCompleteTextView txtHomeSearch;
    ArrayAdapter NPAdapter;
    String selectNPName;
    List<Integer> NPIDonSearch = new ArrayList<Integer>();
    List<String> NPSearch = new ArrayList<String>();

    //
    //SQLiteDatabase mDatabase;
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

    // camping and treking tables
    List<String> CampingName = new ArrayList<String>();
    List<String> TrackName = new ArrayList<String>();
    List<String> CNPID = new ArrayList<String>();
    List<String> TNPID = new ArrayList<String>();
    //

    public static final String MyPREFERENCES = "MyPrefs" ;
    //SharedPreferences sharedpreferences;

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

    // current changes
    private PopupWindow popupFilter;
    private RelativeLayout posPopup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // popup filter

        ImageButton imgfilter = (ImageButton) findViewById(R.id.imgfilter);
        posPopup = (RelativeLayout) findViewById(R.id.relHome);

        imgfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.popup_filter, null);

                popupFilter = new PopupWindow(customView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                popupFilter.showAtLocation(posPopup, Gravity.CENTER, 0, 0);


                distanceCount = (TextView) customView.findViewById(R.id.count);
                seekDistance = (SeekBar) customView.findViewById(R.id.seek_distance);
                seekDistance.setProgress(DistCount);
                //distanceCount.setText(DistCount);
                distanceCount.setText(String.valueOf(DistCount));

                seekDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        distanceCount.setText(progress+"");
                        DistCount = progress;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //new GetParksData().execute();

                    }

                });


                CardView btnFilterApply = (CardView) customView.findViewById(R.id.btnFilterApply);
                btnFilterApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupFilter.dismiss();
                        //Toast.makeText(getApplicationContext(),"yes "+DistCount + " got" ,Toast.LENGTH_LONG).show();
                        new GetParksData().execute();
                    }
                });
                CardView btnFilterCancel = (CardView) customView.findViewById(R.id.btnFilterCancel);
                btnFilterCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupFilter.dismiss();
                    }
                });

            }
        });
        // popup filter

        // toolbar changes
        ImageButton imgExplore = (ImageButton) findViewById(R.id.imgExplore);
        imgExplore.setImageResource(R.mipmap.explore_clicked);


        final ImageButton imgStats = (ImageButton) findViewById(R.id.imgStats);
        imgStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentExplore = new Intent(Home.this, Statistics.class);
                progressDialogueShowClose();
                startActivity(intentExplore);

            }
        });

        final ImageButton imgHelp = (ImageButton) findViewById(R.id.imgHelp);
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgHelp = new Intent(Home.this, Help.class);
                progressDialogueShowClose();
                startActivity(imgHelp);

            }
        });

        final ImageButton imgChecklist = (ImageButton) findViewById(R.id.imgChecklist);
        imgChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgChecklist = new Intent(Home.this, CheckList.class);
                progressDialogueShowClose();
                startActivity(imgChecklist);

            }
        });

        // toolbar changes

        //enabling text search keypad
        txtHomeSearch = (AutoCompleteTextView)findViewById(R.id.txtHomeSearch);
        txtHomeSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });



        // list is updated
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        distanceCount = (TextView) findViewById(R.id.count);
        seekDistance = (SeekBar) findViewById(R.id.seek_distance);
        viewData = (ListView) findViewById(R.id.view_list);
        ParksList = new ArrayList<ParkItems>();
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);


        new GetParksData().execute();
        // list is updated

        // added above code
        NationalParks = Arrays.asList(getResources().getStringArray(R.array.NationalParks));
        Area = Arrays.asList(getResources().getStringArray(R.array.Area));
        Latitude = Arrays.asList(getResources().getStringArray(R.array.Latitude));
        Longitude = Arrays.asList(getResources().getStringArray(R.array.Longitude));
        Distance = Arrays.asList(getResources().getStringArray(R.array.Distance));

        // camping and treking data
        CampingName = Arrays.asList(getResources().getStringArray(R.array.Camping));
        CNPID = Arrays.asList(getResources().getStringArray(R.array.CNPID));
        TrackName = Arrays.asList(getResources().getStringArray(R.array.TrackName));
        TNPID = Arrays.asList(getResources().getStringArray(R.array.TNPID));
        //

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        if (createEcoParkTables()) {
            new Home.UploadData().execute();
            //Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Error has occured. Please try again", Toast.LENGTH_LONG).show();

        }

         try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

            Intent intent = intentBuilder.build(Home.this);
            startActivityForResult(intent, START_PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException
                | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        NPAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,NationalParks);


        txtHomeSearch.setAdapter(NPAdapter);
        txtHomeSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectNPName = (String)parent.getItemAtPosition(position);

                int pos=-1;
                for (int i = 0; i < NationalParks.size(); i++) {
                    if (NationalParks.get(i).equals(selectNPName)) {

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("ParkLatitude",Latitude.get(i));
                        editor.putString("ParkLongitude", Longitude.get(i));
                        editor.putString("ParkName",NationalParks.get(i));
                        editor.putString("ParkArea",Area.get(i));
                        editor.putString("ParkDistance",Distance.get(i));

                        editor.putInt("NPID",NPIDonSearch.get(NPSearch.indexOf(selectNPName)));
                        editor.apply();
                        startActivity(new Intent(Home.this,ParkActivity.class));
                        break;

                    }
                }
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
            //Toast.makeText(getApplicationContext(),la+" : "+ln,Toast.LENGTH_LONG).show();
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
                    new Home.UpdateDistance().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Problem in Uploading Distance. Contact to our Support Team",Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Problem in Uploading Distance. Contact to our Support Team",Toast.LENGTH_LONG).show();
            }

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
            mDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS tbl_Camping (\n" +
                            "   [CampID] INTEGER NOT NULL CONSTRAINT PK_tbl_Camping PRIMARY KEY AUTOINCREMENT,\n" +
                            "   [NPID] [int],\n" +
                            "   [CampingDescription] [varchar](1000),\n" +
                            "   [Status] [varchar](100) ,\n" +
                            "   [CreatedDateTime] [smalldatetime] \n" +
                            ");"
            );
            mDatabase.execSQL(
                    "CREATE TABLE IF NOT EXISTS tbl_Treck_Trail (\n" +
                            "   [TID] INTEGER NOT NULL CONSTRAINT PK_tbl_Treck_Trail PRIMARY KEY AUTOINCREMENT,\n" +
                            "   [NPID] [int],\n" +
                            "   [TrackName] [varchar](1000),\n" +
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
                for(int i=0;i<CampingName.size();i++){
                    if(CheckIsDataAlreadyInDBorNotInt("tbl_Camping",
                            "CampingDescription",CampingName.get(i),
                            "NPID",Integer.parseInt(CNPID.get(i)))){
                        try{
                            String insertSQL = "INSERT INTO tbl_Camping \n" +
                                    "(NPID, CampingDescription,Status,CreatedDateTime)\n" +
                                    "VALUES \n" +
                                    "("+Integer.parseInt(CNPID.get(i))+"," +
                                    " '"+CampingName.get(i)+"'," +
                                    " 'Active'," +
                                    " '"+dateTime+"'" +
                                    ");";


                            mDatabase.execSQL(insertSQL);
                        }catch (SQLException se){
                            Toast.makeText(getApplicationContext(),"Camping Table Loading Problem :"+se.getMessage(),Toast.LENGTH_LONG).show();
                            z="FAIL";
                        }
                    }
                }
                for(int i=0;i<TrackName.size();i++){
                    if(CheckIsDataAlreadyInDBorNotInt("tbl_Treck_Trail",
                            "TrackName",TrackName.get(i),
                            "NPID",Integer.parseInt(TNPID.get(i)))){
                        try{
                            String insertSQL = "INSERT INTO tbl_Treck_Trail \n" +
                                    "(NPID, TrackName,Status,CreatedDateTime)\n" +
                                    "VALUES \n" +
                                    "("+Integer.parseInt(TNPID.get(i))+"," +
                                    " '"+TrackName.get(i)+"'," +
                                    " 'Active'," +
                                    " '"+dateTime+"'" +
                                    ");";


                            mDatabase.execSQL(insertSQL);
                        }catch (SQLException se){
                            Toast.makeText(getApplicationContext(),"Track Table Loading Problem :"+se.getMessage(),Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getApplicationContext(),"Distance Updated.",Toast.LENGTH_LONG).show();
                new GetParksData().execute();
                //startActivity( new Intent(Home.this,Home.class));
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

    public boolean CheckIsDataAlreadyInDBorNotInt(String TableName,
                                                  String dbfield,
                                                  String fieldValue,
                                                  String dbfield1,
                                                  int fieldValue1)
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

    private class GetParksData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Data Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            ParksList.clear();
        }

        @Override
        protected String doInBackground(String... params) {

            String z = "SUCCESS";
            int newArea = 0;

            if(DaysCount == 1){newArea = 8000;}
            else if(DaysCount == 2){newArea = 18000;}
            else if (DaysCount == 3){newArea = 21000;}
            else if (DaysCount == 4){newArea = 28000;}
            else {newArea = 700000;}

            try{

                String query = "select * from tbl_NationalParksList where Distance <="+DistCount+" and CAST(Area as int) <= "+newArea+" order by Distance ASC";
                Cursor cursorRoom = mDatabase.rawQuery(query, null);

                if(cursorRoom.moveToFirst()){
                    do{
                        final ParkItems item = new ParkItems();
                        item.setNationalParks(cursorRoom.getString(cursorRoom.getColumnIndex("NationalPark")));
                        item.setArea(cursorRoom.getString(cursorRoom.getColumnIndex("Area")));
                        item.setLatitude(cursorRoom.getString(cursorRoom.getColumnIndex("Latitude")));
                        item.setLongitude(cursorRoom.getString(cursorRoom.getColumnIndex("Longitude")));
                        item.setDistance(cursorRoom.getInt(cursorRoom.getColumnIndex("Distance")));
                        item.setNPID(cursorRoom.getInt(cursorRoom.getColumnIndex("NPID")));
                        NPIDonSearch.add(cursorRoom.getInt(cursorRoom.getColumnIndex("NPID")));
                        NPSearch.add(cursorRoom.getString(cursorRoom.getColumnIndex("NationalPark")));
                        ParksList.add(item);
                    }while (cursorRoom.moveToNext());
                }
                cursorRoom.close();
            }catch (Exception e){
                z="FAIL";
            }
            return z;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.hide();

            if(result.equals("SUCCESS")){
                if(ParksList.size()>0){
                    ParksAdapter parkListAdapter = new ParksAdapter(Home.this,ParksList);
                    viewData.setAdapter(parkListAdapter);
                    viewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_LONG).show();
                            ParkItems parkListItem = ParksList.get(position);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("ParkLatitude",parkListItem.getLatitude());
                            editor.putString("ParkLongitude", parkListItem.getLongitude());
                            editor.putString("ParkName",parkListItem.getNationalParks());
                            editor.putString("ParkArea",parkListItem.getArea());
                            editor.putString("ParkDistance",parkListItem.getDistance()+"");
                            editor.commit();
                            startActivity(new Intent(Home.this,MapsActivity.class));
                        }
                    });

                }else{
                    viewData.setAdapter(null);
                    Toast.makeText(getApplicationContext(),"No Parks Found to this Distance",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void progressDialogueShowClose(){
        final ProgressDialog progressDialog = ProgressDialog.show(Home.this,
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
