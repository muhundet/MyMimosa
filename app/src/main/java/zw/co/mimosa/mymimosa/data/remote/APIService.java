package zw.co.mimosa.mymimosa.data.remote;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import zw.co.mimosa.mymimosa.data.leave_data.LeaveRequest;

public interface APIService {

    String key="5775EFB0-AAB8-437A-8888-A330875F2B8D";
    @Headers({"TECHNICIAN_KEY: "+key, "format: json","OPERATION_NAME: ADD_REQUEST"})
    @POST("/api/v3/requests/")
//    @FormUrlEncoded
    Call<LeaveRequest> saveLeaveDataModel(@Body LeaveRequest leaveRequest);

}
