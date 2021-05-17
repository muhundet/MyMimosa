package zw.co.mimosa.mymimosa.data.fuel_request_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class FuelRequestRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public FuelRequestRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
