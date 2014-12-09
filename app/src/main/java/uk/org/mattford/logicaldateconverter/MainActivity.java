package uk.org.mattford.logicaldateconverter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private final String logTag = "NAVDateConverter/MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


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

    public void onConvertClick(View v) {
        Log.v(logTag, "Called onConvertClick");
        DatePicker valueToConvertView = (DatePicker)findViewById(R.id.datePicker);
        int day = valueToConvertView.getDayOfMonth();
        int month = valueToConvertView.getMonth();
        int year = valueToConvertView.getYear();
        Date dateToConvert = null;
        Date yearOneDate = null;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateToConvert = format.parse(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));
            yearOneDate = format.parse("03/01/0001");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int diffMSeconds = (int) (dateToConvert.getTime() - yearOneDate.getTime());
        Log.v(logTag, Long.toString(dateToConvert.getTime()) + " - " + Long.toString(yearOneDate.getTime()));
        Log.v(logTag, Integer.toString(diffMSeconds));
        int diffDays = diffMSeconds / (1000 * 60 * 60 * 60 * 24);
        diffDays = (diffDays * 2) + 737;
        Log.v(logTag, Integer.toString(diffDays));

        String converted = Long.toHexString(diffDays);

        TextView tv = (TextView)findViewById(R.id.convertedValue);
        tv.setText("0x"+converted);
        Log.v(logTag,converted);

    }
}
