package zw.co.mimosa.mymimosa.ui.medical_services.covid_screening;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.Pickers.DateOfBirthPicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_return_data.CovidReturnRequest;

public class CovidScreeningContractor extends AppCompatActivity {

    TextInputLayout inputLayoutDateOfBirth, inputLayoutIdNumber, inputLayoutCellNumber, inputLayoutNextOfKin, inputLayoutAddress, inputLayoutAccomodation;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation, etAccomodation;
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
        setContentView(R.layout.activity_covid_screening_contractor);

        etFirstName = findViewById(R.id.et_covidcont_first_name);
        etSurname = findViewById(R.id.et_covidcont_surname);
        etMineNumber = findViewById(R.id.et_covidcont_mine_number);
        etDepartment = findViewById(R.id.et_covidcont_department);
        etDesignation = findViewById(R.id.et_covidcont_designation);

        inputLayoutDateOfBirth = findViewById(R.id.covidcont_date_of_birth);
        inputLayoutIdNumber = findViewById(R.id.covidcont_id_number);
        inputLayoutCellNumber = findViewById(R.id.covidcont_cell_number);
        inputLayoutNextOfKin = findViewById(R.id.covidcont_next_of_kin);
        inputLayoutAddress = findViewById(R.id.covidcont_address);
        inputLayoutAccomodation = findViewById(R.id.covidcont_accomodation);

        etDateOfBirth = findViewById(R.id.et_covidcont_date_of_birth);
        etIdNumber = findViewById(R.id.et_covidcont_id_number);
        etCellNumber = findViewById(R.id.et_covidcont_cell_number);
        etNextOfKin = findViewById(R.id.et_covidcont_next_of_kin);
        etAddress = findViewById(R.id.et_covidcont_address);

        radioGroupGender = findViewById(R.id.radio_covidcont_gender);

        spinnerSection = findViewById(R.id.spinner_covidcont_section);

        pgBarSubmit = findViewById(R.id.progress_bar_covidcont_submit);

        btnSubmit = findViewById(R.id.button_covidcont_submit);

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
                if(validateDOB() && validateIdNumber() && validateCellNumber() && validateNextOfKin() && validateAddress() && validateRadioGender() && validateSpinnerSection()) {
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
                    String department = "ICT";
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
                    String requestType = "Business Travel";
                }
            }
        });
    }

    public void showDateOfBirthPickerDialog(View v) {
        DialogFragment newFragment = new DateOfBirthPicker();
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
            inputLayoutNextOfKin.setError("Next of Kin cannot be empty");
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

    private boolean validateAccomodation() {
        String val = inputLayoutAccomodation.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAddress.setError("Accomodation cannot be empty");
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