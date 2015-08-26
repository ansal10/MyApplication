package org.common.api;

/**
 * Created by amd on 8/23/15.
 */
import junit.framework.TestCase;

import org.common.api.requests.mapper.livetrain.TrainRunningStatus;
        import org.common.api.requests.mapper.trainatstation.TrainsAtStation;
        import org.junit.Test;

public class APICallTest extends TestCase {

    @Test
    public void testNull(){
        assertTrue(true);
    }
//
//    APICall apiCall = new APICall();
//
//    @Test
//    public void testSeatAvailiblity(){
//        String src="BPL";
//        String dest="NDLS";
//        String date="22-10-2015";
//        String classs="SL";
//        String quota="GN";
//        String train="12001";
//
//        List<Availiblity> availiblities = APICall.getAvailiblity(train,src,dest,date,classs,quota);
//
//        assertTrue((availiblities != null && availiblities.size() > 0));
//
//    }
//
//    @Test
//    public void testTrainBetweenStation(){
//        String src="MAS";
//        String dest="SC";
//        String date="22-10-2015";
//
//        List<Train> trains = APICall.getTrainsBetweenStation(src, dest, date);
//
//        assertTrue( trains!=null && trains.size()>0 );
////        System.out.println(trains);
//    }
//
//    @Test
//    public void testTrainRoute(){
//        String train="12511";
//
//        TrainRoute trainRoute = APICall.getTrainRoute(train);
//
//        assertEquals(trainRoute.getRoute().size(), 61);
//    }
    @Test
    public void testTrainAtStaion(){
        String sourceStation = "CNB";
        String hoursToSearch = "10";

        TrainsAtStation trainsAtStation = APICall.getTrainsAtStations(sourceStation, hoursToSearch);

        assertTrue( trainsAtStation.getTotal()>0 && trainsAtStation.getTotal()==trainsAtStation.getTrain().size());
    }
    @Test
    public void testLiveTrainStatus(){
        String train="12512";
        String dayOfJourney = "20150826";
        TrainRunningStatus trainRunningStatus = APICall.getLiveTrainStatus(train, dayOfJourney);

        assertTrue(trainRunningStatus.getError()!=null && trainRunningStatus.getTotal()==60 && trainRunningStatus.getRoute().size()==60 );
    }

}