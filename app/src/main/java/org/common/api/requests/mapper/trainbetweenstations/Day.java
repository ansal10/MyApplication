package org.common.api.requests.mapper.trainbetweenstations;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amd on 8/21/15.
 */
public class Day {

    private String runs;

    @SerializedName("day-code")
    private String dayCode;

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getDayCode() {
        return dayCode;
    }

    public void setDayCode(String dayCode) {
        this.dayCode = dayCode;
    }
}
