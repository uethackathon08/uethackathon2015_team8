package com.j4f.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.j4f.R;
import com.j4f.activities.QuestionDetailActivity;
import com.j4f.application.Utils;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.interfaces.ImageRequestListener;
import com.j4f.models.Question;

import java.text.SimpleDateFormat;
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

        context.makeImageRequest(Configs.BASE_URL + q.getImageLink(), new ImageRequestListener() {
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
                if (b != null) {
                    holder.avatar.setImageBitmap(Utils.getCircleBitmap(paramImageContainer.getBitmap()));
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                context.loge("Load image failed " + error.getMessage());
            }
        });

        holder.voteup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isVoteup) {
                    holder.voteup.setImageDrawable(context.getResources().getDrawable(R.mipmap.voteup_disable));
                    holder.isVoteup = false;
                } else {
                    holder.voteup.setImageDrawable(context.getResources().getDrawable(R.mipmap.voteup_ennable));
                    holder.isVoteup = true;
                }
            }
        });
        holder.votedown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isvotedown) {
                    holder.votedown.setImageDrawable(context.getResources().getDrawable(R.mipmap.votedown_disable));
                    holder.isvotedown = false;
                } else {
                    holder.votedown.setImageDrawable(context.getResources().getDrawable(R.mipmap.votedown_ennable));
                    holder.isvotedown = true;
                }
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, QuestionDetailActivity.class);
                i.putExtra("qId", q.getId());
                i.putExtra("uId", q.getUserId());
                i.putExtra("uName", q.getNameOfUser());
                i.putExtra("avatar", q.getUserAvatarLink());
                i.putExtra("title", q.getTitle());
                i.putExtra("photo", q.getImageLink());
                i.putExtra("content", q.getContent());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                i.putExtra("date", formatter.format(q.getTimestamp()));
                context.startActivity(i);
            }
        });
        holder.share.setOnClickListener(this);
        holder.more.setOnClickListener(this);
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isBookmark) {
                    holder.bookmark.setImageDrawable(context.getResources().getDrawable(R.mipmap.bookmark_disable));
                    holder.isBookmark = false;
                } else {
                    holder.bookmark.setImageDrawable(context.getResources().getDrawable(R.mipmap.bookmark_ennable));
                    holder.isBookmark = true;
                }
            }
        });
        holder.avatar.setOnClickListener(this);
        holder.image.setOnClickListener(this);

        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (String tag : q.getTags()) {
            TextView tv = (TextView) View.inflate(context, R.layout.textview_for_tag, null);
            lp.setMargins(0, 0, 16, 0);
            tv.setLayoutParams(lp);

            tv.setText(tag);
            holder.tags_list.addView(tv);
        }
    }

    public void addItem(Question dataObj) {
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

    public Question getItem(int index) {
        return dataSet.get(index);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        public ImageView image, avatar, voteup, votedown, comment, share, more, bookmark;
        public TextView title, content, name, time;
        public LinearLayout tags_list;
        public boolean isVoteup = false;
        public boolean isvotedown = false;
        public boolean isBookmark = false;

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
            tags_list = (LinearLayout) itemView.findViewById(R.id.tags_list);
        }
    }
}
