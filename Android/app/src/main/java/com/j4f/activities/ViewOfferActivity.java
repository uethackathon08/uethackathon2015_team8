package com.j4f.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.j4f.R;
import com.j4f.adapters.TutorArrayAdapter;
import com.j4f.cores.CoreActivity;
import com.j4f.models.Account;
import com.j4f.network.J4FClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewOfferActivity extends CoreActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Button mBidButton;
    private static String offerID;
    private static String title;
    private static String content;
    private static String tags;
    private static String contact;
    private static String time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Offer");

        offerID = getIntent().getStringExtra("offerID");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        tags = getIntent().getStringExtra("tags");
        contact = getIntent().getStringExtra("contact");
        time = getIntent().getStringExtra("time");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        initViews();
        initViews();
        initListeners();
        initAnimations();
    }

//    private void loadData(String offerID) {
//        String url = Constant.BASE_URL + "offer/new";
//        Map<String, String> params = new HashMap<>();
//        String tags = "";
//        for (String tag : mOfferedTags) {
//            tags += tag + ";";
//        }
//        params.put("tags", tags);
//        params.put("title", title);
//        params.put("content", content);
//        params.put("users_id", MyApplication.USER_ID);
//        if (contact != null) params.put("phone", contact);
//
//        if (mTimeSlotList.size() > 0) {
//            String time = "";
//            for (TimeSlot timeSlot : mTimeSlotList) {
//                time += timeSlot.getDatetime().getTimeInMillis() + ";";
//            }
//            params.put("time", time);
//        }
//
//        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                removePreviousDialog("PostOffer Fragment");
//                String offerID = "";
//                try {
//                    offerID = response.get("data").toString();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                finish();
//
//                Intent intent = new Intent(getBaseContext(), ViewOfferActivity.class);
//                intent.putExtra("offerID", offerID);
//                startActivity(intent);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                alert("Error when posting your offer");
//                removePreviousDialog("PostOffer Fragment");
//                showToastLong("Error");
//            }
//        });
//
//        MyApplication.getInstance().addToRequestQueue(jsObjRequest);
//        showProgressDialog("PostOffer Fragment", "Posting...");
//    }

    @Override
    public void initViews() {
        mBidButton = (Button) findViewById(R.id.bid_button);
//        mBidButton.setVisibility(View.GONE);
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initListeners() {
        mBidButton.setOnClickListener(this);
    }

    @Override
    public void initAnimations() {

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_view_offer, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bid_button:
                alert("Do you want to bid this offer?");
                break;
            default:
                break;
        }
    }

    private void alert(final String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(ViewOfferActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        alertDialog.show();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DETAILS";
                case 1:
                    return "BIDS";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private List<Account> mTutorList;
        private ListView mTutorListView;
        private TutorArrayAdapter mTutorArrayAdapter;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = null;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_view_offer_details, container, false);
                    LinearLayout tagsLayout = (LinearLayout) rootView.findViewById(R.id.tags_list);
                    LinearLayout.LayoutParams lp = new
                            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    if (offerID != null) {
                        RequestParams params = new RequestParams();
                        params.put("offers_id", offerID);

                        String url = "offer/detail";
                        J4FClient.post(url, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    if (response.getString("status").equals("ok")) {
                                        // TODO go to detail page
                                        title = response.getString("title");
                                        content = response.getString("content");
                                        tags = response.getString("tags");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                Log.e("onFailure", e.toString());
                                Log.e("errorResponse", errorResponse);
                            }
                        });

                        // view infor
                        TextView titleText = (TextView) rootView.findViewById(R.id.title);
                        titleText.setText(title);
                        TextView contentText = (TextView) rootView.findViewById(R.id.content);
                        contentText.setText(content);
                        String[] tagList = tags.split(";");
                        for (String tag : tagList) {
                            TextView tv = (TextView) inflater.inflate(R.layout.textview_for_tag, null);
                            lp.setMargins(0, 0, 16, 0);
                            tv.setLayoutParams(lp);
                            tv.setText(tag);
                            tagsLayout.addView(tv);
                        }

                        offerID = null;
                    } else {
                        List<String> tags = new ArrayList<>();
                        tags.add("Tag 1");
                        tags.add("Tag 2");
                        for (String tag : tags) {
                            TextView tv = (TextView) inflater.inflate(R.layout.textview_for_tag, null);
                            lp.setMargins(0, 0, 16, 0);
                            tv.setLayoutParams(lp);
                            tv.setText(tag);
                            tagsLayout.addView(tv);
                        }
                    }

                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_view_offer_bids, container, false);
                    mTutorListView = (ListView) rootView.findViewById(R.id.tutor_listview);
                    mTutorList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        Account tutor = new Account("Trần Đại Số", (int) (Math.random() * (500 - 10)));
                        mTutorList.add(tutor);
                    }
                    mTutorArrayAdapter = new TutorArrayAdapter((CoreActivity) this.getContext(), R.layout.item_tutor, mTutorList);
                    mTutorListView.setAdapter(mTutorArrayAdapter);
                    mTutorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                            alertDialog.setTitle("Do you want to accept this tutor?");
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    getActivity().finish();
                                }
                            });
                            alertDialog.show();
                        }
                    });
                    break;
            }

            return rootView;
        }
    }
}
