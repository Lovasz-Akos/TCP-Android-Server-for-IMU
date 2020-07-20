package com.qnszt.tcpqnszt;

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

    public void setDuration(String duration) {
        try {
            setDuration(Integer.parseInt(duration));
        } catch (Exception e) {
            setDuration(0);
        }
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(float hertz) {
        //this.delay = (int)Math.floor(calculateDelay(hertz)); //map(delay, 50, 100, 20, 10).intValue();
        this.delay = map(hertz, 50, 100, 20, 10).intValue();
    }

    public void setDelay(String delay) {
        try {
            setDelay(Integer.parseInt(delay));
        } catch (Exception e) {
            setDelay(0);
        }
    }

    Float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    double calculateDelay(float x) {
        //return (0.000344431 * Math.pow(x, 4)) - (0.0354434 * Math.pow(x, 3)) + (1.41469 * Math.pow(x, 2)) - (27.3635 * x) + 258.165;
        return (-0.0113332 * Math.pow(x, 3)) + (0.803327 * Math.pow(x, 2)) - (20.7332 * x) + 232.333;
    }
}
