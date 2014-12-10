package uk.org.mattford.logicaldateconverter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    private final int MILLISECONDS_IN_A_DAY = 86400000;
    private final String logTag = "NAVDateConverter/MainActivity";

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
            // Do something with the date chosen by the user
            parentActivity.onDateSelected(Integer.toString(year)+"-"+Integer.toString(month+1)+"-"+Integer.toString(day));
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    No menu for this app.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    public void onSelectDateClick(View v) {
        DialogFragment datepicker = new DatePickerFragment();
        datepicker.show(getFragmentManager(), "datePicker");

    }

    public void onDateSelected(String selectedDate) {
        Log.v(logTag, selectedDate);
        Date dateToConvert = null;
        Date yearOneDate = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateToConvert = format.parse(selectedDate);
            yearOneDate = format.parse("0001-01-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffMSeconds = dateToConvert.getTime() - yearOneDate.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffMSeconds, TimeUnit.MILLISECONDS);
        Log.v(logTag, Long.toString(diffDays));
        diffDays = (diffDays * 2) + 737;
        Log.v(logTag, Long.toString(diffDays));
        //diffDays = Long.reverseBytes(diffDays);
        String converted = "0x"+Long.toHexString(diffDays);
        //converted = converted.replaceAll("0*$", "");
        TextView tv = (TextView)findViewById(R.id.convertedValue);
        tv.setText(getString(R.string.output_value_text,converted));

    }
}
