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
import com.arpaul.contactsfinder.dataObjects.ContactsDO;

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
        final ContactsDO objCouponDO = arrSpinnerList.get(position);

        holder.tvContactName.setText((String) objCouponDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NAME));
        holder.tvContactNumber.setText((String) objCouponDO.getData(ContactsDO.DATA_CONTACT.CONTACT_NUMBER));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactDetailActivity.class);
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvContactName           = (TextView) view.findViewById(R.id.tvContactName);
            tvContactNumber         = (TextView) view.findViewById(R.id.tvContactNumber);
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
