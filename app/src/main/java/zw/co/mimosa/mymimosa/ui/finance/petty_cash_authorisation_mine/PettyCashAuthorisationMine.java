package zw.co.mimosa.mymimosa.ui.finance.petty_cash_authorisation_mine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;

import zw.co.mimosa.mymimosa.MainActivity;
import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.data.acting_allowance_data.ActingAllowanceRequest;
import zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.PettyCashAuthorisationMineRequest;
import zw.co.mimosa.mymimosa.ui.hr.acting_allowance.ActingAllowanceActivity2;
import zw.co.mimosa.mymimosa.utilities.NumberToWordsConverter;

public class PettyCashAuthorisationMine extends AppCompatActivity {
    TextInputLayout inputLayoutAmountInFigures, inputLayoutAmountInWords, inputLayoutReasonForAdvance;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etDesignation;
    Spinner spinnerSection, spinnerCostCentre, spinnerManagementAccountingApprover;
    TextInputEditText etAmountInFigures, etAmountInWords, etReasonForAdvance;
    ProgressBar pgBarSubmit;
    Button btnSubmit;
    RadioGroup radioGroupCurrency;
    RadioButton radioButtonCurrency;
    PettyCashAuthorisationHelper pcam = PettyCashAuthorisationHelper.getPettyCashAuthorisationInstance();
    PettyCashAuthorisationMineRequest pettyCashAuthorisationMineRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petty_cash_authorisation_mine);

        inputLayoutAmountInFigures = findViewById(R.id.pcam_amount_in_figures);
        inputLayoutAmountInWords = findViewById(R.id.pcam_amount_in_words);
        inputLayoutReasonForAdvance = findViewById(R.id.pcam_reason_for_advance);

        etAmountInFigures = findViewById(R.id.et_pcam_amount_in_figures);
        etAmountInWords = findViewById(R.id.et_pcam_amount_in_words);
        etReasonForAdvance = findViewById(R.id.et_pcam_reason_for_advance);

        etFirstName = findViewById(R.id.et_pcam_first_name);
        etSurname = findViewById(R.id.et_pcam_surname);
        etMineNumber = findViewById(R.id.et_pcam_mine_number);
        etDepartment = findViewById(R.id.et_pcam_department);
        etDesignation = findViewById(R.id.et_pcam_designation);

        etFirstName.setText(pcam.getFirstname());
        etSurname.setText(pcam.getSurname());
        etMineNumber.setText(pcam.getEmployeeId());
        etDepartment.setText(pcam.getDepartment());
        etDesignation.setText(pcam.getDesignation());

        spinnerSection = findViewById(R.id.spinner_pcam_section);
        spinnerCostCentre = findViewById(R.id.spinner_pcam_cost_centre);
        spinnerManagementAccountingApprover = findViewById(R.id.spinner_pcam_management_accounting_approver);

        radioGroupCurrency = findViewById(R.id.radio_pcam_currency);

        pgBarSubmit = findViewById(R.id.progress_bar_pcam_submit);

        btnSubmit = findViewById(R.id.button_pcam_submit);

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

        ArrayAdapter<CharSequence> costCentreAdapter = ArrayAdapter.createFromResource(this, R.array.cost_centre_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCostCentre.setAdapter(costCentreAdapter);

        ArrayAdapter<CharSequence> managementAccountingApproverAdapter = ArrayAdapter.createFromResource(this, R.array.management_accounting_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManagementAccountingApprover.setAdapter(managementAccountingApproverAdapter);

        etAmountInFigures.addTextChangedListener(new TextWatcher() {
            String  ntwc;
            int figures = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etAmountInFigures.getText().toString().isEmpty()){
                    ntwc = "Zero";
                    figures = 0;
                }else {
                    figures = Integer.parseInt(etAmountInFigures.getText().toString());
                    ntwc =   NumberToWordsConverter.convert(figures);
                }
                etAmountInWords.setText(ntwc + " dollars only");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateAmountInFigures() && validateReasonForAdvance() && validateSpinnerSection() && validateSpinnerCostCentre() && validateRadioCurrency()){
                int selectedId = radioGroupCurrency.getCheckedRadioButtonId();
                radioButtonCurrency = findViewById(selectedId);
                pcam.setCurrency(radioButtonCurrency.getText().toString());
                pcam.setAmountInFigures(Long.parseLong(etAmountInFigures.getText().toString()));
                pcam.setAmountInWords(etAmountInWords.getText().toString());
                pcam.setCostCentre(spinnerCostCentre.getSelectedItem().toString());
                pcam.setManagementAccountingApprover(spinnerManagementAccountingApprover.getSelectedItem().toString());

                pcam.setPettyCashAuthorisationApprovers();

                String template = "Petty Cash Authorisation Form - Mine";
                String requester = pcam.getFirstname() + " " + pcam.getSurname();
                String subject = "Petty Cash Authorisation Form  for " + pcam.getFirstname() + " " + pcam.getSurname() + " (from android device)";
                String description = etReasonForAdvance.getText().toString();
                String firstname = pcam.getFirstname();
                String surname = pcam.getSurname();
                String employeeId = pcam.getEmployeeId();
                String currency = pcam.getCurrency();
                String amountInWords = pcam.getAmountInWords();
                long amountInFigures = pcam.getAmountInFigures();
                String costCentre = pcam.getCostCentre();
                String managementAccountingApprover = pcam.getManagementAccountingApprover();
                String approverStage1 = pcam.getApproverStage1();
                String approverStage2 = pcam.getApproverStage2();
                String approverStage3 = pcam.getApproverStage3();
                String approverStage4 = pcam.getApproverStage4();

                zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Template templateObj = new zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Template(template);
                zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Requester requesterObj = new zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Requester(requester);
                zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.UdfFields udfFields = new zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.UdfFields(firstname, surname, employeeId, currency, amountInFigures, amountInWords, costCentre, managementAccountingApprover, approverStage1, approverStage2, approverStage3, approverStage4);
                zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Request request = new zw.co.mimosa.mymimosa.data.petty_cash_authorisation_mine_data.Request(description, requesterObj, subject, templateObj, udfFields);

                pettyCashAuthorisationMineRequest = new PettyCashAuthorisationMineRequest(request);

                new AdvanceQueryTask().execute();
            }
            }
        });
    }

    public class AdvanceQueryTask extends AsyncTask<URL, Void, String> {
        String result;
        Gson gson = new Gson();
        String jsonStr = gson.toJson(pettyCashAuthorisationMineRequest);


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

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PettyCashAuthorisationMine.this);
                            dialogBuilder.setMessage("You have successfully applied for Petty CAsh")
                                    .setTitle("Submitted")
                                    .setPositiveButton(android.R.string.ok, null)
                                    .setIcon(R.drawable.checkmark);
                            dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PettyCashAuthorisationMine.this, MainActivity.class);
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
                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PettyCashAuthorisationMine.this);
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

    private boolean validateAmountInFigures() {
        String val = inputLayoutAmountInFigures.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAmountInFigures.setError("Amount in Figures cannot be empty");
            etAmountInFigures.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etAmountInFigures, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAmountInFigures.setError(null);
            inputLayoutAmountInFigures.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAmountInWords() {
        String val = inputLayoutAmountInWords.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutAmountInWords.setError("Amount in words cannot be empty");
            etAmountInWords.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etAmountInWords, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutAmountInWords.setError(null);
            inputLayoutAmountInWords.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateReasonForAdvance() {
        String val = inputLayoutReasonForAdvance.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutReasonForAdvance.setError("Reason for advance cannot be empty");
            etReasonForAdvance.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etReasonForAdvance, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }

        else {
            inputLayoutReasonForAdvance.setError(null);
            inputLayoutReasonForAdvance.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateRadioCurrency() {
        if (radioGroupCurrency.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Currency", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateCurrency:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerSection.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Your Section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateSpinnerCostCentre() {
        if (spinnerCostCentre.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Cost Centre", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerCostCentre:Nothing selected ");
            return false;
        } else {
            return true;
        }
    }
}