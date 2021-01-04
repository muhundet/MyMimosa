package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PettyCashAuthorisationHarareRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public PettyCashAuthorisationHarareRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
