package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnText314 {

    @SerializedName("value")
    @Expose
    private String value;

    public QstnText314(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
