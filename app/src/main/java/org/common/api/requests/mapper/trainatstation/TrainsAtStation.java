package org.common.api.requests.mapper.trainatstation;

import java.util.List;

/**
 * Created by amd on 8/26/15.
 */
public class TrainsAtStation {

    private String responseCode;
    private String station;
    private Long total;
    private List<StationTrain> train;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<StationTrain> getTrain() {
        return train;
    }

    public void setTrain(List<StationTrain> train) {
        this.train = train;
    }
}
