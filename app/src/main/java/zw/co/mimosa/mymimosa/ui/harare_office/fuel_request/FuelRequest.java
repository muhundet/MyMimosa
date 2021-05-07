package zw.co.mimosa.mymimosa.ui.harare_office.fuel_request;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

import zw.co.mimosa.mymimosa.R;

public class FuelRequest extends AppCompatActivity {
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    TextInputEditText etVehicleReg, etReasonForApplication;
    RadioGroup radioQuantityRequested;
    Spinner spinnerRequestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_request);

        etFirstName = findViewById(R.id.et_pcam_first_name);
        etSurname = findViewById(R.id.et_pcam_surname);
        etMineNumber = findViewById(R.id.et_pcam_mine_number);
        etDepartment = findViewById(R.id.et_pcam_department);
        etDesignation = findViewById(R.id.et_pcam_designation);

        etVehicleReg = findViewById(R.id.et_fr_vehicle_reg);
        etReasonForApplication = findViewById(R.id.et_fr_reason_for_application);
        radioQuantityRequested = findViewById(R.id.radio_fr_type);

        spinnerRequestType = findViewById(R.id.spinner_fr_fuel_request_type);

    }
}