package zw.co.mimosa.mymimosa.ui.hr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import zw.co.mimosa.mymimosa.R;

public class LeaveActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etSection, etDesignation;
    RadioGroup radioTypeOfLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        etFirstName = findViewById(R.id.et_leave_first_name);
        etSurname = findViewById(R.id.et_leave_surname);
        etMineNumber = findViewById(R.id.et_leave_mine_number);
        etDepartment = findViewById(R.id.et_leave_department);
        etSection = findViewById(R.id.et_leave_section);
        etDesignation = findViewById(R.id.et_leave_designation);
        radioTypeOfLeave = findViewById(R.id.radio_type_of_leave);

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

        btnNext = findViewById(R.id.button_leave_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTypeOfLeave()) {
                    Intent intent = new Intent(LeaveActivity.this, LeaveActivity2.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    private boolean validateTypeOfLeave() {
        if (radioTypeOfLeave.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Leave Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateTypeOfLeave:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }


}