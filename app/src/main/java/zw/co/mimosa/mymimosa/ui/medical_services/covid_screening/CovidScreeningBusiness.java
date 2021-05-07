package zw.co.mimosa.mymimosa.ui.medical_services.covid_screening;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import zw.co.mimosa.mymimosa.Pickers.DateOfBirthPicker;
import zw.co.mimosa.mymimosa.Pickers.ReturnDatePicker;
import zw.co.mimosa.mymimosa.Pickers.TravelDatePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_return_data.CovidReturnRequest;

public class CovidScreeningBusiness extends AppCompatActivity {
    TextInputLayout inputLayoutDateOfBirth, inputLayoutIdNumber, inputLayoutCellNumber, inputLayoutNextOfKin, inputLayoutAddress,
            inputLayoutTravelDate, inputLayoutReturnDate, inputLayoutDesination, inputLayoutRegNumber, inputLayoutEstimatedDistance;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection;
    TextInputEditText etDateOfBirth, etIdNumber, etCellNumber, etNextOfKin, etAddress, etTravelDate, etReturnDate, etDestination, etRegNumber, etEstimated_Distance;
    RadioGroup radioGroupGender, radioVehicleType;
    RadioButton radioButtonGender, radioButtonVehicleType;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    CovidReturnScreeningHelper cbus = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
    long DateOfBirthLong, travelDateLong, returnDateLong;
    CovidReturnRequest covidReturnRequest;

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

        etDateOfBirth = findViewById(R.id.et_creturn_date_of_birth);
        etIdNumber = findViewById(R.id.et_cbus_id_number);
        etCellNumber = findViewById(R.id.et_cbus_cell_number);
        etNextOfKin = findViewById(R.id.et_cbus_next_of_kin);
        etAddress = findViewById(R.id.et_cbus_address);
        etTravelDate = findViewById(R.id.et_creturn_travel_date);
        etReturnDate = findViewById(R.id.et_creturn_return_date);
        etDestination = findViewById(R.id.et_cbus_destination);
        etRegNumber = findViewById(R.id.et_cbus_vehicle_reg);

        radioGroupGender = findViewById(R.id.radio_cbus_gender);
        radioVehicleType = findViewById(R.id.radio_cbus_vehicle_type);

        spinnerSection = findViewById(R.id.spinner_cbus_section);

        pgBarSubmit = findViewById(R.id.progress_bar_covid_submit);

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
}