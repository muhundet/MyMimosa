package zw.co.mimosa.mymimosa.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveRequest {
    @SerializedName("request")
    @Expose
    private Request request;

    public LeaveRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
