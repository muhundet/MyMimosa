package zw.co.mimosa.mymimosa.data.remote;

import com.google.gson.Gson;
import com.google.gson.JsonParser;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import zw.co.mimosa.mymimosa.data.InputData;
import zw.co.mimosa.mymimosa.data.LeaveRequest;

public interface APIService {

    String key="5775EFB0-AAB8-437A-8888-A330875F2B8D";
    @Headers({"TECHNICIAN_KEY: "+key, "format: json","OPERATION_NAME: ADD_REQUEST"})
    @POST("/api/v3/requests/")
//    @FormUrlEncoded
    Call<LeaveRequest> saveLeaveDataModel(@Body LeaveRequest leaveRequest);
}
