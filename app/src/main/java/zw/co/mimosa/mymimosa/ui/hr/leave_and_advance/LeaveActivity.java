package zw.co.mimosa.mymimosa.ui.hr.leave_and_advance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.utilities.UriUtils;

public class LeaveActivity extends AppCompatActivity {
    Button btnNext;
    TextInputEditText etFirstName, etSurname, etMineNumber, etDepartment, etSection, etDesignation;
    RadioGroup radioTypeOfLeave;
    RadioButton radioButtonTypeOfleave;
    AutoCompleteTextView autoCompleteTextViewSection;
    Spinner spinnerSection;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICKFILE_RESULT_CODE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        etFirstName = findViewById(R.id.et_ea_first_name);
        etSurname = findViewById(R.id.et_ea_surname);
        etMineNumber = findViewById(R.id.et_ea_mine_number);
        etDepartment = findViewById(R.id.et_ea_department);
//        autoCompleteTextViewSection = (AutoCompleteTextView) findViewById(R.id.et_leave_section);
        spinnerSection = findViewById(R.id.spinner_child_level);
        etDesignation = findViewById(R.id.et_ea_designation);
        radioTypeOfLeave = findViewById(R.id.radio_type_of_leave);
        LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();

        radioTypeOfLeave.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId){
                    case R.id.radio_time_in_lieu:
                        lfh.setSickSelected(false);
                        System.out.println("vacation seleceted");
                        break;
                    case R.id.radio_sick:
        //                if (checkedId == R.id.radio_sick) {
                            System.out.println("Sick Selected");
                            AlertDialog.Builder builder = new AlertDialog.Builder(LeaveActivity.this);
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
                                    pickAPictureIntent();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                    case R.id.radio_cilol:
                        lfh.setSickSelected(false);
                        System.out.println("cash in lieu selected");
                        break;
                    case R.id.radio_vacational:
                        lfh.setSickSelected(false);
                        System.out.println("vacation seleceted");
                        break;
                    case R.id.radio_study:
                        lfh.setSickSelected(false);
                        System.out.println("study seleceted");
                        break;
                    case R.id.radio_unpaid:
                        lfh.setSickSelected(false);
                        System.out.println("unpaid seleceted");
                        break;
                    case R.id.radio_special:
                        lfh.setSickSelected(false);
                        System.out.println("special seleceted");
                        break;
                    case R.id.radio_maternity:
                        lfh.setSickSelected(false);
                        System.out.println("maternity seleceted");
                        break;

                }
            }
        });

        ArrayAdapter<CharSequence> sectionAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(sectionAdapter);

            Intent intent = getIntent();
                String department = intent.getStringExtra("DEPARTMENTID");
                String empid =intent.getStringExtra("EMPID");
                String jobtitle =intent.getStringExtra("JOBTITLE");
                String emailid =intent.getStringExtra("EMAILID");
                String firstname =intent.getStringExtra("FIRSTNAME");
                String lastname =intent.getStringExtra("LASTNAME");

                etFirstName.setText(firstname);
                etSurname.setText(lastname);
                etMineNumber.setText(empid);
                etDepartment.setText(department);
                etDesignation.setText(jobtitle);

        btnNext = findViewById(R.id.button_ea_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateTypeOfLeave() | !validateSpinnerSection()) {
                    int selectedId = radioTypeOfLeave.getCheckedRadioButtonId();
                    radioButtonTypeOfleave = findViewById(selectedId);
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setSection(spinnerSection.getSelectedItem().toString());
                    lfh.setTypeOfLeave(radioButtonTypeOfleave.getText().toString());
                    System.out.println(radioButtonTypeOfleave.getText().toString());
                    Intent intent = new Intent(LeaveActivity.this, LeaveActivity2.class);
                    startActivity(intent);
                }
            }
        });

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


    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_IMAGE_CAPTURE:
            if (requestCode == RESULT_OK ) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
            }
            break;
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    Uri selectedFile = data.getData();
                    System.out.println(selectedFile.toString());
                    String filePath = UriUtils.getPathFromUri(this,selectedFile);
                    LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                    lfh.setFilepath(filePath);
                    lfh.setSickSelected(true);
                    System.out.println("filepath: " + filePath);
//                    textFile.setText(FilePath);
                }
                break;
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    private boolean validateTypeOfLeave() {
        if (radioTypeOfLeave.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Leave Type", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateTypeOfLeave:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }

    private boolean validateSpinnerSection() {
        if (spinnerSection.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select Your Section", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "validateSpinnerSection:Nothing selected ");
            return true;
        } else {
            return false;
        }
    }


}