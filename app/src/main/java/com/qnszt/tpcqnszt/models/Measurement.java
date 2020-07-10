package com.qnszt.tpcqnszt.models;

public class Measurement {
    String name;
    int duration;
    int delay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDuration(String duration){
        setDuration(Integer.parseInt(duration));
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setDelay(String delay){
        setDelay(delay);
    }
}
