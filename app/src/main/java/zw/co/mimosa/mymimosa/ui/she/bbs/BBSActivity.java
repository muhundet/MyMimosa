package zw.co.mimosa.mymimosa.ui.she.bbs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import zw.co.mimosa.mymimosa.R;

public class BBSActivity extends AppCompatActivity {
    private Button button;
    DatePickerDialog picker;
    EditText eText, time, firstname, surname, employee_number, duration;
    Spinner spinner1, spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);
        firstname = findViewById(R.id.EditTextName);
        surname = findViewById(R.id.EditTextSurname);
        employee_number = findViewById(R.id.EditEmployeeNo);
        duration = findViewById(R.id.editTextNumber);
        time = findViewById(R.id.editText2);
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BBSActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        time.setText(i + ":" + i1);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

            spinner1 = findViewById(R.id.SpinnerDepartment);
            spinner2 = findViewById(R.id.SpinnerSection);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String department = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String section = spinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        eText = findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                final int year = cldr.get(Calendar.YEAR);
                button = findViewById(R.id.Button);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openMainActivity2();
                    }
                });
                picker = new DatePickerDialog(BBSActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        eText.setText(i2 + "/" + (i1 + 1) + "/" + i);
                    }
                }, year, month, day);
                picker.setTitle("Select Date");
                picker.show();
            }
        });

    }

    private void openMainActivity2() {
        Intent intent = new Intent(this, BBSActivity2.class);
         startActivity(intent);
    }
}
