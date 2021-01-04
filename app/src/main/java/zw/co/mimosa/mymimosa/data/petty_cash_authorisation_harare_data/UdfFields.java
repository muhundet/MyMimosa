package zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfFields {

    @SerializedName("udf_pick_305")
    @Expose
    private String udfPick305;

    public UdfFields(String udfPick305) {
        this.udfPick305 = udfPick305;
    }

    public String getUdfPick305() {
        return udfPick305;
    }

    public void setUdfPick305(String udfPick305) {
        this.udfPick305 = udfPick305;
    }

}
