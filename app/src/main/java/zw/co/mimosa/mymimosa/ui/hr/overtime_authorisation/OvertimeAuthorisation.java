package zw.co.mimosa.mymimosa.ui.hr.overtime_authorisation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.Pickers.DateOfBirthPicker;
import zw.co.mimosa.mymimosa.Pickers.FromTimePicker;
import zw.co.mimosa.mymimosa.Pickers.OvertimeDatePicker;
import zw.co.mimosa.mymimosa.Pickers.ToTimePicker;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.covid_business_data.CovidBusinessRequest;
import zw.co.mimosa.mymimosa.data.overtime_authorisation_data.OvertimeAuthorisationRequest;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidReturnScreeningHelper;
import zw.co.mimosa.mymimosa.ui.medical_services.covid_screening.CovidScreeningBusiness;

public class OvertimeAuthorisation extends AppCompatActivity {
    TextInputLayout inputLayoutSupervisor, inputLayoutDescription;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation, etDescription;
    Spinner spinnerSection, spinnerHos;
    TextInputEditText etSupervisor;
    EditText etOvertimeDate1, etHoursFrom1, etHoursTo1, etTotalHours1;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    ImageButton btnAddOvertime;
    TableLayout tableLayoutOvertime;
    TableRow tableRow1, tableRow2;
    OvertimeAuthorisationHelper oa = OvertimeAuthorisationHelper.getOvertimeAuthorisationInstance();
    OvertimeEntriesHelper oe = OvertimeEntriesHelper.getOvertimeEntriesInstance();
    long overtimeDate1, overtimeHoursFrom, overtimeHoursTo;
    OvertimeAuthorisationRequest overtimeAuthorisationRequest;

    EditText[] etsOvertimeDate = new EditText[11];
    EditText[] etsOvertimeHoursFrom = new EditText[11];
    EditText[] etsOvertimeHoursTo = new EditText[11];
    EditText[] etsOvertimeHoursTotal = new EditText[11];

    private TableLayout tableLayout;

    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_authorisation);
        final int[] i = {0};



        etFirstName = findViewById(R.id.et_oa_first_name);
        etSurname = findViewById(R.id.et_oa_surname);
        etMineNumber = findViewById(R.id.et_oa_mine_number);
        etDepartment = findViewById(R.id.et_oa_department);
        etDesignation = findViewById(R.id.et_oa_designation);

        tableLayoutOvertime = findViewById(R.id.table_layout_overtime);

        tableRow1 = findViewById(R.id.table_row_1);
        etOvertimeDate1 = findViewById(R.id.overtime_date_1);
        etHoursFrom1 = findViewById(R.id.overtime_hours_from_1);
        etHoursTo1 = findViewById(R.id.overtime_hours_to_1);
        etTotalHours1 = findViewById(R.id.overtime_total_1);


        inputLayoutSupervisor = findViewById(R.id.oa_supervisor);
        inputLayoutDescription = findViewById(R.id.oa_description);

        etSupervisor = findViewById(R.id.et_oa_supervisor);
        etDescription = findViewById(R.id.et_oa_description);

        spinnerHos = findViewById(R.id.spinner_oa_hos);
        spinnerSection = findViewById(R.id.spinner_oa_section);

        pgBarSubmit = findViewById(R.id.progress_bar_oa_submit);

        btnSubmit = findViewById(R.id.button_oa_submit);
        btnAddOvertime = findViewById(R.id.button_add_overtime);

        etFirstName.setText(oa.getFirstname());
        etSurname.setText(oa.getSurname());
        etMineNumber.setText(oa.getEmployeeId());
        etDepartment.setText(oa.getDepartment());
        etDesignation.setText(oa.getDesignation());


        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        ArrayAdapter<CharSequence> hosAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hos_array, android.R.layout.simple_spinner_item);
        hosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHos.setAdapter(hosAdapter);

        etOvertimeDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOvertimeDatePickerDialog(v);
            }
        });

        etHoursFrom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromTimePickerDialog(v);
            }
        });

        etHoursTo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToTimePickerDialog(v);
            }
        });
        btnAddOvertime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    System.out.println(counter);
                    View tableRow = LayoutInflater.from(OvertimeAuthorisation.this).inflate(R.layout.overtime_table_item, null, false);
                    EditText tvDateRow = tableRow.findViewById(R.id.overtime_date);
                    EditText tvHoursFromRow = tableRow.findViewById(R.id.overtime_hours_from);
                    EditText tvHoursToRow = tableRow.findViewById(R.id.overtime_hours_to);
                    EditText tvTotalHours = tableRow.findViewById(R.id.overtime_total);

                    tableLayout = findViewById(R.id.tableLayout);


                    switch(counter){
                        case 0:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate1(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom1(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo1(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal1(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;
                        case 1:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate2(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom2(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo2(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal2(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 2:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate3(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom3(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo3(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal3(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 3:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate4(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom4(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo4(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal4(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 4:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate5(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom5(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo5(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal5(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 5:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate6(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom6(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo6(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal6(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 6:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate7(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom7(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo7(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal7(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 7:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate5(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom8(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo8(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal8(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 8:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate9(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom9(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo9(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal9(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 9:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate10(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom10(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo10(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal10(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                        case 10:
                            tvDateRow.setText(etOvertimeDate1.getText().toString());
                            oe.setOtDate11(etOvertimeDate1.getText().toString());
                            tvHoursFromRow.setText(etHoursFrom1.getText().toString());
                            oe.setOtTimeFrom11(etHoursFrom1.getText().toString());
                            tvHoursToRow.setText(etHoursTo1.getText().toString());
                            oe.setOtTimeTo11(etHoursTo1.getText().toString());
                            tvTotalHours.setText(etTotalHours1.getText().toString());
                            oe.setOtTotal11(etTotalHours1.getText().toString());
                            etOvertimeDate1.setText("");
                            etHoursFrom1.setText("");
                            etHoursTo1.setText("");
                            etTotalHours1.setText("");
                            counter=counter+1;
                            break;

                    }


                    tableLayout.addView(tableRow);


            }


        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateSupervisor() && validateDescrption() && validateSpinnerSection() && validateSpinnerHos()) {
//                    String otDate = etOvertimeDate1.getText().toString();
//                    SimpleDateFormat otDateFormat = new SimpleDateFormat("yyyy/mm/dd");
//                    try {
//                        Date d = otDateFormat.parse(otDate);
//                        overtimeDate1 = d.getTime();
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

                    String template = "Overtime Authorisation Form";
                    String requester = oa.getFirstname() + " " + oa.getSurname();
                    String subject = "Overtime Authorisation for " + oa.getFirstname() + " " + oa.getSurname() + " (from android device)";
                    String description = etDescription.getText().toString();
                    String firstname = oa.getFirstname();
                    String surname = oa.getSurname();
                    String employeeId = oa.employeeId;
                    String department = "ICT";
                    String section = oa.getSection();
                    String emailId = oa.getEmailId();
                    String designation = oa.getDesignation();
                    String supervisor = etSupervisor.getText().toString();
                    String reason = etDescription.getText().toString();
                    long dateOfEngagement;
                    long overtimeDateOnly = 1621083;

                    String overtimeDateVar="15/05/2021";
                    String hoursFromVar = "15:00";
                    String hoursToVar = "15:00";
                    String hoursTotalVar="1";

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Template templateObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Template(template);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Requester(requester);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate1());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom1());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo1());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal1());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate2());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom2());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo2());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal2());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj2 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj3 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate4());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom4());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo4());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal4());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj4 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate5());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom5());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo5());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal5());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj5 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate6());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom6());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo6());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal6());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj6 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate7());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom7());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo7());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal7());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj7 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate8());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom8());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo8());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal8());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj8 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate9());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom9());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo9());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal9());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj9 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate10());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom10());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo10());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal10());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj10 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315 fullNameObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText315(requester);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309 empIdObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText309(employeeId);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316 yesObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnRadio316("yes", "347");
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310 occupationObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText310(designation);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658 overtimeDateObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText658(oe.getOtDate3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312 hoursFromObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText312(oe.getOtTimeFrom3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313 hoursToObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText313(oe.getOtTimeTo3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314 hoursTotalObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText314(oe.getOtTotal3());
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101 dateObj11 = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.QstnText2101(emailId);


                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res310 res310Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res310(fullNameObj, overtimeDateObj, empIdObj, yesObj, occupationObj, hoursFromObj,hoursTotalObj, hoursToObj);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res3606 res3606Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res3606(dateObj);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res311 res311Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res311(fullNameObj2, overtimeDateObj2, empIdObj2, yesObj2, occupationObj2, hoursFromObj2,hoursTotalObj2, hoursToObj2);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res312 res312Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res312(fullNameObj3, overtimeDateObj3, empIdObj3, yesObj3, occupationObj3, hoursFromObj3,hoursTotalObj3, hoursToObj3);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res313 res313Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res313(fullNameObj4, overtimeDateObj4, empIdObj4, yesObj4, occupationObj4, hoursFromObj4,hoursTotalObj4, hoursToObj4);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res303 res303Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res303(fullNameObj5, overtimeDateObj5, empIdObj5, yesObj5, occupationObj5, hoursFromObj5,hoursTotalObj5, hoursToObj5);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res304 res304Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res304(fullNameObj6, overtimeDateObj6, empIdObj6, yesObj6, occupationObj6, hoursFromObj6,hoursTotalObj6, hoursToObj6);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res305 res305Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res305(fullNameObj7, overtimeDateObj7, empIdObj7, yesObj7, occupationObj7, hoursFromObj7,hoursTotalObj7, hoursToObj7);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res306 res306Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res306(fullNameObj8, overtimeDateObj8, empIdObj8, yesObj8, occupationObj8, hoursFromObj8,hoursTotalObj8, hoursToObj8);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res307 res307Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res307(fullNameObj9, overtimeDateObj9, empIdObj9, yesObj9, occupationObj9, hoursFromObj9,hoursTotalObj9, hoursToObj9);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res308 res308Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res308(fullNameObj10, overtimeDateObj10, empIdObj10, yesObj10, occupationObj10, hoursFromObj10,hoursTotalObj10, hoursToObj10);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res309 res309Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Res309(fullNameObj11, overtimeDateObj11, empIdObj11, yesObj11, occupationObj11, hoursFromObj11,hoursTotalObj11, hoursToObj11);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Resources resourcesObj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Resources(res310Obj, res3606Obj, res311Obj, res312Obj, res313Obj, res303Obj, res304Obj, res305Obj, res306Obj, res307Obj, res308Obj,res309Obj);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.UdfDate306 udf3606Obj = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.UdfDate306(162100800);

                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.UdfFields(department, description,"Munyaradzi Chiyanike","Munyaradzi Chiyanike","HOD", "Yes", udf3606Obj);
                    zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Request request = new zw.co.mimosa.mymimosa.data.overtime_authorisation_data.Request(description, requesterObj, subject, templateObj, udfFields, resourcesObj);

                    overtimeAuthorisationRequest = new OvertimeAuthorisationRequest(request);
                    new overtimeAuthQueryTask().execute();

                }
            }
        });


    }

    public class overtimeAuthQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(overtimeAuthorisationRequest);


        @Override
        protected String doInBackground(URL... urls) {
            System.out.println(jsonStr);
            AndroidNetworking.post("https://servicedesk.mimosa.co.zw:8090/api/v3/requests")
                    .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                    .addBodyParameter("input_data",jsonStr)
                    .addBodyParameter("OPERATION_NAME","ADD_REQUEST")
                    .setTag(this)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            System.out.println("response");

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OvertimeAuthorisation.this);
                            dialogBuilder.setMessage(" Overtime Authorisation Submitted Successfully")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(OvertimeAuthorisation.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            dialogBuilder.show();
                        }

                        @Override
                        public void onError(ANError error) {

                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());

                            pgBarSubmit.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OvertimeAuthorisation.this);
//                                dialogBuilder.setMessage("Your application was not sent because of bad network, the system will resend when network is detected. No need to redo the request.")
                            dialogBuilder.setMessage("Your application was not sent because of bad network. Please retry.")
                                    .setTitle("Not Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.cancel);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(LeaveActivity2.this, MainActivity.class);
//                                        startActivity(intent);
                                }
                            });
                            dialogBuilder.show();

                        }
                    });

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView tvResponse = (TextView) findViewById(R.id.tvResponse);

            if (result == null) {
//                tvResponse.setText("Null response");
            }
            else {
                tvResponse.setText(result);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgBarSubmit.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }
    }

    public void showOvertimeDatePickerDialog(View v) {
        DialogFragment newFragment = new OvertimeDatePicker(v);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showFromTimePickerDialog(View v) {
        DialogFragment newFragment = new FromTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showToTimePickerDialog(View v) {
        DialogFragment newFragment = new ToTimePicker();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private boolean validateSupervisor() {
        String val = inputLayoutSupervisor.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutSupervisor.setError("Supervisor cannot be empty");
            etSupervisor.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etSupervisor, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutSupervisor.setError(null);
            inputLayoutSupervisor.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDescrption() {
        String val = inputLayoutDescription.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutDescription.setError("Supervisor cannot be empty");
            etDescription.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etDescription, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutDescription.setError(null);
            inputLayoutDescription.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerSection.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerHos() {
        if (spinnerSection.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select HOS", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }


}