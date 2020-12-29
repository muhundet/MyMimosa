package zw.co.mimosa.mymimosa.data.educational_assistance_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfDate324 {

    @SerializedName("value")
    @Expose
    private long value;

    public UdfDate324(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

}
