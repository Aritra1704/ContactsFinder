package com.arpaul.contactslibrary.dataObjects;

/**
 * Created by Aritra on 23-08-2016.
 */
public class EmailIdDO extends BaseDO {
    private String EmailId = "";
    private String EmailType = "";

    public enum DATA_EMAIL {
        EMAIL_ID,
        EMAIL_TYPE
    }

    public void setData(Object data, DATA_EMAIL data_type){
        switch (data_type){
            case EMAIL_ID:
                EmailId = (String) data;
                break;
            case EMAIL_TYPE:
                EmailType = (String) data;
                break;
        }
    }

    public Object getData(DATA_EMAIL data_type){
        switch (data_type){
            case EMAIL_ID:
                return EmailId;
            case EMAIL_TYPE:
                return EmailType;
            default:
                return EmailType;
        }
    }
}
