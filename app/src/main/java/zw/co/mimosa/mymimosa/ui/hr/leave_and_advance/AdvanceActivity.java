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
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.Pickers.DateOfLastAdvancePicker;
import zw.co.mimosa.mymimosa.Pickers.DateRepaidPicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.advance_data.AdvanceRequest;
import zw.co.mimosa.mymimosa.data.advance_data.UdfDate622;
import zw.co.mimosa.mymimosa.data.advance_data.UdfDate623;

public class AdvanceActivity extends AppCompatActivity {

    Button btnAdvanceSubmit;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etSection, etDesignation,etAdvanceAmount,
    etDateOfLastAdvance, etDateRepaid, etAdvanceReason;

    TextInputLayout inputLayoutFirstName, inputLayoutSurname, inputLayoutMineNumber, inputLayoutDepartment, inputLayoutSection,
            inputLayoutDesignation,inputLayoutAdvanceAmount,
            inputLayoutDateOfLastAdvance, inputLayoutDateRepaid, inputLayoutAdvanceReason;
    Spinner spinnerSection, spinnerDeductions, spinnerApproverHos, spinnerApproverHr;

    ProgressBar pgBarSubmit;

    LeaveFormHelper leaveFormHelper = LeaveFormHelper.getLeaveFormHelperInstance();
    long lastAdvanceDateLong, repaidDateLong;
    AdvanceRequest advanceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        etFirstName = findViewById(R.id.et_advance_first_name);
        etSurname = findViewById(R.id.et_advance_last_name);
        etMineNumber = findViewById(R.id.et_advance_mine_number);
        etDepartment = findViewById(R.id.et_advance_department);
        etDesignation = findViewById(R.id.et_advance_designation);
        etAdvanceAmount = findViewById(R.id.et_advance_amount);
        etDateOfLastAdvance = findViewById(R.id.et_advance_date_of_last_advance);
        etDateRepaid = findViewById(R.id.et_advance_date_repaid);
        etAdvanceReason = findViewById(R.id.et_advance_reason);
        btnAdvanceSubmit = findViewById(R.id.button_advance_submit);

        inputLayoutFirstName = findViewById(R.id.ea_firstname);
        inputLayoutSurname = findViewById(R.id.ea_surname);
        inputLayoutMineNumber = findViewById(R.id.ea_mine_number);
        inputLayoutDepartment = findViewById(R.id.ea_department);
        inputLayoutDesignation = findViewById(R.id.ea_designation);
        inputLayoutAdvanceAmount = findViewById(R.id.advance_amount);
        inputLayoutDateOfLastAdvance = findViewById(R.id.advance_date_of_last_advance);
        inputLayoutDateRepaid = findViewById(R.id.advance_date_repaid);
        inputLayoutAdvanceReason = findViewById(R.id.advance_reason);

        spinnerSection = findViewById(R.id.spinner_advance_section);
        spinnerDeductions = findViewById(R.id.spinner_deductions);
        spinnerApproverHos = findViewById(R.id.spinner_advance_approver_hos);
        spinnerApproverHr = findViewById(R.id.spinner_advance_approver_hr);

        pgBarSubmit = findViewById(R.id.progress_bar_advance_submit);

        etAdvanceAmount.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        ArrayAdapter<CharSequence> deductionsAdapter = ArrayAdapter.createFromResource(this, R.array.deductions_criteria_array, android.R.layout.simple_spinner_item);
        deductionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDeductions.setAdapter(deductionsAdapter);

        ArrayAdapter<CharSequence> hosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hos_array, android.R.layout.simple_spinner_item);
        hosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApproverHos.setAdapter(hosAdapter);

        ArrayAdapter<CharSequence> hrAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApproverHr.setAdapter(hrAdapter);

        Intent intent = getIntent();
        String department = intent.getStringExtra("DEPARTMENTID");
        String empid =intent.getStringExtra("EMPID");
        String jobtitle =intent.getStringExtra("JOBTITLE");
        String emailid =intent.getStringExtra("EMAILID");
        String firstname =intent.getStringExtra("FIRSTNAME");
        String lastname =intent.getStringExtra("LASTNAME");

        etFirstName.setText(firstname);
        etSurname.setText(lastname);
        etMineNumber.setText(empid);
        etDepartment.setText(department);
        etDesignation.setText(jobtitle);

        etDateOfLastAdvance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOfLastAdvancePickerDialog(v);
            }
        });
        etDateRepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRepaidPickerDialog(v);
            }
        });
        btnAdvanceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateAdvanceAmount() && validateDateOfLastAdvance() && validateDateRepaid() && validateAdvanceReason() && validateSpinnerSection() && validateSpinnerDeductions() && validateSpinnerApproverHos() && validateSpinnerApproverHr()) {

                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setSection(spinnerSection.getSelectedItem().toString());
                    lfh.setNumberOfMonths(Integer.valueOf(spinnerDeductions.getSelectedItem().toString()));
                    lfh.setApproverHos(spinnerApproverHos.getSelectedItem().toString());
                    lfh.setApproverHr(spinnerApproverHr.getSelectedItem().toString());
                    lfh.setAmount(Integer.valueOf(etAdvanceAmount.getText().toString()));
                    lfh.setAdvanceApprovers();
                    String lastAdvanceDate = etDateOfLastAdvance.getText().toString();
                    String repaidDate = etDateRepaid.getText().toString();

                    SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = startDateFormat.parse(lastAdvanceDate);
                        lastAdvanceDateLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = endDateFormat.parse(repaidDate);
                        repaidDateLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    String template = "Leave and Temporary Earnings Form.";
                    String requester = leaveFormHelper.getFirstname() + " " +leaveFormHelper.getSurname();
                    String subject = "Leave and Temporary Earnings Form for " + leaveFormHelper.getFirstname() + " " + leaveFormHelper.getSurname()+ " (from android device)" ;
                    String description = etAdvanceReason.getText().toString();
                    String udf_sline_29 = leaveFormHelper.getFirstname();
                    String udf_sline_30 = leaveFormHelper.getSurname();
                    String udf_sline_31 = leaveFormHelper.getEmployeeId();
                    String udf_sline_37 = leaveFormHelper.getDepartment();
                    String udf_pick_325 = "ict";
                    String udf_pick_38 = leaveFormHelper.getSection();
                    String udf_sline_32 = leaveFormHelper.getDesignation();
                    int udf_decimal_621 = leaveFormHelper.getAmount();
                    String udf_pick_2101 = " ";
                    String udf_pick_1202 = leaveFormHelper.getApproverHos();
                    String udf_pick_1201 = leaveFormHelper.getApproverHr();
                    String udf_sline_349 = leaveFormHelper.getApproverStage1();
                    String udf_sline_350 = leaveFormHelper.getApproverStage2();
                    String udf_sline_351 = leaveFormHelper.getApproverStage3();
                    String udf_sline_628 = leaveFormHelper.getApproverStage4();
                    String udf_sline_629 = leaveFormHelper.getApproverStage5();
                    String udf_pick_620 = leaveFormHelper.getWhatAreYouApplyingFor();
                    int udf_long_625 = leaveFormHelper.getNumberOfMonths();
                    UdfDate622 udf_date_622 = new UdfDate622(lastAdvanceDateLong);
                    UdfDate623 udf_date_623 = new UdfDate623(repaidDateLong);
                    zw.co.mimosa.mymimosa.data.advance_data.UdfDate324 udf_date_324 = new zw.co.mimosa.mymimosa.data.advance_data.UdfDate324(1551462180000L);
                    zw.co.mimosa.mymimosa.data.advance_data.Template templateObj = new zw.co.mimosa.mymimosa.data.advance_data.Template(template);
                    zw.co.mimosa.mymimosa.data.advance_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.advance_data.Requester(requester);
                    zw.co.mimosa.mymimosa.data.advance_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.advance_data.UdfFields(udf_sline_29, udf_sline_30, udf_sline_31, udf_sline_37, udf_pick_325, udf_pick_38, udf_sline_32, udf_decimal_621, udf_date_324, udf_date_622, udf_date_623,udf_pick_1202, udf_pick_1201, udf_sline_349, udf_sline_350, udf_sline_351, udf_sline_628, udf_sline_629, udf_pick_620, udf_long_625);
                    zw.co.mimosa.mymimosa.data.advance_data.Request request = new zw.co.mimosa.mymimosa.data.advance_data.Request(subject, description, requesterObj, templateObj, udfFields);
                    advanceRequest = new AdvanceRequest(request);

                    new AdvanceQueryTask().execute();
                }
            }
        });

    }

    private boolean validateAdvanceAmount() {
        String val = inputLayoutAdvanceAmount.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAdvanceAmount.setError("Days acrued cannot be empty");
            return false;
        } else if (val.length() > 100) {
            inputLayoutAdvanceAmount.setError("Days acrued is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutAdvanceAmount.setError(null);
            inputLayoutAdvanceAmount.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDateOfLastAdvance() {
        String val = inputLayoutDateOfLastAdvance.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDateOfLastAdvance.setError("Days acrued cannot be empty");
            return false;
        } else if (val.length() > 100) {
            inputLayoutDateOfLastAdvance.setError("Days acrued is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutDateOfLastAdvance.setError(null);
            inputLayoutDateOfLastAdvance.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDateRepaid() {
        String val = inputLayoutDateRepaid.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDateRepaid.setError("Days acrued cannot be empty");
            return false;
        } else if (val.length() > 100) {
            inputLayoutDateRepaid.setError("Days acrued is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutDateRepaid.setError(null);
            inputLayoutDateRepaid.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAdvanceReason() {
        String val = inputLayoutAdvanceReason.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAdvanceReason.setError("Reason for advance cannot be empty");
            return false;
        } else if (val.length() > 100) {
            inputLayoutAdvanceReason.setError("Days acrued is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutAdvanceReason.setError(null);
            inputLayoutAdvanceReason.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerDeductions.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select your section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerDeductions() {
        if (spinnerDeductions.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select deduction criteria", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHos() {
        if (spinnerApproverHos.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Approver HOS", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (spinnerApproverHr.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Approver HR", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    public void showDateOfLastAdvancePickerDialog(View v) {
        DialogFragment newFragment = new DateOfLastAdvancePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDateRepaidPickerDialog(View v) {
        DialogFragment newFragment = new DateRepaidPicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public class AdvanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(advanceRequest);

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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AdvanceActivity.this);
                            dialogBuilder.setMessage("You have successfully applied for advance")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AdvanceActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            dialogBuilder.show();

                        }

                        @Override
                        public void onError(ANError error) {
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());
                            String advanceJsonString = "advanceid_";
                            String currentDate = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(new Date());
                            System.out.println(currentDate);
                            advanceJsonString = advanceJsonString + currentDate + ".json";
                            saveJson(advanceJsonString);
                            pgBarSubmit.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AdvanceActivity.this);
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
                        }
                    });

            return result;
        }

        private void saveJson(String jsonFileName){
            try {
                File fileMain = new File(AdvanceActivity.this.getFilesDir(), String.valueOf(Context.MODE_PRIVATE));
                boolean isDirMade = fileMain.mkdir();
                System.out.println("Directory made: " + isDirMade );
                File file = new File(fileMain, jsonFileName);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonStr);
                bufferedWriter.close();
                System.out.println("json file saved success in: " + file.getPath());
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