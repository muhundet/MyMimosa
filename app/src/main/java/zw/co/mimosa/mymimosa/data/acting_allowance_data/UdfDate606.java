package zw.co.mimosa.mymimosa.data.acting_allowance_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfDate606 {

    @SerializedName("value")
    @Expose
    private long value;

    public UdfDate606(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
