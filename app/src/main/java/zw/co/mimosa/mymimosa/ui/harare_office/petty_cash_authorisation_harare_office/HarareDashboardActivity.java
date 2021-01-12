package zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office;

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
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

public class HarareDashboardActivity extends AppCompatActivity {
    MaterialCardView materialCardViewPettyCashAuthorisation, materialCardViewFuelRequest,
    materialCardViewCSROrder, materialCardViewProcurementAuthorisation;
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
        setContentView(R.layout.activity_harare_dashboard);

        materialCardViewPettyCashAuthorisation = findViewById(R.id.cv_harare_petty_cash_authosrisation_menu);
        materialCardViewFuelRequest = findViewById(R.id.cv_fuel_request_menu);
        materialCardViewCSROrder = findViewById(R.id.cv_csr_requisition_menu);
        materialCardViewProcurementAuthorisation = findViewById(R.id.cv_procurement_authorisation_menu);

        materialCardViewPettyCashAuthorisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToPettyCashAuthorisation();
                Intent intentPettyCashAuthorisationHarare = new Intent(mContext, PettyCashAuthorisationHarare.class);
                intentPettyCashAuthorisationHarare.putExtra("DEPARTMENTID", departmentName[0]);
                intentPettyCashAuthorisationHarare.putExtra("EMPID", empId[0]);
                intentPettyCashAuthorisationHarare.putExtra("JOBTITLE", jobTitle[0]);
                intentPettyCashAuthorisationHarare.putExtra("EMAILID", emailId[0]);
                intentPettyCashAuthorisationHarare.putExtra("FIRSTNAME", firstName[0]);
                intentPettyCashAuthorisationHarare.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentPettyCashAuthorisationHarare);
            }
        });
        materialCardViewFuelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewCSROrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewProcurementAuthorisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void setUserFieldsToPettyCashAuthorisation() {
        PettyCashAuthorisationHarareHelper pcam = PettyCashAuthorisationHarareHelper.getPettyCashAuthorisationInstance();
        pcam.setFirstname(firstName[0]);
        pcam.setSurname(lastName[0]);
        pcam.setEmployeeId(empId[0]);
        pcam.setDepartment(departmentName[0]);
        pcam.setDesignation(jobTitle[0]);
        pcam.setEmailId(emailId[0]);
    }
}