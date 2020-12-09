package zw.co.mimosa.mymimosa.ui.hr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zw.co.mimosa.mymimosa.Pickers.EndDatePicker;
import zw.co.mimosa.mymimosa.Pickers.StartDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.InputData;
import zw.co.mimosa.mymimosa.data.LeaveRequest;
import zw.co.mimosa.mymimosa.data.Request;
import zw.co.mimosa.mymimosa.data.Requester;
import zw.co.mimosa.mymimosa.data.Template;
import zw.co.mimosa.mymimosa.data.UdfDate324;
import zw.co.mimosa.mymimosa.data.UdfDate35;
import zw.co.mimosa.mymimosa.data.UdfDate36;
import zw.co.mimosa.mymimosa.data.UdfFields;
import zw.co.mimosa.mymimosa.data.remote.APIService;
import zw.co.mimosa.mymimosa.data.remote.APIUtils;

public class LeaveActivity2 extends AppCompatActivity {
    TextInputEditText etStartDate, etEndDate, etDaysAcrued, etLeaveReason;
    TextInputLayout inputLayoutDaysAcrued, inputLayoutStartDate, inputLayoutEndDate, inputLayoutLeaveReason;
    RadioGroup radioModeOfPayment;
    TextView tvResponse;
    APIService mAPIService;
    Button btnSubmit;
    Spinner mSpinnerApproverHos;
    Spinner mSpinnerApproverHr;

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

        etEndDate = findViewById(R.id.et_end_date);
        radioModeOfPayment = findViewById(R.id.radio_mode_of_payment);
        mSpinnerApproverHos = findViewById(R.id.spinner_approver_hos);
        mSpinnerApproverHr = findViewById(R.id.spinner_approver_hr);
        btnSubmit = findViewById(R.id.button_leave_submit);
        tvResponse = findViewById(R.id.tvResponse);


        ArrayAdapter<CharSequence> hosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hos_array, android.R.layout.simple_spinner_item);
        hosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerApproverHos.setAdapter(hosAdapter);

        ArrayAdapter<CharSequence> hrAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerApproverHr.setAdapter(hrAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String template = "Leave and Temporary Earnings Form.";
                String requester = "Rodwell Mazambara";
                String subject = "Leave Request";
                String description = "Leave and Temporary earnings";
                String udf_sline_29 = "firstname";
                String udf_sline_30 = "surname";
                String udf_sline_31 = "employeeId";
                String udf_sline_37 = "department";
                String udf_pick_325 = "ict";
                String udf_pick_38 = "ict";
                String udf_sline_32 = "designation";
                //Long udf_date_324 = 100L;
                String udf_pick_344 = "sick";
                Integer udf_long_345 = 7;
                Integer udf_long_346 = 30;
                //Long udf_date_35 = 300L;
                //Long udf_date_36 = 400L;
                String udf_pick_616 = "emergency";
                String udf_pick_1202 = "Brighton Daka";
                String udf_pick_1201 = "George Mawere";
                String udf_sline_349 = "Approver Stage 1";
                String udf_sline_351 = "Approver Stage 3";
                String udf_sline_628 = "Approver Stage 4";
                String udf_sline_629 = "Approver Stage 5";
                String udf_pick_620 = "leave";
                String udf_pick_624 = "month-end";
                UdfDate35 udf_date_35 = new UdfDate35(1551462180000L);
                UdfDate36 udf_date_36 = new UdfDate36(1551462180000L);
                UdfDate324 udf_date_324 = new UdfDate324(1551462180000L);
                Template templateObj = new Template(template);
                Requester requesterObj = new Requester(requester);
                UdfFields udfFields = new UdfFields(udf_sline_29, udf_sline_30, udf_sline_31, udf_sline_37, udf_pick_325, udf_pick_38, udf_sline_32, udf_date_324, udf_pick_344, udf_long_345, udf_long_346, udf_date_35, udf_date_36, udf_pick_616, udf_pick_1202, udf_pick_1201, udf_sline_349, udf_sline_351, udf_sline_628, udf_sline_629, udf_pick_620, udf_pick_624);

                Request request = new Request(subject, description, requesterObj, templateObj, udfFields);

               // if (!validateDaysAcrued() | !validateStartDate() | !validateEndDate() | !validateLeaveReason() | !validateModeOfPayment() | !validateSpinnerApproverHos() | ! validateSpinnerApproverHr()) {
                    sendLeaveDataPost(request);
                //}
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

    public void sendLeaveDataPost(Request request) {
        LeaveRequest leaveRequest = new LeaveRequest(request);
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
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (mSpinnerApproverHr.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select APPROVER HR", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHr:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }
}