package com.example.amd.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.common.api.APICall;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.trainbetweenstations.Day;
import org.common.api.requests.mapper.trainbetweenstations.Train;
import org.common.utils.UTILS;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrainBetweenStations extends Activity implements View.OnClickListener{

    private static int numOfViews = 0;
    private final List<Train> trains = new ArrayList<>();
    private Map<String, String> stationNameAndCodeMapping = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_stations);

        View buttonSearch = findViewById(R.id.searchTrainBetweenStaions);
        buttonSearch.setOnClickListener(this);

        View allAvailiblityButton = findViewById(R.id.allTrainAvailiblity);
        allAvailiblityButton.setOnClickListener(this);

        new AsyncTask<TrainBetweenStations, Void, TrainBetweenStations>(){

            @Override
            protected TrainBetweenStations doInBackground(TrainBetweenStations... params) {
                initializeStationCodeAndMapping();
                return params[0];
            }

            @Override
            public void onPostExecute(TrainBetweenStations t){
                super.onPostExecute(t);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(t, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(stationNameAndCodeMapping.keySet()));
                AutoCompleteTextView sourceTextView = (AutoCompleteTextView) findViewById(R.id.sourceStationCode);
                AutoCompleteTextView destinationTextView = (AutoCompleteTextView) findViewById(R.id.destinationStationCode);
                sourceTextView.setThreshold(1);
                destinationTextView.setThreshold(1);
                sourceTextView.setAdapter(arrayAdapter);
                destinationTextView.setAdapter(arrayAdapter);
            }
        }.execute(this);


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
                if (hiddedLayout != null) {
                    ((ViewGroup) viewById.getParent()).removeView(viewById);
                }
                findViewById(R.id.allClassAvailiblity).setVisibility(View.VISIBLE);
                final String sourceStationCode = stationNameAndCodeMapping.get(((TextView) findViewById(R.id.sourceStationCode)).getText().toString());
                final String destinatonStationCode = stationNameAndCodeMapping.get(((TextView) findViewById(R.id.destinationStationCode)).getText().toString());
                String dayCode = ((TextView) findViewById(R.id.dayCode)).getText().toString();
                String monthCode = ((TextView) findViewById(R.id.monthCode)).getText().toString();
                String yearCode = "20"+((TextView) findViewById(R.id.yearCode)).getText().toString();
                final String date = dayCode+"-"+monthCode+"-"+yearCode;

                if (UTILS.NotNullNotEmpty(sourceStationCode, destinatonStationCode, date) && UTILS.isValidDate(date, "dd-MM-yy")) {
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
                            String daysviews = "Runs : ";
                            for (Day day : train.getDays()) {
                                if (day.getRuns().equals("Y"))
                                    daysviews = daysviews + day.getDayCode().charAt(0) + " ";
                                else
                                    daysviews = daysviews + "- ";
                            }
                            arrivalDeparture = train.getFrom().getCode() +" " + train.getSrcDepartureTime() +" / "+  train.getTo().getCode()+ " " + train.getDestArrivalTime();
                            LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                            View hiddenInfo = getLayoutInflater().inflate(R.layout.hidden_train_between_stations, myLayout, false);
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
                    String clas = ((Spinner) findViewById(R.id.classSelectView)).getSelectedItem().toString();
                    String sourceStationCode = ((TextView) findViewById(R.id.sourceStationCode)).getText().toString();
                    String destinatonStationCode = ((TextView) findViewById(R.id.destinationStationCode)).getText().toString();
                    String dayCode = ((TextView) findViewById(R.id.dayCode)).getText().toString();
                    String monthCode = ((TextView) findViewById(R.id.monthCode)).getText().toString();
                    String yearCode = "20"+((TextView) findViewById(R.id.yearCode)).getText().toString();
                    String date = dayCode+"-"+monthCode+"-"+yearCode;
                    ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbDefault);
                    new HTTPCalls(sourceStationCode, destinatonStationCode, date, clas, trains, progressBar).execute("");
                }
        break;

        }
    }

    private class HTTPCalls extends AsyncTask<String, Integer, Map<String, String>>{

        String sourceStationCode;
        String destinatonStationCode;
        String date;
        String clas;
        List<Train> trains;
        Map<String, String> trainAvailiblityMap;
        private ProgressBar pbM;

        public HTTPCalls(String sourceStationCode, String destinationStationCode, String date, String clas, List<Train> trains, ProgressBar pbM) {
            this.sourceStationCode = sourceStationCode;
            this.destinatonStationCode = destinationStationCode;
            this.date = date;
            this.clas = clas;
            this.trains = trains;
            this.trainAvailiblityMap = new HashMap<>();
            this.pbM=pbM;
        }

        @Override
        public void onPreExecute(){
            super.onPreExecute();
            pbM.setVisibility(View.VISIBLE);
        }

        @Override
        protected Map<String, String> doInBackground(String... params) {
            int totalTrainsProcessed=0;
            for(Train train : trains){
                totalTrainsProcessed++;
                String available="";
                sourceStationCode = train.getFrom().getCode();
                destinatonStationCode = train.getTo().getCode();
                List<Availiblity> availiblityList = APICall.getAvailiblity(train.getNumber().toString(), sourceStationCode, destinatonStationCode, date, clas, "GN");
                if(availiblityList==null || availiblityList.size()==0){
                    available = "Class Not Present";
                    }
                    else{
                        for(Availiblity availiblity:availiblityList){
                            available= available+availiblity.getDate()+" "+availiblity.getStatus().replace("AVAILABLE","AVL")+"\n";
                        }
                    }
                trainAvailiblityMap.put(train.getNumber().toString(), available);
                int progress = (totalTrainsProcessed*100)/trains.size();
                publishProgress(progress);

            }
            return trainAvailiblityMap;
        }

        @Override
        public void onProgressUpdate(Integer... progress){
            super.onProgressUpdate(progress[0]);
            pbM.setProgress(progress[0]);
            for(Map.Entry entry : trainAvailiblityMap.entrySet()){
                View view = findViewById(Integer.parseInt(entry.getKey().toString()));
                ((TextView) view.findViewById(R.id.availiblityView)).setText(entry.getValue().toString());
            }
        }
        @Override
        protected void onPostExecute(Map<String, String> result) {
            super.onPostExecute(result);
            pbM.setVisibility(View.INVISIBLE);
            pbM.setProgress(0);
//            for(Map.Entry entry : result.entrySet()){
//                View view = findViewById(Integer.parseInt(entry.getKey().toString()));
//                ((TextView) view.findViewById(R.id.availiblityView)).setText(entry.getValue().toString());
//            }
        }


    }

    public void initializeStationCodeAndMapping() {

        stationNameAndCodeMapping = new HashMap<>();
        Scanner scanner = null;
        InputStream is = null;
        String line="initial";
        String []reader;
        System.out.print("STARTS AT : " + new Date().getTime());
        try {
             is = getResources().getAssets().open("staioncodeandname.txt");
            scanner = new Scanner(is);
            while (scanner.hasNext()){
                 line  = scanner.nextLine();
                 reader = line.split(",");
                stationNameAndCodeMapping.put(reader[0]+"  <"+reader[1]+">", reader[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("ISSue with : "+line);
        }
        finally {
            if (scanner!=null)
                scanner.close();
            if(is!=null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            System.out.print("ENDS AT : " + new Date().getTime());
        }

    }
}


