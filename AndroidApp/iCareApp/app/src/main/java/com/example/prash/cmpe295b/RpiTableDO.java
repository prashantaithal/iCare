package com.example.prash.cmpe295b;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;


@DynamoDBTable(tableName = "cmpeb-mobilehub-1483690200-RpiTable")

public class RpiTableDO {
    private Double _serialNo;
    private Double _pulse;
    private Double _temp;
    private String _accX;
    private String _accY;
    private String _accZ;
    private String _gyrX;
    private String _gyrY;
    private String _gyrZ;



    @DynamoDBHashKey(attributeName = "serialNo")
    @DynamoDBAttribute(attributeName = "serialNo")
    public Double getSerialNo() {
        return _serialNo;
    }

    public void setSerialNo(final Double _serialNo) {
        this._serialNo = _serialNo;
    }

    @DynamoDBAttribute(attributeName = "pulse")
    public Double getPulse() {
        return _pulse;
    }

    public void setPulse(final Double _pulse) {
        this._pulse = _pulse;
    }
    @DynamoDBAttribute(attributeName = "temp")
    public Double getTemp() {
        return _temp;
    }

    public void setTemp(final Double _temp) {
        this._temp = _temp;
    }


    @DynamoDBAttribute(attributeName = "ACCx")
    public String getAccX() {
        return _accX;
    }

    public void setAccX(final String _accX) {
        this._accX = _accX;
    }

    @DynamoDBAttribute(attributeName = "ACCy")
    public String getAccY() {
        return _accY;
    }

    public void setAccY(final String _accY) {
        this._accY = _accY;
    }

    @DynamoDBAttribute(attributeName = "ACCz")
    public String getAccZ() {
        return _accZ;
    }

    public void setAccZ(final String _accZ) {
        this._accZ = _accZ;
    }


    @DynamoDBAttribute(attributeName = "GYRx")
    public String getGyrX() {
        return _accX;
    }

    public void setGyrX(final String _gyrX) {
        this._gyrX = _gyrX;
    }

    @DynamoDBAttribute(attributeName = "GYRy")
    public String getGyrY() {
        return _gyrY;
    }

    public void setGyrY(final String _gyrY) {
        this._gyrY = _gyrY;
    }

    @DynamoDBAttribute(attributeName = "GYRz")
    public String getGyrZ() {
        return _gyrZ;
    }

    public void setGyrZ(final String _gyrZ) {
        this._gyrZ = _gyrZ;
    }
}
