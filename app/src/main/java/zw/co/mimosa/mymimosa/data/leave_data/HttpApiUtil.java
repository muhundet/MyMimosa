package zw.co.mimosa.mymimosa.data.leave_data;

import android.net.Uri;

import java.net.URL;

import static java.lang.String.valueOf;

public class HttpApiUtil {
    private HttpApiUtil(){}

    public static final String BASE_API_URL = "https://servicedesk.mimosa.co.zw:8090/api/v3/requests";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY = "TECHNICIAN_KEY";
    public static final String TECHNICIAN_KEY = "5775EFB0-AAB8-437A-8888-A330875F2B8";


    public static URL buildUrl() {

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
//                .appendQueryParameter(KEY, TECHNICIAN_KEY)
              //  .appendQueryParameter("OPERATION_NAME", "ADD_REQUEST")
//                .appendQueryParameter("INPUT_DATA", s)
                .build();

        try {
            url = new URL(uri.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }
}
