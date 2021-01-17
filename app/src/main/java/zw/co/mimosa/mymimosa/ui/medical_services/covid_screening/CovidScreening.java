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
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_return_data.CovidReturnRequest;

public class CovidScreening extends AppCompatActivity {
    TextInputLayout inputLayoutDateOfBirth, inputLayoutIdNumber, inputLayoutCellNumber, inputLayoutNextOfKin, inputLayoutAddress;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection;
    TextInputEditText etDateOfBirth, etIdNumber, etCellNumber, etNextOfKin, etAddress;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGender;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    CovidReturnScreeningHelper creturn = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
    long DateOfBirthLong;
    CovidReturnRequest covidReturnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_screeening);

        etFirstName = findViewById(R.id.et_creturn_first_name);
        etSurname = findViewById(R.id.et_creturn_surname);
        etMineNumber = findViewById(R.id.et_creturn_mine_number);
        etDepartment = findViewById(R.id.et_creturn_department);
        etDesignation = findViewById(R.id.et_creturn_designation);

        inputLayoutDateOfBirth = findViewById(R.id.creturn_date_of_birth);
        inputLayoutIdNumber = findViewById(R.id.creturn_id_number);
        inputLayoutCellNumber = findViewById(R.id.creturn_cell_number);
        inputLayoutNextOfKin = findViewById(R.id.creturn_next_of_kin);
        inputLayoutAddress = findViewById(R.id.creturn_address);

        etDateOfBirth = findViewById(R.id.et_creturn_date_of_birth);
        etIdNumber = findViewById(R.id.et_creturn_id_number);
        etCellNumber = findViewById(R.id.et_creturn_cell_number);
        etNextOfKin = findViewById(R.id.et_creturn_next_of_kin);
        etAddress = findViewById(R.id.et_creturn_address);

        radioGroupGender = findViewById(R.id.radio_creturn_gender);

        spinnerSection = findViewById(R.id.spinner_creturn_section);

        pgBarSubmit = findViewById(R.id.progress_bar_covid_submit);

        btnSubmit = findViewById(R.id.button_creturn_submit);

        etFirstName.setText(creturn.getFirstname());
        etSurname.setText(creturn.getSurname());
        etMineNumber.setText(creturn.getEmployeeId());
        etDepartment.setText(creturn.getDepartment());
        etDesignation.setText(creturn.getDesignation());

        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateOfBirthPickerDialog(v);
            }
        });

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDOB() && validateIdNumber() && validateCellNumber() && validateNextOfKin() && validateAddress() && validateRadioGender() && validateSpinnerSection()){
                String dob = etDateOfBirth.getText().toString();
                SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = dateOfBirthFormat.parse(dob);
                    DateOfBirthLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender = findViewById(selectedId);
                creturn.setGender(radioButtonGender.getText().toString());
                creturn.setSection(spinnerSection.getSelectedItem().toString());
                creturn.setDateOfBirth(DateOfBirthLong);
                creturn.setIdNumber(etIdNumber.getText().toString());
                creturn.setCellNumber(etCellNumber.getText().toString());
                creturn.setNextOfKin(etNextOfKin.getText().toString());
                creturn.setAddress(etAddress.getText().toString());

                String template = creturn.getTemplate();
                String requester = creturn.getFirstname() + " " + creturn.getSurname();
                String subject = "COVID-19 SCREENING FORM for " + creturn.getFirstname() + " " + creturn.getSurname() + " (from android device)";
                String description = creturn.getDescription();
                String firstname = creturn.getFirstname();
                String surname = creturn.getSurname();
                String employeeId = creturn.employeeId;
                String department = creturn.getDepartment();
                String section = creturn.getSection();
                String emailId = creturn.getEmailId();
                String designation = creturn.getDesignation();
                long dateOfEngagement;
                long dateOfBirth = creturn.getDateOfBirth();
                String gender = creturn.getGender();
                String companyName = "Mimosa Mining Company"; //creturn.getCompanyName();
                String idNumber = creturn.getIdNumber();
                String cellNumber = creturn.getCellNumber();
                String nextOfKin = creturn.getNextOfKin();
                String address = creturn.getAddress();

                zw.co.mimosa.mymimosa.data.covid_return_data.UdfDate686 udfDate686 = new zw.co.mimosa.mymimosa.data.covid_return_data.UdfDate686(dateOfBirth);
                zw.co.mimosa.mymimosa.data.covid_return_data.Template templateObj = new zw.co.mimosa.mymimosa.data.covid_return_data.Template(template);
                zw.co.mimosa.mymimosa.data.covid_return_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.covid_return_data.Requester(requester);
                zw.co.mimosa.mymimosa.data.covid_return_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.covid_return_data.UdfFields(udfDate686, firstname, surname, designation, gender, companyName, idNumber, cellNumber, nextOfKin, address);
                zw.co.mimosa.mymimosa.data.covid_return_data.Request request = new zw.co.mimosa.mymimosa.data.covid_return_data.Request(description, requesterObj, subject, templateObj, udfFields);

                covidReturnRequest = new CovidReturnRequest(request);

                new AdvanceQueryTask().execute();
            }
        }
        });
    }

    public void showDateOfBirthPickerDialog(View v) {
        DialogFragment newFragment = new DateOfBirthPicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public class AdvanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(covidReturnRequest);


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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidScreening.this);
                            dialogBuilder.setMessage("You have successfully applied for advance")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(CovidScreening.this, MainActivity.class);
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
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidScreening.this);
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
}