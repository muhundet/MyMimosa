package zw.co.mimosa.mymimosa.ui.hr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.database.CipherOpenHelper;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.AdvanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveFormHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

public class HrMainDashboardActivity extends AppCompatActivity {
    MaterialCardView materialCardViewLeave, materialCardViewEducationalAssistance, materialCardViewActingAllowance, materialCardViewLabourRequest,
    materialCardViewActingAppointment, materialCardViewTimeSheet, materialCardViewOvertimeAuthorisation, materialCardViewStatusChange,
    materialCardViewApplicationForBankTransfer;
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
        setContentView(R.layout.activity_hr_main_dashboard);
        setTitle("HR Forms");
        materialCardViewLeave = findViewById(R.id.cv_leave_menu);
        materialCardViewEducationalAssistance = findViewById(R.id.cv_educational_assistance_menu);
        materialCardViewActingAllowance = findViewById(R.id.cv_acting_allowance_menu);
        materialCardViewLabourRequest = findViewById(R.id.cv_labour_request_menu);
        materialCardViewActingAppointment = findViewById(R.id.cv_acting_appointment_menu);
        materialCardViewTimeSheet = findViewById(R.id.cv_Timesheet_menu);
        materialCardViewOvertimeAuthorisation = findViewById(R.id.cv_overtime_authorisation_menu);
        materialCardViewStatusChange = findViewById(R.id.cv_status_change_menu);

        materialCardViewLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("Select an Option below");
                String[] items = {"Leave","Advance"};
                alertDialog.setItems(items, (dialog, which) -> {
                    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                    switch (which) {
                        case 0:
                            LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                            lfh.setWhatAreYouApplyingFor("leave");
                            AlertDialog.Builder alertDialog12 = new AlertDialog.Builder(mContext);
                            alertDialog12.setTitle("Choose an option");
                            String[] items12 = {"Self","On Behalf"};
                            alertDialog12.setItems(items12, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                                            String empIdFromLUAU = luau.getEmployeeId();
                                            getUserFromDatabase(empIdFromLUAU);
                                            setUserFieldsToLeaveLeaveSelfFormHelper();
                                            Intent intentLeaveSelf = new Intent(mContext, LeaveActivity.class);
                                            intentLeaveSelf.putExtra("DEPARTMENTID", departmentName[0]);
                                            intentLeaveSelf.putExtra("EMPID", empId[0]);
                                            intentLeaveSelf.putExtra("JOBTITLE", jobTitle[0]);
                                            intentLeaveSelf.putExtra("EMAILID", emailId[0]);
                                            intentLeaveSelf.putExtra("FIRSTNAME", firstName[0]);
                                            intentLeaveSelf.putExtra("LASTNAME", lastName[0]);
                                            mContext.startActivity(intentLeaveSelf);
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                            View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.text_input_mine_number, null, false);
                                            final TextInputEditText input =  viewInflated.findViewById(R.id.input);
                                            input.requestFocus();
                                            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                            builder.setView(viewInflated);
                                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String employeeId = input.getText().toString().trim();
                                                    getUserFromDatabase(employeeId);
                                                    setUserFieldsToLeaveLeaveOnBehalfFormHelper();
                                                    Intent intentLeaveOnBehalf = new Intent(mContext, LeaveActivity.class);
                                                    intentLeaveOnBehalf.putExtra("DEPARTMENTID", departmentName[0]);
                                                    intentLeaveOnBehalf.putExtra("EMPID", empId[0]);
                                                    intentLeaveOnBehalf.putExtra("JOBTITLE", jobTitle[0]);
                                                    intentLeaveOnBehalf.putExtra("EMAILID", emailId[0]);
                                                    intentLeaveOnBehalf.putExtra("FIRSTNAME", firstName[0]);
                                                    intentLeaveOnBehalf.putExtra("LASTNAME", lastName[0]);
                                                    mContext.startActivity(intentLeaveOnBehalf);

                                                }
                                            });
                                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.show();
                                            dialog.dismiss();
                                            break;
                                    }
                                }
                            });
                            AlertDialog alert = alertDialog12.create();
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();
                            dialog.dismiss();
                            break;
                        case 1:
                            LeaveFormHelper leaveFormHelperAdvance = LeaveFormHelper.getLeaveFormHelperInstance();
                            leaveFormHelperAdvance.setWhatAreYouApplyingFor("advance");
                            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(mContext);
                            alertDialog1.setTitle("Choose an option");
                            String[] items1 = {"Self","On Behalf"};
                            int checkedItem1 = -1;
                            alertDialog1.setItems(items1,  new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                                            String empIdFromLUAU = luau.getEmployeeId();
                                            getUserFromDatabase(empIdFromLUAU);
                                            setUserFieldsToLeaveAdvanceSelfFormHelper();
                                            Intent intentAdvanceSelf = new Intent(mContext, AdvanceActivity.class);
                                            intentAdvanceSelf.putExtra("DEPARTMENTID", departmentName[0]);
                                            intentAdvanceSelf.putExtra("EMPID", empId[0]);
                                            intentAdvanceSelf.putExtra("JOBTITLE", jobTitle[0]);
                                            intentAdvanceSelf.putExtra("EMAILID", emailId[0]);
                                            intentAdvanceSelf.putExtra("FIRSTNAME", firstName[0]);
                                            intentAdvanceSelf.putExtra("LASTNAME", lastName[0]);
                                            mContext.startActivity(intentAdvanceSelf);
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                            View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.text_input_mine_number, null, false);
                                            final TextInputEditText input =  viewInflated.findViewById(R.id.input);
                                            input.requestFocus();
                                            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
                                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                            builder.setView(viewInflated);
                                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String employeeId = input.getText().toString().trim();
                                                    getUserFromDatabase(employeeId);
                                                    setUserFieldsToLeaveAdvanceOnBehalfFormHelper();
                                                    Intent intentAdvanceOnBehalf = new Intent(mContext, AdvanceActivity.class);
                                                    intentAdvanceOnBehalf.putExtra("DEPARTMENTID", departmentName[0]);
                                                    intentAdvanceOnBehalf.putExtra("EMPID", empId[0]);
                                                    intentAdvanceOnBehalf.putExtra("JOBTITLE", jobTitle[0]);
                                                    intentAdvanceOnBehalf.putExtra("EMAILID", emailId[0]);
                                                    intentAdvanceOnBehalf.putExtra("FIRSTNAME", firstName[0]);
                                                    intentAdvanceOnBehalf.putExtra("LASTNAME", lastName[0]);
                                                    mContext.startActivity(intentAdvanceOnBehalf);

                                                }
                                            });
                                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                            builder.show();
                                            dialog.dismiss();
                                            break;
                                    }
                                }
                            });
                            AlertDialog alert1 = alertDialog1.create();
                            alert1.setCanceledOnTouchOutside(false);
                            alert1.show();
                            dialog.dismiss();
                            break;
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();

            }
        });
        materialCardViewEducationalAssistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToEducationalAssistance();
                Intent intentEducationalAssistance = new Intent(HrMainDashboardActivity.this, EducationalAssistanceActivity.class);
                intentEducationalAssistance.putExtra("DEPARTMENTID", departmentName[0]);
                intentEducationalAssistance.putExtra("EMPID", empId[0]);
                intentEducationalAssistance.putExtra("JOBTITLE", jobTitle[0]);
                intentEducationalAssistance.putExtra("EMAILID", emailId[0]);
                intentEducationalAssistance.putExtra("FIRSTNAME", firstName[0]);
                intentEducationalAssistance.putExtra("LASTNAME", lastName[0]);
                HrMainDashboardActivity.this.startActivity(intentEducationalAssistance);
            }
        });
        materialCardViewActingAllowance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToActingAllowance();
                Intent intentActingAllowance = new Intent(mContext, ActingAllowanceActivity.class);
                intentActingAllowance.putExtra("DEPARTMENTID", departmentName[0]);
                intentActingAllowance.putExtra("EMPID", empId[0]);
                intentActingAllowance.putExtra("JOBTITLE", jobTitle[0]);
                intentActingAllowance.putExtra("EMAILID", emailId[0]);
                intentActingAllowance.putExtra("FIRSTNAME", firstName[0]);
                intentActingAllowance.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentActingAllowance);
            }
        });
        materialCardViewLabourRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewActingAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewTimeSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewOvertimeAuthorisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        materialCardViewStatusChange.setOnClickListener(new View.OnClickListener() {
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

    private void setUserFieldsToLeaveLeaveSelfFormHelper() {
        LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
        lfh.setFirstname(firstName[0]);
        lfh.setSurname(lastName[0]);
        lfh.setEmployeeId(empId[0]);
        lfh.setDepartment(departmentName[0]);
        lfh.setDesignation(jobTitle[0]);
        lfh.setWhatAreYouApplyingFor("leave");
        lfh.setWhoAreYouApplyingFor("self");
        lfh.setEmailId(emailId[0]);
    }

    private void setUserFieldsToLeaveLeaveOnBehalfFormHelper() {
        LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
        lfh.setFirstname(firstName[0]);
        lfh.setSurname(lastName[0]);
        lfh.setEmployeeId(empId[0]);
        lfh.setDepartment(departmentName[0]);
        lfh.setDesignation(jobTitle[0]);
        lfh.setWhatAreYouApplyingFor("leave");
        lfh.setWhoAreYouApplyingFor("onbehalf");
        lfh.setEmailId(emailId[0]);
    }

    private void setUserFieldsToLeaveAdvanceSelfFormHelper() {
        LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
        lfh.setFirstname(firstName[0]);
        lfh.setSurname(lastName[0]);
        lfh.setEmployeeId(empId[0]);
        lfh.setDepartment(departmentName[0]);
        lfh.setDesignation(jobTitle[0]);
        lfh.setWhatAreYouApplyingFor("advance");
        lfh.setWhoAreYouApplyingFor("self");
        lfh.setEmailId(emailId[0]);
    }

    private void setUserFieldsToLeaveAdvanceOnBehalfFormHelper() {
        LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
        lfh.setFirstname(firstName[0]);
        lfh.setSurname(lastName[0]);
        lfh.setEmployeeId(empId[0]);
        lfh.setDepartment(departmentName[0]);
        lfh.setDesignation(jobTitle[0]);
        lfh.setWhatAreYouApplyingFor("advance");
        lfh.setWhoAreYouApplyingFor("onbehalf");
        lfh.setEmailId(emailId[0]);
    }

    private void setUserFieldsToEducationalAssistance() {
        EducationalAssistanceHelper eah = EducationalAssistanceHelper.getEducationalAssistanceInstance();
        eah.setFirstname(firstName[0]);
        eah.setSurname(lastName[0]);
        eah.setEmployeeId(empId[0]);
        eah.setDepartment(departmentName[0]);
        eah.setDesignation(jobTitle[0]);
        eah.setEmailId(emailId[0]);
    }

    private void setUserFieldsToActingAllowance() {
        ActingAllowanceHelper eah = ActingAllowanceHelper.getActingAllowanceInstance();
        eah.setFirstname(firstName[0]);
        eah.setSurname(lastName[0]);
        eah.setEmployeeId(empId[0]);
        eah.setDepartment(departmentName[0]);
        eah.setDesignation(jobTitle[0]);
        eah.setEmailId(emailId[0]);
    }
}