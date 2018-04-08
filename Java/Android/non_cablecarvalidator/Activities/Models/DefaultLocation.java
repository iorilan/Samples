package com.example.tdw.non_cablecarvalidator.Activities.Models;

/**
 * Created by lanliang on 20/9/16.
 */
public class DefaultLocation {
    public String facilityId;
    public String facilityName;

    public String operationId;
    public String operationName;

    public String facilityLineNum;
    public String operationLineNum;

    public DefaultLocation(String facilityId, String facilityName, String operationId, String operationName, String facilityLineNum, String operationLineNum){
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.operationId = operationId;
        this.operationName = operationName;
        this.facilityLineNum = facilityLineNum;
        this.operationLineNum = operationLineNum;
    }
}
