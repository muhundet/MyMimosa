package zw.co.mimosa.mymimosa.Pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import zw.co.mimosa.mymimosa.R;

public class TravelDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

        int actualMonth = month + 1;
        // Do something with the date chosen by the user
        TextInputEditText startDate = (TextInputEditText) getActivity().findViewById(R.id.et_creturn_travel_date);
        if(actualMonth<10 && dayOfMonth<10){
            startDate.setText(year + "/0" + actualMonth + "/" + "0"+ dayOfMonth);

        }else if(dayOfMonth<10){
            startDate.setText(year + "/" + actualMonth + "/" + "0"+ dayOfMonth );
        }else if(actualMonth<10){
            startDate.setText(year + "/0" + actualMonth + "/" + dayOfMonth );
        }else
            startDate.setText( year + "/" + actualMonth + "/" + dayOfMonth);


        //  Toast.makeText(getContext(), "Start Date Set " , Toast.LENGTH_LONG).show();
    }
}
