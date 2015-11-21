package com.j4f.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.models.Offer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by nvg58 on 11/21/15.
 * Project Android
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.DataObjectHolder> implements View.OnClickListener {
    private ArrayList<Offer> dataSet;
    private CoreActivity context;

    public OfferAdapter(ArrayList<Offer> myDataSet, CoreActivity context) {
        this.context = context;
        this.dataSet = myDataSet;
    }

    @Override
    public OfferAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
        return new DataObjectHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(OfferAdapter.DataObjectHolder holder, int position) {
        final Offer c = dataSet.get(position);
        holder.title.setText(c.getTitle());
        holder.content.setText(c.getContent());
        holder.number_bids.setText(String.valueOf(c.getBid_list().length) + " bid(s)");
//        Log.e("c.getCreated_at()", Long.getLong(c.getCreated_at()).toString());

//        long days_past = TimeUnit.MILLISECONDS.toHours(Long.getLong(c.getCreated_at()) - Calendar.getInstance().getTimeInMillis());
        holder.time_past.setText("10 days");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View view) {

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        public TextView title, content, number_bids, time_past;

        public DataObjectHolder(View itemView, int type) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            number_bids = (TextView) itemView.findViewById(R.id.number_bids);
            time_past = (TextView) itemView.findViewById(R.id.time_past);
        }
    }
}
