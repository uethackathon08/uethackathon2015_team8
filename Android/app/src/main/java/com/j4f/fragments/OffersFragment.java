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


    public void addOffer(final Offer o) {
        offersList.add(o);
        offerAdapter.notifyDataSetChanged();
    }
    public int mMaxOffer = -1;
    public int mCurrentOfferPage = 1;
    public int mLimit = -1;
    @TargetApi(Build.VERSION_CODES.M)
    public void initOffersList() {
        offersList = new ArrayList<Offer>();
        offerAdapter = new OfferAdapter(offersList, mActivity);
        offerRecyclerView.setHasFixedSize(true);
        offerRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        offerRecyclerView.setAdapter(offerAdapter);
        offerRecyclerView.enableLoadmore();
        offerRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mCurrentOfferPage++;
                if (mCurrentOfferPage <= mLimit) {
                    getAllOffers(Configs.OFFER_PAGE_LIMIT, mCurrentOfferPage);
                }
            }
        });
        offerRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offersList = new ArrayList<Offer>();
                offerAdapter = new OfferAdapter(offersList, mActivity);
                offerAdapter.notifyDataSetChanged();
                offerRecyclerView.setAdapter(offerAdapter);
                mMaxOffer = -1;
                mCurrentOfferPage = 1;
                mLimit = -1;
                getAllOffers(Configs.OFFER_PAGE_LIMIT, mCurrentOfferPage);
            }
        });
        offerRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        getAllOffers(Configs.OFFER_PAGE_LIMIT, mCurrentOfferPage);
    }
    public void getAllOffers(final int limit, final int page) {
        mContext.showProgressDialog("Offer Fragment", "Loading offers ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_OFFERS + "?limit=" + limit + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if(status.equals("ok")) {
                                if(mMaxOffer == -1) {
                                    mMaxOffer = response.getInt("count");
                                    int d = mMaxOffer / Configs.OFFER_PAGE_LIMIT;
                                    mLimit = mMaxOffer % Configs.OFFER_PAGE_LIMIT == 0 ? d : d + 1;
                                }
                                JSONArray ja = response.getJSONArray("data");
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    String id = jo.getString("id");
                                    String title = jo.getString("title");
                                    String content = jo.getString("content");
                                    String[] time = null;
                                    if (jo.getString("time").equals("null")) {
                                        time = jo.getString("time").split(";");
                                    }
                                    String[] bid_list = null;
                                    if (!jo.getString("bid_users_list_id").equals("null")) {
                                        bid_list = jo.getString("bid_users_list_id").split(";");
                                    }

                                    String createdAt = jo.getString("created_at");
                                    String phone = jo.getString("phone");
                                    String[] tags = jo.getString("tags").split(";");
                                    addOffer(new Offer(id, title, tags, content, time, phone, bid_list, createdAt));
                                }
                                offerRecyclerView.setRefreshing(false);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mContext.removePreviousDialog("Offer Fragment");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mContext.loge(error.getMessage());
                mContext.removePreviousDialog("Offer Fragment");
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
