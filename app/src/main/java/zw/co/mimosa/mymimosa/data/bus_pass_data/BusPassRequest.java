package zw.co.mimosa.mymimosa.data.bus_pass_data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BusPassRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public BusPassRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
