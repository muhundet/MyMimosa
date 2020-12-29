package zw.co.mimosa.mymimosa.data.educational_assistance_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationalAssistanceRequest {
    @SerializedName("request")
    @Expose
    private Request request;

    public EducationalAssistanceRequest(Request request) {
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
