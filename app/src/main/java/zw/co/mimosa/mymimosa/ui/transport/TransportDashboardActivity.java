package zw.co.mimosa.mymimosa.ui.transport;

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
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationMine;
import zw.co.mimosa.mymimosa.ui.transport.buss_pass_application.BusPassApplication;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

public class TransportDashboardActivity extends AppCompatActivity {
    MaterialCardView materialCardViewBusPassApplication;
    Context mContext = this;
    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
    String empIdFromLUAU = luau.getEmployeeId();
    final String[] departmentName = new String[1];
    final String[] empId = new String[1];
    final String[] jobTitle = new String[1];
    final String[] emailId = new String[1];
    final String[] firstName = new String[1];
    final String[] lastName = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_dashboard);

        materialCardViewBusPassApplication = findViewById(R.id.cv_bus_pass_application_menu);

        materialCardViewBusPassApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToBusPassApplication();
                Intent intentBusPassApplication = new Intent(mContext, BusPassApplication.class);
                intentBusPassApplication.putExtra("DEPARTMENTID", departmentName[0]);
                intentBusPassApplication.putExtra("EMPID", empId[0]);
                intentBusPassApplication.putExtra("JOBTITLE", jobTitle[0]);
                intentBusPassApplication.putExtra("EMAILID", emailId[0]);
                intentBusPassApplication.putExtra("FIRSTNAME", firstName[0]);
                intentBusPassApplication.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentBusPassApplication);
            }
        });

    }

    private void getUserFromDatabase(String empIdFromLUAU) {
        CipherOpenHelper dbOpenHelper;
        dbOpenHelper = new CipherOpenHelper(mContext);
        try {
            Cursor cursor = dbOpenHelper.getLoginUser(empIdFromLUAU);
            if (cursor.moveToFirst()) {
                departmentName[0] = cursor.getString(cursor.getColumnIndex("DEPTNAME"));
                empId[0] = cursor.getString(cursor.getColumnIndex("EMPLOYEEID"));
                jobTitle[0] = cursor.getString(cursor.getColumnIndex("JOBTITLE"));
                emailId[0] = cursor.getString(cursor.getColumnIndex("EMAILID"));
                firstName[0] = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
                lastName[0] = cursor.getString(cursor.getColumnIndex("LASTNAME"));
            } else {
                Toast.makeText(mContext, "No such user in database", Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Toast.makeText(mContext, "SQL Exception: no such user", Toast.LENGTH_LONG).show();
        }
    }

    private void setUserFieldsToBusPassApplication() {
        PettyCashAuthorisationHelper pcam = PettyCashAuthorisationHelper.getPettyCashAuthorisationInstance();
        pcam.setFirstname(firstName[0]);
        pcam.setSurname(lastName[0]);
        pcam.setEmployeeId(empId[0]);
        pcam.setDepartment(departmentName[0]);
        pcam.setDesignation(jobTitle[0]);
        pcam.setEmailId(emailId[0]);
    }
}