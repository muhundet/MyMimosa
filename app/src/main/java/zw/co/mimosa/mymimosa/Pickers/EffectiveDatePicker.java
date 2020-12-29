package zw.co.mimosa.mymimosa.Pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import zw.co.mimosa.mymimosa.R;

public class EffectiveDatePicker extends DialogFragment
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
        TextInputEditText effectiveDate = (TextInputEditText) getActivity().findViewById(R.id.et_aa_effective_date);
        if(actualMonth<10 && dayOfMonth<10){
            effectiveDate.setText(year + "/0" + actualMonth + "/" + "0"+ dayOfMonth);

        }else if(dayOfMonth<10){
            effectiveDate.setText(year + "/" + actualMonth + "/" + "0"+ dayOfMonth );
        }else if(actualMonth<10){
            effectiveDate.setText(year + "/0" + actualMonth + "/" + dayOfMonth );
        }else
            effectiveDate.setText( year + "/" + actualMonth + "/" + dayOfMonth);

    }
}

