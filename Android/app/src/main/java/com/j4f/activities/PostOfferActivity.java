package com.j4f.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j4f.R;
import com.j4f.adapters.TimeSlotArrayAdapter;
import com.j4f.application.MyApplication;
import com.j4f.cores.CoreActivity;
import com.j4f.models.TimeSlot;
import com.j4f.utils.Constant;
import com.tokenautocomplete.ContactsCompletionView;
import com.tokenautocomplete.Tag;
import com.j4f.utils.CustomRequest;
import com.j4f.utils.TimeUtils;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostOfferActivity extends CoreActivity implements TokenCompleteTextView.TokenListener {

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
    private ContactsCompletionView mCompletionView;
    private Tag[] mTags;
    private ArrayAdapter<Tag> mTagAdapter;
    private List<String> mOfferedTags;

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

        mOfferedTags = new ArrayList<>();

        initViews();
        initModels();
        initListeners();
        initAnimations();

        mTags = new Tag[]{
                new Tag(1, "Business"),
                new Tag(2, "Data Entry & Admin"),
                new Tag(3, "Design, Media & Architecture"),
                new Tag(4, "Engineering and Science"),
                new Tag(5, "Local Jobs & Service"),
                new Tag(6, "Sales & Marketing"),
                new Tag(7, "Mobile Phones & Computing"),
                new Tag(8, "Others"),
                new Tag(9, "Product Sourcing & Manufacturing"),
                new Tag(10, "Translation & Languages"),
                new Tag(11, "Websites, IT & Software"),
                new Tag(12, "Writing & Content")
        };

        mTagAdapter = new FilteredArrayAdapter<Tag>(this, R.layout.item_tag, mTags) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {

                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.item_tag, parent, false);
                }

                Tag p = getItem(position);
                ((TextView)convertView.findViewById(R.id.name)).setText(p.getName());
                String uri = "@drawable/tag_icon_" + String.valueOf(p.getId());
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                ((ImageView)convertView.findViewById(R.id.icon)).setImageDrawable(getResources().getDrawable(imageResource));

                return convertView;
            }

            @Override
            protected boolean keepObject(Tag tag, String mask) {
                mask = mask.toLowerCase();
                return (tag.getName().toLowerCase().startsWith(mask) && !mOfferedTags.contains(tag.getName()));
            }
        };

        mCompletionView = (ContactsCompletionView)findViewById(R.id.searchView);
        mCompletionView.setAdapter(mTagAdapter);
        mCompletionView.setTokenListener(this);
        mCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
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
                    mCalendar = Calendar.getInstance();
                    showDatePicker();
                } else {
                    mTimeSlotListView.setVisibility(View.GONE);
                    mAddMoreTimeSlotButton.setVisibility(View.GONE);
                }
            }
        });

        mTimeSlotListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCalendar = mTimeSlotList.get(i).getDatetime();
                changeTimeSlot(i);
            }
        });

        mTimeSlotListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mTimeSlotList.remove(i);
                mTimeSlotArrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void initAnimations() {

    }

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
                String title = mTitleText.getText().toString();
                String content = mDescriptionText.getText().toString();
                String contact = mContactText.getText().toString();

                if (title == null || title.length() < 10) {
                    alert("Title required more than 10 characters");
                    break;
                }
                if (content == null || content.length() < 10) {
                    alert("Description required more than 10 characters");
                    break;
                }
                if (mOfferedTags.size() < 1) {
                    alert("Your offer must have at least 1 tag");
                    break;
                }

                String url = Constant.BASE_URL + "offer/new";
                Map<String, String> params = new HashMap<>();
                String tags = "";
                for (String tag : mOfferedTags) {
                    tags += tag + ";";
                }
                params.put("tags", tags);
                params.put("title", title);
                params.put("content", content);
                params.put("users_id", MyApplication.USER_ID);
                if (contact != null) params.put("phone", contact);

                if (mTimeSlotList.size() > 0) {
                    String time = "";
                    for (TimeSlot timeSlot : mTimeSlotList) {
                        time += timeSlot.getDatetime().getTimeInMillis() + ";";
                    }
                    params.put("time", time);
                }

                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        removePreviousDialog("PostOffer Fragment");
                        finish();
                        startActivity(new Intent(PostOfferActivity.this, ViewOfferActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alert("Error when posting your offer");
                        removePreviousDialog("PostOffer Fragment");
                        showToastLong("Error");
                    }
                });

                MyApplication.getInstance().addToRequestQueue(jsObjRequest);
                showProgressDialog("PostOffer Fragment", "Posting...");
                break;
            default:
                break;
        }
    }

    private void alert(final String title) {
        AlertDialog alertDialog = new AlertDialog.Builder(PostOfferActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void changeTimeSlot(final int timeSlotIndex) {
        new DatePickerDialog(PostOfferActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                new TimePickerDialog(PostOfferActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, i);
                        mCalendar.set(Calendar.MINUTE, i1);

                        mTimeSlotList.set(timeSlotIndex, new TimeSlot(TimeUtils.formatDate(mCalendar),
                                TimeUtils.formatTime(mCalendar), mCalendar));
                        mTimeSlotArrayAdapter.notifyDataSetChanged();
                    }
                }, mCalendar.
                        get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
            }
        }, mCalendar
                .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDatePicker() {
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
            }
        };

        new TimePickerDialog(PostOfferActivity.this, listener, mCalendar.
                get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false).show();
    }


    @Override
    public void onTokenAdded(Object token) {
        mOfferedTags.add(((Tag)token).getName());
    }

    @Override
    public void onTokenRemoved(Object token) {

    }
}

