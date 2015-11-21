package com.j4f.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
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
import com.j4f.adapters.CategoryAdapter;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Category;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class CategoriesFragment extends CoreFragment {

    private ArrayList<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private UltimateRecyclerView categoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    public void getAllCategory(final int limit, final int page) {
        mContext.showProgressDialog("Loading categories ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_CATEGORIES +  "?limit=" + limit + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("ok")) {
                                if(mMaxCategory == -1) {
                                    mMaxCategory = response.getInt("count");
                                    int d = mMaxCategory / Configs.CATEGORY_PAGE_LIMIT;
                                    mLimit = mMaxCategory % Configs.CATEGORY_PAGE_LIMIT == 0 ? d : d + 1;
                                }
                                JSONArray ja = response.getJSONArray("data");
                                for(int i=0; i<ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    String id = jo.getString("id");
                                    String name = jo.getString("name");
                                    String icon = jo.getString("icon");
                                    addCategory(new Category(id, name, icon, 5, 5));
                                }
                            } else {
                                mContext.loge("Status failed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.removePreviousDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mContext.loge(error.getMessage());
                mContext.removePreviousDialog();
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    public void addCategory(final Category c) {
        categoryList.add(c);
        categoryAdapter.notifyDataSetChanged();
    }
    public int mMaxCategory = -1;
    public int mCurrentCategoryPage = 1;
    public int mLimit = -1;
    @TargetApi(Build.VERSION_CODES.M)
    public void initCategoryList() {
        categoryList = new ArrayList<Category>();
        categoryAdapter = new CategoryAdapter(categoryList, mActivity);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.enableLoadmore();
        categoryRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mCurrentCategoryPage++;
                if (mCurrentCategoryPage <= mLimit) {
                    getAllCategory(Configs.CATEGORY_PAGE_LIMIT, mCurrentCategoryPage);
                }
            }
        });


        categoryRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                mActivity.loge("DDDDDDD " + scrollY);
            }

            @Override
            public void onDownMotionEvent() {
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                mActivity.loge("okokokokkokoo");
                if (observableScrollState == ObservableScrollState.DOWN) {
//                    categoryRecyclerView.showToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.UP) {
//                    categoryRecyclerView.hideToolbar(toolbar, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });


        getAllCategory(Configs.CATEGORY_PAGE_LIMIT, mCurrentCategoryPage);
    }
    private int scrollOld = 0;

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {
        initCategoryList();
    }

    @Override
    protected void initViews(View v) {
        categoryRecyclerView = (UltimateRecyclerView) v.findViewById(R.id.category_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static MainActivity mActivity;
    public static CategoriesFragment mInstance;
    public static CategoriesFragment getInstance(MainActivity activity) {
        if (mInstance == null) {
            mInstance = new CategoriesFragment();
        }
        mActivity = activity;
        return mInstance;
    }
}
