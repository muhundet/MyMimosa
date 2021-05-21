package zw.co.mimosa.mymimosa.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.database.CipherOpenHelper;
import zw.co.mimosa.mymimosa.ui.finance.FinanceDashboardActivity;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationMine;
import zw.co.mimosa.mymimosa.ui.harare_office.fuel_request.FuelRequest;
import zw.co.mimosa.mymimosa.ui.harare_office.fuel_request.FuelRequestHelper;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.HarareDashboardActivity;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.PettyCashAuthorisationHarare;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.PettyCashAuthorisationHarareHelper;
import zw.co.mimosa.mymimosa.ui.hr.HrDashboardActivity;
import zw.co.mimosa.mymimosa.ui.hr.HrMainDashboardActivity;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.AdvanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveFormHelper;
import zw.co.mimosa.mymimosa.ui.hr.overtime_authorisation.OvertimeAuthorisation;
import zw.co.mimosa.mymimosa.ui.hr.overtime_authorisation.OvertimeAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.medical_services.MedicalServicesDashboardActivity;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidReturnScreeningHelper;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreening;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreeningBusiness;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreeningContractor;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreeningDriver;
import zw.co.mimosa.mymimosa.ui.transport.TransportDashboardActivity;
import zw.co.mimosa.mymimosa.ui.transport.buss_pass_application.BusPassApplication;
import zw.co.mimosa.mymimosa.ui.transport.buss_pass_application.BusPassApplicationHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A placeholder fragment containing a simple view.
 */
public class FormsFragment extends Fragment  {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    MaterialButton btnLeave, btnEducational, btnActingAllowance, btnOvertimeAuth, btnPettyCashMine, btnPettyCashHarare, btnFuelRequest,
    btnBusPass, btnCovidPersonal, btnCovidBusiness, btnCovidDriver, btnCovidContractor;


    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
    String empIdFromLUAU = luau.getEmployeeId();

    final String[] departmentName = new String[1];
    final String[] empId = new String[1];
    final String[] jobTitle = new String[1];
    final String[] emailId = new String[1];
    final String[] firstName = new String[1];
    final String[] lastName = new String[1];

    public static FormsFragment newInstance(int index) {
        FormsFragment fragment = new FormsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context mContext = getActivity();
        View root = inflater.inflate(R.layout.fragment_forms, container, false);
        getActivity().setTitle("DASHBOARD");

        btnLeave = root.findViewById(R.id.button_menu_leave);
        btnEducational = root.findViewById(R.id.button_menu_educational_assistance);
        btnActingAllowance = root.findViewById(R.id.button_menu_acting_allowance);
        btnOvertimeAuth = root.findViewById(R.id.button_menu_overtime_auth);
        btnPettyCashMine = root.findViewById(R.id.button_menu_pettycash_mine);
        btnPettyCashHarare = root.findViewById(R.id.button_menu_pettycash_harare);
        btnFuelRequest = root.findViewById(R.id.button_menu_fuel_req);
        btnBusPass = root.findViewById(R.id.button_menu_bus_pass);
        btnCovidPersonal = root.findViewById(R.id.button_menu_covid_personal);
        btnCovidBusiness = root.findViewById(R.id.button_menu_covid_business);
        btnCovidDriver = root.findViewById(R.id.button_menu_covid_driver);
        btnCovidContractor = root.findViewById(R.id.button_menu_covid_contractor);


        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Select an Option below");
                String[] items = {"Leave","Advance"};
                alertDialog.setItems(items, (dialog, which) -> {
                    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                    switch (which) {
                        case 0:
                            LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                            lfh.setWhatAreYouApplyingFor("leave");
                            AlertDialog.Builder alertDialog12 = new AlertDialog.Builder(getActivity());
                            alertDialog12.setTitle("Choose an option");
                            String[] items12 = {"Self","On Behalf"};
                            alertDialog12.setItems(items12, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                                            String empIdFromLUAU = luau.getEmployeeId();
                                            System.out.println("EMP ID" + empIdFromLUAU);
                                            getUserFromDatabase(empIdFromLUAU);
                                            setUserFieldsToLeaveLeaveSelfFormHelper();
                                            Intent intentLeaveSelf = new Intent(getActivity(), LeaveActivity.class);
                                            intentLeaveSelf.putExtra("DEPARTMENTID", departmentName[0]);
                                            intentLeaveSelf.putExtra("EMPID", empId[0]);
                                            intentLeaveSelf.putExtra("JOBTITLE", jobTitle[0]);
                                            intentLeaveSelf.putExtra("EMAILID", emailId[0]);
                                            intentLeaveSelf.putExtra("FIRSTNAME", firstName[0]);
                                            intentLeaveSelf.putExtra("LASTNAME", lastName[0]);
                                            getActivity().startActivity(intentLeaveSelf);
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.text_input_mine_number, null, false);
                                            final TextInputEditText input =  viewInflated.findViewById(R.id.input);
                                            input.requestFocus();
                                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
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
                                @RequiresApi(api = Build.VERSION_CODES.M)
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

        btnEducational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToEducationalAssistance();
                Intent intentEducationalAssistance = new Intent(mContext, EducationalAssistanceActivity.class);
                intentEducationalAssistance.putExtra("DEPARTMENTID", departmentName[0]);
                intentEducationalAssistance.putExtra("EMPID", empId[0]);
                intentEducationalAssistance.putExtra("JOBTITLE", jobTitle[0]);
                intentEducationalAssistance.putExtra("EMAILID", emailId[0]);
                intentEducationalAssistance.putExtra("FIRSTNAME", firstName[0]);
                intentEducationalAssistance.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentEducationalAssistance);
            }
        });

        btnActingAllowance.setOnClickListener(new View.OnClickListener() {
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

        btnOvertimeAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToOvertimeAuthorisation();
                Intent intentOvertimeAuth = new Intent(mContext, OvertimeAuthorisation.class);
                intentOvertimeAuth.putExtra("DEPARTMENTID", departmentName[0]);
                intentOvertimeAuth.putExtra("EMPID", empId[0]);
                intentOvertimeAuth.putExtra("JOBTITLE", jobTitle[0]);
                intentOvertimeAuth.putExtra("EMAILID", emailId[0]);
                intentOvertimeAuth.putExtra("FIRSTNAME", firstName[0]);
                intentOvertimeAuth.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentOvertimeAuth);
            }
        });

        btnPettyCashMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToPettyCashAuthorisation();
                Intent intentPettyCashAuthorisationMine = new Intent(mContext, PettyCashAuthorisationMine.class);
                intentPettyCashAuthorisationMine.putExtra("DEPARTMENTID", departmentName[0]);
                intentPettyCashAuthorisationMine.putExtra("EMPID", empId[0]);
                intentPettyCashAuthorisationMine.putExtra("JOBTITLE", jobTitle[0]);
                intentPettyCashAuthorisationMine.putExtra("EMAILID", emailId[0]);
                intentPettyCashAuthorisationMine.putExtra("FIRSTNAME", firstName[0]);
                intentPettyCashAuthorisationMine.putExtra("LASTNAME", lastName[0]);
                mContext.startActivity(intentPettyCashAuthorisationMine);
            }
        });

        btnPettyCashHarare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToPettyCashAuthorisationHarare();
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

        btnBusPass.setOnClickListener(new View.OnClickListener() {
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

        btnCovidPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                creturn.setTemplate("COVID-19 SCREENING FORM- Return From Isolation");
//                creturn.setTemplate("COVID-19 SCREENING FORM- Return From Isolation Description");
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(mContext, CovidScreening.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);
            }
        });

        btnCovidBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(mContext, CovidScreeningBusiness.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);
            }
        });

        btnCovidContractor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(mContext, CovidScreeningContractor.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);
            }
        });

        btnCovidDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToCovidReturnToWork();
                Intent intentCovidReturnToWork = new Intent(mContext, CovidScreeningDriver.class);
                intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName);
                intentCovidReturnToWork.putExtra("EMPID", empId);
                intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle);
                intentCovidReturnToWork.putExtra("EMAILID", emailId);
                intentCovidReturnToWork.putExtra("FIRSTNAME", firstName);
                intentCovidReturnToWork.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentCovidReturnToWork);
            }
        });

        btnFuelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luau = LoggedInUserAccessUtility.getInstance();
                String empIdFromLUAU = luau.getEmployeeId();
                getUserFromDatabase(empIdFromLUAU);
                setUserFieldsToFuelRequest();
                Intent intentFuelRequest = new Intent(mContext, FuelRequest.class);
                intentFuelRequest.putExtra("DEPARTMENTID", departmentName);
                intentFuelRequest.putExtra("EMPID", empId);
                intentFuelRequest.putExtra("JOBTITLE", jobTitle);
                intentFuelRequest.putExtra("EMAILID", emailId);
                intentFuelRequest.putExtra("FIRSTNAME", firstName);
                intentFuelRequest.putExtra("LASTNAME", lastName);
                mContext.startActivity(intentFuelRequest);
            }
        });

        return root;
    }

    private void getUserFromDatabase(String empIdFromLUAU) {
        CipherOpenHelper dbOpenHelper;
        dbOpenHelper = new CipherOpenHelper(getContext());
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
                Toast.makeText(getActivity(), "No such user in database", Toast.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Toast.makeText(getActivity(), "SQL Exception: no such user", Toast.LENGTH_LONG).show();
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

    private void setUserFieldsToPettyCashAuthorisation() {
        PettyCashAuthorisationHelper pcam = PettyCashAuthorisationHelper.getPettyCashAuthorisationInstance();
        pcam.setFirstname(firstName[0]);
        pcam.setSurname(lastName[0]);
        pcam.setEmployeeId(empId[0]);
        pcam.setDepartment(departmentName[0]);
        pcam.setDesignation(jobTitle[0]);
        pcam.setEmailId(emailId[0]);
    }

    private void setUserFieldsToPettyCashAuthorisationHarare() {
        PettyCashAuthorisationHarareHelper pcam = PettyCashAuthorisationHarareHelper.getPettyCashAuthorisationInstance();
        pcam.setFirstname(firstName[0]);
        pcam.setSurname(lastName[0]);
        pcam.setEmployeeId(empId[0]);
        pcam.setDepartment(departmentName[0]);
        pcam.setDesignation(jobTitle[0]);
        pcam.setEmailId(emailId[0]);
    }

    private void setUserFieldsToBusPassApplication() {
        BusPassApplicationHelper bpa = BusPassApplicationHelper.getBusPassApplicationInstance();
        bpa.setFirstname(firstName[0]);
        bpa.setSurname(lastName[0]);
        bpa.setEmployeeId(empId[0]);
        bpa.setDepartment(departmentName[0]);
        bpa.setDesignation(jobTitle[0]);
        bpa.setEmailId(emailId[0]);
    }

    private void setUserFieldsToCovidReturnToWork() {
        CovidReturnScreeningHelper creturn = CovidReturnScreeningHelper.getCovidReturnToWorkHelperInstance();
        creturn.setFirstname(firstName[0]);
        creturn.setSurname(lastName[0]);
        creturn.setEmployeeId(empId[0]);
        creturn.setDepartment(departmentName[0]);
        creturn.setDesignation(jobTitle[0]);
        creturn.setEmailId(emailId[0]);
    }

    private void setUserFieldsToFuelRequest() {
        FuelRequestHelper fr = FuelRequestHelper.getRequestInstance();
        fr.setFirstname(firstName[0]);
        fr.setSurname(lastName[0]);
        fr.setEmployeeId(empId[0]);
        fr.setDepartment(departmentName[0]);
        fr.setDesignation(jobTitle[0]);
        fr.setEmailId(emailId[0]);
    }

    private void setUserFieldsToOvertimeAuthorisation() {
        OvertimeAuthorisationHelper oa = OvertimeAuthorisationHelper.getOvertimeAuthorisationInstance();
        oa.setFirstname(firstName[0]);
        oa.setSurname(lastName[0]);
        oa.setEmployeeId(empId[0]);
        oa.setDepartment(departmentName[0]);
        oa.setDesignation(jobTitle[0]);
        oa.setEmailId(emailId[0]);
    }


}