package com.qnszt.tpcqnszt;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientWorker {
    private static ArrayList<Client> clients = new ArrayList<>();

    public static void registerClient(String name, BufferedReader reader, BufferedWriter writer){
        Client temp_client = new Client(name, reader, writer);
        clients.add(temp_client);
        Log.d("RUN", String.format("Registered: %s", name));
        send(temp_client, "OK");
    }

    public static void resetClients(){
        clients = new ArrayList<>();
    }

    public static ArrayList<Client> getClients(){
        return clients;
    }

    public static void startMeasurement(@org.jetbrains.annotations.NotNull Measurement measurement){
        broadcast(String.format("nam%s", measurement.name));
        broadcast(String.format("dur%s", measurement.duration));
        broadcast(String.format("del%s", measurement.delay));
        broadcast(String.format("sta%s", "")); //TODO: Send system time
        Log.d("RUN", "Measurement `"+measurement.name+"` started for "+measurement.duration+" minutes");
    }

    public static void broadcast(String meassage) {
        if(meassage != null){
            for (Client client: clients) {
                send(client, meassage);
            }
        }
    }

    public static void send(@NotNull Client client, String message) {
        try {
            client.getWriter().write("<"+message+">");
        } catch (IOException e) {
            Log.d("ERROR", String.format("Failed to send `%s` to %s (%s)", message, client.getName(), e.getMessage()));
        }
    }

    public static void removeClient(final String clientName) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName() == clientName) {
                clients.remove(i);
                break;
            }
        }
    }
}
