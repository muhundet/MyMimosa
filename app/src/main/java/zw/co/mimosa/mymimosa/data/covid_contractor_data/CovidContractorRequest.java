package zw.co.mimosa.mymimosa.data.covid_contractor_data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidContractorRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public CovidContractorRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}