package com.arpaul.contactsfinder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arpaul.contactsfinder.R;
import com.arpaul.contactslibrary.dataObjects.AddressDO;
import com.arpaul.contactslibrary.dataObjects.ContactsDO;
import com.arpaul.contactslibrary.dataObjects.EmailIdDO;
import com.arpaul.utilitieslib.UnCaughtException;

import java.util.ArrayList;

public class ContactDetailActivity extends AppCompatActivity {

    private ContactsDO objContactsDO;

    private Toolbar toolbar;
    private TextView tvContactName, tvContactNumber, tvContactEmail, tvContactNotes, tvContactAddress, tvContactBday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(ContactDetailActivity.this,"aritra1704@gmail.com",getString(R.string.app_name)));
        setContentView(R.layout.activity_contact_detail);

        if(getIntent().hasExtra("CONTACT_DETAIL"))
            objContactsDO = (ContactsDO) getIntent().getExtras().get("CONTACT_DETAIL");

        initialiseControls();

        bindControls();
    }

    private void bindControls(){
        tvContactName.setText((String) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NAME));

        ArrayList<String> arrContactNumber = (ArrayList<String>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NUMBER);
        StringBuilder contacts = new StringBuilder();
        for(String contact : arrContactNumber){
            contacts.append(contact).append("\n");
        }
        tvContactNumber.setText(contacts.toString());

        ArrayList<EmailIdDO> arrEmail = (ArrayList<EmailIdDO>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_EMAIL);
        StringBuilder emails = new StringBuilder();
        for(EmailIdDO email : arrEmail){
            emails.append((String) email.getData(EmailIdDO.DATA_EMAIL.EMAIL_ID)).append("\n");
        }
        tvContactEmail.setText(emails.toString());

        tvContactNotes.setText((String) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NOTES));

        ArrayList<AddressDO> arrAddress = (ArrayList<AddressDO>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_ADDRESS);
        StringBuilder address = new StringBuilder();
        for(AddressDO contact : arrAddress){
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_POBOX)).append(" ");
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_STREET)).append(" ");
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_CITY)).append(" ");
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_STATE)).append(" ");
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_POSTCODE)).append(" ");
            address.append((String) contact.getData(AddressDO.DATA_ADDRESS.ADDRESS_COUNTRY)).append("\n");
        }
        tvContactAddress.setText(address.toString());

        ArrayList<String> arrBirthday = (ArrayList<String>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_BDAY);
        StringBuilder bdays = new StringBuilder();
        for(String bday : arrBirthday){
            bdays.append(bday).append("\n");
        }
        tvContactBday.setText(bdays.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initialiseControls(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvContactName       = (TextView) findViewById(R.id.tvContactName);
        tvContactNumber     = (TextView) findViewById(R.id.tvContactNumber);
        tvContactEmail      = (TextView) findViewById(R.id.tvContactEmail);
        tvContactNotes      = (TextView) findViewById(R.id.tvContactNotes);
        tvContactAddress    = (TextView) findViewById(R.id.tvContactAddress);
        tvContactBday       = (TextView) findViewById(R.id.tvContactBday);

    }
}
