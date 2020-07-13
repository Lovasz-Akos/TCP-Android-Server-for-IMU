package com.qnszt.tcpqnszt;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Client {
    private String Name;
    private BufferedWriter Writer;
    private BufferedReader Reader;

    public Client() {
    }

    public Client(String name, BufferedReader reader, BufferedWriter writer) {
        Name = name;
        Writer = writer;
        Reader = reader;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BufferedWriter getWriter() {
        return Writer;
    }

    public void setWriter(BufferedWriter writer) {
        Writer = writer;
    }

    public BufferedReader getReader() {
        return Reader;
    }

    public void setReader(BufferedReader reader) {
        Reader = reader;
    }

    public void send(String message) {

    }
}
