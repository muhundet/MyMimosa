package zw.co.mimosa.mymimosa.ui.medical_services.covid_screening;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.Pickers.DateOfBirthPicker;
import zw.co.mimosa.mymimosa.Pickers.ReturnDatePicker;
import zw.co.mimosa.mymimosa.Pickers.TravelDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_business_data.CovidBusinessRequest;
import zw.co.mimosa.mymimosa.data.covid_return_data.CovidReturnRequest;

public class CovidScreeningBusiness extends AppCompatActivity {
    TextInputLayout inputLayoutDateOfBirth, inputLayoutIdNumber, inputLayoutCellNumber, inputLayoutNextOfKin, inputLayoutAddress,
            inputLayoutTravelDate, inputLayoutReturnDate, inputLayoutDesination, inputLayoutRegNumber, inputLayoutEstimatedDistance, inputLayoutReason;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection;
    TextInputEditText etDateOfBirth, etIdNumber, etCellNumber, etNextOfKin, etAddress, etTravelDate, etReturnDate, etDestination,
            etRegNumber, etEstimated_Distance, etReason;
    RadioGroup radioGroupGender, radioGroupVehicleType;
    RadioButton radioButtonGender, radioButtonVehicleType;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    CovidReturnScreeningHelper cbus = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
    long DateOfBirthLong, travelDateLong, returnDateLong;
    CovidBusinessRequest covidBusinessRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_screening_business);

        etFirstName = findViewById(R.id.et_cbus_first_name);
        etSurname = findViewById(R.id.et_cbus_surname);
        etMineNumber = findViewById(R.id.et_cbus_mine_number);
        etDepartment = findViewById(R.id.et_cbus_department);
        etDesignation = findViewById(R.id.et_cbus_designation);

        inputLayoutDateOfBirth = findViewById(R.id.cbus_date_of_birth);
        inputLayoutIdNumber = findViewById(R.id.cbus_id_number);
        inputLayoutCellNumber = findViewById(R.id.cbus_cell_number);
        inputLayoutNextOfKin = findViewById(R.id.cbus_next_of_kin);
        inputLayoutAddress = findViewById(R.id.cbus_address);
        inputLayoutTravelDate = findViewById(R.id.cbus_travel_date);
        inputLayoutReturnDate = findViewById(R.id.cbus_return_date);
        inputLayoutDesination = findViewById(R.id.cbus_destination);
        inputLayoutRegNumber = findViewById(R.id.cbus_vehicle_reg);
        inputLayoutEstimatedDistance = findViewById(R.id.cbus_distance);
        inputLayoutReason = findViewById(R.id.cbus_reason);

        etDateOfBirth = findViewById(R.id.et_creturn_date_of_birth);
        etIdNumber = findViewById(R.id.et_cbus_id_number);
        etCellNumber = findViewById(R.id.et_cbus_cell_number);
        etNextOfKin = findViewById(R.id.et_cbus_next_of_kin);
        etAddress = findViewById(R.id.et_cbus_address);
        etTravelDate = findViewById(R.id.et_creturn_travel_date);
        etReturnDate = findViewById(R.id.et_creturn_return_date);
        etDestination = findViewById(R.id.et_cbus_destination);
        etRegNumber = findViewById(R.id.et_cbus_vehicle_reg);
        etEstimated_Distance = findViewById(R.id.et_cbus_distance);
        etReason = findViewById(R.id.et_cbus_reason);


        radioGroupGender = findViewById(R.id.radio_cbus_gender);
        radioGroupVehicleType = findViewById(R.id.radio_cbus_vehicle_type);

        spinnerSection = findViewById(R.id.spinner_cbus_section);

        pgBarSubmit = findViewById(R.id.progress_bar_cbus_submit);

        btnSubmit = findViewById(R.id.button_cbus_submit);

        etFirstName.setText(cbus.getFirstname());
        etSurname.setText(cbus.getSurname());
        etMineNumber.setText(cbus.getEmployeeId());
        etDepartment.setText(cbus.getDepartment());
        etDesignation.setText(cbus.getDesignation());

        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOfBirthPickerDialog(v);
            }
        });

        etTravelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelDatePickerDialog(v);
            }
        });

        etReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnDatePickerDialog(v);
            }
        });

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDOB() && validateIdNumber() && validateCellNumber() && validateNextOfKin() && validateAddress() && validateRadioGender() && validateSpinnerSection()
                && validateRegNumber() && validateDistance() && validateReason() && validateRadioVehicleType()){
                    String dob = etDateOfBirth.getText().toString();
                    SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = dateOfBirthFormat.parse(dob);
                        DateOfBirthLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String travelDate = etTravelDate.getText().toString();
                    SimpleDateFormat travelDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = travelDateFormat.parse(travelDate);
                        travelDateLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String returnDate = etReturnDate.getText().toString();
                    SimpleDateFormat returnDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = returnDateFormat.parse(returnDate);
                        returnDateLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    int selectedId = radioGroupGender.getCheckedRadioButtonId();
                    radioButtonGender = findViewById(selectedId);
                    int selectedTransport = radioGroupVehicleType.getCheckedRadioButtonId();
                    radioButtonVehicleType = findViewById(selectedTransport);

                    cbus.setGender(radioButtonGender.getText().toString());
                    cbus.setSection(spinnerSection.getSelectedItem().toString());
                    cbus.setDateOfBirth(DateOfBirthLong);
                    cbus.setIdNumber(etIdNumber.getText().toString());
                    cbus.setCellNumber(etCellNumber.getText().toString());
                    cbus.setNextOfKin(etNextOfKin.getText().toString());
                    cbus.setAddress(etAddress.getText().toString());

                    String template = "Request To Travel and Covid-19 Screening";
                    String requester = cbus.getFirstname() + " " + cbus.getSurname();
                    String subject = "COVID-19 SCREENING FORM for " + cbus.getFirstname() + " " + cbus.getSurname() + " (from android device)";
                    String description = etReason.getText().toString();;
                    String firstname = cbus.getFirstname();
                    String surname = cbus.getSurname();
                    String employeeId = cbus.employeeId;
                    String department = "ICT";
                    String section = cbus.getSection();
                    String emailId = cbus.getEmailId();
                    String designation = cbus.getDesignation();
                    long dateOfEngagement;
                    long dateOfBirth = cbus.getDateOfBirth();
                    String gender = cbus.getGender();
                    String companyName = "Mimosa Mining Company"; //creturn.getCompanyName();
                    String idNumber = cbus.getIdNumber();
                    String cellNumber = cbus.getCellNumber();
                    String nextOfKin = cbus.getNextOfKin();
                    String address = cbus.getAddress();
                    String requestType = radioButtonVehicleType.getText().toString();
                    long travelDateLong1 = travelDateLong;
                    long returnDateLong1 = returnDateLong;
                    String destination = etDestination.getText().toString();
                    String regNumber = etRegNumber.getText().toString();
                    String estimatedDistance = etEstimated_Distance.getText().toString();
                    long distanceLong = Long.parseLong(estimatedDistance);

                    zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate686 udfDate686 = new zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate686(dateOfBirth);
                    zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate4509 udfDate4509 = new zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate4509(travelDateLong1);
                    zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate4510 udfDate4510 = new zw.co.mimosa.mymimosa.data.covid_business_data.UdfDate4510(returnDateLong1);
                    zw.co.mimosa.mymimosa.data.covid_business_data.Template templateObj = new zw.co.mimosa.mymimosa.data.covid_business_data.Template(template);
                    zw.co.mimosa.mymimosa.data.covid_business_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.covid_business_data.Requester(requester);
                    zw.co.mimosa.mymimosa.data.covid_business_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.covid_business_data.UdfFields(firstname, surname, employeeId, department, department, section, udfDate686, gender, idNumber, cellNumber, nextOfKin, address, designation, requestType, destination, udfDate4509, udfDate4510,  companyName, "approverHOD", "approverGM", "Business Travel", regNumber, distanceLong);
                    zw.co.mimosa.mymimosa.data.covid_business_data.Request request = new zw.co.mimosa.mymimosa.data.covid_business_data.Request(description, requesterObj, subject, templateObj, udfFields);

                    covidBusinessRequest = new CovidBusinessRequest(request);

                    new CovidScreeningQueryTask().execute();
                }
            }
        });
    }

    public class CovidScreeningQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(covidBusinessRequest);


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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidScreeningBusiness.this);
                            dialogBuilder.setMessage(" Screening Details Submitted Successfully")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(CovidScreeningBusiness.this, MainActivity.class);
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
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidScreeningBusiness.this);
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

    public void showDateOfBirthPickerDialog(View v) {
        DialogFragment newFragment = new DateOfBirthPicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTravelDatePickerDialog(View v) {
        DialogFragment newFragment = new TravelDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showReturnDatePickerDialog(View v) {
        DialogFragment newFragment = new ReturnDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validateDOB() {
        String val = inputLayoutDateOfBirth.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDateOfBirth.setError("D.O.B cannot be empty");
            etDateOfBirth.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etDateOfBirth, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutDateOfBirth.setError(null);
            inputLayoutDateOfBirth.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateIdNumber() {
        String val = inputLayoutIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutIdNumber.setError("ID/Passport cannot be empty");
            etIdNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etIdNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutIdNumber.setError(null);
            inputLayoutIdNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCellNumber() {
        String val = inputLayoutCellNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutCellNumber.setError("Cell number cannot be empty");
            etCellNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etCellNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutCellNumber.setError(null);
            inputLayoutCellNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNextOfKin() {
        String val = inputLayoutNextOfKin.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutNextOfKin.setError("Days Acted(in figures) cannot be empty");
            etNextOfKin.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etNextOfKin, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutNextOfKin.setError(null);
            inputLayoutNextOfKin.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAddress() {
        String val = inputLayoutAddress.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAddress.setError("Address cannot be empty");
            etAddress.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etAddress, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAddress.setError(null);
            inputLayoutAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRegNumber() {
        String val = inputLayoutRegNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAddress.setError("Reg number cannot be empty");
            etRegNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etRegNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAddress.setError(null);
            inputLayoutAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDistance() {
        String val = inputLayoutEstimatedDistance.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutEstimatedDistance.setError("Reg number cannot be empty");
            etEstimated_Distance.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etEstimated_Distance, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAddress.setError(null);
            inputLayoutAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateReason() {
        String val = inputLayoutReason.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutReason.setError("Reg number cannot be empty");
            etReason.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etReason, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAddress.setError(null);
            inputLayoutAddress.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerSection.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRadioGender() {
        if (radioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateGender:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRadioVehicleType() {
        if (radioGroupVehicleType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Vehicle Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateGVehicleType:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }
}