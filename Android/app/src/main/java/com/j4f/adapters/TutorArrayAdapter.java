package com.j4f.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.customizes.MyTextView;
import com.j4f.models.Account;
import com.j4f.models.TimeSlot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuanTQ on 11/21/15.
 */
public class TutorArrayAdapter extends ArrayAdapter<Account> {
    public List<Account> data = new ArrayList<>();
    public CoreActivity mActivity;
    public LayoutInflater mInflate;
    public ArrayList<RecyclerView.ViewHolder> mHolderList = new ArrayList<>();

    public TutorArrayAdapter(CoreActivity context, int textViewResourceId, List<Account> objects) {
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
    public Account getItem(int position) {
        return data.get(position);
    }

    public void addItem(Account model) {
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
        final Account model = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflate.inflate(R.layout.item_tutor, null);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.reputation = (MyTextView) convertView.findViewById(R.id.reputation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(model.getUsername());
        holder.reputation.setText((int) (Math.random() * (50 - 1)) + " Tutors • " + "Reputation: " + model.getReputation());

        return convertView;
    }

    class ViewHolder {
        public ImageView avatar;
        public TextView name;
        public MyTextView reputation;
    }
}
