package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data;



import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class PettyCashAuthorisationMineRequest {

    @SerializedName("request")
    @Expose
    private Request request;

    public PettyCashAuthorisationMineRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
