package zw.co.mimosa.mymimosa.ui.hr.overtime_authorisation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import zw.co.mimosa.mymimosa.Pickers.DateOfBirthPicker;
import zw.co.mimosa.mymimosa.Pickers.OvertimeDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_business_data.CovidBusinessRequest;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidReturnScreeningHelper;

public class OvertimeAuthorisation extends AppCompatActivity {
    TextInputLayout inputLayoutSupervisor;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection, spinnerHos;
    TextInputEditText etSupervisor;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    ImageButton btnAddOvertime;
    OvertimeAuthorisationHelper oa = OvertimeAuthorisationHelper.getOvertimeAuthorisationInstance();
    long overtimeDate;
    CovidBusinessRequest covidBusinessRequest;


    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_authorisation);
        final int[] i = {0};
        int x = 0;

        etFirstName = findViewById(R.id.et_oa_first_name);
        etSurname = findViewById(R.id.et_oa_surname);
        etMineNumber = findViewById(R.id.et_oa_mine_number);
        etDepartment = findViewById(R.id.et_oa_department);
        etDesignation = findViewById(R.id.et_oa_designation);

        inputLayoutSupervisor = findViewById(R.id.oa_supervisor);

        etSupervisor = findViewById(R.id.et_oa_supervisor);

        spinnerHos = findViewById(R.id.spinner_oa_hos);
        spinnerSection = findViewById(R.id.spinner_oa_section);

        pgBarSubmit = findViewById(R.id.progress_bar_oa_submit);

        btnSubmit = findViewById(R.id.button_oa_submit);
        btnAddOvertime = findViewById(R.id.button_add_overtime);

        btnAddOvertime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i[0] = i[0]+1;
                System.out.println("Index i = "+i[0]);
                if(i[0]<=10) {
                    View tableRow = LayoutInflater.from(OvertimeAuthorisation.this).inflate(R.layout.overtime_table_item, null, false);
                    EditText tvDateRow = tableRow.findViewById(R.id.overtime_date);
                    EditText tvHoursFromRow = tableRow.findViewById(R.id.overtime_hours_from);
                    EditText tvHoursToRow = tableRow.findViewById(R.id.overtime_hours_to);
                    EditText tvTotalHours = tableRow.findViewById(R.id.overtime_total);

                    tableLayout = findViewById(R.id.tableLayout);

                    tvDateRow.setHint("Date");
                    tvDateRow.setId(R.id.overtime_date + i[0]);

                    tvHoursFromRow.setHint("From");
                    tvHoursFromRow.setId(R.id.overtime_hours_from + i[0]);
                    tvHoursToRow.setHint("To");
                    tvHoursToRow.setId(R.id.overtime_hours_to + i[0]);
                    tvTotalHours.setHint("Hours");
                    tvHoursToRow.setId(R.id.overtime_total + i[0]);
                    tableLayout.addView(tableRow);
                    tvDateRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showOvertimeDatePickerDialog(v);
                        }
                    });
                    System.out.println("Date = "+ tvDateRow.getId());
                    System.out.println("from = "+ tvHoursFromRow.getId());
                    System.out.println("to = "+ tvHoursToRow.getId());
                    System.out.println("hours = "+ tvTotalHours.getId());
                }else{
                    Toast.makeText(OvertimeAuthorisation.this, "Maximum entries reached", Toast.LENGTH_SHORT).show();
                }
            }


        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void showOvertimeDatePickerDialog(View v) {
        DialogFragment newFragment = new OvertimeDatePicker(v);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


}