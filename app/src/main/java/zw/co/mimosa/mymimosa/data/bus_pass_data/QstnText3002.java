package zw.co.mimosa.mymimosa.data.bus_pass_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnText3002 {

    @SerializedName("value")
    @Expose
    private String value;

    public QstnText3002(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
