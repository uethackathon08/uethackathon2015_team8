package com.j4f.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.j4f.R;
import com.j4f.activities.MainActivity;
import com.j4f.adapters.QuestionAdapter;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Answer;
import com.j4f.models.Category;
import com.j4f.models.Question;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class QuestionsFragment extends CoreFragment {

    private ArrayList<Question> questionList;
    private QuestionAdapter questionAdapter;
    private UltimateRecyclerView questionRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    public void addQuestion(final Question q) {
        questionList.add(q);
        questionAdapter.notifyDataSetChanged();
    }
    public int mMaxQuestion = -1;
    public int mCurrentQuestionPage = 1;
    public int mLimit = -1;
    @TargetApi(Build.VERSION_CODES.M)
    public void initQuestionList() {
        questionList = new ArrayList<Question>();
        questionAdapter = new QuestionAdapter(questionList, mActivity);
        questionRecyclerView.setHasFixedSize(true);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        questionRecyclerView.setAdapter(questionAdapter);
        questionRecyclerView.enableLoadmore();
        questionRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mCurrentQuestionPage++;
                if (mCurrentQuestionPage <= mLimit) {
                    getAllQuestion(Configs.QUESTION_PAGE_LIMIT, mCurrentQuestionPage);
                }
            }
        });
        questionRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                questionList = new ArrayList<Question>();
                questionAdapter = new QuestionAdapter(questionList, mActivity);
                questionAdapter.notifyDataSetChanged();
                questionRecyclerView.setAdapter(questionAdapter);
                mMaxQuestion = -1;
                mCurrentQuestionPage = 1;
                mLimit = -1;
                getAllQuestion(Configs.QUESTION_PAGE_LIMIT, mCurrentQuestionPage);
            }
        });
        getAllQuestion(Configs.QUESTION_PAGE_LIMIT, mCurrentQuestionPage);
    }
    public void getAllQuestion(final int limit, final int page) {
        mContext.showProgressDialog("Question Fragment", "Loading questions ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_QUESTIONS +  "?limit=" + limit + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("ok")) {
                                if(mMaxQuestion == -1) {
                                    mMaxQuestion = response.getInt("count");
                                    int d = mMaxQuestion / Configs.CATEGORY_PAGE_LIMIT;
                                    mLimit = mMaxQuestion % Configs.CATEGORY_PAGE_LIMIT == 0 ? d : d + 1;
                                }
                                JSONArray ja = response.getJSONArray("data");
                                for(int i=0; i<ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    mContext.loge(jo.toString());
                                    String id = jo.getString("id");
                                    String[] tag = jo.getString("tags").split(";");
                                    String title = jo.getString("title");
                                    String content = jo.getString("content");
                                    String photo = jo.getString("photo");
                                    int upvotes = Integer.parseInt(jo.getString("upvotes"));
                                    int downvotes = Integer.parseInt(jo.getString("downvotes"));
                                    String users_id = jo.getString("users_id");
                                    String nameOfUser = jo.getString("user_name");
                                    String avatar = jo.getString("user_avatar");
                                    String timestamp = jo.getString("created_at");

                                    ArrayList<Category> categoriesList = new ArrayList<>();
                                    for(String k : tag) categoriesList.add(new Category("ID", k, "Link", 5, 5));
                                    try {
                                        addQuestion(new Question(categoriesList, id, users_id, nameOfUser, avatar,
                                                title, content, photo, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp),
                                                upvotes, downvotes, 20, new ArrayList<Answer>(), tag));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                questionRecyclerView.setRefreshing(false);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.removePreviousDialog("Question Fragment");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mContext.loge(error.getMessage());
                mContext.removePreviousDialog("Question Fragment");
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {
        initQuestionList();
    }

    @Override
    protected void initViews(View v) {
        questionRecyclerView = (UltimateRecyclerView) v.findViewById(R.id.question_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static MainActivity mActivity;
    public static QuestionsFragment mInstance;
    public static QuestionsFragment getInstance(MainActivity activity) {
        if (mInstance == null) {
            mInstance = new QuestionsFragment();
        }
        mActivity = activity;
        return mInstance;
    }
    private QuestionsFragment() {

    }
}
