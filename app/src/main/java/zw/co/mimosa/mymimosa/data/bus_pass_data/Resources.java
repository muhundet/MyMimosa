package zw.co.mimosa.mymimosa.data.bus_pass_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resources {

    @SerializedName("res_3061")
    @Expose
    private Res3061 res3061;

    public Resources(Res3061 res3061) {
        this.res3061 = res3061;
    }

    public Res3061 getRes3061() {
        return res3061;
    }

    public void setRes3061(Res3061 res3061) {
        this.res3061 = res3061;
    }
}


