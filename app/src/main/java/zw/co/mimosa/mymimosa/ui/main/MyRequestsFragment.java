package zw.co.mimosa.mymimosa.ui.main;

import android.content.BroadcastReceiver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.models.RequestModel;
import zw.co.mimosa.mymimosa.ui.hr.HrFormRecyclerAdapter;
import zw.co.mimosa.mymimosa.ui.hr.HrMenuModel;
import zw.co.mimosa.mymimosa.utilities.NetworkStateChecker3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRequestsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvRequests;
    TextView tvNoRequests;

    ArrayList<RequestModel> rmodel;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_requests, container, false);
        // Inflate the layout for this fragment
        rvRequests = root.findViewById(R.id.rv_requests);
        tvNoRequests = root.findViewById(R.id.tv_no_requests);
        RequestModel rm1 = new RequestModel("LEAVE AND TEMPORARY EARNINGS FOR ------------","Not Submitted");
        RequestModel rm2 = new RequestModel("LEAVE AND TEMPORARY EARNINGS FOR ------------","Not Submitted");
        RequestModel rm3 = new RequestModel("LEAVE AND TEMPORARY EARNINGS FOR ------------","Not Submitted");

        rmodel = new ArrayList<>();
//        rmodel.add(rm1);
//        rmodel.add(rm2);
//        rmodel.add(rm3);
        getRequestsNotSubmitted();
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
                    rmodel.add(new RequestModel(result, "Not Submitted"));

                }catch(Exception e){
                    e.printStackTrace();
                }
//                new NetworkStateChecker3.AdvanceQueryTask().execute();
            }

        }
    }
}