package com.example.tdw.non_cablecarvalidator.LocalStorage;

/**
 * Created by lanliang on 25/8/16.
 */
public class OfflineUsageModel {
    public OfflineUsageModel(String usageDate, String ticketNo, String facilityId,
                             String operationId, String validatorId, String operatorName, String ticketCondition)
    {
        UsageDate = usageDate;
        TicketNo = ticketNo;
        FacilityID = facilityId;
        OperationID = operationId;
        ValidatorID = validatorId;
        OperatorName = operatorName;
        TicketCondition = ticketCondition;
    }

    public  String UsageDate ;

    public String TicketNo ;

    public String FacilityID ;

    public String OperationID;

    public String ValidatorID;
    public String OperatorName;
    public String TicketCondition;

}
