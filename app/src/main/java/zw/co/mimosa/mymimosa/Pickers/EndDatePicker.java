package zw.co.mimosa.mymimosa.Pickers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.ui.hr.leave_and_advance.LeaveFormHelper;

public class EndDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{
    long startDateLongCalc, endDateLongCalc;

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
        TextInputEditText endDate = (TextInputEditText) getActivity().findViewById(R.id.et_end_date);
        TextInputEditText startDate = getActivity().findViewById(R.id.et_start_date);
        if (actualMonth < 10 && dayOfMonth < 10) {
            endDate.setText(year + "/0" + actualMonth + "/" + "0" + dayOfMonth);

        } else if (dayOfMonth < 10) {
            endDate.setText(year + "/" + actualMonth + "/" + "0" + dayOfMonth);
        } else if (actualMonth < 10) {
            endDate.setText(year + "/0" + actualMonth + "/" + dayOfMonth);
        } else
            endDate.setText(year + "/" + actualMonth + "/" + dayOfMonth);

        SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy/mm/dd");
        try {
            Date d = endDateFormat.parse(endDate.getText().toString());
            endDateLongCalc = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy/mm/dd");
        try {
            Date d = startDateFormat.parse(startDate.getText().toString());
            startDateLongCalc = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // Toast.makeText(getContext(), "End Date Set ", Toast.LENGTH_LONG).show();

        long diff = endDateLongCalc - startDateLongCalc;
        if (endDateLongCalc < startDateLongCalc) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Your end date cannot be earlier than start date")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            endDate.setText("");
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("You are applying for " + ((int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1) + " days")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LeaveFormHelper lfh = LeaveFormHelper.getLeaveFormHelperInstance();
                                lfh.setNumberOfDays((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
