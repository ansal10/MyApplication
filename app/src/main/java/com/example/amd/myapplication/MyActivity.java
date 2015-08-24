package com.example.amd.myapplication;


import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

public class MyActivity extends Activity {

    public final String STATION_CODES_FILES = "TrainStationCodes.csv";

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {

    }

    public void seatAvailiblityClick(View view) {
    }

    public void trainRoutesClick(View view) {


    }

    public void trainBetweenStationsClick(View view) throws IOException {


//        File stationCodeFile = new File("TrainStationCodes.csv");
//        Scanner s = new Scanner(stationCodeFile);
//        while(s.hasNext()){
//            System.out.println(s.nextLine());
//        }


        Intent intent = new Intent(this, TrainBetweenStations.class);
        startActivity(intent);
    }
}
