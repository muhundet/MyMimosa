package zw.co.mimosa.mymimosa.ui.medical_services.covid_return;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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
import zw.co.mimosa.mymimosa.Pickers.EffectiveDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.ActingAllowanceRequest;
import zw.co.mimosa.mymimosa.data.covid_return_data.CovidReturnRequest;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity2;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceHelper;

public class CovidReturnToWork extends AppCompatActivity {
    TextInputLayout inputLayoutDateOfBirth, inputLayoutIdNumber, inputLayoutCellNumber, inputLayoutNextOfKin, inputLayoutAddress;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection;
    TextInputEditText etDateOfBirth, etIdNumber, etCellNumber, etNextOfKin, etAddress;
    RadioGroup radioGroupgender;
    RadioButton radioButtonGender;
    Button btnSubmit;
    CovidReturnToWorkHelper creturn = CovidReturnToWorkHelper.getCovidReturnToWorkHelperInstance();
    long DateOfBirthLong;
    CovidReturnRequest covidReturnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_return_to_work);

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

        radioGroupgender = findViewById(R.id.radio_creturn_gender);

        spinnerSection = findViewById(R.id.spinner_creturn_section);

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
                String dob = etDateOfBirth.getText().toString();
                SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = dateOfBirthFormat.parse(dob);
                    DateOfBirthLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int selectedId = radioGroupgender.getCheckedRadioButtonId();
                radioButtonGender = findViewById(selectedId);
                creturn.setGender(radioButtonGender.getText().toString());
                creturn.setSection(spinnerSection.getSelectedItem().toString());
                creturn.setDateOfBirth(DateOfBirthLong);
                creturn.setIdNumber(etIdNumber.getText().toString());
                creturn.setCellNumber(etCellNumber.getText().toString());
                creturn.setNextOfKin(etNextOfKin.getText().toString());
                creturn.setAddress(etAddress.getText().toString());

                String template = "COVID-19 SCREENING FORM - Routine";
                String requester = creturn.getFirstname() + " " + creturn.getSurname();
                String subject = "COVID-19 SCREENING FORM for " + creturn.getFirstname() + " " + creturn.getSurname() + " (from android device)";
                String description = "COVID-19 SCREENING FORM- Return To Work Description";
                String firstname = creturn.getFirstname();
                String surname = creturn.getSurname();
                String employeeId = creturn.employeeId;
                String department = creturn.getDepartment();
                String section = creturn.getSection();
                String emailId = creturn.getEmailId();
                String designation = creturn.getDesignation();
                long dateOfEngagement;
                long dateOfBirth = creturn.getDateOfBirth();
                String gender= creturn.getGender();
                String companyName= "Mimosa Mining Company"; //creturn.getCompanyName();
                String idNumber= creturn.getIdNumber();
                String cellNumber= creturn.getCellNumber();
                String nextOfKin= creturn.getNextOfKin();
                String address= creturn.getAddress();

                zw.co.mimosa.mymimosa.data.covid_return_data.UdfDate686 udfDate686 = new zw.co.mimosa.mymimosa.data.covid_return_data.UdfDate686(dateOfBirth);
                zw.co.mimosa.mymimosa.data.covid_return_data.Template templateObj = new zw.co.mimosa.mymimosa.data.covid_return_data.Template(template);
                zw.co.mimosa.mymimosa.data.covid_return_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.covid_return_data.Requester(requester);
                zw.co.mimosa.mymimosa.data.covid_return_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.covid_return_data.UdfFields(udfDate686, firstname, surname, designation, gender, companyName, idNumber, cellNumber, nextOfKin, address);
                zw.co.mimosa.mymimosa.data.covid_return_data.Request request = new zw.co.mimosa.mymimosa.data.covid_return_data.Request(description,  requesterObj, subject, templateObj, udfFields);

                covidReturnRequest = new CovidReturnRequest(request);

                new AdvanceQueryTask().execute();
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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidReturnToWork.this);
                            dialogBuilder.setMessage("You have successfully applied for advance")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(CovidReturnToWork.this, MainActivity.class);
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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CovidReturnToWork.this);
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

        }
    }
}