package zw.co.mimosa.mymimosa.data.bus_pass_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnText3602 {

    @SerializedName("value")
    @Expose
    private String value;

    public QstnText3602(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
