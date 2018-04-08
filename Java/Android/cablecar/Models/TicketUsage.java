package com.example.fuyan.test.Models;

import com.example.fuyan.test.Activities.MyActivity;
import com.example.fuyan.test.Config.GlobalValues;
import com.example.fuyan.test.Models.OfflineUsageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by lanliang on 13/9/16.
 */
public class TicketUsage
{
    public TicketUsage(OfflineUsageModel offlineUsage){

        EntryMethod = 1;
        EntryWeight = 1;
        ItemID = "offline";
        ItemDescription = "offline";
        UseQty = 1;
        FacilityID = offlineUsage.FacilityID;
        OperationID = offlineUsage.OperationID;
        TicketCode = offlineUsage.TicketNo;
        TicketLookupLineNum = "1";
        TicketTableID = "1";
        TicketLookupID = "1";
        UniqueUsageID = UUID.randomUUID().toString();
        UsageStatus = "1";
        ValidatorID = GlobalValues.DeviceName();
        UsageCondition = "0"; // online / offline
        OriginalUsageStatus = ""; // no override
        Override = "0"; // admin enter
        //CreatedDateTime = dateStr;
        UsageDate = offlineUsage.UsageDate;
        AccessID = "1";
        PackageLineGroup = "1";
        Operator = offlineUsage.OperatorName;
        TicketCondition = offlineUsage.TicketCondition;
    }

    public int EntryMethod;
    public int EntryWeight;
    public String ItemID;
    public String ItemDescription;
    public int UseQty;
    public String FacilityID;
    public String OperationID;
    public String TicketCode;
    public String TicketLookupLineNum;
    public String TicketTableID;
    public String TicketLookupID;
    public String UniqueUsageID;
    public String UsageStatus;
    public String ValidatorID;
    public String UsageCondition;
    public String TicketCondition;
    public String OriginalUsageStatus;
    public String Override;
    public String CreatedDateTime;
    public String UsageDate;
    public String AccessID;
    public String PackageLineGroup;
    public String Operator;


}

