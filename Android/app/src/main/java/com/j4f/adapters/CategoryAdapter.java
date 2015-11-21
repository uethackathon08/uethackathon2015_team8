package com.j4f.adapters;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.interfaces.ImageRequestListener;
import com.j4f.models.Category;

import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataObjectHolder> implements View.OnClickListener {
    private ArrayList<Category> dataSet;
    private CoreActivity context;

    public CategoryAdapter(ArrayList<Category> myDataSet, CoreActivity context) {
        this.context = context;
        this.dataSet = myDataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new DataObjectHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final Category c = dataSet.get(position);
        holder.name.setText(c.getName());
        holder.info.setText(c.getNumberOfQuestion() + " Questions • " + c.getNumberOfTutor() + " Offers");
        context.makeImageRequest(c.getIconLink(), new ImageRequestListener() {
            @Override
            public void onBefore() {
                holder.icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                if(paramImageContainer.getBitmap() != null) {
                    holder.icon.setImageBitmap(paramImageContainer.getBitmap());
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                context.loge("Load image failed " + error.getMessage());
            }
        });
    }

    public void addItem(Category dataObj, int index) {
        dataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        if (index != -1) {
            dataSet.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void clearData() {
        dataSet.clear();
    }

    public Category getItem(int index) {
        return dataSet.get(index);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View v) {

    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name, info;

        public DataObjectHolder(View itemView, int type) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            info = (TextView) itemView.findViewById(R.id.info);
        }
    }
}
