package zw.co.mimosa.mymimosa.data.acting_allowance_data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActingAllowanceRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public ActingAllowanceRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
