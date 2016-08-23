package com.arpaul.contactsfinder.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.arpaul.contactsfinder.R;
import com.arpaul.contactsfinder.adapter.ContactListAdapter;
import com.arpaul.contactslibrary.DeviceContacts;
import com.arpaul.contactslibrary.ResponseListener;
import com.arpaul.contactslibrary.dataObjects.ContactsDO;
import com.arpaul.utilitieslib.PermissionUtils;
import com.arpaul.utilitieslib.UnCaughtException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ARPaul on 23-08-2016.
 */
public class ContactListActivity extends AppCompatActivity {

    private RecyclerView rvContactList;
    private ProgressBar pbLoader;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(ContactListActivity.this,"aritra1704@gmail.com",getString(R.string.app_name)));
        setContentView(R.layout.activity_contact_list);

        initialiseControls();

        bindControls();

    }

    private void bindControls(){
        if(new PermissionUtils().checkPermission(ContactListActivity.this) != 0){
            new PermissionUtils().verifyLocation(ContactListActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS});
        } else
            getContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            getContacts();
        }
    }

    private void getContacts(){
        pbLoader.setVisibility(View.VISIBLE);
        rvContactList.setVisibility(View.GONE);
        new DeviceContacts(ContactListActivity.this).getAllContacts(new ResponseListener() {
            @Override
            public void getResponse(Object data) {
                if(data != null){
                    ArrayList<ContactsDO> listAllContacts = (ArrayList<ContactsDO>) data;

                    adapter.refresh(listAllContacts);

                    pbLoader.setVisibility(View.GONE);
                    rvContactList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initialiseControls(){
        rvContactList = (RecyclerView) findViewById(R.id.rvContactList);
        pbLoader = (ProgressBar) findViewById(R.id.pbLoader);

        adapter = new ContactListAdapter(ContactListActivity.this, new ArrayList<ContactsDO>());
        rvContactList.setAdapter(adapter);
    }
}
