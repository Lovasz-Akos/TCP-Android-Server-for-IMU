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

import org.json.JSONObject;

import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity implements OnTCPMessageRecievedListener {

    static TCPCommunicator writer =TCPCommunicator.getInstance();

    private static Handler handler = new Handler();
    public static Measurement measurement = new Measurement();
    public static MainActivity mainActivity;

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


        MainActivity.mainActivity = this;
        TCPCommunicator.addListener((OnTCPMessageRecievedListener) this);

        new StartServer().execute();

    }

    public void MUKODJ(){
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

    @Override
    public void onTCPMessageRecieved(final String message){
        // TODO Auto-generated method stub

        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try
                {
                    String theMessage = message.toString();
                    TextView msgRecieved = findViewById(R.id.lbl_status);
                    msgRecieved.setText(theMessage.toString());
                    Log.d("Message Recieved", theMessage.toString());
                    new SendMsg("pls work maaaaan").execute();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        });


    }

    private static class SendMsg extends AsyncTask<Void, Void, Void>{

        String message;

        public SendMsg(String msg)
        {
            this.message = msg;
        }
        @Override
        protected Void doInBackground(Void... voids) {

            TCPCommunicator.writeToSocket(message);
            Log.d("TAG", "doInBackground: sent message to client");
            return null;
        }
    }

    private static class StartServer extends AsyncTask<Void, Void, Void>{
        public StartServer(){

        }

        @Override
        protected Void doInBackground(Void... voids) {

            writer.init(1883);
            return null;
        }

    }

    public void someButtonClicked(View view)
    {
        JSONObject obj = new JSONObject();
        try
        {
            if(view.getId()==R.id.btn_startMeasurement)
            {
                obj.put(EnumsAndStatics.MESSAGE_TYPE_FOR_JSON, EnumsAndStatics.MessageTypes.MessageFromServer);
                TextView txtContent = findViewById(R.id.lbl_status);
                obj.put(EnumsAndStatics.MESSAGE_CONTENT_FOR_JSON, txtContent.getText().toString());
            }

            final JSONObject jsonReadyForSend=obj;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    TCPCommunicator.writeToSocket(jsonReadyForSend.toString());
                }
            });
            thread.start();

        }
        catch(Exception e)
        {

        }

    }



}