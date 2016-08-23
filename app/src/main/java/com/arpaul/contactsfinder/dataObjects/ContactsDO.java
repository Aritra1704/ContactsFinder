package com.arpaul.contactsfinder.dataObjects;

/**
 * Created by ARPaul on 23-08-2016.
 */
public class ContactsDO extends BaseDO {
    private String ContactName = "";
    private String ContactNumber = "";

    public enum DATA_CONTACT {
        CONTACT_NAME,
        CONTACT_NUMBER
    }

    public void setData(Object data, DATA_CONTACT data_type){
        switch (data_type){
            case CONTACT_NAME:
                ContactName = (String) data;
                break;
            case CONTACT_NUMBER:
                ContactNumber = (String) data;
                break;
        }
    }

    public Object getData(DATA_CONTACT data_type){
        switch (data_type){
            case CONTACT_NAME:
                return ContactName;
            case CONTACT_NUMBER:
                return ContactNumber;
            default:
                return ContactName;
        }
    }
}
