package com.qnszt.tpcqnszt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.StateSet;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qnszt.tpcqnszt.models.Measurement;
import com.qnszt.tpcqnszt.models.TCP_Server;

import org.json.JSONObject;

import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity{


    private static Handler handler = new Handler();
    public static Measurement measurement = new Measurement();
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TCP_Server.SERVERPORT = 1883;
        new TCP_Server().server_start();
        MainActivity.mainActivity = this;

    }

    public void startMeasurementClicked(){
        Measurement measurement = new Measurement();
        measurement.setName(((TextView)findViewById(R.id.txt_measurementName)).getText().toString());
        measurement.setDuration(((TextView)findViewById(R.id.txt_measurementDuration)).getText().toString());
        RadioButton selected = findViewById(((RadioGroup) findViewById(R.id.rdg_frequency)).getCheckedRadioButtonId());
        try{
            measurement.setDelay(selected.getText() == "50hz" ? 20 : 10); //TODO: Add more freq options
        }catch (Exception e){

        }
        MainActivity.measurement = measurement;
        Log.d("ye", "EEEEEEEEEEEEEEEEEEEEEEEEE");
        //TODO: TCP.send(measurement.name);TCP.send(measurement.duration);TCP.send(measurement.delay);TCP.send(sys.time);
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
}