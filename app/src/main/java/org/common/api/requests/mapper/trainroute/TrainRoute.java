package org.common.api.requests.mapper.trainroute;

import org.common.api.requests.mapper.trainbetweenstations.Train;

import java.util.List;

/**
 * Created by amd on 8/21/15.
 */
public class TrainRoute {

    private String responseCode;
    private List<Route> route;
    private Train train;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
