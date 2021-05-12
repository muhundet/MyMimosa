package zw.co.mimosa.mymimosa.data.covid_business_data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidBusinessRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public CovidBusinessRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}