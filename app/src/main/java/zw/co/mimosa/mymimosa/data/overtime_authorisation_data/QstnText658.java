package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QstnText658 {

    @SerializedName("value")
    @Expose
    private String value;

    public QstnText658(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
