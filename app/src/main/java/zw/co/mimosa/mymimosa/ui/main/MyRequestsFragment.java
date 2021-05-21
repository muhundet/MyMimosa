package zw.co.mimosa.mymimosa.ui.main;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.models.RequestModel;
import zw.co.mimosa.mymimosa.ui.hr.HrFormRecyclerAdapter;
import zw.co.mimosa.mymimosa.ui.hr.HrMenuModel;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;
import zw.co.mimosa.mymimosa.utilities.NetworkStateChecker3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRequestsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView rvRequests;
    TextView tvNoRequests;

    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
    String empIdFromLUAU = luau.getEmployeeId();

    ArrayList<RequestModel> rmodel;

   // private static String ip = "10.3.200.146";
   private static String ip = "192.168.2.144";
    private static String port = "1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "servicedesk";
    private static String username = "servicedesk";
    private static String password = "Mimosa123";
    private static String url = "jdbc:jtds:sqlserver://"+ip+"/"+database;

    private Connection connection = null;

    public MyRequestsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyRequestsFragment newInstance(int index) {
        MyRequestsFragment fragment = new MyRequestsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);

//        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            System.out.println("SQL Server connected success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("SQL Server not connected success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Server not success with error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_requests, container, false);
        // Inflate the layout for this fragment
        rvRequests = root.findViewById(R.id.rv_requests);
        tvNoRequests = root.findViewById(R.id.tv_no_requests);

        rmodel = new ArrayList<>();

        getRequestsNotSubmitted();
        getRequestsSubmitted();
        RequestsRecyclerAdapter adapter = new RequestsRecyclerAdapter(rmodel);
        rvRequests.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRequests.setAdapter(adapter);
        return root;
    }

    public void getRequestsNotSubmitted(){
        String jsonString;
        String jsonFilePath;
        List<String> jsonFileList;
        jsonFileList = new ArrayList<>();
        File folder = new File(String.valueOf(getContext().getFilesDir()+"/0/"));
        File[] filesInFolder = folder.listFiles();
        for (File file : filesInFolder) {
            if (!file.isDirectory()){
                jsonFileList.add(file.getName());
            }
        }

        System.out.println("Number of requests available for sync: " +jsonFileList.size());
        if(jsonFileList.size() == 0){
            System.out.println("no json files");
            tvNoRequests.setText("No requests");
        }else{
            for(int i = 0; i<jsonFileList.size(); i++){
                jsonFilePath = jsonFileList.get(i);
                System.out.println("Json path for request: " + jsonFilePath);
                try {
                    File file = new File(getContext().getFilesDir() +"/0/", jsonFilePath);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append("\n");
                        line = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    jsonString = stringBuilder.toString();
                    JSONObject obj = new JSONObject(jsonString);
                    JSONObject jobj = obj.getJSONObject("request");
                    String result = jobj.get("subject").toString();
                    System.out.println("Subject: " + result);
                    rmodel.add(new RequestModel(result, "Not Submitted", "No Request Id", "Not Sent to approver", "Date"));

                }catch(Exception e){
                    e.printStackTrace();
                }
//                new NetworkStateChecker3.AdvanceQueryTask().execute();
            }

        }
    }

    public void getRequestsSubmitted(){
        if (connection!=null){
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select [WORKORDERID]\n" +
                        ",[Pending Approver]\n" +
                        ",[TEMPLATENAME]\n" +
                        ",[Date Raised], [STATUSNAME]\n" +
                        "\n" +
                        ",[TITLE]from [dbo].[pendingapproval] ('"+empIdFromLUAU+"');");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1));
                    rmodel.add(new RequestModel(resultSet.getString(3), resultSet.getString(5), resultSet.getString(1), resultSet.getString(2), resultSet.getString(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Connection is null");
        }
    }

}