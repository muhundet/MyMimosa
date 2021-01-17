package zw.co.mimosa.mymimosa.ui.hr.leave_and_advance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.Pickers.EndDatePicker;
import zw.co.mimosa.mymimosa.Pickers.StartDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.leave_data.LeaveRequest;
import zw.co.mimosa.mymimosa.data.leave_data.Request;
import zw.co.mimosa.mymimosa.data.leave_data.Requester;
import zw.co.mimosa.mymimosa.data.leave_data.Template;
import zw.co.mimosa.mymimosa.data.leave_data.UdfDate324;
import zw.co.mimosa.mymimosa.data.leave_data.UdfDate35;
import zw.co.mimosa.mymimosa.data.leave_data.UdfDate36;
import zw.co.mimosa.mymimosa.data.leave_data.UdfFields;
import zw.co.mimosa.mymimosa.data.remote.APIService;
import zw.co.mimosa.mymimosa.data.remote.APIUtils;

public class LeaveActivity2 extends AppCompatActivity {
    TextInputEditText etStartDate, etEndDate, etDaysAcrued, etLeaveReason;
    TextInputLayout inputLayoutDaysAcrued, inputLayoutStartDate, inputLayoutEndDate, inputLayoutLeaveReason;
    RadioGroup radioModeOfPayment;
    RadioButton radioButtonModeOfPayment;
    public TextView tvResponse;
    APIService mAPIService;
    Button btnSubmit;
    ProgressBar pgBarSubmit;
    Spinner mSpinnerApproverHos;
    Spinner mSpinnerApproverHr;
    public LeaveRequest leaveRequest;
    private static final String TAG = "LeaveActivity2";
    LeaveFormHelper leaveFormHelper = LeaveFormHelper.getLeaveFormHelperInstance();
    long startDateLong, endDateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave2);

        inputLayoutDaysAcrued = findViewById(R.id.days_acrued);
        inputLayoutStartDate = findViewById(R.id.start_date);
        inputLayoutEndDate = findViewById(R.id.end_date);
        inputLayoutLeaveReason = findViewById(R.id.leave_reason);

        etDaysAcrued = findViewById(R.id.et_days_acrued);
        etLeaveReason = findViewById(R.id.et_leave_reason);
        etStartDate = findViewById(R.id.et_start_date);
        pgBarSubmit = findViewById(R.id.progressBarSubmit);
        etEndDate = findViewById(R.id.et_end_date);
        radioModeOfPayment = findViewById(R.id.radio_mode_of_payment);
        mSpinnerApproverHos = findViewById(R.id.spinner_approver_hos);
        mSpinnerApproverHr = findViewById(R.id.spinner_approver_hr);
        btnSubmit = findViewById(R.id.button_leave_submit);
        tvResponse = findViewById(R.id.tvResponse);

        etDaysAcrued.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        AndroidNetworking.initialize(getApplicationContext());

        if(leaveFormHelper.getDepartment().startsWith("HOD") | leaveFormHelper.getDepartment().startsWith("Head") | leaveFormHelper.getDepartment().startsWith("Senior") | leaveFormHelper.getDepartment().startsWith("EXMA") | leaveFormHelper.getDepartment().startsWith("MD") | leaveFormHelper.getDepartment().startsWith("Cluster")){
            ArrayAdapter<CharSequence> hosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hod_array, android.R.layout.simple_spinner_item);
            hosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerApproverHos.setAdapter(hosAdapter);
        }else {
            ArrayAdapter<CharSequence> hosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hos_array, android.R.layout.simple_spinner_item);
            hosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerApproverHos.setAdapter(hosAdapter);
        }

        ArrayAdapter<CharSequence> hrAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerApproverHr.setAdapter(hrAdapter);


        radioModeOfPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case R.id.radio_emergency:
                        System.out.println("Emergency");
                        if(leaveFormHelper.checkDepartment()) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LeaveActivity2.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View myView = inflater.inflate(R.layout.line_approvers, null);
                            dialogBuilder.setView(myView);
                            Spinner spinnerLineApprovers = (Spinner) myView.findViewById(R.id.spinner_line_approvers);
                            ArrayAdapter<CharSequence> lineApproverAdapter = ArrayAdapter.createFromResource(LeaveActivity2.this, R.array.line_approver_array, android.R.layout.simple_spinner_item);
                            lineApproverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerLineApprovers.setAdapter(lineApproverAdapter);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        dialogBuilder.show();
                        }
                        break;
                    case R.id.radio_mid_month:
                        System.out.println("mid-month");
                        if(leaveFormHelper.checkDepartment()) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LeaveActivity2.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View myView = inflater.inflate(R.layout.line_approvers, null);
                            dialogBuilder.setView(myView);
                            Spinner spinnerLineApprovers = (Spinner) myView.findViewById(R.id.spinner_line_approvers);
                            ArrayAdapter<CharSequence> lineApproverAdapter = ArrayAdapter.createFromResource(LeaveActivity2.this, R.array.line_approver_array, android.R.layout.simple_spinner_item);
                            lineApproverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerLineApprovers.setAdapter(lineApproverAdapter);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialogBuilder.show();
                        }
                        break;
                    case R.id.radio_sick:
                        System.out.println("month-end");
                        if(leaveFormHelper.checkDepartment()) {
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LeaveActivity2.this);
                            LayoutInflater inflater = getLayoutInflater();
                            final View myView = inflater.inflate(R.layout.line_approvers, null);
                            dialogBuilder.setView(myView);
                            Spinner spinnerLineApprovers = (Spinner) myView.findViewById(R.id.spinner_line_approvers);
                            ArrayAdapter<CharSequence> lineApproverAdapter = ArrayAdapter.createFromResource(LeaveActivity2.this, R.array.line_approver_array, android.R.layout.simple_spinner_item);
                            lineApproverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerLineApprovers.setAdapter(lineApproverAdapter);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    leaveFormHelper.setApproverLine(spinnerLineApprovers.getSelectedItem().toString().replaceAll(" ", ".") + "@mimosa.co.zw");
                                }
                            });
                            dialogBuilder.show();
                        }
                        break;
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateDaysAcrued() && validateStartDate() && validateEndDate() && validateLeaveReason() && validateModeOfPayment() && validateSpinnerApproverHos() && validateSpinnerApproverHr()) {
                int selectedId = radioModeOfPayment.getCheckedRadioButtonId();
                radioButtonModeOfPayment = findViewById(selectedId);
                LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                lfh.setApproverHos(mSpinnerApproverHos.getSelectedItem().toString());
                lfh.setApproverHr(mSpinnerApproverHr.getSelectedItem().toString());
                lfh.setModeOfPayment(radioButtonModeOfPayment.getText().toString());

                lfh.setApprovers();
                String startDate = etStartDate.getText().toString();
                String endDate = etEndDate.getText().toString();

                SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = startDateFormat.parse(startDate);
                    startDateLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = endDateFormat.parse(startDate);
                    endDateLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String template = "Leave and Temporary Earnings Form.";
                String requester = leaveFormHelper.getFirstname() + " " +leaveFormHelper.getSurname();
                String subject = "Leave and Temporary Earnings Form for " + leaveFormHelper.getFirstname() + " " + leaveFormHelper.getSurname()+ " (from android device)" ;
                String description = etLeaveReason.getText().toString();
                String udf_sline_29 = leaveFormHelper.getFirstname();
                String udf_sline_30 = leaveFormHelper.getSurname();
                String udf_sline_31 = leaveFormHelper.getEmployeeId();
                String udf_sline_37 = leaveFormHelper.getDepartment();
                String udf_pick_325 = "ict";
                String udf_pick_38 = leaveFormHelper.getSection();
                String udf_sline_32 = leaveFormHelper.getDesignation();
                //Long udf_date_324 = 100L;
                String udf_pick_344 = leaveFormHelper.getTypeOfLeave();
                System.out.println(udf_pick_344);
                Integer udf_long_345 = leaveFormHelper.getNumberOfDays();
                Integer udf_long_346 = Integer.valueOf(etDaysAcrued.getText().toString());
                //Long udf_date_35 = 300L;
                //Long udf_date_36 = 400L;
                String udf_pick_616 = radioButtonModeOfPayment.getText().toString();
                String udf_pick_2101 = leaveFormHelper.getApproverLine();
                String udf_pick_1202 = leaveFormHelper.getApproverHos();
                System.out.println(udf_pick_1202);
                String udf_pick_1201 = leaveFormHelper.getApproverHr();
                System.out.println(udf_pick_1201);
                String udf_sline_349 = leaveFormHelper.getApproverStage1();
                String udf_sline_350 = leaveFormHelper.getApproverStage2();
                String udf_sline_351 = leaveFormHelper.getApproverStage3();
                String udf_sline_628 = leaveFormHelper.getApproverStage4();
                String udf_sline_629 = leaveFormHelper.getApproverStage5();
                String udf_pick_620 = leaveFormHelper.getWhatAreYouApplyingFor();
//                String udf_pick_624 = "month-end";
                UdfDate35 udf_date_35 = new UdfDate35(startDateLong);
                UdfDate36 udf_date_36 = new UdfDate36(endDateLong);
                UdfDate324 udf_date_324 = new UdfDate324(1551462180000L);
                Template templateObj = new Template(template);
                Requester requesterObj = new Requester(requester);
                UdfFields udfFields = new UdfFields(udf_sline_29, udf_sline_30, udf_sline_31, udf_sline_37, udf_pick_325, udf_pick_38, udf_sline_32, udf_date_324, udf_pick_344, udf_long_345, udf_long_346, udf_date_35, udf_date_36, udf_pick_616, udf_pick_1202, udf_pick_1201, udf_sline_349, udf_sline_350, udf_sline_351, udf_sline_628, udf_sline_629, udf_pick_620);

                Request request = new Request(subject, description, requesterObj, templateObj, udfFields);
                leaveRequest = new LeaveRequest(request);


//                    sendLeaveDataPost(leaveRequest);
                new BooksQueryTask().execute();
                }
                }
        });

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog(v);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog(v);
            }
        });


    }

    public void sendLeaveDataPost(LeaveRequest leaveRequest) {

        mAPIService = APIUtils.getAPIService();

//        InputData inputData = new InputData(leaveRequest);

//        mAPIService.saveLeaveDataModel(leaveRequest).enqueue(new Callback<LeaveRequest>() {
//            @Override
//            public void onResponse(@NotNull Call<LeaveRequest> call, @NotNull Response<LeaveRequest> response) {
//                if (response.isSuccessful()) {
//                    showResponse(response.body().toString());
//                    Log.d("TAG", "post submitted to API." + response.body().toString());
//                }else{
//                    Log.d("TAG", "post submitted to API." + response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<LeaveRequest> call, Throwable t) {
//                Log.e("TAG", "Unable to submit post to API.");
//                t.printStackTrace();
//            }
//        });

        Call<LeaveRequest> call = mAPIService.saveLeaveDataModel(leaveRequest);

        call.enqueue(new Callback<LeaveRequest>() {
            @Override
            public void onResponse(@NotNull Call<LeaveRequest> call, @NotNull Response<LeaveRequest> response) {

                if (response.isSuccessful()){
                    //response is successful so response.body() won't be null
                    Log.d("TAG", "Into  successful.");
                    showResponse(response.body().toString());
                }else{
                    //response is not successful so response.body is null but response.errorBody is not null
                    String message = "Server Error!";
                    try {
                        String errorBody = response.errorBody().string();
                        Log.d("TAG", "Not posted." + errorBody);
//                        showResponse(response.body().toString());
                        //parse the errorBody string to json and retrieve whichever attribute you want
//                        Gson gson;
//                        JsonObject jsonObject = gson.fromJson(errorBody, JsonObject.class);
//                        if (jsonObject!=null && jsonObject.has("message") && !jsonObject.get("message").isJsonNull()) {
//                            message =  jsonObject.get("message").getAsString();
//                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(@NotNull Call<LeaveRequest> call, @NotNull Throwable t) {
                Log.e("TAG", "Unable to submit post to API.");
                t.printStackTrace();
            }

        });
    }

//                                                            void sendPostHttp(LeaveRequest leaveRequest){
//                                                                try {
//                                                                    URL url = HttpApiUtil.buildUrl();
//                                                                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
//                                                                    con.setRequestMethod("POST");
//
//                                                                    con.setRequestProperty("Content-Type", "application/json; utf-8");
//                                                                    con.setRequestProperty("Accept", "application/json");
//                                                                    con.setDoOutput(true);
//                                                                    Gson gson = new Gson();
//                                                                    String jsonInputString = gson.toJson(leaveRequest);
//
//                                                                    try(OutputStream os = con.getOutputStream()) {
//                                                                        byte[] input = jsonInputString.getBytes("utf-8");
//                                                                        os.write(input, 0, input.length);
//                                                                    }
//
//                                                                    try(BufferedReader br = new BufferedReader(
//                                                                            new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                                                                        StringBuilder response = new StringBuilder();
//                                                                        String responseLine = null;
//                                                                        while ((responseLine = br.readLine()) != null) {
//                                                                            response.append(responseLine.trim());
//                                                                        }
//                                                                        System.out.println(response.toString());
//                                                                    }
//
//                                                                }catch(Exception e){
//                                                                    e.printStackTrace();
//                                                                }
//                                                            }


    public void showResponse(String response) {
        tvResponse.setText(response);
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new StartDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new EndDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validateDaysAcrued() {
        String val = inputLayoutDaysAcrued.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDaysAcrued.setError("Days acrued can not be empty");
            return false;
        } else if (val.length() > 100) {
            inputLayoutDaysAcrued.setError("Days acrued is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutDaysAcrued.setError(null);
            inputLayoutDaysAcrued.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateStartDate() {
        String val = inputLayoutStartDate.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutStartDate.setError("Start can not be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutStartDate.setError("Start Date is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutStartDate.setError(null);
            inputLayoutStartDate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEndDate() {
        String val = inputLayoutEndDate.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutEndDate.setError("End can not be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutEndDate.setError("End is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutEndDate.setError(null);
            inputLayoutEndDate.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateLeaveReason() {
        String val = inputLayoutLeaveReason.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutLeaveReason.setError("Leave Reason cannot be empty");
            etLeaveReason.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etLeaveReason, InputMethodManager.SHOW_IMPLICIT);
            return false;
        } else if (val.length() > 20) {
            inputLayoutLeaveReason.setError("Leave reason is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutLeaveReason.setError(null);
            inputLayoutLeaveReason.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateModeOfPayment() {
        if (radioModeOfPayment.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select mode of payment", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateTypeOfLeave:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHos() {
        if (mSpinnerApproverHos.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select HOS", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (mSpinnerApproverHr.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select APPROVER HR", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHr:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }


    public class BooksQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(leaveRequest);


        @Override
        protected String doInBackground(URL... urls) {
            AndroidNetworking.post("https://servicedesk.mimosa.co.zw:8090/api/v3/requests")
                    .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                    .addBodyParameter("input_data",jsonStr)
                    .addBodyParameter("OPERATION_NAME","ADD_REQUEST")
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            System.out.println("response");
                            if(leaveFormHelper.isSickSelected) {
                                String status_code;
                                try {
                                    JSONObject jobj = response.getJSONObject("request");
                                    JSONObject response_jobj = response.getJSONObject("response_status");
                                    result = jobj.get("id").toString();
                                    String attachmentJsonString = "{\n" +
                                            "\t\"attachment\": {\n" +
                                            "\t\t\"request\": {\n" +
                                            "\t\t\t\"id\": \"" + result + "\"\n" +
                                            "\t\t}\n" +
                                            "\t}\n" +
                                            "}";
                                    status_code = response_jobj.get("status_code").toString();
                                    System.out.println("This is the id: " + status_code);

//                                    File file = new File("/sdcard/DCIM/Camera/attachment.jpg");
                                    File file = new File(leaveFormHelper.getFilepath());
                                    AndroidNetworking.upload("https://servicedesk.mimosa.co.zw:8090/api/v3/attachment")
                                            .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                                            .addMultipartFile("image", file)
                                            .addMultipartParameter("OPERATION_NAME", "ADD_ATTACHMENT")
                                            .addMultipartParameter("input_data", attachmentJsonString)
                                            .setPriority(Priority.HIGH)
                                            .build()
                                            .setUploadProgressListener(new UploadProgressListener() {
                                                @Override
                                                public void onProgress(long bytesUploaded, long totalBytes) {
                                                    // do anything with progress
                                                }
                                            })
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    System.out.println("Attachment for sick success" + response.toString());
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaveActivity2.this);
                                                    builder.setMessage("You have successfully applied for sick leave. Sick note submitted ")
                                                            .setTitle("Success")
                                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .setIcon(R.drawable.checkmark);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    System.out.println("Attachment not success " + error.toString());
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaveActivity2.this);
                                                    builder.setMessage("Attachment failed")
                                                            .setTitle("Attachment Failure")
                                                            .setPositiveButton(android.R.string.ok, null)
                                                            .setIcon(R.drawable.checkmark);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveActivity2.this);
                                builder.setMessage("You have successfully applied for leave")
                                        .setTitle("Success")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setIcon(R.drawable.checkmark);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }

                        @Override
                        public void onError(ANError error) {

                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());

                            String leaveJsonString = "leaveId_";
                            String currentDate = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(new Date());
                            System.out.println(currentDate);
                            leaveJsonString = leaveJsonString + currentDate;
                            saveJson(leaveJsonString);
                            if(leaveFormHelper.isSickSelected) {
                                saveAttachmentPath(leaveFormHelper.getFilepath(), leaveJsonString);
                            }
//                            if (!error.getErrorDetail().equals("connectionError")){
//                                pgBarSubmit.setVisibility(View.INVISIBLE);
//                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LeaveActivity2.this);
//                                dialogBuilder.setMessage("You have successfully applied for leave")
//                                        .setTitle("Submitted")
//                                        .setPositiveButton(android.R.string.ok, null)
//                                        .setIcon(R.drawable.checkmark);
//                                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
//                                        startActivity(intent);
//                                    }
//                                });
//                                dialogBuilder.show();
//                            }
//                            else {                        }
                                pgBarSubmit.setVisibility(View.INVISIBLE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LeaveActivity2.this);
//                                dialogBuilder.setMessage("Your application was not sent because of bad network, the system will resend when network is detected. No need to redo the request.")
                                dialogBuilder.setMessage("Your application was not sent because of bad network. Please retry.")
                                        .setTitle("Not Submitted")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(R.drawable.cancel);
                                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
//                                        startActivity(intent);
                                    }
                                });
                                dialogBuilder.show();
//                            }

                        }
                    });

            return result;
        }

        private void saveJson(String jsonFileName){
            try {
//                File mainDir = LeaveActivity2.this.getDir("MainFolder", Context.MODE_PRIVATE);
                File fileMain = new File(LeaveActivity2.this.getFilesDir(), String.valueOf(Context.MODE_PRIVATE));
                boolean isDirMade = fileMain.mkdir();
                System.out.println("Directory made: " + isDirMade );
                File file = new File(fileMain, jsonFileName + ".json");
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonStr);
                bufferedWriter.close();
                System.out.println("json file saved success in: " + file.getPath());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        private void saveAttachmentPath(String attachmentPath, String attachmentName){
            try {
                File file = new File(LeaveActivity2.this.getFilesDir(), attachmentName + "_attachment.txt");
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(attachmentPath);
                bufferedWriter.close();
                System.out.println("Attachment file saved success in: " + file.getPath());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            TextView tvResponse = (TextView) findViewById(R.id.tvResponse);

            if (result == null) {
//                tvResponse.setText("Null response");
            }
            else {
                tvResponse.setText(result);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgBarSubmit.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}


