package com.j4f.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.models.TimeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanTQ on 11/21/15.
 */
public class TimeSlotArrayAdapter extends ArrayAdapter<TimeSlot> {
    public List<TimeSlot> data = new ArrayList<>();
    public CoreActivity mActivity;
    public LayoutInflater mInflate;
    public ArrayList<RecyclerView.ViewHolder> mHolderList = new ArrayList<>();

    public TimeSlotArrayAdapter(CoreActivity context, int textViewResourceId, List<TimeSlot> objects) {
        super(context, textViewResourceId, objects);
        mActivity = context;
        mInflate = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (objects != null) {
            this.data = objects;
        }
    }

    public int getCount() {
        return data.size();
    }

    @Override
    public TimeSlot getItem(int position) {
        return data.get(position);
    }

    public void addItem(TimeSlot model) {
        this.data.add(model);
        notifyDataSetChanged();
    }

    public void removeItem(TimeSlot model) {
        this.data.remove(model);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final TimeSlot model = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflate.inflate(R.layout.item_row_timeslot, null);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        return convertView;
    }

    class ViewHolder {
        public TextView date;
        public TextView time;
    }
}
