package com.arpaul.contactslibrary.dataObjects;

/**
 * Created by Aritra on 24-08-2016.
 */
public class PhoneNumberDO extends BaseDO {
    private String PhoneNum = "";
    private int PhoneType = 0;

    public static final int TYPE_HOME          = 1;
    public static final int TYPE_MOBILE        = 2;
    public static final int TYPE_WORK          = 3;
    public static final int TYPE_WORK_MOBILE   = 17;
    public static final int TYPE_OTHER         = 7;

    public enum DATA_PHONE {
        PHONE_NUM,
        PHONE_TYPE
    }

    public void setData(Object data, DATA_PHONE data_type){
        switch (data_type){
            case PHONE_NUM:
                PhoneNum = (String) data;
                break;
            case PHONE_TYPE:
                PhoneType = (int) data;
                break;
        }
    }

    public Object getData(DATA_PHONE data_type){
        switch (data_type){
            case PHONE_NUM:
                return PhoneNum;
            case PHONE_TYPE:
                return PhoneType;
            default:
                return PhoneType;
        }
    }
}
