package zw.co.mimosa.mymimosa.ui.hr.acting_allowance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.Pickers.EffectiveDatePicker;
import zw.co.mimosa.mymimosa.Pickers.EndDatePicker;
import zw.co.mimosa.mymimosa.Pickers.StartDatePicker;
import zw.co.mimosa.mymimosa.R;

public class ActingAllowanceActivity extends AppCompatActivity {

    TextInputLayout inputLayoutActingPosition, inputLayoutEffectiveDate, inputLayoutStartDate, inputLayoutEndDate;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection, spinnerActingGrade, spinnerCurrentGrade;
    TextInputEditText etActingPosition, etEffectiveDate, etStartDate, etEndDate;
    Button btnNext;
    ActingAllowanceHelper aah = ActingAllowanceHelper.getActingAllowanceInstance();
    long effectiveDateLong;
    long startDateLong;
    long endDateLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acting_allowance);

        etFirstName = findViewById(R.id.et_aa_first_name);
        etSurname = findViewById(R.id.et_aa_surname);
        etMineNumber = findViewById(R.id.et_aa_mine_number);
        etDepartment = findViewById(R.id.et_aa_department);
        etDesignation = findViewById(R.id.et_aa_designation);

        inputLayoutActingPosition = findViewById(R.id.aa_acting_position);
        inputLayoutEffectiveDate = findViewById(R.id.aa_effective_date);
        inputLayoutStartDate = findViewById(R.id.aa_start_date);
        inputLayoutEndDate = findViewById(R.id.aa_end_date);

        etActingPosition = findViewById(R.id.et_aa_acting_position);
        etEffectiveDate = findViewById(R.id.et_aa_effective_date);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);

        spinnerActingGrade = findViewById(R.id.spinner_aa_acting_grade);
        spinnerCurrentGrade = findViewById(R.id.spinner_aa_current_grade);
        spinnerSection = findViewById(R.id.spinner_aa_section);

        btnNext = findViewById(R.id.button_aa_next);

        etFirstName.setText(aah.getFirstname());
        etSurname.setText(aah.getSurname());
        etMineNumber.setText(aah.getEmployeeId());
        etDepartment.setText(aah.getDepartment());
        etDesignation.setText(aah.getDesignation());

        etEffectiveDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEffectiveDatePickerDialog(v);
            }
        });

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog(v);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog(v);
            }
        });

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        ArrayAdapter<CharSequence> currentGradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        currentGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrentGrade.setAdapter(currentGradeAdapter);

        ArrayAdapter<CharSequence> actingGradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade_array, android.R.layout.simple_spinner_item);
        actingGradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActingGrade.setAdapter(actingGradeAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String effectiveDate = etEffectiveDate.getText().toString();
                SimpleDateFormat effectiveDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = effectiveDateFormat.parse(effectiveDate);
                    effectiveDateLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String startDate = etStartDate.getText().toString();
                SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = startDateFormat.parse(effectiveDate);
                    startDateLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String endDate = etEndDate.getText().toString();
                SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy/mm/dd");
                try {
                    Date d = endDateFormat.parse(effectiveDate);
                    endDateLong = d.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                aah.setSection(spinnerSection.getSelectedItem().toString());
                aah.setActingPosition(etActingPosition.getText().toString());
                aah.setEffectiveDate(effectiveDateLong);
                aah.setStartDate(startDateLong);
                aah.setEndDate(endDateLong);
                aah.setCurrentGrade(spinnerCurrentGrade.getSelectedItem().toString());
                aah.setActingGrade(spinnerActingGrade.getSelectedItem().toString());
                Intent intent = new Intent(ActingAllowanceActivity.this, ActingAllowanceActivity2.class);
                startActivity(intent);
            }
        });

    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newFragment = new StartDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newFragment = new EndDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEffectiveDatePickerDialog(View v) {
        DialogFragment newFragment = new EffectiveDatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
