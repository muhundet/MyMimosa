package zw.co.mimosa.mymimosa.data.advance_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvanceRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public AdvanceRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

