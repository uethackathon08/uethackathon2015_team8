package com.j4f.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.j4f.R;
import com.j4f.adapters.CommentAdapter;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.models.Comment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class QuestionDetailActivity extends CoreActivity {

    private String userId;
    private String questionId;
    private String nameOfUser;
    private String userAvatarLink;
    private String title;

    private boolean isSend = false;

    private ImageView send;
    private ImageView voice;
    private EditText typing;

    private ArrayList<Comment> commentList;
    private CommentAdapter commentAdapter;
    private UltimateRecyclerView commentRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        questionId = i.getStringExtra("qId");
        userId = i.getStringExtra("uId");
        nameOfUser = i.getStringExtra("uName");
        userAvatarLink = i.getStringExtra("avatar");
        title = i.getStringExtra("title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        initViews();
        initModels();
        initListeners();
        initAnimations();
    }

    public void addComment(final Comment o) {
        commentList.add(o);
        commentAdapter.notifyDataSetChanged();
    }
    public int mMaxComment = -1;
    public int mCurrentCommentPage = 1;
    public int mLimit = -1;
    @TargetApi(Build.VERSION_CODES.M)
    public void initCommentList() {
        commentList = new ArrayList<Comment>();
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));
        commentList.add(new Comment("12345", "Việt Anh Vũ", "http://taigamemoinhat.com/wp-content/uploads/2013/04/girlxinh36.jpg", new Date(), "Lá thư viết vội, có tên rất lạ, chắc là người con thương rất nhiều. Một ngày mẹ thấy, con buồn vu vơ. Nụ dồ vẫn ở trong ngăn bàn. Là đâu đã tàn, hoa đâu đã vàng...", 15, 20));

        commentAdapter = new CommentAdapter(commentList, QuestionDetailActivity.this);
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(QuestionDetailActivity.this));
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.enableLoadmore();
        commentRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mCurrentCommentPage++;
                if (mCurrentCommentPage <= mLimit) {
                    getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
                }
            }
        });
        commentRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentList = new ArrayList<Comment>();
                commentAdapter = new CommentAdapter(commentList, QuestionDetailActivity.this);
                commentAdapter.notifyDataSetChanged();
                commentRecyclerView.setAdapter(commentAdapter);
                mMaxComment = -1;
                mCurrentCommentPage = 1;
                mLimit = -1;
                getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
            }
        });
        getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
    }
    public void getALlComment(final String qId, final int limited, final int page) {
        showProgressDialog("Loading offers ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_COMMENTS + "?questions_id=" + qId + "&limit=" + limited + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("ok")) {
                                if(mMaxComment == -1) {
                                    mMaxComment = response.getInt("count");
                                    int d = mMaxComment / Configs.COMMENT_PAGE_LIMIT;
                                    mLimit = mMaxComment % Configs.COMMENT_PAGE_LIMIT == 0 ? d : d + 1;
                                }
                                JSONArray ja = response.getJSONArray("data");
                                loge(ja.toString());
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    loge(jo.toString());
//                                    String id = jo.getString("id");
//                                    String title = jo.getString("title");
//                                    String content = jo.getString("content");
//                                    String[] time = null;
//                                    if (jo.getString("time").equals("null")) {
//                                        time = jo.getString("time").split(";");
//                                    }
//                                    String[] bid_list = null;
//                                    if (!jo.getString("bid_users_list_id").equals("null")) {
//                                        bid_list = jo.getString("bid_users_list_id").split(";");
//                                    }
//
//                                    String createdAt = jo.getString("created_at");
//                                    String phone = jo.getString("phone");
//                                    String[] tags = jo.getString("tags").split(";");
//                                    addOffer(new Offer(id, title, tags, content, time, phone, bid_list, createdAt));
                                }
                                commentRecyclerView.setRefreshing(false);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        removePreviousDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loge(error.getMessage());
                removePreviousDialog();
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    @Override
    public void initViews() {
        commentRecyclerView = (UltimateRecyclerView) findViewById(R.id.comment_list);
        typing = (EditText) findViewById(R.id.typing);
        send = (ImageView) findViewById(R.id.camera);
        voice = (ImageView) findViewById(R.id.voice);
    }

    @Override
    public void initModels() {
        initCommentList();
    }

    @Override
    public void initListeners() {
        voice.setOnClickListener(this);
        send.setOnClickListener(this);
        typing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    isSend = true;
                    send.setImageDrawable(getResources().getDrawable(R.mipmap.send));
                } else {
                    isSend = false;
                    send.setImageDrawable(getResources().getDrawable(R.mipmap.camera));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initAnimations() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice:
                break;
            case R.id.camera:
                if(isSend) {
                    String s = typing.getText().toString();
                    if(!s.equals("")) {
                        String myId = "12345";
                        sendComment(questionId, myId, s);
                    }
                } else {
                    // chup anh
                }
                break;
            default:
                break;
        }
    }

    public void sendComment(String questionId, String myId, String msg) {

    }
}
