package com.arpaul.contactsfinder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.arpaul.contactsfinder.R;
import com.arpaul.contactsfinder.adapter.ContactListAdapter;
import com.arpaul.contactsfinder.dataObjects.ContactsDO;

import java.util.ArrayList;

/**
 * Created by ARPaul on 23-08-2016.
 */
public class ContactListActivity extends AppCompatActivity {

    private RecyclerView rvContactList;
    private ContactListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_list);

        initialiseControls();

        bindControls();
    }

    private void bindControls(){

    }

    private void initialiseControls(){
        rvContactList = (RecyclerView) findViewById(R.id.rvContactList);

        adapter = new ContactListAdapter(ContactListActivity.this, new ArrayList<ContactsDO>());
        rvContactList.setAdapter(adapter);
    }
}
