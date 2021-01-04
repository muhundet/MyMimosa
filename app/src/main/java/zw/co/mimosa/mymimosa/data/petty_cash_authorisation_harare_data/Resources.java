package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resources {

    @SerializedName("res_2431")
    @Expose
    private Res2431 res2431;

    public Resources(Res2431 res2431) {
        this.res2431 = res2431;
    }

    public Res2431 getRes2431() {
        return res2431;
    }

    public void setRes2431(Res2431 res2431) {
        this.res2431 = res2431;
    }

}
