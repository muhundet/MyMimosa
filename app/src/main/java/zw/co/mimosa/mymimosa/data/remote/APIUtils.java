package zw.co.mimosa.mymimosa.data.remote;

public class APIUtils {
    private APIUtils() {}

    public static final String BASE_URL = "https://servicedesk.mimosa.co.zw:8090";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
