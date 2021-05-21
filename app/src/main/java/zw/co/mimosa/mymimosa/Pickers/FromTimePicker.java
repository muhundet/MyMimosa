package zw.co.mimosa.mymimosa.Pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import zw.co.mimosa.mymimosa.R;

public class FromTimePicker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        EditText overtimeDate = (EditText) getActivity().findViewById(R.id.overtime_hours_from_1);

        if(hourOfDay<10 && minute<10){
            overtimeDate.setText("0" + hourOfDay + ":0" + minute);

        }else if(hourOfDay<10){
            overtimeDate.setText("0" + hourOfDay + ":" + minute);
        }else if(minute<10){
            overtimeDate.setText(hourOfDay + ":0" + minute);
        }else
            overtimeDate.setText(hourOfDay + ":" + minute);

    }
}
