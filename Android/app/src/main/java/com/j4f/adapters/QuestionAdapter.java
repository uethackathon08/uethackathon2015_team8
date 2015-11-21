package com.j4f.adapters;

import android.graphics.Bitmap;
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
import com.j4f.models.Question;

import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.DataObjectHolder> implements View.OnClickListener {
    private ArrayList<Question> dataSet;
    private CoreActivity context;
    public QuestionAdapter(ArrayList<Question> myDataSet, CoreActivity context) {
        this.context = context;
        this.dataSet = myDataSet;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return -1;
//    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_row, parent, false);
        return new DataObjectHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final Question q = dataSet.get(position);
        holder.title.setText(q.getTitle());
        holder.title.setSelected(true);
        holder.content.setText(q.getContent());
        holder.time.setText(" • " + q.getTimestamp().getHours() + ":" + q.getTimestamp().getMinutes());
        holder.name.setText(q.getNameOfUser());

        context.makeImageRequest(q.getImageLink(), new ImageRequestListener() {
            @Override
            public void onBefore() {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                holder.image.setImageBitmap(paramImageContainer.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                context.loge("Load image failed " + error.getMessage());
            }
        });

        context.makeImageRequest(q.getUserAvatarLink(), new ImageRequestListener() {
            @Override
            public void onBefore() {
                holder.avatar.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            }
            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                Bitmap b = paramImageContainer.getBitmap();
                if(b != null) {
                    holder.avatar.setImageBitmap(Utils.getCircleBitmap(paramImageContainer.getBitmap()));
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                context.loge("Load image failed " + error.getMessage());
            }
        });

        holder.voteup.setOnClickListener(this);
        holder.votedown.setOnClickListener(this);
        holder.comment.setOnClickListener(this);
        holder.share.setOnClickListener(this);
        holder.more.setOnClickListener(this);
        holder.bookmark.setOnClickListener(this);
        holder.avatar.setOnClickListener(this);
        holder.image.setOnClickListener(this);
    }

    public void addItem(Question dataObj, int index) {
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
    public Question getItem(int index) {
        return dataSet.get(index);
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar:
                break;
            case R.id.image:
                break;
            case R.id.voteup:
                break;
            case R.id.votedown:
                break;
            case R.id.comment:
                break;
            case R.id.share:
                break;
            case R.id.more:
                break;
            case R.id.bookmark:
                break;
            default:
                break;
        }
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{
        public ImageView image, avatar, voteup, votedown, comment, share, more, bookmark;
        public TextView title, content, name, time;
        public DataObjectHolder(View itemView, int type) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            voteup = (ImageView) itemView.findViewById(R.id.voteup);
            votedown = (ImageView) itemView.findViewById(R.id.votedown);
            comment = (ImageView) itemView.findViewById(R.id.comment);
            share = (ImageView) itemView.findViewById(R.id.share);
            more = (ImageView) itemView.findViewById(R.id.more);
            bookmark = (ImageView) itemView.findViewById(R.id.bookmark);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
