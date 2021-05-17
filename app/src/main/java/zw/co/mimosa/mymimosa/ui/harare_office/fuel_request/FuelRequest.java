package zw.co.mimosa.mymimosa.ui.harare_office.fuel_request;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.fuel_request_data.FuelRequestRequest;
import zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.PettyCashAuthorisationMineRequest;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationMine;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreeningContractor;

public class FuelRequest extends AppCompatActivity {
    TextInputLayout inputLayoutVehicleReg, inputLayoutReason, inputLayoutQuantity, inputLayoutAccount;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    TextInputEditText etVehicleReg, etReasonForApplication, etQuantity, etAccount;
    RadioGroup radioQuantityRequested;
    RadioButton radioButtonType;
    Spinner spinnerRequestType, spinnerGrade;
    Button btnSubmit;
    ProgressBar pgBarSubmit;
    FuelRequestHelper fr = FuelRequestHelper.getRequestInstance();
    FuelRequestRequest fuelRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_request);

        inputLayoutVehicleReg = findViewById(R.id.fr_vehicle_reg);
        inputLayoutReason = findViewById(R.id.fr_reason_for_application);
        inputLayoutQuantity = findViewById(R.id.fr_quantity);
        inputLayoutAccount = findViewById(R.id.fr_account);

        etFirstName = findViewById(R.id.et_fr_first_name);
        etSurname = findViewById(R.id.et_fr_surname);
        etMineNumber = findViewById(R.id.et_fr_mine_number);
        etDepartment = findViewById(R.id.et_fr_department);
        etDesignation = findViewById(R.id.et_fr_designation);

        etFirstName.setText(fr.getFirstname());
        etSurname.setText(fr.getSurname());
        etMineNumber.setText(fr.getEmployeeId());
        etDepartment.setText(fr.getDepartment());
        etDesignation.setText(fr.getDesignation());

        etVehicleReg = findViewById(R.id.et_fr_vehicle_reg);
        etReasonForApplication = findViewById(R.id.et_fr_reason_for_application);
        etQuantity = findViewById(R.id.et_fr_quantity);
        etAccount = findViewById(R.id.et_fr_account);

        radioQuantityRequested = findViewById(R.id.radio_fr_type);

        spinnerRequestType = findViewById(R.id.spinner_fr_fuel_request_type);
        spinnerGrade = findViewById(R.id.spinner_fr_grade);

        pgBarSubmit = findViewById(R.id.progress_bar_fr_submit);
        btnSubmit = findViewById(R.id.button_fr_submit);

        ArrayAdapter<CharSequence> requestTypeAdapter = ArrayAdapter.createFromResource(this, R.array.fuel_request_type, android.R.layout.simple_spinner_item);
        requestTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestType.setAdapter(requestTypeAdapter);

        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this, R.array.current_grade_array, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradeAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateVehicleReg() && validateReasonForApplication() && validateQuantity() && validateAccount() && validateRadioFuelType() && validateSpinnerGrade() && validateSpinnerRequestType()){
                    int selectedId = radioQuantityRequested.getCheckedRadioButtonId();
                    radioButtonType = findViewById(selectedId);
                    fr.setFuelRequestApprovers();

                    String template = "Mine Fuel Request Form";
                    String requester = fr.getFirstname() + " " + fr.getSurname();
                    String subject = "Fuel Request  for " + fr.getFirstname() + " " + fr.getSurname() + " (from android device)";
                    String description = etReasonForApplication.getText().toString();
                    String firstname = fr.getFirstname();
                    String surname = fr.getSurname();
                    String employeeId = fr.getEmployeeId();
                    String department = fr.getDepartment();
                    String grade = spinnerGrade.getSelectedItem().toString();
                    String requestType = spinnerRequestType.getSelectedItem().toString();
                    String vehicleReg = etVehicleReg.getText().toString();
                    String Account = etAccount.getText().toString();
                    String Reason = etReasonForApplication.getText().toString();
                    String quantity = etQuantity.getText().toString();
                    String fuelType = radioButtonType.getText().toString();

                    zw.co.mimosa.mymimosa.data.fuel_request_data.Template templateObj = new zw.co.mimosa.mymimosa.data.fuel_request_data.Template(template);
                    zw.co.mimosa.mymimosa.data.fuel_request_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.fuel_request_data.Requester(requester);
                    zw.co.mimosa.mymimosa.data.fuel_request_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.fuel_request_data.UdfFields(firstname, surname, employeeId, department, grade, vehicleReg, quantity, requestType, fuelType);
                    zw.co.mimosa.mymimosa.data.fuel_request_data.Request request = new zw.co.mimosa.mymimosa.data.fuel_request_data.Request(description, requesterObj, subject, templateObj, udfFields);

                    fuelRequest = new FuelRequestRequest(request);

                    new FuelRequestQueryTask().execute();
                }
            }
        });

    }
    public class FuelRequestQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(fuelRequest);


        @Override
        protected String doInBackground(URL... urls) {
            System.out.println(jsonStr);
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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FuelRequest.this);
                            dialogBuilder.setMessage(" Screening Details Submitted Successfully")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(FuelRequest.this, MainActivity.class);
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
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FuelRequest.this);
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

    private boolean validateVehicleReg() {
        String val = inputLayoutVehicleReg.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutVehicleReg.setError("Reason for advance cannot be empty");
            etVehicleReg.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etVehicleReg, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutVehicleReg.setError(null);
            inputLayoutVehicleReg.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateReasonForApplication() {
        String val = inputLayoutReason.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutReason.setError("Reason for advance cannot be empty");
            etReasonForApplication.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etReasonForApplication, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutReason.setError(null);
            inputLayoutReason.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateQuantity() {
        String val = inputLayoutQuantity.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutQuantity.setError("Reason for advance cannot be empty");
            etQuantity.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etQuantity, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutQuantity.setError(null);
            inputLayoutQuantity.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAccount() {
        String val = inputLayoutQuantity.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutQuantity.setError("Reason for advance cannot be empty");
            etQuantity.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etQuantity, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutQuantity.setError(null);
            inputLayoutQuantity.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRadioFuelType() {
        if (radioQuantityRequested.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Fuel Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateFuelType:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerGrade() {
        if (spinnerGrade.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Your Grade", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerGrade:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerRequestType() {
        if (spinnerRequestType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select RequestType", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }


}