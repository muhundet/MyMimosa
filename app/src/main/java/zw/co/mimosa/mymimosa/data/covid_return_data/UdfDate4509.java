package zw.co.mimosa.mymimosa.data.covid_return_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfDate4509 {

    @SerializedName("value")
    @Expose
    private long value;

    public UdfDate4509(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
