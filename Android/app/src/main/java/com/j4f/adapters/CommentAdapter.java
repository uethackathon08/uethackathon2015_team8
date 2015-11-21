package com.j4f.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.models.Comment;

import java.util.ArrayList;

/**
 * Created by hunter on 11/22/2015.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.DataObjectHolder> implements View.OnClickListener {
    private ArrayList<Comment> dataSet;
    private CoreActivity context;

    public CommentAdapter(ArrayList<Comment> myDataSet, CoreActivity context) {
        this.context = context;
        this.dataSet = myDataSet;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new DataObjectHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final Comment c = dataSet.get(position);
//        holder.name.setText(c.getName());
//        holder.info.setText(c.getNumberOfQuestion() + " Questions • " + c.getNumberOfTutor() + " Offers");
//        context.makeImageRequest(Configs.BASE_URL + c.getIconLink(), new ImageRequestListener() {
//            @Override
//            public void onBefore() {
//                holder.icon.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//            }
//            @Override
//            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
//                if(paramImageContainer.getBitmap() != null) {
//                    holder.icon.setImageBitmap(Utils.getCircleBitmap(paramImageContainer.getBitmap()));
//                }
//            }
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                context.loge("Load image failed " + error.getMessage());
//            }
//        });
    }

    public void addItem(Comment dataObj) {
        dataSet.add(dataObj);
        notifyDataSetChanged();
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

    public Comment getItem(int index) {
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
        public ImageView avatar;
        public TextView name, content, voteUp, voteDown, voteUpIcon, voteDownIcon, time;

        public DataObjectHolder(View itemView, int type) {
            super(itemView);
//            icon = (ImageView) itemView.findViewById(R.id.icon);
//            name = (TextView) itemView.findViewById(R.id.name);
//            info = (TextView) itemView.findViewById(R.id.info);
        }
    }
}
