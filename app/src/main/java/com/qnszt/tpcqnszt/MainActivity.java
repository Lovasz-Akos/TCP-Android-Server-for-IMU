package com.qnszt.tpcqnszt;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.qnszt.tpcqnszt.szerverstuff.EnumsAndStatics;
import com.qnszt.tpcqnszt.szerverstuff.OnTCPMessageRecievedListener;

import com.qnszt.tpcqnszt.TCPCommunicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnTCPMessageRecievedListener {

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
       
        TCPCommunicator writer =TCPCommunicator.getInstance();
        TCPCommunicator.addListener((OnTCPMessageRecievedListener) this);
        writer.init(1500);
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

    @Override
    public void onTCPMessageRecieved(String message) {
        // TODO Auto-generated method stub
        final String theMessage=message;
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try
                {
                    //EditText editTxt = (EditText)findViewById(R.id.majd ide jöhet egy label vagy valami");
                    //editTxt.setText(theMessage);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


    }

    public void someButtonClicked(View view)
    {
        JSONObject obj = new JSONObject();
        try
        {
            if(view.getId()==R.id.btn_startMeasurement)
            {

                obj.put(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON, EnumsAndStatics.MessageTypes.MessageFromServer);
                EditText txtContent = (EditText)findViewById(R.id.txt_measurementName);
                obj.put(EnumsAndStatics.MESSAGE_CONTENT_FOR_JSON, txtContent.getText().toString());
            }

            final JSONObject thingReadyForSend=obj;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    TCPCommunicator.writeToSocket(thingReadyForSend);
                }
            });
            thread.start();

        }
        catch(Exception e)
        {

        }

    }

}