package zw.co.mimosa.mymimosa.ui.hr.acting_allowance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.net.URL;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.ActingAllowanceRequest;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate324;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate35;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate36;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate606;
import zw.co.mimosa.mymimosa.data.advance_data.AdvanceRequest;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.AdvanceActivity;
import zw.co.mimosa.mymimosa.utilities.NumberToWordsConverter;

public class ActingAllowanceActivity2 extends AppCompatActivity {

    TextInputLayout inputLayoutDaysActedFigures, inputLayoutDaysActedWords, inputLayoutOvertime15Times, inputLayoutOvertime20Times,
            inputLayoutNightShiftAllowance, inputLayoutStandbyWeeks, inputLayoutUndergroundAllowance;
    TextInputEditText etDaysActedFigures, etDaysActedWords, etOvertime15Times, etOvertime20Times,
            etNightShiftAllowance, etStandbyWeeks, etUndergroundAllowance;
    Spinner spinnerApproverHr,spinnerApproverHos;
    Button btnSubmit;
    ProgressBar pgBarSubmit;
    ActingAllowanceHelper actingAllowanceHelper = ActingAllowanceHelper.getActingAllowanceInstance();
    ActingAllowanceRequest actingAllowanceRequest;

    String descriptionForActing;
    long overtime15Times = 0L, overtime20Times = 0L, nightShiftAllowance = 0L, standbyWeeks = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acting_allowance2);

        inputLayoutDaysActedFigures = findViewById(R.id.aa_days_acted_figures);
        inputLayoutDaysActedWords = findViewById(R.id.aa_days_acted_words);
        inputLayoutOvertime15Times = findViewById(R.id.aa_overtime_15_times);
        inputLayoutOvertime20Times = findViewById(R.id.aa_overtime_20_times);
        inputLayoutNightShiftAllowance = findViewById(R.id.aa_night_shift_allowance);
        inputLayoutUndergroundAllowance = findViewById(R.id.aa_underground_allowance);
        inputLayoutStandbyWeeks = findViewById(R.id.aa_standby_weeks);

        etDaysActedFigures = findViewById(R.id.et_aa_days_acted_figures);
        etDaysActedWords = findViewById(R.id.et_aa_days_acted_words);
        etOvertime15Times = findViewById(R.id.et_aa_overtime_15_times);
        etOvertime20Times = findViewById(R.id.et_aa_overtime_20_times);
        etNightShiftAllowance = findViewById(R.id.et_aa_night_shift_allowance);
        etUndergroundAllowance = findViewById(R.id.et_aa_underground_allowance);
        etStandbyWeeks = findViewById(R.id.et_aa_standby_weeks);

        spinnerApproverHos = findViewById(R.id.spinner_aa_approver_hos);
        spinnerApproverHr = findViewById(R.id.spinner_aa_approver_hr);

        pgBarSubmit = findViewById(R.id.progress_bar_aa_submit);

        btnSubmit = findViewById(R.id.button_aa_submit);

        etDaysActedFigures.requestFocus();
        InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        AndroidNetworking.initialize(getApplicationContext());

        ArrayAdapter<CharSequence> approverHosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hos_array, android.R.layout.simple_spinner_item);
        approverHosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApproverHos.setAdapter(approverHosAdapter);

        ArrayAdapter<CharSequence> approverHrAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        approverHrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApproverHr.setAdapter(approverHrAdapter);

        etDaysActedFigures.addTextChangedListener(new TextWatcher() {
            String  ntwc;
            int figures = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etDaysActedFigures.getText().toString().isEmpty()){
                    ntwc = "Zero";
                    figures = 0;
                }else {
                    figures = Integer.parseInt(etDaysActedFigures.getText().toString());
                    ntwc =   NumberToWordsConverter.convert(figures);
                }
                etDaysActedWords.setText(ntwc + " days acted");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDaysActedFigures() && validateDaysActedwords() && validateSpinnerApproverHos() && validateSpinnerApproverHr()){
                    validateOvertime15Times();
                    validateOvertime20Times();
                    validateNightShiftAllowance();
                    validateUndergroundAllowance();
                    validateStandbyWeeks();

                AlertDialog.Builder builder = new AlertDialog.Builder(ActingAllowanceActivity2.this);
                builder.setTitle("Reason for Acting");
                final EditText input = new EditText(ActingAllowanceActivity2.this);
                input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                input.setSingleLine(false);
                input.requestFocus();
                input.setLines(5);
                input.setMaxLines(5);
                input.setGravity(Gravity.LEFT | Gravity.TOP);
                builder.setView(input);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        descriptionForActing = input.getText().toString().trim();
                        if (!descriptionForActing.isEmpty()) {
                            actingAllowanceHelper.setNumberOfDaysActedFigures(Long.parseLong(etDaysActedFigures.getText().toString()));
                            actingAllowanceHelper.setNumberOfDaysActedWords(etDaysActedWords.getText().toString());
                            actingAllowanceHelper.setOvertime15Times(overtime15Times);
                            actingAllowanceHelper.setOvertime20Times(overtime20Times);
                            actingAllowanceHelper.setNighShiftAllowance(nightShiftAllowance);
                            actingAllowanceHelper.setStandbyWeeks(standbyWeeks);
                            actingAllowanceHelper.setApprover(spinnerApproverHos.getSelectedItem().toString());
                            actingAllowanceHelper.setApproverHr(spinnerApproverHr.getSelectedItem().toString());

                            actingAllowanceHelper.setActingAllowanceApprovers();

                            String template = "Acting Allowance Form";
                            String requester = actingAllowanceHelper.getFirstname() + " " + actingAllowanceHelper.getSurname();
                            String subject = "Acting Allowance for " + actingAllowanceHelper.getFirstname() + " " + actingAllowanceHelper.getSurname() + " (from android device)";
                            String description = descriptionForActing;
                            String firstname = actingAllowanceHelper.getFirstname();
                            String surname = actingAllowanceHelper.getSurname();
                            String employeeId = actingAllowanceHelper.employeeId;
                            String department = actingAllowanceHelper.getDepartment();
                            String section = actingAllowanceHelper.getSection();
                            String emailId = actingAllowanceHelper.getEmailId();
                            String designation = actingAllowanceHelper.getDesignation();
                            long dateOfEngagement;
                            String approver = actingAllowanceHelper.getApprover();
                            String actingPosition = actingAllowanceHelper.getActingPosition();
                            String actingGrade = actingAllowanceHelper.getActingGrade();
                            String currentGrade = actingAllowanceHelper.getCurrentGrade();
                            long startDate = actingAllowanceHelper.getStartDate();
                            long endDate = actingAllowanceHelper.getEndDate();
                            long effectiveDate = actingAllowanceHelper.getEffectiveDate();
                            String numberOfDaysActedWords = actingAllowanceHelper.getNumberOfDaysActedWords();
                            long numberOfDaysActedFigures = actingAllowanceHelper.getNumberOfDaysActedFigures();
                            long overtime15Times = actingAllowanceHelper.getOvertime15Times();
                            long overtime20Times = actingAllowanceHelper.getOvertime20Times();
                            long nighShiftAllowance = actingAllowanceHelper.getNighShiftAllowance();
                            long standbyWeeks = actingAllowanceHelper.getStandbyWeeks();
                            String approverHr = actingAllowanceHelper.getApproverHr();
                            String approverStage1 = actingAllowanceHelper.getApproverStage1();
                            String approverStage2 = actingAllowanceHelper.getApproverStage2();
                            String approverStage3 = actingAllowanceHelper.getApproverStage3();
                            String approverStage4 = actingAllowanceHelper.getApproverStage4();

                            zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate324 udfDate324 = new zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate324(1551462180000L);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate35 udfDate35 = new zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate35(startDate);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate36 udfDate36 = new zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate36(endDate);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate606 udfDate606 = new zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfDate606(effectiveDate);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.Template templateObj = new zw.co.mimosa.mymimosa.data.acting_allowance_data.Template(template);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.acting_allowance_data.Requester(requester);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.acting_allowance_data.UdfFields(udfDate324, udfDate606, udfDate35, udfDate36, approver, department, section, firstname, surname, employeeId, designation, actingPosition, actingGrade, currentGrade, numberOfDaysActedWords, overtime15Times, overtime20Times, nighShiftAllowance, numberOfDaysActedFigures, standbyWeeks, approverHr, approverStage1, approverStage2, approverStage3, approverStage4);
                            zw.co.mimosa.mymimosa.data.acting_allowance_data.Request request = new zw.co.mimosa.mymimosa.data.acting_allowance_data.Request(description, requesterObj, subject, templateObj, udfFields);

                            actingAllowanceRequest = new ActingAllowanceRequest(request);

                            new AdvanceQueryTask().execute();
                        } else {
                            Toast.makeText(ActingAllowanceActivity2.this, "Please Select Enter Reason for Acting", Toast.LENGTH_SHORT).show();
                            input.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });

                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                input.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        ((AlertDialog) alert).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        ((AlertDialog) alert).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    }
                });

                alert.show();


            }
        }
        });

    }

    private boolean validateDaysActedFigures() {
        String val = inputLayoutDaysActedFigures.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDaysActedFigures.setError("Days Acted(in figures) cannot be empty");
            etDaysActedFigures.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etDaysActedFigures, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutDaysActedFigures.setError(null);
            inputLayoutDaysActedFigures.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDaysActedwords() {
        String val = inputLayoutDaysActedWords.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDaysActedWords.setError("Days Acted(in words) cannot be empty");
            etDaysActedWords.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etDaysActedWords, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutDaysActedWords.setError(null);
            inputLayoutDaysActedWords.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateReasonForActing() {
        String val = inputLayoutDaysActedWords.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDaysActedWords.setError("Days Acted(in words) cannot be empty");
            etDaysActedWords.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etDaysActedWords, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutDaysActedWords.setError(null);
            inputLayoutDaysActedWords.setErrorEnabled(false);
            return true;
        }
    }

    private void validateOvertime15Times() {
        String val = inputLayoutOvertime15Times.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (!val.isEmpty()) {
            overtime15Times = Long.parseLong(etOvertime15Times.getText().toString());
        }
    }

    private void validateOvertime20Times() {
        String val = inputLayoutOvertime20Times.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (!val.isEmpty()) {
            overtime15Times = Long.parseLong(etOvertime20Times.getText().toString());
        }
    }

    private void validateNightShiftAllowance() {
        String val = inputLayoutNightShiftAllowance.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (!val.isEmpty()) {
            overtime15Times = Long.parseLong(etNightShiftAllowance.getText().toString());
        }
    }

    private void validateUndergroundAllowance() {
        String val = inputLayoutUndergroundAllowance.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (!val.isEmpty()) {
            overtime15Times = Long.parseLong(etUndergroundAllowance.getText().toString());
        }
    }

    private void validateStandbyWeeks() {
        String val = inputLayoutStandbyWeeks.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (!val.isEmpty()) {
            overtime15Times = Long.parseLong(etStandbyWeeks.getText().toString());
        }
    }

    private boolean validateSpinnerApproverHos() {
        if (spinnerApproverHos.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select HOS", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (spinnerApproverHr.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select HR Approver", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerApproverHr:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }



    public class AdvanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(actingAllowanceRequest);


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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActingAllowanceActivity2.this);
                            dialogBuilder.setMessage("You have successfully applied for advance")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ActingAllowanceActivity2.this, MainActivity.class);
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

                            pgBarSubmit.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ActingAllowanceActivity2.this);
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