package uk.org.mattford.logicaldateconverter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        MainActivity parentActivity = null;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            parentActivity = (MainActivity) getActivity();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            parentActivity.onDateSelected(year, month+1, day);
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        onDateSelected(year, month+1, day);
    }

    public void onSelectDateClick(View v) {
        new DatePickerFragment().show(getFragmentManager(), "datePicker");
    }

    public void onDateSelected(int year, int month, int day) {
        DateTime dateToConvert = null;
        DateTime yearOneDate = null;
        dateToConvert = new DateTime(year, month, day, 0, 0, 0);
        yearOneDate = new DateTime(0001,01,03, 0, 0, 0);

        int diffDays = Days.daysBetween(yearOneDate, dateToConvert).getDays();

        diffDays = (diffDays * 2) + 737;

        diffDays = Integer.reverseBytes(diffDays);
        String converted = Integer.toHexString(diffDays);
        converted = converted.replaceAll("0*$", "");
        converted = "0x"+converted.toUpperCase()+"00";
        TextView tv = (TextView)findViewById(R.id.convertedValue);
        tv.setText(converted);

    }
}
