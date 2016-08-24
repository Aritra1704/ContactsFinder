package com.arpaul.contactslibrary;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.arpaul.contactslibrary.dataObjects.AddressDO;
import com.arpaul.contactslibrary.dataObjects.ContactsDO;
import com.arpaul.contactslibrary.dataObjects.EmailIdDO;
import com.arpaul.contactslibrary.dataObjects.PhoneNumberDO;
import com.arpaul.utilitieslib.StringUtils;

import java.util.ArrayList;

/**
 * Created by Aritra on 23-08-2016.
 */
public class DeviceContacts {

    private Context context;

    public DeviceContacts(Context context){
        this.context = context;
    }

    public void getAllContacts(final ResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<ContactsDO> listAllContacts = new ArrayList<>();
                ContactsDO objContactsDO = null;

                Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if (cur != null && cur.moveToFirst() && cur.getCount() > 0) {
                    do {
                        objContactsDO = new ContactsDO();

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        //Contact numbers
                        ArrayList<PhoneNumberDO> arrContactNum = null;
                        PhoneNumberDO objPhoneNumberDO = null;
                        if (StringUtils.getInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                    new String[]{id}, null);
                            if(cursor != null/* && cursor.moveToFirst()*/){
                                arrContactNum = new ArrayList<>();
                                while (cursor.moveToNext()) {
                                    int phoneType 		= cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                    //String isStarred 		= pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                    String phoneNo 	= cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                    objPhoneNumberDO = new PhoneNumberDO();
                                    switch (phoneType)
                                    {
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                            phoneType = PhoneNumberDO.TYPE_MOBILE;
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                            phoneType = PhoneNumberDO.TYPE_HOME;
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                            phoneType = PhoneNumberDO.TYPE_WORK;
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                                            phoneType = PhoneNumberDO.TYPE_WORK_MOBILE;
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                            phoneType = PhoneNumberDO.TYPE_OTHER;
                                            break;
                                        default:
                                            break;
                                    }
                                    objPhoneNumberDO.setData(phoneType, PhoneNumberDO.DATA_PHONE.PHONE_TYPE);
                                    objPhoneNumberDO.setData(phoneNo, PhoneNumberDO.DATA_PHONE.PHONE_NUM);
                                    arrContactNum.add(objPhoneNumberDO);
                                } ;
                            }
                            cursor.close();

                            //Contact Email ids
                            ArrayList<EmailIdDO> arrEmailId = null;
                            EmailIdDO objEmailIdDO = null;
                            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    new String[]{ContactsContract.CommonDataKinds.Email.DATA,ContactsContract.CommonDataKinds.Email.TYPE},
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id},
                                    null);
                            if(cursor != null && cursor.moveToFirst()){
                                arrEmailId = new ArrayList<>();
                                do {
                                    objEmailIdDO = new EmailIdDO();

                                    String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                    String emailType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                                    objEmailIdDO.setData(email, EmailIdDO.DATA_EMAIL.EMAIL_ID);
                                    objEmailIdDO.setData(emailType, EmailIdDO.DATA_EMAIL.EMAIL_TYPE);

                                    arrEmailId.add(objEmailIdDO);
                                } while (cursor.moveToNext());
                            }
                            cursor.close();

                            //Contact notes
                            String note = "";
                            String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] noteWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Note.NOTE}, noteWhere, noteWhereParams, null);
                            if(cursor != null && cursor.moveToFirst()){
                                note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                            }
                            cursor.close();

                            //Postal Address
                            ArrayList<AddressDO> arrAddress = null;
                            AddressDO objAddress = null;
                            String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] addrWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, addrWhere, addrWhereParams, null);
                            if(cursor != null && cursor.moveToFirst()){
                                arrAddress = new ArrayList<>();
                                do {
                                    String poBox = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                                    String street = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                    String city = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                    String state = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                                    String postalCode = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                                    String country = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                                    String type = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                                    objAddress = new AddressDO();

                                    objAddress.setData(poBox, AddressDO.DATA_ADDRESS.ADDRESS_POBOX);
                                    objAddress.setData(street, AddressDO.DATA_ADDRESS.ADDRESS_STREET);
                                    objAddress.setData(city, AddressDO.DATA_ADDRESS.ADDRESS_CITY);
                                    objAddress.setData(state, AddressDO.DATA_ADDRESS.ADDRESS_STATE);
                                    objAddress.setData(postalCode, AddressDO.DATA_ADDRESS.ADDRESS_POSTCODE);
                                    objAddress.setData(country, AddressDO.DATA_ADDRESS.ADDRESS_COUNTRY);
                                    objAddress.setData(type, AddressDO.DATA_ADDRESS.ADDRESS_TYPE);

                                    arrAddress.add(objAddress);
                                } while(cursor.moveToNext());
                            }
                            cursor.close();

                            //Instant Messenger
                            String imName = "";
                            String imType = "";
                            String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] imWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                imName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                                imType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                            }
                            cursor.close();

                            //Organisation
                            String orgName = "";
                            String title = "";
                            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] orgWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null);
                            if(cursor != null && cursor.moveToFirst()){
                                orgName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                                title = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                            }
                            cursor.close();

                            // Bday events
                            ArrayList<String> arrBirthday = null;
                            String[] projection = new String[] {
                                    ContactsContract.Contacts.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                                    ContactsContract.CommonDataKinds.Event.START_DATE
                            };
                            String where = ContactsContract.Data.MIMETYPE + "= ? AND " +
                                    ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                                    ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
                            String[] selectionArgs = new String[] {ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE};
                            String sortOrder = null;
                            cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, where, selectionArgs, sortOrder);
                            int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
                            if(cursor != null && cursor.moveToFirst()){
                                arrBirthday = new ArrayList<String>();
                                do {
                                    String bDay = cursor.getString(bDayColumn);
                                    arrBirthday.add(bDay);
                                } while (cursor.moveToNext());
                            }
                            cursor.close();

                            objContactsDO.setData(id, ContactsDO.DATA_CONTACT.CONTACT_ID);
                            objContactsDO.setData(name, ContactsDO.DATA_CONTACT.CONTACT_NAME);

                            if(arrContactNum != null)
                                objContactsDO.setData(arrContactNum, ContactsDO.DATA_CONTACT.CONTACT_NUMBER);

                            if(arrEmailId != null && arrEmailId.size() > 0)
                                objContactsDO.setData(arrEmailId, ContactsDO.DATA_CONTACT.CONTACT_EMAIL);

                            objContactsDO.setData(note, ContactsDO.DATA_CONTACT.CONTACT_NOTES);

                            if(arrAddress != null && arrAddress.size() > 0)
                                objContactsDO.setData(arrAddress, ContactsDO.DATA_CONTACT.CONTACT_ADDRESS);

                            objContactsDO.setData(imName, ContactsDO.DATA_CONTACT.CONTACT_IM_NAME);
                            objContactsDO.setData(imType, ContactsDO.DATA_CONTACT.CONTACT_IM_TYPE);

                            objContactsDO.setData(orgName, ContactsDO.DATA_CONTACT.CONTACT_ORG_NAME);
                            objContactsDO.setData(title, ContactsDO.DATA_CONTACT.CONTACT_ORG_TITLE);

                            if(arrBirthday != null)
                                objContactsDO.setData(arrBirthday, ContactsDO.DATA_CONTACT.CONTACT_BDAY);

                            listAllContacts.add(objContactsDO);
                        }
                    } while (cur.moveToNext());
                }

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null)
                            listener.getResponse(listAllContacts);
                    }
                });
            }
        }).start();
    }
}
