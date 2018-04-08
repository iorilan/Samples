package com.example.tdw.non_cablecarvalidator.Activities.Models;

/**
 * Created by lanliang on 9/9/16.
 */
public class TicketLookup
{
    public TicketLookup(int entryWeight,
                        String itemId,
                        String itemDesc,
                        int qtyGroup,
                        String facilityID,
                        String operationID,
                        String ticketCode,
                        String lineNum,
                        String ticketTableID,
                        String ticketLookupID,
                        String accessID,
                        String packageLineGroup,
                        String packageID,
                        String packageLineItemId,
                        String packageLineItemDescription
    ){
        EntryWeight = entryWeight;
        ItemID = itemId;
        ItemDescription = itemDesc;
        QtyGroup = qtyGroup;
        FacilityID = facilityID;
        OperationID = operationID;
        TicketCode = ticketCode;
        LineNum = lineNum;
        TicketTableID = ticketTableID;
        TicketLookupID = ticketLookupID;
        AccessID = accessID;
        PackageLineGroup = packageLineGroup;
        PackageID = packageID;
        PackageLineItemId = packageLineItemId;
        PackageLineItemDescription = packageLineItemDescription;
    }
    public int EntryWeight;
    public String ItemID;
    public String ItemDescription;
    public int QtyGroup;
    public String FacilityID;
    public String OperationID;
    public String TicketCode;
    public String LineNum;
    public String TicketTableID;
    public String TicketLookupID;
    public String AccessID;
    public String PackageLineGroup;
    public String PackageID;
    public String PackageLineItemId;
    public String PackageLineItemDescription;
}
