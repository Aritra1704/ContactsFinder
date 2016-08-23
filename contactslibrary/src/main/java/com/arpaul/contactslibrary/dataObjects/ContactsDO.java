package com.arpaul.contactslibrary.dataObjects;

import java.util.ArrayList;

/**
 * Created by ARPaul on 23-08-2016.
 */
public class ContactsDO extends BaseDO {
    private String ContactId = "";
    private String ContactName = "";
    private ArrayList<String> arrContactNumber = new ArrayList<>();
    private ArrayList<EmailIdDO> arrEmailID = new ArrayList<>();
    private String ContactNotes = "";
    private ArrayList<AddressDO> arrAddress = new ArrayList<>();
    private String ContactIMName = "";
    private String ContactIMType = "";
    private String ContactORGName = "";
    private String ContactORGTitle = "";
    private ArrayList<String> arrBirthday = new ArrayList<>();

    public enum DATA_CONTACT {
        CONTACT_ID,
        CONTACT_NAME,
        CONTACT_NUMBER,
        CONTACT_EMAIL,
        CONTACT_NOTES,
        CONTACT_ADDRESS,
        CONTACT_IM_NAME,
        CONTACT_IM_TYPE,
        CONTACT_ORG_NAME,
        CONTACT_ORG_TITLE,
        CONTACT_BDAY,
    }

    public void setData(Object data, DATA_CONTACT data_type){
        switch (data_type){
            case CONTACT_ID:
                ContactId = (String) data;
                break;
            case CONTACT_NAME:
                ContactName = (String) data;
                break;
            case CONTACT_NUMBER:
                arrContactNumber = (ArrayList<String>) data;
                break;
            case CONTACT_EMAIL:
                arrEmailID = (ArrayList<EmailIdDO>) data;
                break;
            case CONTACT_NOTES:
                ContactNotes = (String) data;
                break;
            case CONTACT_ADDRESS:
                arrAddress = (ArrayList<AddressDO>) data;
                break;
            case CONTACT_IM_NAME:
                ContactIMName = (String) data;
                break;
            case CONTACT_IM_TYPE:
                ContactIMType = (String) data;
                break;
            case CONTACT_ORG_NAME:
                ContactORGName = (String) data;
                break;
            case CONTACT_ORG_TITLE:
                ContactORGTitle = (String) data;
                break;
            case CONTACT_BDAY:
                arrBirthday = (ArrayList<String>) data;
                break;
        }
    }

    public Object getData(DATA_CONTACT data_type){
        switch (data_type){
            case CONTACT_ID:
                return ContactId;
            case CONTACT_NAME:
                return ContactName;
            case CONTACT_NUMBER:
                return arrContactNumber;
            case CONTACT_EMAIL:
                return arrEmailID;
            case CONTACT_NOTES:
                return ContactNotes;
            case CONTACT_ADDRESS:
                return arrAddress;
            case CONTACT_IM_NAME:
                return ContactIMName;
            case CONTACT_IM_TYPE:
                return ContactIMType;
            case CONTACT_ORG_NAME:
                return ContactORGName;
            case CONTACT_ORG_TITLE:
                return ContactORGTitle;
            case CONTACT_BDAY:
                return arrBirthday;
            default:
                return ContactName;
        }
    }
}
