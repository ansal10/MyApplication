package org.common.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.common.api.requests.mapper.livetrain.TrainRunningStatus;
import org.common.api.requests.mapper.seatavailiblity.Availiblity;
import org.common.api.requests.mapper.seatavailiblity.SeatAvailiblity;
import org.common.api.requests.mapper.trainatstation.TrainsAtStation;
import org.common.api.requests.mapper.trainbetweenstations.Train;
import org.common.api.requests.mapper.trainbetweenstations.TrainBetweenStations;
import org.common.api.requests.mapper.trainroute.TrainRoute;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String HOURS_TO_SEARCH_WITHIN="[[HOURS_TO_SEARCH_WITHIN]]";
    private static final String DAY_OF_JOURNEY = "[[DAY_OF_JOURNEY]]";
    private static final String APIKEY="[[APIKEY]]";



    private static final String API_KEY="xpxgh9950";


    private static final String SEAT_AVAILIBLITY = "http://api.railwayapi.com/check_seat/train/[[TRAIN]]/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/class/[[CLASS]]/quota/[[QUOTA]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_BETWEEN_STATION = "http://api.railwayapi.com/between/source/[[SOURCE]]/dest/[[DESTINATION]]/date/[[DATE]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_ROUTE = "http://api.railwayapi.com/route/train/[[TRAIN]]/apikey/[[APIKEY]]/";
    private static final String TRAIN_AT_STAION = "http://api.railwayapi.com/arrivals/station/[[SOURCE]]/hours/[[HOURS_TO_SEARCH_WITHIN]]/apikey/[[APIKEY]]/";
    private static final String LIVE_TRAIN_STATUS = "http://api.railwayapi.com/live/train/[[TRAIN]]/doj/[[DAY_OF_JOURNEY]]/apikey/[[APIKEY]]/";

    public static int calls=0;

    private static Gson gson =new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

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

        TrainRoute trainRoute = gson.fromJson(response, TrainRoute.class);
        if(trainRoute.getResponseCode().equals(SUCCESS_RESPONSE))
            return trainRoute;
        else
            return null;


    }

    public static TrainsAtStation getTrainsAtStations(String sourceStation, String hoursToSearch){
        String url = TRAIN_AT_STAION
                        .replace(SOURCE, sourceStation)
                        .replace(HOURS_TO_SEARCH_WITHIN, hoursToSearch)
                        .replace(APIKEY, API_KEY);

        String response = HTTPCall(url);

        TrainsAtStation trainsAtStation = gson.fromJson(response, TrainsAtStation.class);
        return trainsAtStation.getResponseCode().equals(SUCCESS_RESPONSE) ? trainsAtStation : null;
    }

    public static TrainRunningStatus getLiveTrainStatus(String train, String dayOfJourney){
        String url = LIVE_TRAIN_STATUS
                        .replace(TRAIN, train)
                        .replace(DAY_OF_JOURNEY, dayOfJourney)
                        .replace(APIKEY, API_KEY);

        String response = HTTPCall(url);

        TrainRunningStatus trainRunningStatus = gson.fromJson(response, TrainRunningStatus.class);
        return trainRunningStatus.getResponseCode().equals(SUCCESS_RESPONSE) ? trainRunningStatus : null;
    }
}
