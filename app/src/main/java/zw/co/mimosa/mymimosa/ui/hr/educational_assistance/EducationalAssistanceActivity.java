package zw.co.mimosa.mymimosa.ui.hr.educational_assistance;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.Pickers.ChildDobPicker;
import zw.co.mimosa.mymimosa.R;

public class EducationalAssistanceActivity extends AppCompatActivity {
    TextInputLayout inputLayoutChildFirstName, inputLayoutChildSurname, inputLayoutSchoolName, inputLayoutChildDob;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerChildLevel, spinnerSection;
    TextInputEditText etChildFirstName, etChildSurname, etChildSchoolName, etChildDob;
    Button btnNext;
    EducationalAssistanceHelper eah = EducationalAssistanceHelper.getEducationalAssistanceInstance();
    long childDobLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_assistance);

        etFirstName = findViewById(R.id.et_ea_first_name);
        etSurname = findViewById(R.id.et_ea_surname);
        etMineNumber = findViewById(R.id.et_ea_mine_number);
        etDepartment = findViewById(R.id.et_ea_department);
        etDesignation = findViewById(R.id.et_ea_designation);

        inputLayoutChildFirstName = findViewById(R.id.ea_child_name);
        inputLayoutChildSurname = findViewById(R.id.ea_child_surname);
        inputLayoutSchoolName = findViewById(R.id.ea_child_school_name);
        inputLayoutChildDob = findViewById(R.id.ea_child_dob);

        etChildFirstName = findViewById(R.id.et_ea_child_name);
        etChildSurname = findViewById(R.id.et_ea_child_surname);
        etChildSchoolName = findViewById(R.id.et_ea_child_school_name);
        etChildDob = findViewById(R.id.et_ea_child_dob);

        btnNext = findViewById(R.id.button_ea_next);

        spinnerSection = findViewById(R.id.spinner_ea_section);
        spinnerChildLevel = findViewById(R.id.spinner_child_level);
        etFirstName.setText(eah.getFirstname());
        etSurname.setText(eah.getSurname());
        etMineNumber.setText(eah.getEmployeeId());
        etDepartment.setText(eah.getDepartment());
        etDesignation.setText(eah.getDesignation());

        ArrayAdapter<CharSequence> childLevelAdapter = ArrayAdapter.createFromResource(this, R.array.child_level_array, android.R.layout.simple_spinner_item);
        childLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChildLevel.setAdapter(childLevelAdapter);

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        childLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        etChildDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChildDobPickerDialog(v);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( validateChildFirstName() | validateChildSurname() | validateChildDob() | validateChildSchool() | !validateSpinnerApproverHr()) {
                    String childDob = etChildDob.getText().toString();
                    SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                    try {
                        Date d = startDateFormat.parse(childDob);
                        childDobLong = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    eah.setSection(spinnerSection.getSelectedItem().toString());
                    eah.setChildFirstName(etChildFirstName.getText().toString());
                    eah.setChildSurname(etChildSurname.getText().toString());
                    eah.setChildSchoolName(etChildSchoolName.getText().toString());
                    eah.setChildDob(childDobLong);
                    eah.setChildLevel(spinnerChildLevel.getSelectedItem().toString());
                    Intent intent = new Intent(EducationalAssistanceActivity.this, EducationalAssistanceActivity2.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void showChildDobPickerDialog(View v) {
        DialogFragment newFragment = new ChildDobPicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validateChildFirstName() {
        String val = inputLayoutChildFirstName.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutChildFirstName.setError("Child's first name cannot be empty");
            return false;
        }
//        else if (val.length() > 20) {
//            inputLayoutChildFirstName.setError("Child's first name is too large!");
//            return false;
//        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutChildFirstName.setError(null);
            inputLayoutChildFirstName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateChildSurname() {
        String val = inputLayoutChildSurname.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutChildSurname.setError("Child's surname cannot be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutChildSurname.setError("Child's surname is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutChildSurname.setError(null);
            inputLayoutChildSurname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateChildSchool() {
        String val = inputLayoutSchoolName.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutSchoolName.setError("School cannot be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutSchoolName.setError("School is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutSchoolName.setError(null);
            inputLayoutSchoolName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateChildDob() {
        String val = inputLayoutChildDob.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutChildDob.setError("Child's DOB cannot be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutChildDob.setError("Child's DOB is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutChildDob.setError(null);
            inputLayoutChildDob.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (spinnerChildLevel.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select APPROVER HR", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHr:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }

}