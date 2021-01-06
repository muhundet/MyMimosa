package zw.co.mimosa.mymimosa;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.Validator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import zw.co.mimosa.mymimosa.database.OpenHelper;
import zw.co.mimosa.mymimosa.utilities.LoggedInUserAccessUtility;

@RequiresApi(api = Build.VERSION_CODES.O_MR1)
public class LoginActivity extends AppCompatActivity  {
    Button loginButton;
    OpenHelper dbOpenHelper;
    String employeeId;
    String password;

    TextInputLayout inputLayoutUsername;
    TextInputLayout inputLayoutPassword;
    TextInputEditText editTextUsername;
    TextInputEditText editTextPassword;

    private boolean validateUsername() {
        String val = inputLayoutUsername.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";

        if (val.isEmpty()) {
            inputLayoutUsername.setError("Username can not be empty");
            return false;
        } else if (val.length() > 20) {
            inputLayoutUsername.setError("Username is too large!");
            return false;
        }
//        else if (!val.matches(checkspaces)) {
//            inputLayoutUsername.setError("No White spaces are allowed!");
//            return false;
//        }
        else {
            inputLayoutUsername.setError(null);
            inputLayoutUsername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = inputLayoutPassword.getEditText().getText().toString().trim();
        String checkPassword =
                //"^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])"       //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=S+$)" +           //no white spaces
                //".{4,}" +               //at least 4 characters
                //"$"
                ;

        if (val.isEmpty()) {
            inputLayoutPassword.setError("Password can not be empty");
            return false;
        }
        else if (!val.matches(checkPassword))
        {
            //inputLayoutPassword.setError("");
            return false;
        }
        else {
            inputLayoutPassword.setError(null);
            inputLayoutPassword.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbOpenHelper = new OpenHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputLayoutUsername = findViewById(R.id.usernameLayout);
        inputLayoutPassword = findViewById(R.id.passwordLayout);
        editTextUsername = findViewById(R.id.days_acrued);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                employeeId = editTextUsername.getText().toString().trim();
                password =editTextPassword.getText().toString().trim();
                if ( !validateUsername() | !validatePassword()) {

                    try {
                        Cursor cursor = dbOpenHelper.getLoginUser(employeeId);
                        if (cursor.moveToFirst()) {
                            String departmentName = cursor.getString(cursor.getColumnIndex("DEPTNAME"));
                            String empId = cursor.getString(cursor.getColumnIndex("EMPLOYEEID"));
                            String jobTitle = cursor.getString(cursor.getColumnIndex("JOBTITLE"));
                            String emailId = cursor.getString(cursor.getColumnIndex("EMAILID"));
                            String firstName = cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
                            String lastName = cursor.getString(cursor.getColumnIndex("LASTNAME"));

                            if (password.equals("Mimosa")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("EMPLOYEEID", empId);
                                intent.putExtra("LASTNAME", lastName);
                                LoggedInUserAccessUtility luau = LoggedInUserAccessUtility.getInstance();
                                luau.setEmployeeId(empId);
                                startActivity(intent);
                                finish();
                            } else {
                                inputLayoutPassword.setError("Incorrect password");
                                Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "No such user in database", Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        Toast.makeText(LoginActivity.this, "SQL Exception: no such user", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }


}