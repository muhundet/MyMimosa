package zw.co.mimosa.mymimosa.ui.hr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import zw.co.mimosa.mymimosa.Pickers.DateOfLastAdvancePicker;
import zw.co.mimosa.mymimosa.Pickers.DateRepaidPicker;
import zw.co.mimosa.mymimosa.Pickers.EndDatePicker;
import zw.co.mimosa.mymimosa.Pickers.StartDatePicker;
import zw.co.mimosa.mymimosa.R;

public class AdvanceActivity extends AppCompatActivity {

    Button btnAdvanceSubmit;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etSection, etDesignation,etAdvanceAmount,
    etDateOfLastAdvance, etDateRepaid, etAdvanceReason;

    TextInputLayout inputLayoutFirstName, inputLayoutSurname, inputLayoutMineNumber, inputLayoutDepartment, inputLayoutSection,
            inputLayoutDesignation,inputLayoutAdvanceAmount,
            inputLayoutDateOfLastAdvance, inputLayoutDateRepaid, inputLayoutAdvanceReason;
    Spinner spinnerDeductions, spinnerApproverHos, spinnerApproverHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        etFirstName = findViewById(R.id.et_advance_first_name);
        etSurname = findViewById(R.id.et_advance_last_name);
        etMineNumber = findViewById(R.id.et_advance_mine_number);
        etDepartment = findViewById(R.id.et_advance_department);
        etSection = findViewById(R.id.et_advance_section);
        etDesignation = findViewById(R.id.et_advance_designation);
        etAdvanceAmount = findViewById(R.id.et_advance_amount);
        etDateOfLastAdvance = findViewById(R.id.et_advance_date_of_last_advance);
        etDateRepaid = findViewById(R.id.et_advance_date_repaid);
        etAdvanceReason = findViewById(R.id.et_advance_reason);
        btnAdvanceSubmit = findViewById(R.id.button_advance_submit);

        inputLayoutFirstName = findViewById(R.id.advance_firstname);
        inputLayoutSurname = findViewById(R.id.advance_surname);
        inputLayoutMineNumber = findViewById(R.id.advance_mine_number);
        inputLayoutDepartment = findViewById(R.id.advance_department);
        inputLayoutSection = findViewById(R.id.advance_section);
        inputLayoutDesignation = findViewById(R.id.advance_designation);
        inputLayoutAdvanceAmount = findViewById(R.id.advance_amount);
        inputLayoutDateOfLastAdvance = findViewById(R.id.advance_date_of_last_advance);
        inputLayoutDateRepaid = findViewById(R.id.advance_date_repaid);
        inputLayoutAdvanceReason = findViewById(R.id.advance_reason);

        spinnerDeductions = findViewById(R.id.spinner_deductions);
        spinnerApproverHos = findViewById(R.id.spinner_advance_approver_hos);
        spinnerApproverHr = findViewById(R.id.spinner_advance_approver_hr);

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
                if (!validateAdvanceAmount() | !validateDateOfLastAdvance() | !validateDateRepaid() | !validateAdvanceReason() | !validateSpinnerDeductions() | !validateSpinnerApproverHos() | ! validateSpinnerApproverHr()) {
                    Toast.makeText(AdvanceActivity.this, "Bho pa advance validation", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateAdvanceAmount() {
        String val = inputLayoutAdvanceAmount.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAdvanceAmount.setError("Days acrued can not be empty");
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
            inputLayoutDateOfLastAdvance.setError("Days acrued can not be empty");
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
            inputLayoutDateRepaid.setError("Days acrued can not be empty");
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
            inputLayoutAdvanceReason.setError("Days acrued can not be empty");
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

    private boolean validateSpinnerDeductions() {
        if (spinnerDeductions.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select deduction criteria", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSpinnerApproverHos() {
        if (spinnerApproverHos.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Approver HOS", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (spinnerApproverHr.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Approver HR", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHOS:Nothing selected ");
            return true;
        } else {
            return false;
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

}