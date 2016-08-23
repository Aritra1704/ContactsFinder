package com.arpaul.contactslibrary;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.arpaul.contactslibrary.dataObjects.AddressDO;
import com.arpaul.contactslibrary.dataObjects.ContactsDO;
import com.arpaul.contactslibrary.dataObjects.EmailIdDO;
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

                ContentResolver cr = context.getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        objContactsDO = new ContactsDO();

                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        //Contact numbers
                        ArrayList<String> arrContactNum = null;
                        if (StringUtils.getInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                    new String[]{id}, null);
                            if(pCur != null && pCur.moveToFirst()){
                                arrContactNum = new ArrayList<>();
                                while (pCur.moveToNext()) {
                                    arrContactNum.add(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                }
                            }
                            pCur.close();

                            //Contact Email ids
                            ArrayList<EmailIdDO> arrEmailId = new ArrayList<>();
                            EmailIdDO objEmailIdDO = null;
                            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (emailCur.moveToNext()) {
                                objEmailIdDO = new EmailIdDO();

                                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                String emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                                objEmailIdDO.setData(email, EmailIdDO.DATA_EMAIL.EMAIL_ID);
                                objEmailIdDO.setData(emailType, EmailIdDO.DATA_EMAIL.EMAIL_TYPE);

                                arrEmailId.add(objEmailIdDO);
                            }
                            emailCur.close();

                            //Contact notes
                            String note = "";
                            String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] noteWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                            Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                            if (noteCur.moveToFirst()) {
                                note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                            }
                            noteCur.close();

                            //Postal Address
                            ArrayList<AddressDO> arrAddress = new ArrayList<>();
                            AddressDO objAddress = null;
                            String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] addrWhereParams = new String[]{id, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                            Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI, null, addrWhere, addrWhereParams, null);
                            while(addrCur.moveToNext()) {
                                String poBox = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                                String street = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                                String city = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                                String state = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                                String postalCode = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                                String country = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                                String type = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                                objAddress = new AddressDO();

                                objAddress.setData(poBox, AddressDO.DATA_ADDRESS.ADDRESS_POBOX);
                                objAddress.setData(street, AddressDO.DATA_ADDRESS.ADDRESS_STREET);
                                objAddress.setData(city, AddressDO.DATA_ADDRESS.ADDRESS_CITY);
                                objAddress.setData(state, AddressDO.DATA_ADDRESS.ADDRESS_STATE);
                                objAddress.setData(postalCode, AddressDO.DATA_ADDRESS.ADDRESS_POSTCODE);
                                objAddress.setData(country, AddressDO.DATA_ADDRESS.ADDRESS_COUNTRY);
                                objAddress.setData(type, AddressDO.DATA_ADDRESS.ADDRESS_TYPE);

                                arrAddress.add(objAddress);
                            }
                            addrCur.close();

                            //Instant Messenger
                            String imName = "";
                            String imType = "";
                            String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] imWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                            Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null);
                            if (imCur.moveToFirst()) {
                                imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                                imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                            }
                            imCur.close();

                            //Organisation
                            String orgName = "";
                            String title = "";
                            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                            String[] orgWhereParams = new String[]{id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                            Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI, null, orgWhere, orgWhereParams, null);
                            if (orgCur.moveToFirst()) {
                                orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                                title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                            }
                            orgCur.close();

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
                            Cursor bDayCur = cr.query(ContactsContract.Data.CONTENT_URI, projection, where, selectionArgs, sortOrder);
                            int bDayColumn = bDayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
                            if(bDayCur != null && bDayCur.moveToFirst()){
                                arrBirthday = new ArrayList<String>();
                                while (bDayCur.moveToNext()) {
                                    String bDay = bDayCur.getString(bDayColumn);
                                    arrBirthday.add(bDay);
                                }
                            }
                            bDayCur.close();

                            objContactsDO.setData(id, ContactsDO.DATA_CONTACT.CONTACT_ID);
                            objContactsDO.setData(name, ContactsDO.DATA_CONTACT.CONTACT_NAME);
                            if(arrContactNum != null)
                                objContactsDO.setData(arrContactNum, ContactsDO.DATA_CONTACT.CONTACT_NUMBER);
                            if(arrEmailId != null && arrEmailId.size() > 0)
                                objContactsDO.setData(arrEmailId, ContactsDO.DATA_CONTACT.CONTACT_EMAIL);
                            objContactsDO.setData(name, ContactsDO.DATA_CONTACT.CONTACT_NOTES);
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
                    }
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
