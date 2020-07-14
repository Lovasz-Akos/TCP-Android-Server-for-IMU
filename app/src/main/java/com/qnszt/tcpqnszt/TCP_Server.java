package com.qnszt.tcpqnszt;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TCP_Server {

    private static final Pattern regex = Pattern.compile("<(.+)>");
    public static int SERVERPORT = 1883;
    Handler updateConversationHandler;
    Thread serverThread = null;
    private ServerSocket serverSocket;
    private TextView text;

    public void server_start() {

        updateConversationHandler = new Handler();

        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();

    }


    protected void onStop() {
        //onStop();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerThread implements Runnable {

        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(SERVERPORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {

                try {

                    socket = serverSocket.accept();

                    CommunicationThread commThread = new CommunicationThread(socket);
                    new Thread(commThread).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;

        private BufferedReader input;
        private BufferedWriter output;

        public CommunicationThread(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {

                this.output = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
                output.flush();

                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

                this.clientSocket.setSoTimeout(10000);
                Log.d("TAG", "CommunicationThread: " + this.input.readLine());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    String read = input.readLine();

                    updateConversationHandler.post(new updateUIThread(read));
                    if ((read == null) || Objects.equals(read, "null")) {
                        input.close();
                        output.close();
                        Log.d("RUN", "Client disconnected");
                    }
                    Log.d("DEBUG", "Message = `" + read + "`");

                    Matcher matcher = regex.matcher(read);
                    if (matcher.find()) {
                        String find = matcher.group(1);
                        if (find != null) {
                            if (find.contains("-CONNECTED")) {
                                ClientWorker.registerClient(find.substring(0, find.indexOf('-')), input, output);

                            } else if (find.contains("-DISCONNECTED")) {
                                ClientWorker.removeClient(find.substring(0, find.indexOf('-')));
                            }
                        }
                    }


                    /*output.write(":)");
                    output.flush();*/

                } catch (Exception s) {
                    //s.printStackTrace();
                }
            }
        }


    }

    class updateUIThread implements Runnable {
        private String msg;

        public updateUIThread(String str) {
            this.msg = str;
        }

        @Override
        public void run() {
            // text.setText(text.getText().toString()+"Client Says: "+ msg + "\n");
            if (msg != null)
                Log.d("TAG", "run: " + msg);


            //TODO: add the incoming messages to a list
            MainActivity.mainActivity.listIncomingMessages(msg);

        }
    }
}
