package com.j4f.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j4f.R;
import com.j4f.application.MyApplication;
import com.j4f.cores.CoreActivity;
import com.j4f.network.J4FClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tokenautocomplete.ContactsCompletionView;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.Tag;
import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PostQuestionActivity extends CoreActivity implements TokenCompleteTextView.TokenListener {

    private EditText mTitleText;
    private EditText mDescriptionText;
    private static final int CAMERA_REQUEST = 1888;
    private Button mPostQuestionButton;
    private ImageView imageView;
    private String mCurrentPhotoPath;

    private ContactsCompletionView mCompletionView;
    private Tag[] mTags;
    private ArrayAdapter<Tag> mTagAdapter;
    private List<String> mOfferedTags;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_offer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post a question");

        mTitleText = (EditText) findViewById(R.id.title);
        mDescriptionText = (EditText) findViewById(R.id.description);
        mPostQuestionButton = (Button) findViewById(R.id.post_button);
        imageView = (ImageView) findViewById(R.id.my_image);

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
        mPostQuestionButton.setOnClickListener(this);
    }

    @Override
    public void initAnimations() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question_details, menu);
        return true;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_image:
                Log.d("TestCamera", "function reached");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {

                    }
                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                    }
                }
                break;
        }
//        //noinspection SimplifiableIfStatement
//        if (id == android.R.id.home) {
//
//        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            File imgFile = new File(mCurrentPhotoPath);
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_button:
                if (mTitleText.getText().toString().isEmpty() || mDescriptionText.getText().toString().isEmpty()
                        || mOfferedTags.size() < 1) {
                    Toast.makeText(this, "Please input title and description!", Toast.LENGTH_SHORT).show();
                } else {
                    RequestParams params = new RequestParams();
                    String tags = "";
                    for (String tag : mOfferedTags) {
                        tags += tag + ";";
                    }
                    params.put("tags", tags);
                    params.put("title", mTitleText.getText().toString());
                    params.put("content", mDescriptionText.getText().toString());
                    params.put("users_id", MyApplication.USER_ID);
                    if (mCurrentPhotoPath != null) {
                        try {
                            params.put("photo", new File(mCurrentPhotoPath));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    showProgressDialog("PostQuestion", "Posting...");

                    String url = "question/new";
                    J4FClient.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            try {
                                if (response.getString("status").equals("ok")) {
                                    // TODO go to detail page
                                    removePreviousDialog("PostQuestion");
                                    finish();
                                    startActivity(new Intent(PostQuestionActivity.this, QuestionDetailActivity.class));
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
                    break;
                }
        }
    }

    @Override
    public void onTokenAdded(Object token) {
        mOfferedTags.add(((Tag)token).getName());
    }

    @Override
    public void onTokenRemoved(Object token) {

    }
}

