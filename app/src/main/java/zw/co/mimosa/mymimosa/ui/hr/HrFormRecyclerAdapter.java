package zw.co.mimosa.mymimosa.ui.hr;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.database.OpenHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

public class HrFormRecyclerAdapter extends RecyclerView.Adapter<HrFormRecyclerAdapter.HrFormViewHolder> {
    ArrayList<HrMenuModel> hrMenuList;
    Context mContext;

    public HrFormRecyclerAdapter(Context context, ArrayList<HrMenuModel> hrMenuList) {
        this.mContext = context;
        this.hrMenuList = hrMenuList;
    }

    @NonNull
    @Override
    public HrFormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.hr_menu_list_item_1, parent, false);

        return new HrFormViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HrFormViewHolder holder, int position) {
        HrMenuModel hrMenuModel = hrMenuList.get(position);
        holder.bind(hrMenuModel);

    }

    @Override
    public int getItemCount() {
        return hrMenuList.size();
    }

    public class HrFormViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
//        TextView tvHrFormMenu;
//        ImageView imageHrForm;
        MaterialButton btnHrMenuButton;



        public HrFormViewHolder(View itemView) {
            super(itemView);
//            tvHrFormMenu = itemView.findViewById(R.id.tv_hr_form_menu);
//            imageHrForm = itemView.findViewById(R.id.image_hr_form_menu);
            btnHrMenuButton = itemView.findViewById(R.id.btnHrMenuTitle);
            btnHrMenuButton.setOnClickListener(this);
        }

        public void bind(HrMenuModel hrMenuModel) {
            btnHrMenuButton.setText(hrMenuModel.getMenuTitle());
        }


        @Override
        public void onClick(View v) {

            final String[] departmentName = new String[1];
            final String[] empId = new String[1];
            final String[] jobTitle = new String[1];
            final String[] emailId = new String[1];
            final String[] firstName = new String[1];
            final String[] lastName = new String[1];

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setTitle("Select an Option below");
            String[] items = {"Leave","Advance"};
            int checkedItem = -1;
            alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                            alertDialog.setTitle("Choose an option");
                            String[] items = {"Self","On Behalf"};
                            int checkedItem = -1;
                            alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
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
                                            builder.setTitle("Enter Mine Number for");
                                            View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.text_input_mine_number, null, false);
                                            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
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
                            AlertDialog alert = alertDialog.create();
                            alert.setCanceledOnTouchOutside(false);
                            alert.show();
                            dialog.dismiss();
                            break;
                        case 1:
                            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(mContext);
                            alertDialog1.setTitle("Choose an option");
                            String[] items1 = {"Self","On Behalf"};
                            int checkedItem1 = -1;
                            alertDialog1.setSingleChoiceItems(items1, checkedItem1, new DialogInterface.OnClickListener() {
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
                                            builder.setTitle("Enter Mine Number for");
                                            View viewInflated = LayoutInflater.from(mContext).inflate(R.layout.text_input_mine_number, null, false);
                                            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
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
                }

                private void setUserFieldsToLeaveLeaveSelfFormHelper() {
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setFirstname(firstName[0]);
                    lfh.setSurname(lastName[0]);
                    lfh.setDepartment(departmentName[0]);
                    lfh.setDesignation(jobTitle[0]);
                    lfh.setWhatAreYouApplyingFor("leave");
                    lfh.setWhoAreYouApplyingFor("self");
                    lfh.setEmailId(empId[0]);
                }

                private void setUserFieldsToLeaveLeaveOnBehalfFormHelper() {
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setFirstname(firstName[0]);
                    lfh.setSurname(lastName[0]);
                    lfh.setDepartment(departmentName[0]);
                    lfh.setDesignation(jobTitle[0]);
                    lfh.setWhatAreYouApplyingFor("leave");
                    lfh.setWhoAreYouApplyingFor("onbehalf");
                    lfh.setEmailId(empId[0]);
                }

                private void setUserFieldsToLeaveAdvanceSelfFormHelper() {
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setFirstname(firstName[0]);
                    lfh.setSurname(lastName[0]);
                    lfh.setDepartment(departmentName[0]);
                    lfh.setDesignation(jobTitle[0]);
                    lfh.setWhatAreYouApplyingFor("advance");
                    lfh.setWhoAreYouApplyingFor("self");
                    lfh.setEmailId(empId[0]);
                }

                private void setUserFieldsToLeaveAdvanceOnBehalfFormHelper() {
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setFirstname(firstName[0]);
                    lfh.setSurname(lastName[0]);
                    lfh.setDepartment(departmentName[0]);
                    lfh.setDesignation(jobTitle[0]);
                    lfh.setWhatAreYouApplyingFor("advance");
                    lfh.setWhoAreYouApplyingFor("onbehalf");
                    lfh.setEmailId(empId[0]);
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
            });
            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

}
