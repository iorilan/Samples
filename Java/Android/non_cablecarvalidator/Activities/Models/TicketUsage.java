package com.example.tdw.non_cablecarvalidator.Activities.Models;

import com.example.tdw.non_cablecarvalidator.Activities.HomeActivity;
import com.example.tdw.non_cablecarvalidator.Activities.LoginActivity;
import com.example.tdw.non_cablecarvalidator.Config.GlobalValues;
import com.example.tdw.non_cablecarvalidator.LocalStorage.OfflineUsageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by lanliang on 9/9/16.
 */
public class TicketUsage
{
    public TicketUsage(){

    }
    public TicketUsage(TicketLookup record, String facilityId, String operationId)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.ENGLISH);
        String dateStr = sdf.format(new Date());


        EntryMethod = 1;
        EntryWeight = record.EntryWeight;
        ItemID = record.ItemID;
        ItemDescription = record.ItemDescription;
        UseQty = record.QtyGroup;
        FacilityID = facilityId;
        OperationID = operationId;
        TicketCode = record.TicketCode;
        TicketLookupLineNum = record.LineNum;
        TicketTableID = record.TicketTableID;
        TicketLookupID = record.TicketLookupID;
        UniqueUsageID = UUID.randomUUID().toString();
        UsageStatus = "0"; // this step will be always after validation
        ValidatorID = GlobalValues.DeviceName();
        UsageCondition = "1"; // online / offline
        OriginalUsageStatus = ""; // no override
        Override = "0"; // admin enter
        //CreatedDateTime = dateStr;
       // UsageDate = dateStr;
        AccessID = record.AccessID;
        PackageLineGroup = record.PackageLineGroup;
        Operator = LoginActivity.LoginUserName;
    }

    public TicketUsage(OfflineUsageModel offlineUsage){


        EntryMethod = 1;
        EntryWeight = 1;
        ItemID = "offline";
        ItemDescription = "offline";
        UseQty = 1;
        FacilityID = offlineUsage.FacilityID;
        OperationID = offlineUsage.OperationID;
        TicketCode = offlineUsage.TicketNo;
        Operator = offlineUsage.OperatorName;
        TicketLookupLineNum = "1";
        TicketTableID = "1";
        TicketLookupID = "1";
        UniqueUsageID = UUID.randomUUID().toString();
        UsageStatus = "1"; // this step will be always after validation
        ValidatorID = GlobalValues.DeviceName();
        UsageCondition = "0"; // online / offline
        TicketCondition = offlineUsage.TicketCondition;
        OriginalUsageStatus = ""; // no override
        Override = "0"; // admin enter
        //CreatedDateTime = dateStr;
        UsageDate = offlineUsage.UsageDate;
        AccessID = "1";
        PackageLineGroup = "1";
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
