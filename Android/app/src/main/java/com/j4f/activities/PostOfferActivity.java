package com.j4f.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j4f.R;
import com.j4f.adapters.TimeSlotArrayAdapter;
import com.j4f.application.MyApplication;
import com.j4f.cores.CoreActivity;
import com.j4f.models.TimeSlot;
import com.j4f.utils.CustomRequest;
import com.j4f.utils.TimeUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostOfferActivity extends CoreActivity {

    private static final String BASE_URL = "http://188.166.241.34/hackathon/j4f/";

    private EditText mTitleText;
    private EditText mContactText;
    private EditText mDescriptionText;
    private Button mPostOfferButton;
    private RelativeLayout mAddMoreTimeSlotButton;
    private Switch mAddTimeSwitch;
    private Calendar mCalendar;
    private List<TimeSlot> mTimeSlotList;
    private ListView mTimeSlotListView;
    private TimeSlotArrayAdapter mTimeSlotArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post a Tutor offer");

        mTitleText = (EditText) findViewById(R.id.title);
        mContactText = (EditText) findViewById(R.id.contact);
        mDescriptionText = (EditText) findViewById(R.id.description);
        mPostOfferButton = (Button) findViewById(R.id.post_button);
        mAddTimeSwitch = (Switch) findViewById(R.id.add_time);
        mAddMoreTimeSlotButton = (RelativeLayout) findViewById(R.id.add_more_timeslot_button);
        mAddMoreTimeSlotButton.setVisibility(View.GONE);

        mTimeSlotListView = (ListView) findViewById(R.id.timeslot_listview);
        mTimeSlotListView.setVisibility(View.GONE);
        mTimeSlotList = new ArrayList<>();
        mTimeSlotArrayAdapter = new TimeSlotArrayAdapter(this, R.layout.item_row_timeslot, mTimeSlotList);
        mTimeSlotListView.setAdapter(mTimeSlotArrayAdapter);

        initViews();
        initModels();
        initListeners();
        initAnimations();
    }


    @Override
    public void initViews() {
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initListeners() {
        mAddMoreTimeSlotButton.setOnClickListener(this);
        mPostOfferButton.setOnClickListener(this);

        mAddTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mAddMoreTimeSlotButton.setVisibility(View.VISIBLE);
                    mTimeSlotListView.setVisibility(View.VISIBLE);

                    showDatePicker();



//                    showToastLong("checked");
                } else {
                    mTimeSlotListView.setVisibility(View.GONE);
                    mAddMoreTimeSlotButton.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initAnimations() {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_more_timeslot_button:
                showDatePicker();
                break;
            case R.id.post_button:
                String url = BASE_URL + "offer/new";
                Map<String, String> params = new HashMap<>();
                params.put("tags", "tag1");
                params.put("title", "Test");
                params.put("content", "content");
                params.put("users_id", MyApplication.USER_ID);

                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToastLong(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToastLong("Error");
                    }
                });

                MyApplication.getInstance().addToRequestQueue(jsObjRequest);
                break;
            default:
                break;
        }
    }

    private void showDatePicker() {
        mCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                // TODO Auto-generated method stub
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                showTimePicker();
            }
        };

        new DatePickerDialog(PostOfferActivity.this, listener, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mCalendar.set(Calendar.HOUR_OF_DAY, i);
                mCalendar.set(Calendar.MINUTE, i1);

                mTimeSlotList.add(new TimeSlot(TimeUtils.formatDate(mCalendar),
                        TimeUtils.formatTime(mCalendar), mCalendar));
                mTimeSlotArrayAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(mTimeSlotListView);
            }
        };

        new TimePickerDialog(PostOfferActivity.this, listener, mCalendar.
                get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        int totalHeight = 0;
        for (int i = 0; i < mTimeSlotArrayAdapter.getCount(); i++) {
            View listItem = mTimeSlotArrayAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mTimeSlotArrayAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}

