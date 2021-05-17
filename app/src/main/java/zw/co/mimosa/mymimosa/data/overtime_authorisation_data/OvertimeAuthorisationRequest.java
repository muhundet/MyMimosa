package zw.co.mimosa.mymimosa.data.overtime_authorisation_data;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class OvertimeAuthorisationRequest {

        @SerializedName("request")
        @Expose
        private Request request;

        public Request getRequest() {
                return request;
        }

        public void setRequest(Request request) {
                this.request = request;
        }

}
