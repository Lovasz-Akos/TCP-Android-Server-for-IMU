package com.qnszt.tpcqnszt;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qnszt.tpcqnszt.models.Measurement;

import org.json.JSONObject;

import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements OnTCPMessageRecievedListener {

    TCPCommunicator writer =TCPCommunicator.getInstance();

    private static Handler handler = new Handler();
    public static Measurement measurement = new Measurement();

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


        TCPCommunicator.addListener((OnTCPMessageRecievedListener) this);

        writer.init(1883);

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
                    TextView msgRecieved = findViewById(R.id.lbl_status);
                    msgRecieved.setText(theMessage);
                    Log.d("TAG", "run: " + theMessage);
                    JSONObject xd = new JSONObject();
                    xd.put("xd", "lmao");

                    writer.writeToSocket("Szia tibi :)");
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

            final String thingReadyForSend = obj.toString();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    TCPCommunicator.writeToSocket(thingReadyForSend);
                    TCPCommunicator.writeToSocket("XDXDXD");
                    Log.d("TAG", "onClick: clicked the thing :)");
                    TextView msgRecieved = findViewById(R.id.lbl_status);
                    msgRecieved.setText("XDXD");
                }
            });
            thread.start();

        }
        catch(Exception e)
        {
            Log.d("TAG", "someButtonClicked: " + e.getMessage());
        }
    }



}