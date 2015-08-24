package com.example.amd.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.common.api.APICall;
import org.common.api.MockAPICall;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.trainbetweenstations.Classes;
import org.common.api.requests.mapper.trainbetweenstations.Day;
import org.common.api.requests.mapper.trainbetweenstations.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainBetweenStations extends Activity implements View.OnClickListener{

    public static int numOfViews = 0;
    public final List<Train> trains = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_stations);

        View buttonSearch = findViewById(R.id.searchTrainBetweenStaions);
        buttonSearch.setOnClickListener(this);

        View allAvailiblityButton = findViewById(R.id.allTrainAvailiblity);
        allAvailiblityButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_between_stations, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchTrainBetweenStaions: {

                View viewById = findViewById(R.id.trainBetweenStationHiddenLayout);
                RelativeLayout hiddedLayout = (RelativeLayout) viewById;
                if (hiddedLayout != null)
                    ((ViewGroup) hiddedLayout.getParent()).removeView(viewById);
                final String sourceStationCode = ((TextView) findViewById(R.id.sourceStationCode)).getText().toString();
                final String destinatonStationCode = ((TextView) findViewById(R.id.destinatonStationCode)).getText().toString();
                final String date = ((TextView) findViewById(R.id.dayCode)).getText().toString();

                if (NotNullNotEmpty(sourceStationCode, destinatonStationCode, date)) {
                    trains.clear();
                    Thread t = new Thread(new Runnable() {


                        @Override
                        public void run() {
                            trains.addAll(APICall.getTrainsBetweenStation(sourceStationCode, destinatonStationCode, date));
                        }
                    });
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!trains.isEmpty())

                        for (Train train : trains) {
                            String trainNameNumber = train.getName() + " / " + train.getNumber();
                            String arrivalDeparture = "";
                            String daysviews = "";
                            for (Day day : train.getDays()) {
                                if (day.getRuns().equals("Y"))
                                    daysviews = daysviews + day.getDayCode().charAt(0) + " ";
                                else
                                    daysviews = daysviews + "x ";
                            }
                            arrivalDeparture = "Src " + train.getSrcDepartureTime() + " Dest " + train.getDestArrivalTime();
                            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                            View hiddenInfo = getLayoutInflater().inflate(R.layout.train_between_stations, myLayout, false);
                            View view = hiddenInfo.findViewById(R.id.trainBetweenStationHiddenLayout);
                            view.setId(train.getNumber().intValue());
                            ((TextView) view.findViewById(R.id.trainNameNumber)).setText(trainNameNumber);
                            ((TextView) view.findViewById(R.id.arrivalDepartureView)).setText(arrivalDeparture);
                            ((TextView) view.findViewById(R.id.daysView)).setText(daysviews);
                            myLayout.addView(hiddenInfo);
                            numOfViews++;
                        }
                }
            }
                break;

            case R.id.allTrainAvailiblity : {
                    final String clas = ((Spinner) findViewById(R.id.classSelectView)).getSelectedItem().toString();
                    final String sourceStationCode = ((TextView) findViewById(R.id.sourceStationCode)).getText().toString();
                    final String destinatonStationCode = ((TextView) findViewById(R.id.destinatonStationCode)).getText().toString();
                    final String date = ((TextView) findViewById(R.id.dayCode)).getText().toString();
                    final List<Availiblity> availiblities = new ArrayList<>();

                            for (final Train train : trains) {
                                String available="";
                                availiblities.clear();
                                View view = findViewById(train.getNumber().intValue());

                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        availiblities.addAll(APICall.getAvailiblity(train.getNumber().toString(), sourceStationCode, destinatonStationCode, date, clas, "GN"));
                                    }
                                });
                                t.start();
                                try {
                                    t.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if(availiblities==null || availiblities.size()==0){
                                    available = "Class Not Present";
                                }
                                else{
                                    for(Availiblity availiblity:availiblities){
                                        available= available+availiblity.getDate()+" "+availiblity.getStatus()+"|";
                                    }
                                }
                                ((TextView) view.findViewById(R.id.availiblityView)).setText(available);
                            }
                }
                break;

        }
    }

    private boolean NotNullNotEmpty(String... params) {
        for(String p:params){
            if(p==null || p.trim().equals(""))
                return false;
        }
        return true;
    }
}
