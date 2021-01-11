package zw.co.mimosa.mymimosa.utilities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.AdvanceActivity;

import static android.provider.Settings.System.getString;

public class NetworkStateChecker extends Service {
    Context mContext;
    String jsonString;
    BroadcastReceiver broadcastReceiverNetwork;
    String jsonFilePath;

    @Override
    public void onCreate()
    {
        registerBroadcastReceiverNetwork();
        registerReceiver(broadcastReceiverNetwork, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onDestroy()
    {
        unregisterReceiver(broadcastReceiverNetwork);

    }
    public void registerBroadcastReceiverNetwork(){
        broadcastReceiverNetwork = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mContext = context;

//                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//                filter.addAction(Intent.ACTION_MANAGE_NETWORK_USAGE);
//                mContext.registerReceiver(broadcastReceiverNetwork, filter);


                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork != null) {
                    //if connected to wifi or mobile data plan
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                        List<String> jsonFileList = new ArrayList<>();
                        File folder = new File(String.valueOf(context.getFilesDir()));
                        File[] filesInFolder = folder.listFiles();
                        for (File file : filesInFolder) {
                            if (!file.isDirectory()) {
                                jsonFileList.add(file.getName());
                            }
                        }

                        if(jsonFileList.size() == 0){
                            System.out.println("no json files");
                        }else{
                            for(int i = 0; i<jsonFileList.size(); i++){
                                jsonFilePath = jsonFileList.get(i);
                                System.out.println(jsonFilePath);
                                try {
                                    File file = new File(context.getFilesDir(), jsonFilePath);
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
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                new AdvanceQueryTask().execute();
                            }

                        }
                    }
                }
            }
        };
    }

//    public void onReceive(Context context, Intent intent) {
//        this.context = context;
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//
//        if (activeNetwork != null) {
//            //if connected to wifi or mobile data plan
//            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//
//                List<String> jsonFileList = new ArrayList<>();
//                File folder = new File(String.valueOf(context.getFilesDir()));
//                File[] filesInFolder = folder.listFiles();
//                for (File file : filesInFolder) {
//                    if (!file.isDirectory()) {
//                        jsonFileList.add(file.getPath());
//                    }
//                }
//
//                if(jsonFileList.size() == 0){
//                    System.out.println("no json files");
//                }else{
//                    for(int i = 0; i<jsonFileList.size(); i++){
//                        String jsonFilePath = jsonFileList.get(i);
//                        System.out.println(jsonFilePath);
//                        try {
//                            File file = new File(context.getFilesDir(), "TestAdvance.json");
//                            FileReader fileReader = new FileReader(file);
//                            BufferedReader bufferedReader = new BufferedReader(fileReader);
//                            StringBuilder stringBuilder = new StringBuilder();
//                            String line = bufferedReader.readLine();
//                            while (line != null) {
//                                stringBuilder.append(line).append("\n");
//                                line = bufferedReader.readLine();
//                            }
//                            bufferedReader.close();
//                            jsonString = stringBuilder.toString();
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//                        new AdvanceQueryTask().execute();
//                    }
//
//                }
//            }
//        }

//    }


    private String readJsonFile() {

        return  jsonString;
    }

    private void deleteJsonFile(String jsonFileName){
        File dir = getFilesDir();
        File file = new File(dir, jsonFileName);
        boolean deleted = file.delete();
        System.out.println("Json file deleted: " + deleted);
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.mimosa_logo_no_motto)
                        .setContentTitle("Advance Application")
                        .setContentText("Sent successfully, now awaits approval");

        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class AdvanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(readJsonFile());

        @Override
        protected String doInBackground(URL... urls) {
            AndroidNetworking.post("https://servicedesk.mimosa.co.zw:8090/api/v3/requests")
                    .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                    .addBodyParameter("input_data",jsonString)
                    .addBodyParameter("OPERATION_NAME","ADD_REQUEST")
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            System.out.println("response");
                            addNotification();
                            deleteJsonFile(jsonFilePath);
//                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AdvanceActivity.this);
//                            dialogBuilder.setMessage("You have successfully applied for advance")
//                                    .setTitle("Submitted")
//                                    .setPositiveButton(android.R.string.ok, null)
//                                    .setIcon(R.drawable.checkmark);
//                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//                            dialogBuilder.show();
                        }

                        @Override
                        public void onError(ANError error) {
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
//                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context.this);
////                                dialogBuilder.setMessage("Your application was not sent because of bad network, the system will resend when network is detected. No need to redo the request.")
//                            dialogBuilder.setMessage("Your application was not sent because of bad network. Please retry.")
//                                    .setTitle("Not Submitted")
//                                    .setPositiveButton(android.R.string.ok, null)
//                                    .setIcon(R.drawable.cancel);
//                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
////                                        Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
////                                        startActivity(intent);
//                                }
//                            });
//                            dialogBuilder.show();
                        }
                    });

            return result;
        }

//        private void saveJson(){
//            try {
//                File file = new File(context.getFilesDir(), "TestAdvance.json");
//                FileWriter fileWriter = new FileWriter(file);
//                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                bufferedWriter.write(jsonStr);
//                bufferedWriter.close();
//                System.out.println("json file saved success in: " + file.getPath());
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }
}
