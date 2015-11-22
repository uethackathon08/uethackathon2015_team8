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
import com.j4f.application.Utils;
import com.j4f.cores.CoreActivity;
import com.j4f.interfaces.ImageRequestListener;
import com.j4f.models.Comment;

import java.util.ArrayList;
import java.util.Date;

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
        holder.name.setText(c.getUserName());
        holder.content.setText(c.getContent());
        holder.voteUp.setText(c.getVoteUp() + "");
        holder.voteDown.setText(c.getVoteDown() + "");
        Date d = c.getTimestamp();
        holder.time.setText("Ngày " + d.getDay() + "/" + d.getMonth() + "/" + d.getYear() + " " + d.getHours() + ":" + d.getMinutes());

        context.makeImageRequest(c.getAvatarLink(), new ImageRequestListener() {
            @Override
            public void onBefore() {
                holder.avatar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                if (paramImageContainer.getBitmap() != null) {
                    holder.avatar.setImageBitmap(Utils.getCircleBitmap(paramImageContainer.getBitmap()));
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                context.loge("Load image failed " + error.getMessage());
            }
        });
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
        public ImageView avatar, voteUpIcon, voteDownIcon;
        public TextView name, content, voteUp, voteDown, time;

        public DataObjectHolder(View itemView, int type) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.comment_avatar);
            voteUpIcon = (ImageView) itemView.findViewById(R.id.comment_voteup);
            voteDownIcon = (ImageView) itemView.findViewById(R.id.comment_votedown);
            name = (TextView) itemView.findViewById(R.id.comment_name);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            voteUp = (TextView) itemView.findViewById(R.id.comment_voteup_quan);
            voteDown = (TextView) itemView.findViewById(R.id.comment_votedown_quan);
            time = (TextView) itemView.findViewById(R.id.comment_time);
        }
    }
}
