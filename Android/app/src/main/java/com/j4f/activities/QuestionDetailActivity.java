package com.j4f.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.j4f.R;
import com.j4f.adapters.CommentAdapter;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.models.Comment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

public class QuestionDetailActivity extends CoreActivity {

    private String userId;
    private String questionId;
    private String nameOfUser;
    private String userAvatarLink;
    private String title;

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

    public void initCommentList() {
        commentList = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(commentList, QuestionDetailActivity.this);
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(QuestionDetailActivity.this));
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.enableLoadmore();
        getAllComment(questionId, Configs.COMMENT_PAGE_LIMIT, 1);
    }

    public void getAllComment(String questionId, int limited, int page) {

    }

    @Override
    public void initViews() {
        commentRecyclerView = (UltimateRecyclerView) findViewById(R.id.question_details_list);
    }

    @Override
    public void initModels() {
        initCommentList();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initAnimations() {

    }

    @Override
    public void onClick(View v) {

    }
}
