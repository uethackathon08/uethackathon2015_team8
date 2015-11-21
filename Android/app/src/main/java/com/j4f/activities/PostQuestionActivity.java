package com.j4f.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.j4f.R;
import com.j4f.application.MyApplication;
import com.j4f.cores.CoreActivity;
import com.j4f.utils.CustomRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostQuestionActivity extends CoreActivity {

    private static final String BASE_URL = "http://188.166.241.34/hackathon/j4f/";

    private EditText mTitleText;
    private EditText mDescriptionText;
    private static final int CAMERA_REQUEST = 1888;
    private Button mPostQuestionButton;
    private ImageView imageView;
    private String mCurrentPhotoPath;

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

    private File createImageFile() throws IOException{
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
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_button:
                String url = BASE_URL + "question/new";
                Map<String, String> params = new HashMap<>();
                params.put("tags", "Thật là vui");
                params.put("title", "Vui Vui Vui : Đại ca thống");
                params.put("content", "Ha ha ha");
                params.put("users_id", MyApplication.USER_ID);
                File imgFile = new File(mCurrentPhotoPath);
                Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                params.put("photo",encodedImage);
                Log.d("Hello world",encodedImage);
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
        }
    }
}

