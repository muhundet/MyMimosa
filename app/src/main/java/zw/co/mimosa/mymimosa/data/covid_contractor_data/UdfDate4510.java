package zw.co.mimosa.mymimosa.data.covid_contractor_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfDate4510 {

    @SerializedName("value")
    @Expose
    private long value;

    public UdfDate4510(long value) {
        this.value = value;
    }


    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
