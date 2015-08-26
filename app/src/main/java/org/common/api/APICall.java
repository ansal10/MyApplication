package org.common.api;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.seatavailiblity.SeatAvailiblity;
import org.common.api.requests.mapper.trainbetweenstations.Train;
import org.common.api.requests.mapper.trainbetweenstations.TrainBetweenStations;
import org.common.api.requests.mapper.trainroute.TrainRoute;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amd on 8/21/15.
 */
public class APICall {


    private static final String SUCCESS_RESPONSE="200";

    private static final String TRAIN="[[TRAIN]]";
    private static final String SOURCE="[[SOURCE]]";
    private static final String DEST="[[DESTINATION]]";
    private static final String DATE="[[DATE]]";
    private static final String CLASS="[[CLASS]]";
    private static final String QUOTA="[[QUOTA]]";
    private static final String APIKEY="[[APIKEY]]";


    private static final String API_KEY="xpxgh9950";


    private static final String SEAT_AVAILIBLITY = "http://api.railwayapi.com/check_seat/train/[[TRAIN]]/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/class/[[CLASS]]/quota/[[QUOTA]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_BETWEEN_STATION = "http://api.railwayapi.com/between/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_ROUTE = "http://api.railwayapi.com/route/train/[[TRAIN]]/apikey/[[APIKEY]]/";



    public static int calls=0;

    public static String HTTPCall(String urlString) {


        if(!canCall())
            return "{ responseCode : 403 }";

        InputStream is = null;
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlString);
            System.out.println(url);
            conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            String response = IOUtils.toString(is);
            System.out.println(response);
            calls++;
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "{ responseCode : 403 }";
        }catch (Throwable t){
            t.printStackTrace();
            return "{ responseCode : 403 }";
        }finally {
            if (is!=null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            is = null;
        }
    }

    public static boolean canCall(){
        return calls <= 10000;
    }


    public static List<Availiblity> getAvailiblity(String train, String source, String destination, String date, String cclass, String quota){

        String url = SEAT_AVAILIBLITY
                    .replace(TRAIN, train)
                    .replace(SOURCE, source)
                    .replace(DEST, destination)
                    .replace(DATE, date)
                    .replace(CLASS, cclass)
                    .replace(QUOTA, quota)
                    .replace(APIKEY, API_KEY);


        String response = HTTPCall(url);
        int times=5;

//        while(times>=0 && response.contains("\"error\": true")){
//            response = HTTPCall(url);
//            times--;
//        }
//        System.out.println("Tried "+(5-times)+" times");
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        SeatAvailiblity seatAvailiblity = gson.fromJson( response , SeatAvailiblity.class);
        if(seatAvailiblity.getResponseCode().equals(SUCCESS_RESPONSE)){
            return seatAvailiblity.getAvailability();
        }else{
            return new ArrayList<>();
        }
    }

    public static List<Train> getTrainsBetweenStation(String source , String dest, String date){
        String url = TRAIN_BETWEEN_STATION
                        .replace(DEST, dest)
                        .replace(SOURCE, source)
                        .replace(DATE, date)
                        .replace(APIKEY, API_KEY);

        String response = HTTPCall(url);
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        TrainBetweenStations trainBetweenStations = gson.fromJson( response, TrainBetweenStations.class);
        if(trainBetweenStations.getResponseCode().equals(SUCCESS_RESPONSE))
            return trainBetweenStations.getTrain();
        else
            return new ArrayList<>();
    }

    public static TrainRoute getTrainRoute(String train){
        String url = TRAIN_ROUTE
                    .replace(TRAIN, train)
                    .replace(APIKEY, API_KEY);

        String response = HTTPCall(url);
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        TrainRoute trainRoute = gson.fromJson(response, TrainRoute.class);

        if(trainRoute.getResponseCode().equals(SUCCESS_RESPONSE))
            return trainRoute;
        else
            return null;


    }
}
