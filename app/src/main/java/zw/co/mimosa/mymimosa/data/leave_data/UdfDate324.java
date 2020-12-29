package zw.co.mimosa.mymimosa.data.leave_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UdfDate324 {
    @SerializedName("value")
    @Expose
    private Long value;

    public UdfDate324(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
