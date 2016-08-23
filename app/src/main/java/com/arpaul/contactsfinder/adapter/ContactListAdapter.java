package com.arpaul.contactsfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpaul.contactsfinder.R;
import com.arpaul.contactsfinder.activity.ContactDetailActivity;
import com.arpaul.contactslibrary.dataObjects.ContactsDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ARPaul on 23-08-2016.
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private Context context;
    private List<ContactsDO> arrSpinnerList;

    public ContactListAdapter(Context context, List<ContactsDO> arrCallDetails) {
        this.context=context;
        this.arrSpinnerList = arrCallDetails;
    }

    public void refresh(List<ContactsDO> arrCallDetails) {
        this.arrSpinnerList = arrCallDetails;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contacts_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ContactsDO objContactsDO = arrSpinnerList.get(position);

        holder.tvContactName.setText((String) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NAME));
        ArrayList<String> arrContactNumber = (ArrayList<String>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NUMBER);
        if(arrContactNumber != null && arrContactNumber.size() > 0)
            holder.tvContactNumber.setText(arrContactNumber.get(0));

        ArrayList<String> arrContactBday = (ArrayList<String>) objContactsDO.getData(ContactsDO.DATA_CONTACT.CONTACT_BDAY);
        if(arrContactBday != null && arrContactBday.size() > 0)
            holder.tvContactBDay.setText(arrContactBday.get(0));
        else {
            holder.tvContactBDay.setText("");
            holder.tvContactBDay.setVisibility(View.GONE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetailActivity.class);
                intent.putExtra("CONTACT_DETAIL", objContactsDO);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(arrSpinnerList != null)
            return arrSpinnerList.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvContactName;
        public final TextView tvContactNumber;
        public final TextView tvContactBDay;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvContactName           = (TextView) view.findViewById(R.id.tvContactName);
            tvContactNumber         = (TextView) view.findViewById(R.id.tvContactNumber);
            tvContactBDay           = (TextView) view.findViewById(R.id.tvContactBDay);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
