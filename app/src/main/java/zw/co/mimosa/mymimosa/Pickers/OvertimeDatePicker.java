package zw.co.mimosa.mymimosa.Pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import zw.co.mimosa.mymimosa.R;

public class OvertimeDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private int i = 0;
    View v;
    int viewId;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
    }

    public OvertimeDatePicker(View v) {
        this.v = v;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {

        int actualMonth = month + 1;
        // Do something with the date chosen by the user
        EditText overtime_date =  (EditText) getActivity().findViewById(R.id.overtime_date_1);
        System.out.println(v.getId());
        System.out.println(month);
        if(actualMonth<10 && dayOfMonth<10){
            overtime_date.setText(year + "/0" + actualMonth + "/" + "0"+ dayOfMonth);

        }else if(dayOfMonth<10){
            overtime_date.setText(year + "/" + actualMonth + "/" + "0"+ dayOfMonth );
        }else if(actualMonth<10){
            overtime_date.setText(year + "/0" + actualMonth + "/" + dayOfMonth );
        }else
            overtime_date.setText( year + "/" + actualMonth + "/" + dayOfMonth);

    }
}

