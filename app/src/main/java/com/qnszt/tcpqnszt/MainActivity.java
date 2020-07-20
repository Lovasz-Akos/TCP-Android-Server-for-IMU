package com.qnszt.tcpqnszt;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Measurement measurement = new Measurement();
    public static MainActivity mainActivity;
    private static int textSizeOfList = 18;
    private static Handler handler = new Handler();
    private static int tapCounter = 0;
    List<String> messages = new ArrayList<String>();

    Date currentMilis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //theButton.setVisibility(View.VISIBLE);
        //theButton.setBackgroundColor(Color.TRANSPARENT);

        TCP_Server.SERVERPORT = 1883;
        new TCP_Server().server_start();
        MainActivity.mainActivity = this;


    }

    public void startMeasurementClicked() {
        Measurement measurement = new Measurement();
        measurement.setName(((TextView) findViewById(R.id.txt_measurementName)).getText().toString());
        measurement.setDuration(((TextView) findViewById(R.id.txt_measurementFrequency)).getText().toString());
        measurement.setDelay(((TextView) findViewById(R.id.txt_measurementFrequency)).getText().toString());
        MainActivity.measurement = measurement;
        ClientWorker.startMeasurement(measurement);
    }

    public void listIncomingMessages(String msg) {

        ListView msgList = findViewById(R.id.list_messageList);

        Calendar i = Calendar.getInstance();

        i.add(Calendar.DATE, 1);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

        String formazottDatum = format1.format(i.getTime());

        msg = StringUtils.strip(msg, "<>");


        if (msg != null && !msg.equals("null") && !msg.equals("") && !StringUtils.strip(msg, " ").equals("") && !msg.isEmpty()) {
            messages.add(0, formazottDatum + " | " + msg);

            if (tapCounter >= 10) {
                secretDarkmode(this.findViewById(R.id.secretBtn));
            } else {
                ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        TextView textView = view.findViewById(android.R.id.text1);

                        textView.setTextSize(textSizeOfList);
                        /*YOUR CHOICE OF COLOR*/
                        textView.setTextColor(Color.BLACK);
                        return view;
                    }
                };


                msgList.setAdapter(adapter);
                msgList.deferNotifyDataSetChanged();

            }

            ClientConnected();
        }

    }

    public void secretDarkmode(View view) {
        tapCounter++;

        Snackbar mySnackbar = Snackbar.make(view, "Welcome to the dark side    o((>ω< ))o", 1000);
        mySnackbar.setBackgroundTint(Color.parseColor("#c43e00"));
        mySnackbar.setTextColor(Color.BLACK);

        EditText et1 = findViewById(R.id.txt_measurementFrequency);
        EditText et2 = findViewById(R.id.txt_measurementName);
        EditText et3 = findViewById(R.id.txt_measurementDuration);

        Button btn = findViewById(R.id.btn_startMeasurement);
        TextView tv6 = findViewById(R.id.textView6);

        TextView tv4 = findViewById(R.id.textView4);
        TextView tv5 = findViewById(R.id.statCounter);
        CheckBox cb1 = findViewById(R.id.chk_testerLed);

        TextInputLayout container1 = findViewById(R.id.msr_name_container);
        TextInputLayout container2 = findViewById(R.id.msr_dur_container);
        TextInputLayout container3 = findViewById(R.id.msr_freq_container);

        if (tapCounter >= 10) {
            if (tapCounter == 10) {
                mySnackbar.show();
            }
            ListView list = findViewById(R.id.list_messageList);
            View bg = findViewById(R.id.bg);
            //TextView listText = list.findViewById(R.id.list_messageList);

            bg.setBackgroundColor(getResources().getColor(R.color.colorBackground_DarkTheme));

            findViewById(R.id.btn_startMeasurement).setBackgroundColor(getResources().getColor(R.color.colorAccent_DarkTheme));
            cb1.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            tv5.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            tv6.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));

            tv4.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            // tv11.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            et1.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            et2.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            et3.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            //<+item name="android:windowLightStatusBar">false</item>


            container1.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));
            container2.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));
            container3.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));

            container1.setBoxStrokeColor(getResources().getColor(R.color.colorAccent_DarkTheme));
            container2.setBoxStrokeColor(getResources().getColor(R.color.colorAccent_DarkTheme));
            container3.setBoxStrokeColor(getResources().getColor(R.color.colorAccent_DarkTheme));

            container1.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));
            container2.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));
            container3.setDefaultHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorMainText_DarkTheme)));

            container1.setBoxBackgroundColor(getResources().getColor(R.color.colorMainText_DarkTheme));
            list.setBackgroundColor(Color.TRANSPARENT);
            //listText.setTextColor(getResources().getColor(R.color.colorMainText_DarkTheme));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView textView = view.findViewById(android.R.id.text1);

                    textView.setTextSize(textSizeOfList);
                    /*YOUR CHOICE OF COLOR*/
                    textView.setTextColor(Color.WHITE);
                    return view;
                }
            };

            /*SET THE ADAPTER TO LISTVIEW*/
            list.setAdapter(adapter);
            list.deferNotifyDataSetChanged();

        }
        Log.d("OnClick", "secretDarkmode: clicked" + tapCounter);

        currentMilis = new Date();
        Long miliNumbers = currentMilis.getTime();
        Log.d("Test", "milis:" + miliNumbers);


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
        return super.onOptionsItemSelected(item);
    }

    public void ClientConnected() {

        int clientNum = ClientWorker.getClients().size();
        ((TextView) findViewById(R.id.statCounter)).setText(String.valueOf(clientNum));
        Log.d("MainActivity", "ClientConnected: " + clientNum);
    }
}
