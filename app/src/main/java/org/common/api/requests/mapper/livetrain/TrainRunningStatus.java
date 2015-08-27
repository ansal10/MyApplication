package org.common.api.requests.mapper.livetrain;

import java.util.List;

/**
 * Created by amd on 8/26/15.
 */
public class TrainRunningStatus {

    private String responseCode;
    private String position;
    private Long trainNumber;
    private Long total;
    private String error;
    private List<TrainRunningRoute> route;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(Long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<TrainRunningRoute> getRoute() {
        return route;
    }

    public void setRoute(List<TrainRunningRoute> route) {
        this.route = route;
    }
}
