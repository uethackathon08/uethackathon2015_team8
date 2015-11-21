package com.j4f.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.j4f.R;
import com.j4f.adapters.OfferAdapter;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Offer;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.ui.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class OffersFragment extends CoreFragment {

    private ArrayList<Offer> offersList;
    private OfferAdapter offerAdapter;
    private UltimateRecyclerView offerRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    public void getAllOffers(final int limit, final int page) {
        mContext.showProgressDialog("Loading offers ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_OFFERS + "?limit=" + limit + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("response", response.toString());

                            String status = response.getString("status");
                            if (status.equals("ok")) {
                                JSONArray ja = response.getJSONArray("data");
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    String id = jo.getString("id");
                                    String title = jo.getString("title");
                                    String content = jo.getString("content");
                                    String[] time = jo.getString("time").split(";");

                                    String[] bid_list = jo.getString("bid_users_list_id").split(";");

                                    String createdAt = jo.getString("created_at");
                                    String phone = jo.getString("phone");
                                    String[] tags = jo.getString("tags").split(";");
                                    offersList.add(new Offer(id, title, tags, content, time, phone, bid_list, createdAt));
                                    if (i == ja.length() - 1) {
                                        offerAdapter = new OfferAdapter(offersList, mActivity);
                                        offerRecyclerView.setHasFixedSize(true);
                                        offerRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                                        offerRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
                                        offerRecyclerView.setAdapter(offerAdapter);
                                    }
                                }
                            } else {
                                System.err.println("Error loading offers");
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

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {
        initOffersList();
    }

    private void initOffersList() {
        offersList = new ArrayList<Offer>();
        mContext.loge("Start getting offers");
        getAllOffers(100, 1);
    }

    @Override
    protected void initViews(View v) {
        offerRecyclerView = (UltimateRecyclerView) v.findViewById(R.id.offers_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static CoreActivity mActivity;
    public static OffersFragment mInstance;

    public static OffersFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new OffersFragment();
        }
        mActivity = activity;
        return mInstance;
    }

}
