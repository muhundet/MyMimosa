package zw.co.mimosa.mymimosa.ui.hr.educational_assistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.educational_assistance_data.EducationalAssistanceRequest;
import zw.co.mimosa.mymimosa.data.educational_assistance_data.UdfDate686;
import zw.co.mimosa.mymimosa.utilities.UriUtils;

public class EducationalAssistanceActivity2 extends AppCompatActivity {
    RadioGroup radioGroupCurrency;
    RadioButton radioButtonCurrency;
    TextInputLayout inputLayoutReceiptValue, inputLayoutInvoiceValue;
    TextInputEditText etInvoiceValue, etReceiptvalue;
    Button btnSubmit, btnAttachDocuments;
    Spinner spinnerEAApprover;
    TextView tvAttachmentRecorder;
    ProgressBar pgBarSubmit;
    EducationalAssistanceHelper educationalAssistanceHelper = EducationalAssistanceHelper.getEducationalAssistanceInstance();
    public EducationalAssistanceRequest educationalAssistanceRequest;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICKFILE_RESULT_CODE =2;
    boolean isReceiptAttachment=false;
    boolean isInvoiceAttachment=false;
    String attachmentSelection;
    List<File> list = new ArrayList<>();
    int attachmentCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_assistance2);
        radioGroupCurrency = findViewById(R.id.radio_ea_currency);

//        int selectedId = radioGroupCurrency.getCheckedRadioButtonId();
//        radioButtonCurrency = findViewById(selectedId);

        inputLayoutInvoiceValue = findViewById(R.id.ea_invoice_value);
        inputLayoutReceiptValue = findViewById(R.id.ea_receipt_value);

        etInvoiceValue = findViewById(R.id.et_ea_invoice_value);
        etReceiptvalue = findViewById(R.id.et_ea_receipt_value);

        spinnerEAApprover = findViewById(R.id.spinner_ea_approver);

        tvAttachmentRecorder = findViewById(R.id.tvAttachmentRecorder);

        pgBarSubmit = findViewById(R.id.progress_bar_ea_submit);

        btnSubmit = findViewById(R.id.button_ea_submit);
        btnAttachDocuments = findViewById(R.id.button_attach_documents);


        ArrayAdapter<CharSequence> hrAdapter = ArrayAdapter.createFromResource(this, R.array.approver_hr_array, android.R.layout.simple_spinner_item);
        hrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEAApprover.setAdapter(hrAdapter);

        btnAttachDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EducationalAssistanceActivity2.this);
                builder.setMessage("File Upload Options")
                        .setCancelable(false)
                        .setPositiveButton("Take a photo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dispatchTakePictureIntent();
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("Upload from phone", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        attachmentSelection = "invoice";
                        pickAPictureIntent();
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCurrency() && validateAttachments () && validateSpinnerApproverHr() && (validateInvoiceValue() | validateReceiptValue())) {
                    int selectedId = radioGroupCurrency.getCheckedRadioButtonId();
                    radioButtonCurrency = findViewById(selectedId);
                    educationalAssistanceHelper.setApprover(spinnerEAApprover.getSelectedItem().toString());
//                educationalAssistanceHelper.setApproverStage1(spinnerEAApprover.getSelectedItem().toString());
                    educationalAssistanceHelper.setEAApprovers();
                    System.out.println();
                    String template = "Educational Assistance Form";
                    String requester = educationalAssistanceHelper.getFirstname() + " " + educationalAssistanceHelper.getSurname();
                    String subject = "Educational Assistance for " + educationalAssistanceHelper.getFirstname() + " " + educationalAssistanceHelper.getSurname() + " (from android device)";
                    String description = "Educational Assistance Form Description";
                    String firstname = educationalAssistanceHelper.getFirstname();
                    String surname = educationalAssistanceHelper.getSurname();
                    String employeeId = educationalAssistanceHelper.getEmployeeId();
                    String department = educationalAssistanceHelper.getDepartment();
                    String section = educationalAssistanceHelper.getSection();
                    String emailId = educationalAssistanceHelper.getEmailId();
                    String designation = educationalAssistanceHelper.getDesignation();
                    String jobGrade = "L";
                    String childFirstName = educationalAssistanceHelper.getChildFirstName();
                    String childSurname = educationalAssistanceHelper.getChildSurname();
                    long childDob = educationalAssistanceHelper.getChildDob();
                    String childSchoolName = educationalAssistanceHelper.getChildSchoolName();
                    String childLevel = educationalAssistanceHelper.getChildLevel();
                    String approver = educationalAssistanceHelper.getApprover();
                    String udf_sline_349 = educationalAssistanceHelper.getApproverStage1();
                    long dateOfEngagement = educationalAssistanceHelper.getDateOfEngagement();
                    String currency = radioButtonCurrency.getText().toString();
                    System.out.println(currency);
                    List<String> attachements = new ArrayList<>();
                    attachements.add("Invoice");
                    attachements.add("Receipt");
                    int invoiceValue = educationalAssistanceHelper.getInvoiceValue();
                    int receiptValue = educationalAssistanceHelper.getReceiptValue();

                    UdfDate686 udfDate686 = new UdfDate686(childDob);
                    zw.co.mimosa.mymimosa.data.educational_assistance_data.UdfDate324 udfDate324 = new zw.co.mimosa.mymimosa.data.educational_assistance_data.UdfDate324(1551462180000L);
                    zw.co.mimosa.mymimosa.data.educational_assistance_data.Template templateObj = new zw.co.mimosa.mymimosa.data.educational_assistance_data.Template(template);
                    zw.co.mimosa.mymimosa.data.educational_assistance_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.educational_assistance_data.Requester(requester);
                    zw.co.mimosa.mymimosa.data.educational_assistance_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.educational_assistance_data.UdfFields(firstname, surname, employeeId, department, section, designation, invoiceValue, receiptValue, childFirstName, childSurname, childSchoolName, childLevel, jobGrade, currency, approver, attachements, udfDate324, udfDate686, udf_sline_349);
                    zw.co.mimosa.mymimosa.data.educational_assistance_data.Request request = new zw.co.mimosa.mymimosa.data.educational_assistance_data.Request(subject, description, requesterObj, templateObj, udfFields);
                    educationalAssistanceRequest = new EducationalAssistanceRequest(request);
                    new EducationalAssistanceQueryTask().execute();
//                }
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == RESULT_OK ) {
//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);

                    Uri selectedFile = data.getData();
                    String filePath = UriUtils.getPathFromUri(this,selectedFile);
                    EducationalAssistanceHelper eah = EducationalAssistanceHelper.getEducationalAssistanceInstance();

                    isInvoiceAttachment=true;
                    eah.setInvoiceFilePath(filePath);
                    eah.setInvoiceSelected(true);
                    System.out.println("Camera filepath: " + filePath);
                    File f = new File(filePath);
                    list.add(f);
                    attachmentCounter = attachmentCounter + 1;
                    tvAttachmentRecorder.setText(attachmentCounter + " file(s) attached");
                }else{
                    System.out.println("Nothing from camera");
                }
                break;
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                  Uri selectedFile = data.getData();
                    String filePath = UriUtils.getPathFromUri(this,selectedFile);
                    EducationalAssistanceHelper eah = EducationalAssistanceHelper.getEducationalAssistanceInstance();

                            isInvoiceAttachment=true;
                            eah.setInvoiceFilePath(filePath);
                            eah.setInvoiceSelected(true);
                            System.out.println("Invoice filepath: " + filePath);
                            File f = new File(filePath);
                            list.add(f);
                            attachmentCounter = attachmentCounter + 1;
                            tvAttachmentRecorder.setText(attachmentCounter + " file(s) attached");
                            break;
                }
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }

    private void pickAPictureIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,PICKFILE_RESULT_CODE);
    }

    private boolean validateInvoiceValue() {
        String val = inputLayoutInvoiceValue.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            etInvoiceValue.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etInvoiceValue, InputMethodManager.SHOW_IMPLICIT);
            return false;
        } else if (val.length() > 20) {
            inputLayoutInvoiceValue.setError("Invoice value is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutInvoiceValue.setError(null);
            inputLayoutInvoiceValue.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateReceiptValue() {
        String val = inputLayoutReceiptValue.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            etReceiptvalue.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etReceiptvalue, InputMethodManager.SHOW_IMPLICIT);
            return false;
        } else if (val.length() > 20) {
            inputLayoutReceiptValue.setError("Receipt value is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutReceiptValue.setError(null);
            inputLayoutReceiptValue.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateCurrency() {
        if (radioGroupCurrency.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Currency", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateCurrency:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAttachments() {
        if (attachmentCounter<1) {
            Toast.makeText(this, "Make sure you have attached at least a supporting document", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateAttachment:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerApproverHr() {
        if (spinnerEAApprover.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Approver", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerApprover:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }


    public class EducationalAssistanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(educationalAssistanceRequest);
        CountDownLatch countDownLatch;

        public String getRealPathFromURI(Uri contentURI, Activity context) {
            String[] projection = { MediaStore.Images.Media.DATA };
            @SuppressWarnings("deprecation")
            Cursor cursor = context.managedQuery(contentURI, projection, null,
                    null, null);
            if (cursor == null)
                return null;
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                String s = cursor.getString(column_index);
                // cursor.close();
                return s;
            }

            return null;
        }

        @Override
        protected String doInBackground(URL... urls) {
        countDownLatch = new CountDownLatch(60);
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

                            System.out.println(response.toString());
                            String result="";
                            String status_code="";
                            String success="";
                            try {
                                JSONObject jobj = response.getJSONObject("request");
                                JSONObject response_jobj=response.getJSONObject("response_status");
                                result = jobj.get("id").toString();
                                status_code=response_jobj.get("status_code").toString();
                                System.out.println("This is the id: " +status_code);

                                    for(int i = 0; i<list.size();i++) {
                                        AndroidNetworking.upload("https://servicedesk.mimosa.co.zw:8090/api/v3/attachment")
                                                .addHeaders("TECHNICIAN_KEY", "5775EFB0-AAB8-437A-8888-A330875F2B8D")
                                                .addMultipartFile("image", list.get(i))
                                                .addMultipartParameter("OPERATION_NAME", "ADD_ATTACHMENT")
                                                .addMultipartParameter("input_data", "{\n" +
                                                        "\t\"attachment\": {\n" +
                                                        "\t\t\"request\": {\n" +
                                                        "\t\t\t\"id\": \"" + result + "\"\n" +
                                                        "\t\t}\n" +
                                                        "\t}\n" +
                                                        "}")
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
                                                        System.out.println("Attachment" + response.toString());
                                                    }

                                                    @Override
                                                    public void onError(ANError error) {
                                                        System.out.println("Attachment " + error.toString());
                                                    }
                                                });
                                    }
//                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(EducationalAssistanceActivity2.this);
                            builder.setMessage("You have successfully applied for Educational Assistance. Your request ID is: "+result)
                                    .setTitle("Success")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onError(ANError error) {
                            Log.d("TAG", "onError errorCode : " + error.getErrorCode());
                            Log.d("TAG", "onError errorBody : " + error.getErrorBody());
                            Log.d("TAG", "onError errorDetail : " + error.getErrorDetail());

                            if (!error.getErrorDetail().equals("connectionError")){
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EducationalAssistanceActivity2.this);
                                dialogBuilder.setMessage("You have successfully applied for Educational Assistance")
                                        .setTitle("Submitted")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(R.drawable.checkmark);
                                dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(EducationalAssistanceActivity2.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                dialogBuilder.show();
                            }
                            else {

                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EducationalAssistanceActivity2.this);
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

            pgBarSubmit.setVisibility(View.INVISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pgBarSubmit.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}

