package com.j4f.fragments;

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
import com.j4f.adapters.CategoryAdapter;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Category;
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
//        mListener.onBefore();
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_CATEGORIES +  "?limit=" + limit + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("ok")) {
                                JSONArray ja = response.getJSONArray("data");
                                for(int i=0; i<ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    String id = jo.getString("id");
                                    String name = jo.getString("name");
                                    String icon = jo.getString("icon");
                                    categoryList.add(new Category(id, name, icon, 5, 5));
                                    if(i == ja.length()-1) {
                                        categoryAdapter = new CategoryAdapter(categoryList, mActivity);
                                        categoryRecyclerView.setHasFixedSize(true);
                                        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                                        categoryRecyclerView.setAdapter(categoryAdapter);
                                    }
                                }
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mContext.loge(error.getMessage());
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    public void initCategoryList() {
        categoryList = new ArrayList<Category>();
        mContext.loge("Start getting categories");
        getAllCategory(100, 1);
    }

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
    public static CoreActivity mActivity;
    public static CategoriesFragment mInstance;
    public static CategoriesFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new CategoriesFragment();
        }
        mActivity = activity;
        return mInstance;
    }
}
