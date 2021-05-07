package zw.co.mimosa.mymimosa.ui.transport.buss_pass_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.bus_pass_data.BusPassRequest;
import zw.co.mimosa.mymimosa.data.petty_cash_authorisation_harare_data.PettyCashAuthorisationHarareRequest;
import zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.PettyCashAuthorisationMineRequest;
import zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine.PettyCashAuthorisationHelper;
import zw.co.mimosa.mymimosa.ui.harare_office.petty_cash_authorisation_harare_office.PettyCashAuthorisationHarare;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveActivity2;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveFormHelper;
import zw.co.mimosa.mymimosa.utilities.UriUtils;

public class BusPassApplication extends AppCompatActivity {
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;

    TextInputLayout inputLayoutIdNumber, inputLayoutCompanyName, inputLayoutApplyForName,
            inputLayoutApplyForIdNumber, inputLayoutApplyForRelationship, inputLayoutApplyForSchool;

    Spinner spinnerSection, spinnerApplyForLevel, spinnerHrApprover;

    TextInputEditText etIdNumber, etCompanyName, etApplyForName, etApplyforIdNumber, etApplyForRelationship, etApplyForSchool;
    TextView tvAttach;
    ProgressBar pgBarSubmit;
    Button btnSubmit, btnAttach;
    RadioGroup radioGroupType, radioGroupApplicant;
    RadioButton radioButtonType, radioButtonApplicant;

    BusPassApplicationHelper bpa = BusPassApplicationHelper.getBusPassApplicationInstance();
    BusPassRequest busPassRequest;
    private static final String TAG = "BusPassApplication";
    static final int PICKFILE_RESULT_CODE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pass_application);
        etFirstName = findViewById(R.id.et_bpa_first_name);
        etSurname = findViewById(R.id.et_bpa_surname);
        etMineNumber = findViewById(R.id.et_bpa_mine_number);
        etDepartment = findViewById(R.id.et_bpa_department);
        etDesignation = findViewById(R.id.et_bpa_designation);

        etFirstName.setText(bpa.getFirstname());
        etSurname.setText(bpa.getSurname());
        etMineNumber.setText(bpa.getEmployeeId());
        etDepartment.setText(bpa.getDepartment());
        etDesignation.setText(bpa.getDesignation());

        inputLayoutIdNumber = findViewById(R.id.bpa_id_number);
        inputLayoutCompanyName = findViewById(R.id.bpa_company_name);
        inputLayoutApplyForName = findViewById(R.id.bpa_applyfor_name);
        inputLayoutApplyForIdNumber = findViewById(R.id.bpa_applyfor_id_dob);
        inputLayoutApplyForRelationship = findViewById(R.id.bpa_applyfor_relationship);
        inputLayoutApplyForSchool = findViewById(R.id.bpa_applyfor_school);

        etIdNumber = findViewById(R.id.et_bpa_id_number);
        etCompanyName = findViewById(R.id.et_bpa_company_name);
        etApplyforIdNumber = findViewById(R.id.et_bpa_applyfor_id_dob);
        etApplyForName = findViewById(R.id.et_bpa_applyfor_name);
        etApplyForRelationship = findViewById(R.id.et_bpa_applyfor_relationship);
        etApplyForSchool = findViewById(R.id.et_bpa_applyfor_school);

        tvAttach = findViewById(R.id.tv_bpa_attach);
        spinnerSection = findViewById(R.id.spinner_bpa_section);
        spinnerApplyForLevel = findViewById(R.id.spinner_bpa_level);
        spinnerHrApprover = findViewById(R.id.spinner_bpa_hr_approver);

        radioGroupType = findViewById(R.id.radio_group_bpa_type);
        radioGroupApplicant = findViewById(R.id.radio_group_bpa_applicant);


        pgBarSubmit = findViewById(R.id.progress_bar_bpa_submit);

        btnSubmit = findViewById(R.id.button_bpa_submit);
        btnAttach = findViewById(R.id.button_bpa_attach);

        ArrayAdapter<CharSequence> bpaSectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        bpaSectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(bpaSectionAdapter);

        ArrayAdapter<CharSequence> bpaTypeAdapter = ArrayAdapter.createFromResource(this, R.array.child_level_array, android.R.layout.simple_spinner_item);
        bpaTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerApplyForLevel.setAdapter(bpaTypeAdapter);

        ArrayAdapter<CharSequence> hrApproverAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrApproverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHrApprover.setAdapter(hrApproverAdapter);

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickAPictureIntent();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateIdNumber() && validateCompanyName() && validateApplyForName()
                        && validateApplyForIdNumber() && validateApplyForRelationship()
                        && validateApplyForSchool() && validateRadioApplicant()
                        &&  validateRadioType() && validateSpinnerLevel() && validateSpinnerHrApproval() && validateSpinnerSection()){
                    int selectedId = radioGroupApplicant.getCheckedRadioButtonId();
                    radioButtonApplicant = findViewById(selectedId);
                    int selectedIdType = radioGroupType.getCheckedRadioButtonId();
                    radioButtonType = findViewById(selectedIdType);


                    String template = "Application for Bus Pass";
                    String requester = bpa.getFirstname() + " " + bpa.getSurname();
                    String subject = "Bus Pass Application for " + bpa.getFirstname() + " " + bpa.getSurname() + " (from android device)";
                    String description = "Application for bus pass";
                    String firstname = bpa.getFirstname();
                    String surname = bpa.getSurname();
                    String employeeId = bpa.getEmployeeId();
                    String department = bpa.getDepartment();
                    String section = spinnerSection.getSelectedItem().toString();
                    String designation = Objects.requireNonNull(etDesignation.getText()).toString();
                    String idNumber = etIdNumber.getText().toString();
                    String companyName = etCompanyName.getText().toString();
                    String busPassType = radioButtonType.getText().toString();
                    String applicant = radioButtonApplicant.getText().toString();
                    String applyForName = etApplyForName.getText().toString();
                    String applyForIdNumber = etApplyforIdNumber.getText().toString();
                    String applyForRelationship = etApplyForRelationship.getText().toString();
                    String applyForSchool = etApplyForSchool.getText().toString();
                    String childLevel = spinnerApplyForLevel.getSelectedItem().toString();
                    String hrApprover = spinnerHrApprover.getSelectedItem().toString();
                    String terms = "Yes, I Agree";


                    zw.co.mimosa.mymimosa.data.bus_pass_data.Template templateObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.Template(template);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.Requester(requester);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.QstnSelect3908 applyForLevelObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.QstnSelect3908(childLevel);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText315 applyForNameObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText315(applyForName);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3002 applyForIdObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3002(applyForIdNumber);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3601 applyForRelationshipObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3601(applyForRelationship);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3602 applyForSchoolObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.QstnText3602(applyForSchool);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.Res3061 res3061Obj = new zw.co.mimosa.mymimosa.data.bus_pass_data.Res3061(applyForNameObj, applyForRelationshipObj, applyForSchoolObj, applyForLevelObj,applyForIdObj);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.Resources resourcesObj = new zw.co.mimosa.mymimosa.data.bus_pass_data.Resources(res3061Obj);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.bus_pass_data.UdfFields(section, department, hrApprover, "stage1", "stage2", "stage3", firstname, idNumber, surname, designation, employeeId,"Yes, I agree", busPassType, applicant, department);
                    zw.co.mimosa.mymimosa.data.bus_pass_data.Request request = new zw.co.mimosa.mymimosa.data.bus_pass_data.Request(description, requesterObj, subject, templateObj, udfFields, resourcesObj);

                    busPassRequest = new BusPassRequest(request);

                    new BusPassQueryTask().execute();

                }

            }
        });


    }

    private void pickAPictureIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,PICKFILE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                 if(resultCode==RESULT_OK){
                    Uri selectedFile = data.getData();
                    System.out.println(selectedFile.toString());
                    String filePath = UriUtils.getPathFromUri(this,selectedFile);
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    BusPassApplicationHelper bpa = BusPassApplicationHelper.getBusPassApplicationInstance();
                    bpa.setFilePath(filePath);
                    System.out.println("filepath bus pass: " + filePath);
                    tvAttach.setText("File Uploaded successfully");
//                    textFile.setText(FilePath);
                }

    }

    public class BusPassQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(busPassRequest);


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
                                String status_code;
                                try {
                                    JSONObject jobj = response.getJSONObject("request");
                                    JSONObject response_jobj = response.getJSONObject("response_status");
                                    result = jobj.get("id").toString();
                                    String attachmentJsonString = "{\n" +
                                            "\t\"attachment\": {\n" +
                                            "\t\t\"request\": {\n" +
                                            "\t\t\t\"id\": \"" + result + "\"\n" +
                                            "\t\t}\n" +
                                            "\t}\n" +
                                            "}";
                                    status_code = response_jobj.get("status_code").toString();
                                    System.out.println("This is the id: " + status_code);

//                                    File file = new File("/sdcard/DCIM/Camera/attachment.jpg");
                                    File file = new File(bpa.getFilePath());
                                    AndroidNetworking.upload("https://servicedesk.mimosa.co.zw:8090/api/v3/attachment")
                                            .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                                            .addMultipartFile("image", file)
                                            .addMultipartParameter("OPERATION_NAME", "ADD_ATTACHMENT")
                                            .addMultipartParameter("input_data", attachmentJsonString)
                                            .setPriority(Priority.HIGH)
                                            .build()
                                            .setUploadProgressListener(new UploadProgressListener() {
                                                @Override
                                                public void onProgress(long bytesUploaded, long totalBytes) {
                                                    // do anything with progress
                                                }
                                            })
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    System.out.println("Attachment for sick success" + response.toString());
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(BusPassApplication.this);
                                                    builder.setMessage("You have successfully applied Bus Pass. Picture submitted ")
                                                            .setTitle("Success")
                                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent(BusPassApplication.this, MainActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            })
                                                            .setIcon(R.drawable.checkmark);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    System.out.println("Attachment not success " + error.toString());
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(BusPassApplication.this);
                                                    builder.setMessage("Attachment failed")
                                                            .setTitle("Attachment Failure")
                                                            .setPositiveButton(android.R.string.ok, null)
                                                            .setIcon(R.drawable.checkmark);
                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                        }

                        @Override
                        public void onError(ANError error) {

                            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
                            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
                            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());

                            String leaveJsonString = "leaveId_";
                            String currentDate = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault()).format(new Date());
                            System.out.println(currentDate);
                            leaveJsonString = leaveJsonString + currentDate;

                            pgBarSubmit.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BusPassApplication.this);
//                                dialogBuilder.setMessage("Your application was not sent because of bad network, the system will resend when network is detected. No need to redo the request.")
                            dialogBuilder.setMessage("Your application was not sent because of bad network. Please retry.")
                                    .setTitle("Not Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.cancel);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(BusPassApplication.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            dialogBuilder.show();
//                            }

                        }
                    });

            return result;
        }

        private void saveJson(String jsonFileName){
            try {
//                File mainDir = LeaveActivity2.this.getDir("MainFolder", Context.MODE_PRIVATE);
                File fileMain = new File(BusPassApplication.this.getFilesDir(), String.valueOf(Context.MODE_PRIVATE));
                boolean isDirMade = fileMain.mkdir();
                System.out.println("Directory made: " + isDirMade );
                File file = new File(fileMain, jsonFileName + ".json");
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(jsonStr);
                bufferedWriter.close();
                System.out.println("json file saved success in: " + file.getPath());
            }catch(Exception e){
                e.printStackTrace();
            }
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

    private boolean validateIdNumber () {
        String val = inputLayoutIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutIdNumber.setError("Id Number cannot be empty");
            etIdNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etIdNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutIdNumber.setError(null);
            inputLayoutIdNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCompanyName () {
        String val = inputLayoutIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutCompanyName.setError("Company name cannot be empty");
            etCompanyName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etCompanyName, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutCompanyName.setError(null);
            inputLayoutCompanyName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForName() {
        String val = inputLayoutApplyForName.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForName.setError("Id number cannot be empty");
            etApplyForName.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForName, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForName.setError(null);
            inputLayoutApplyForName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForIdNumber() {
        String val = inputLayoutApplyForIdNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForIdNumber.setError("Id number cannot be empty");
            etApplyforIdNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyforIdNumber, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForIdNumber.setError(null);
            inputLayoutApplyForIdNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForRelationship() {
        String val = inputLayoutApplyForRelationship.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForRelationship.setError("Id number cannot be empty");
            etApplyForRelationship.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForRelationship, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForRelationship.setError(null);
            inputLayoutApplyForRelationship.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateApplyForSchool() {
        String val = inputLayoutApplyForSchool.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutApplyForSchool.setError("Id number cannot be empty");
            etApplyForSchool.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etApplyForSchool, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutApplyForSchool.setError(null);
            inputLayoutApplyForSchool.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRadioType() {
        if (radioGroupType.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Pass Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateType:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateRadioApplicant() {
        if (radioGroupApplicant.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Applicant type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateApplicant:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerHrApprover.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerLevel() {
        if (spinnerApplyForLevel.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Level of child", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerLevel:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerHrApproval() {
        if (spinnerHrApprover.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Hr Approver", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerHrApprover:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }


}