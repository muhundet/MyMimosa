package zw.co.mimosa.mymimosa.data.leave_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputData {
    @SerializedName("input_data = @")
    @Expose
    private LeaveRequest request;

    public InputData(LeaveRequest request) {
        this.request = request;
    }

    public LeaveRequest getRequest() {
        return request;
    }

    public void setRequest(LeaveRequest request) {
        this.request = request;
    }
}
