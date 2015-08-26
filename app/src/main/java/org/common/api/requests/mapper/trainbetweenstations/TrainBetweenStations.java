package org.common.api.requests.mapper.trainbetweenstations;

import java.util.List;

/**
 * Created by amd on 8/21/15.
 */
public class TrainBetweenStations {

    private String responseCode;
    private Long total;
    private List<Train> train;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<Train> getTrain() {
        return train;
    }

    public void setTrain(List<Train> train) {
        this.train = train;
    }
}
