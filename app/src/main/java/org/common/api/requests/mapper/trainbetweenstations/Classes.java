package org.common.api.requests.mapper.trainbetweenstations;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amd on 8/21/15.
 */
public class Classes {

    private String available;

    @SerializedName("class-code")
    private String classCode;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
