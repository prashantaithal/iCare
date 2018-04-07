package com.example.prash.cmpe295b;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "cmpeb-mobilehub-1483690200-RpiTable")

public class RpiTableDO {
    private Double _serialNo;
    private Double _accel;
    private Double _pulse;
    private Double _temp;

    @DynamoDBHashKey(attributeName = "serialNo")
    @DynamoDBAttribute(attributeName = "serialNo")
    public Double getSerialNo() {
        return _serialNo;
    }

    public void setSerialNo(final Double _serialNo) {
        this._serialNo = _serialNo;
    }
    @DynamoDBAttribute(attributeName = "accel")
    public Double getAccel() {
        return _accel;
    }

    public void setAccel(final Double _accel) {
        this._accel = _accel;
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

}
