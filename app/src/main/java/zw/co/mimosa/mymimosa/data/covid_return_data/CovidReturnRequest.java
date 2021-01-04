package zw.co.mimosa.mymimosa.data.covid_return_data;


import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class CovidReturnRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public CovidReturnRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}