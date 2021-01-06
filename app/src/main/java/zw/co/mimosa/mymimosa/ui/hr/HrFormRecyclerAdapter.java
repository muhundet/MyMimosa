package zw.co.mimosa.mymimosa.ui.hr;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.ActingAllowanceRequest;
import zw.co.mimosa.mymimosa.database.OpenHelper;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationMine;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.PettyCashAuthorisationHarare;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.PettyCashAuthorisationHarareHelper;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity2;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.educational_assistance.EducationalAssistanceHelper;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.AdvanceActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveActivity;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveFormHelper;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_return.CovidReturnToWork;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_return.CovidReturnToWorkHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HrFormRecyclerAdapter extends RecyclerView.Adapter<HrFormRecyclerAdapter.HrFormViewHolder> {
    ArrayList<HrMenuModel> hrMenuList;
    Context mContext;
    private int position;

    public HrFormRecyclerAdapter(Context context, ArrayList<HrMenuModel> hrMenuList) {
        this.mContext = context;
        this.hrMenuList = hrMenuList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public HrFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.hr_menu_list_item_1, parent, false);

        return new HrFormViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(HrFormViewHolder holder, int position) {
        HrMenuModel hrMenuModel = hrMenuList.get(position);
        holder.bind(hrMenuModel);
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return hrMenuList.size();
    }

    public class HrFormViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
//        TextView tvHrFormMenu;
//        ImageView imageHrForm;
        MaterialButton btnHrMenuButton;



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public HrFormViewHolder(View itemView) {
            super(itemView);

//            tvHrFormMenu = itemView.findViewById(R.id.tv_hr_form_menu);
//            imageHrForm = itemView.findViewById(R.id.image_hr_form_menu);
            mContext = itemView.getContext();
            btnHrMenuButton = itemView.findViewById(R.id.btnHrMenuTitle);
            btnHrMenuButton.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(HrMenuModel hrMenuModel) {
            btnHrMenuButton.setText(hrMenuModel.getMenuTitle());
            int pos = getAdapterPosition();
            switch (pos){
                case 0:
//                    btnHrMenuButton.setIcon(mContext.getDrawable(R.drawable.iconedu));
//                    btnHrMenuButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.iconedu, 0, 0, 0);
                case 1:
//                    btnHrMenuButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.iconedu, 0, 0, 0);
//                    btnHrMenuButton.setIcon(mContext.getDrawable(R.drawable.iconedu));
//                    btnHrMenuButton.setPadding(10,10,10,10);
            }
        }

        final String[] departmentName = new String[1];
        final String[] empId = new String[1];
        final String[] jobTitle = new String[1];
        final String[] emailId = new String[1];
        final String[] firstName = new String[1];
        final String[] lastName = new String[1];
        final String[] covidTemplate = new String[1];

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            switch (pos){
                case 0:

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
                    break;

                case 1:
                    LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                    String empIdFromLUAU = luau.getEmployeeId();
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
//                    Intent intent =  new Intent(mContext, EducationalAssistanceActivity.class);
//                    mContext.startActivity(intent);
//                    break;
//                default:
//                    intent =  new Intent(mContext, DefaultActivity.class);
                    break;
                case 2:
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
                    break;

                case 3:
                    luau = LoggedInUserAccessUtility.getInstance();
                    empIdFromLUAU = luau.getEmployeeId();
                    getUserFromDatabase(empIdFromLUAU);
                    setUserFieldsToPettyCashAuthorisation();
                    Intent intentPettyCashAuthorisation = new Intent(mContext, PettyCashAuthorisationMine.class);
                    intentPettyCashAuthorisation.putExtra("DEPARTMENTID", departmentName[0]);
                    intentPettyCashAuthorisation.putExtra("EMPID", empId[0]);
                    intentPettyCashAuthorisation.putExtra("JOBTITLE", jobTitle[0]);
                    intentPettyCashAuthorisation.putExtra("EMAILID", emailId[0]);
                    intentPettyCashAuthorisation.putExtra("FIRSTNAME", firstName[0]);
                    intentPettyCashAuthorisation.putExtra("LASTNAME", lastName[0]);
                    mContext.startActivity(intentPettyCashAuthorisation);
                    break;

                case 4:
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
                    break;

                case 5:

//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle("Covid Screening Type");
//                builder.setPositiveButton("Return", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        covidTemplate[0] = "COVID-19 SCREENING FORM- Return To Work Description";
////                        dialog.dismiss();
//                    }
//                });
//                builder.setNeutralButton("Driver", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        covidTemplate[0] = "COVID-19 SCREENING FORM- Return To Work Description";
////                        dialog.dismiss();
//                    }
//                });
//
//                builder.setNegativeButton("Routine",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                covidTemplate[0] = "COVID-19 SCREENING FORM- Return To Work Description";
////                                dialog.dismiss();
//                            }
//                        });
//                AlertDialog covidAlert = builder.create();
//                covidAlert.show();

                    luau = LoggedInUserAccessUtility.getInstance();
                    empIdFromLUAU = luau.getEmployeeId();
                    getUserFromDatabase(empIdFromLUAU);
                    setUserFieldsToCovidReturnToWork();
                    Intent intentCovidReturnToWork = new Intent(mContext, CovidReturnToWork.class);
                    intentCovidReturnToWork.putExtra("DEPARTMENTID", departmentName[0]);
                    intentCovidReturnToWork.putExtra("EMPID", empId[0]);
                    intentCovidReturnToWork.putExtra("JOBTITLE", jobTitle[0]);
                    intentCovidReturnToWork.putExtra("EMAILID", emailId[0]);
                    intentCovidReturnToWork.putExtra("FIRSTNAME", firstName[0]);
                    intentCovidReturnToWork.putExtra("LASTNAME", lastName[0]);
                    mContext.startActivity(intentCovidReturnToWork);
                    break;
            }

        }

        private void getUserFromDatabase(String empIdFromLUAU) {
            OpenHelper dbOpenHelper;
            dbOpenHelper = new OpenHelper(mContext);
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

        private void setUserFieldsToPettyCashAuthorisation() {
            PettyCashAuthorisationHarareHelper pcam = PettyCashAuthorisationHarareHelper.getPettyCashAuthorisationInstance();
            pcam.setFirstname(firstName[0]);
            pcam.setSurname(lastName[0]);
            pcam.setEmployeeId(empId[0]);
            pcam.setDepartment(departmentName[0]);
            pcam.setDesignation(jobTitle[0]);
            pcam.setEmailId(emailId[0]);
        }

        private void setUserFieldsToCovidReturnToWork() {
            CovidReturnToWorkHelper creturn = CovidReturnToWorkHelper.getCovidReturnToWorkHelperInstance();
            creturn.setFirstname(firstName[0]);
            creturn.setSurname(lastName[0]);
            creturn.setEmployeeId(empId[0]);
            creturn.setDepartment(departmentName[0]);
            creturn.setDesignation(jobTitle[0]);
            creturn.setEmailId(emailId[0]);
        }
    }
}
