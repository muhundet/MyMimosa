package zw.co.mimosa.mymimosa.ui.transport.buss_pass_application;

import androidx.appcompat.app.AppCompatActivity;

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

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.PettyCashAuthorisationMineRequest;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;

public class BusPassApplication extends AppCompatActivity {
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;

    TextInputLayout inputLayoutIdNumber, inputLayoutCompanyName, inputLayoutApplyForName,
            inputLayoutApplyForIdNumber, inputLayoutApplyForRelationship, inputLayoutApplyForSchool;
    Spinner spinnerApplyForLevel, spinnerHrApprover;

    TextInputEditText etIdNumber, etCompanyName, etApplyForName, etApplyforIdNumber, etApplyForRelationship, etApplyForSchool;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    RadioGroup radioGroupType, radioGroupApplicant;
    RadioButton radioButtonType, radioButtonApplicant;

    BusPassApplicationHelper bpa = BusPassApplicationHelper.getBusPassApplicationInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pass_application);
        etFirstName = findViewById(R.id.et_pcam_first_name);
        etSurname = findViewById(R.id.et_pcam_surname);
        etMineNumber = findViewById(R.id.et_pcam_mine_number);
        etDepartment = findViewById(R.id.et_pcam_department);
        etDesignation = findViewById(R.id.et_pcam_designation);

        inputLayoutIdNumber = findViewById(R.id.bpa_id_number);
        inputLayoutCompanyName = findViewById(R.id.bpa_company_name);
        inputLayoutApplyForName = findViewById(R.id.bpa_applyfor_name);
        inputLayoutApplyForIdNumber = findViewById(R.id.bpa_applyfor_id_dob);
        inputLayoutApplyForRelationship = findViewById(R.id.bpa_applyfor_relationship);
        inputLayoutApplyForSchool = findViewById(R.id.bpa_applyfor_school);

        etIdNumber = findViewById(R.id.et_bpa_id_number);
        etCompanyName = findViewById(R.id.et_bpa_company_name);
        etApplyforIdNumber = findViewById(R.id.et_bpa_applyfor_id_dob);
        etApplyForName = findViewById(R.id.et_bpa_applyfor_name);
        etApplyForRelationship = findViewById(R.id.et_bpa_applyfor_relationship);
        etApplyForSchool = findViewById(R.id.et_bpa_applyfor_school);

        spinnerApplyForLevel = findViewById(R.id.spinner_bpa_level);
        spinnerHrApprover = findViewById(R.id.spinner_bpa_hr_approver);

        radioGroupType = findViewById(R.id.radio_group_bpa_type);
        radioGroupApplicant = findViewById(R.id.radio_group_bpa_applicant);

        pgBarSubmit = findViewById(R.id.progress_bar_bpa_submit);

        btnSubmit = findViewById(R.id.button_bpa_submit);

        ArrayAdapter<CharSequence> bpaTypeAdapter = ArrayAdapter.createFromResource(this, R.array.child_level_array, android.R.layout.simple_spinner_item);
        bpaTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApplyForLevel.setAdapter(bpaTypeAdapter);

        ArrayAdapter<CharSequence> hrApproverAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrApproverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHrApprover.setAdapter(hrApproverAdapter);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateIdNumber() && validateCompanyName() && validateApplyForName()
                        && validateApplyForIdNumber() && validateApplyForRelationship()
                        && validateApplyForSchool() && validateRadioApplicant()
                        &&  validateRadioType() && validateSpinnerLevel() && validateSpinnerHrApproval()){

//                    int selectedId = radioGroupType.getCheckedRadioButtonId();
//                    radioButtonType = findViewById(selectedId);
//                    int selectedId1 = radioGroupApplicant.getCheckedRadioButtonId();
//                    radioButtonApplicant = findViewById(selectedId1);
//
//                    pcam.setCurrency(radioButtonCurrency.getText().toString());
//                    pcam.setAmountInFigures(Long.parseLong(etAmountInFigures.getText().toString()));
//                    pcam.setAmountInWords(etAmountInWords.getText().toString());
//                    pcam.setCostCentre(spinnerCostCentre.getSelectedItem().toString());
//                    pcam.setManagementAccountingApprover(spinnerManagementAccountingApprover.getSelectedItem().toString());
//
//                    pcam.setPettyCashAuthorisationApprovers();
//
//                    String template = "Petty Cash Authorisation Form - Mine";
//                    String requester = pcam.getFirstname() + " " + pcam.getSurname();
//                    String subject = "Petty Cash Authorisation Form  for " + pcam.getFirstname() + " " + pcam.getSurname() + " (from android device)";


                }

            }
        });


    }


    private boolean validateIdNumber () {
        String val = inputLayoutIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutIdNumber.setError("Id Number cannot be empty");
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

    private boolean validateCompanyName () {
        String val = inputLayoutIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutCompanyName.setError("Company name cannot be empty");
            etCompanyName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etCompanyName, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutCompanyName.setError(null);
            inputLayoutCompanyName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForName() {
        String val = inputLayoutApplyForName.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForName.setError("Id number cannot be empty");
            etApplyForName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForName, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForName.setError(null);
            inputLayoutApplyForName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForIdNumber() {
        String val = inputLayoutApplyForIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForIdNumber.setError("Id number cannot be empty");
            etApplyforIdNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyforIdNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForIdNumber.setError(null);
            inputLayoutApplyForIdNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForRelationship() {
        String val = inputLayoutApplyForRelationship.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForRelationship.setError("Id number cannot be empty");
            etApplyForRelationship.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForRelationship, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForRelationship.setError(null);
            inputLayoutApplyForRelationship.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForSchool() {
        String val = inputLayoutApplyForSchool.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForSchool.setError("Id number cannot be empty");
            etApplyForSchool.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForSchool, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForSchool.setError(null);
            inputLayoutApplyForSchool.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRadioType() {
        if (radioGroupType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Pass Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateType:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRadioApplicant() {
        if (radioGroupApplicant.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Applicant type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateApplicant:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerLevel() {
        if (spinnerApplyForLevel.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Level of child", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerLevel:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerHrApproval() {
        if (spinnerHrApprover.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Hr Approver", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHrApprover:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }



}