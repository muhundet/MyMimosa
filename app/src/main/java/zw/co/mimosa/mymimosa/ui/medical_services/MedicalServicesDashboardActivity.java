package zw.co.mimosa.mymimosa.ui.medical_services;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.database.CipherOpenHelper;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreening;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidReturnScreeningHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

public class MedicalServicesDashboardActivity extends AppCompatActivity {
    MaterialCardView materialCardViewReturn, materialCardViewRoutine, materialCardViewdrivers;
    Context mContext = this;
    CovidReturnScreeningHelper creturn = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
    LoggedInUserAccessUtility luau;

    String departmentName;
    String empId;
    String jobTitle;
    String emailId;
    String firstName;
    String lastName;
    String covidTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_services_dashboard);

        materialCardViewReturn = findViewById(R.id.return_to_work_menu);
        materialCardViewRoutine = findViewById(R.id.routine_menu);
        materialCardViewdrivers = findViewById(R.id.drivers_menu);

        materialCardViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creturn.setTemplate("COVID-19 SCREENING FORM- Return From Isolation");
                creturn.setTemplate("COVID-19 SCREENING FORM- Return From Isolation Description");
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(MedicalServicesDashboardActivity.this, CovidScreening.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);

            }
        });

        materialCardViewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creturn.setTemplate("COVID-19 SCREENING FORM - Routine");
                creturn.setDescription("COVID-19 SCREENING FORM- Routine Description");
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(MedicalServicesDashboardActivity.this, CovidScreening.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);

            }
        });

        materialCardViewdrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creturn.setTemplate("COVID-19 DRIVERS SCREENING FORM");
                creturn.setDescription("COVID-19 SCREENING FORM- Drivers Description");
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(MedicalServicesDashboardActivity.this, CovidScreening.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);

            }
        });
    }

    private void getUserFromDatabase(String empIdFromLUAU) {
        CipherOpenHelper dbOpenHelper;
        dbOpenHelper = new CipherOpenHelper(this);
        try {
            Cursor cursor = dbOpenHelper.getLoginUser(empIdFromLUAU);
            if (cursor.moveToFirst()) {
                departmentName = cursor.getString(cursor.getColumnIndex("DEPTNAME"));
                empId = cursor.getString(cursor.getColumnIndex("EMPLOYEEID"));
                jobTitle = cursor.getString(cursor.getColumnIndex("JOBTITLE"));
                emailId = cursor.getString(cursor.getColumnIndex("EMAILID"));
                firstName = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
                lastName = cursor.getString(cursor.getColumnIndex("LASTNAME"));

            } else {
                Toast.makeText(this, "No such user in database", Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Toast.makeText(this, "SQL Exception: no such user", Toast.LENGTH_LONG).show();
        }
    }

    private void setUserFieldsToCovidReturnToWork() {
        CovidReturnScreeningHelper creturn = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
        creturn.setFirstname(firstName);
        creturn.setSurname(lastName);
        creturn.setEmployeeId(empId);
        creturn.setDepartment(departmentName);
        creturn.setDesignation(jobTitle);
        creturn.setEmailId(emailId);
    }
}