package com.example.prash.cmpe295b;

import android.util.Log;

public class Data {

    private Double pulse;
    private Double temp;
    private Double accx;
    private Double accy;
    private Double accz;
    private Double gyrx;
    private Double gyry;
    private Double gyrz;

    public Double getPulseSQL() {
        return pulse;
    }
    public Double getTempSQL() {
        return temp;
    }
    public Double getAccxSQL() {
        return accx;
    }
    public Double getAccySQL() {
        return accy;
    }
    public Double getAcczSQL() {
        return accz;
    }
    public Double getGyrxSQL() {
        return gyrx;
    }
    public Double getGyrySQL() {
        return gyry;
    }
    public Double getGyrzSQL() {
        return gyrz;
    }

    public void setData(Double[] fields){

        pulse = fields[0];
        temp = fields[1];
        accx = fields[2];
        accy = fields[3];
        accz = fields[4];
        gyrx = fields[5];
        gyry = fields[6];
        gyrz = fields[7];
    }
}
