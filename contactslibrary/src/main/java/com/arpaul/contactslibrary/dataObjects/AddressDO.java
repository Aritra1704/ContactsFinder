package com.arpaul.contactslibrary.dataObjects;

/**
 * Created by Aritra on 23-08-2016.
 */
public class AddressDO extends BaseDO {
    private String AddressPoBox = "";
    private String AddressStreet = "";
    private String AddressCity = "";
    private String AddressState = "";
    private String AddressPostCode = "";
    private String AddressCountry = "";
    private String AddressType = "";

    public enum DATA_ADDRESS {
        ADDRESS_POBOX,
        ADDRESS_STREET,
        ADDRESS_CITY,
        ADDRESS_STATE,
        ADDRESS_POSTCODE,
        ADDRESS_COUNTRY,
        ADDRESS_TYPE,
    }

    public void setData(Object data, DATA_ADDRESS data_type){
        switch (data_type){
            case ADDRESS_POBOX:
                AddressPoBox = (String) data;
                break;
            case ADDRESS_STREET:
                AddressStreet = (String) data;
                break;
            case ADDRESS_CITY:
                AddressCity = (String) data;
                break;
            case ADDRESS_STATE:
                AddressState = (String) data;
                break;
            case ADDRESS_POSTCODE:
                AddressPostCode = (String) data;
                break;
            case ADDRESS_COUNTRY:
                AddressCountry = (String) data;
                break;
            case ADDRESS_TYPE:
                AddressType = (String) data;
                break;
        }
    }

    public Object getData(DATA_ADDRESS data_type){
        switch (data_type){
            case ADDRESS_POBOX:
                return AddressPoBox;
            case ADDRESS_STREET:
                return AddressStreet;
            case ADDRESS_CITY:
                return AddressCity;
            case ADDRESS_STATE:
                return AddressState;
            case ADDRESS_POSTCODE:
                return AddressPostCode;
            case ADDRESS_COUNTRY:
                return AddressCountry;
            case ADDRESS_TYPE:
                return AddressType;
            default:
                return AddressStreet;
        }
    }
}
