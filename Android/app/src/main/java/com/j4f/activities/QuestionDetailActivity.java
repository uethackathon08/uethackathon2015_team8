package com.j4f.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.j4f.R;
import com.j4f.adapters.CommentAdapter;
import com.j4f.application.MyApplication;
import com.j4f.application.Utils;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.interfaces.ImageRequestListener;
import com.j4f.models.Account;
import com.j4f.models.Comment;
import com.j4f.network.J4FClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class QuestionDetailActivity extends CoreActivity {

    private String userId;
    private String questionId;
    private String nameOfUser;
    private String userAvatarLink;
    private String title;
    private String photo, contentText;
    private Date date;

    private ImageView userAvatar;
    private TextView quesTittle, nameUser, content, time;

    private boolean isSend = false;

    private ImageView send;
    private ImageView voice;
    private EditText typing;

    private ArrayList<Comment> commentList;
    private CommentAdapter commentAdapter;
    private UltimateRecyclerView commentRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        questionId = i.getStringExtra("qId");
        userId = i.getStringExtra("uId");
        nameOfUser = i.getStringExtra("uName");
        userAvatarLink = i.getStringExtra("avatar");
        title = i.getStringExtra("title");
        photo = i.getStringExtra("photo");
        contentText = i.getStringExtra("content");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = formatter.parse(i.getStringExtra("date"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
        initViews();
        initModels();
        initListeners();
        initAnimations();
    }

    public void addComment(final Comment o) {
        commentList.add(o);
        commentAdapter.notifyDataSetChanged();
    }

    public int mMaxComment = -1;
    public int mCurrentCommentPage = 1;
    public int mLimit = -1;

    @TargetApi(Build.VERSION_CODES.M)
    public void initCommentList() {
        commentList = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(commentList, QuestionDetailActivity.this);
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(QuestionDetailActivity.this));
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.enableLoadmore();
        commentRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                mCurrentCommentPage++;
                if (mCurrentCommentPage <= mLimit) {
                    getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
                }
            }
        });
        commentRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentList = new ArrayList<Comment>();
                commentAdapter = new CommentAdapter(commentList, QuestionDetailActivity.this);
                commentAdapter.notifyDataSetChanged();
                commentRecyclerView.setAdapter(commentAdapter);
                mMaxComment = -1;
                mCurrentCommentPage = 1;
                mLimit = -1;
                getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
            }
        });
        getALlComment(questionId, Configs.COMMENT_PAGE_LIMIT, mCurrentCommentPage);
    }

    public void getALlComment(final String qId, final int limited, final int page) {
        showProgressDialog("Question Detail Fragment", "Loading offers ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, Configs.BASE_URL +
                Configs.GET_ALL_COMMENTS + "?questions_id=" + qId + "&limit=" + limited + "&page=" + page,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            if (status.equals("ok")) {
                                if (mMaxComment == -1) {
                                    mMaxComment = response.getInt("count");
                                    int d = mMaxComment / Configs.COMMENT_PAGE_LIMIT;
                                    mLimit = mMaxComment % Configs.COMMENT_PAGE_LIMIT == 0 ? d : d + 1;
                                }
                                JSONArray ja = response.getJSONArray("data");
                                loge(ja.toString());
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jo = ja.getJSONObject(i);
                                    String id = jo.getString("id");
                                    String photo = jo.getString("photo");
                                    String content = jo.getString("content");
                                    String questions_id = jo.getString("questions_id");
                                    String userId = jo.getString("users_id");
                                    String upvotes = jo.getString("upvotes");
                                    String downvotes = jo.getString("downvotes");
                                    String avatar = jo.getString("avatar");
                                    String date = jo.getString("created_at");
                                    String name = jo.getString("username");
                                    addComment(new Comment(userId, name, avatar, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date),
                                            content, Integer.parseInt(upvotes), Integer.parseInt(downvotes)));
                                }
                                commentRecyclerView.setRefreshing(false);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        removePreviousDialog("Question Detail Fragment");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loge(error.getMessage());
                removePreviousDialog("Question Detail Fragment");
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    @Override
    public void initViews() {
        commentRecyclerView = (UltimateRecyclerView) findViewById(R.id.comment_list);
        typing = (EditText) findViewById(R.id.typing);
        send = (ImageView) findViewById(R.id.camera);
        voice = (ImageView) findViewById(R.id.voice);
        userAvatar = (ImageView) findViewById(R.id.avatar);
        quesTittle = (TextView) findViewById(R.id.title);
        nameUser = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        content = (TextView) findViewById(R.id.content);
    }

    @Override
    public void initModels() {
        initCommentList();

        makeImageRequest(userAvatarLink, new ImageRequestListener() {
            @Override
            public void onBefore() {
                userAvatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer paramImageContainer, boolean paramBoolean) {
                if (paramImageContainer.getBitmap() != null) {
                    userAvatar.setImageBitmap(Utils.getCircleBitmap(paramImageContainer.getBitmap()));
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loge("Load image failed " + error.getMessage());
            }
        });
        quesTittle.setText(title);
        nameUser.setText(nameOfUser);
        content.setText(contentText);

        time.setText(new SimpleDateFormat("HH:mm dd/MM ").format(date));
    }

    @Override
    public void initListeners() {
        voice.setOnClickListener(this);
        send.setOnClickListener(this);
        typing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    isSend = true;
                    send.setImageDrawable(getResources().getDrawable(R.mipmap.send));
                } else {
                    isSend = false;
                    send.setImageDrawable(getResources().getDrawable(R.mipmap.camera));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initAnimations() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice:
                break;
            case R.id.camera:
                if (isSend) {
                    String s = typing.getText().toString();
                    if (!s.equals("")) {
                        sendCommentRequest(s, questionId, photo);
                    }
                } else {
                    Log.d("TestCamera", "function reached");
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {

                        }
                        if (photoFile != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private String mCurrentPhotoPath;

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

    private static final int CAMERA_REQUEST = 1888;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            File imgFile = new File(mCurrentPhotoPath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
        }
    }

    public void sendCommentRequest(final String content, final String questionId, final String photo) {
        final Account a = MainActivity.currentAccount;
        RequestParams params = new RequestParams();
        params.put("content", content);
        params.put("users_id", a.getId());
        params.put("questions_id", questionId);
        params.put("photo", photo);
        showProgressDialog("Comment", "Waiting...");
        J4FClient.post(Configs.COMMENT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("status").equals("ok")) {
                        loge(response.toString());
                        removePreviousDialog("Comment");
                        addComment(new Comment(a.getId(), a.getName(), a.getAvatarLink(), new Date(), content, 0, 0));
                        typing.setText("");
                    }
                } catch (JSONException e) {
                    removePreviousDialog("Comment");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                removePreviousDialog("Comment");
                Log.e("onFailure", e.toString());
                Log.e("errorResponse", errorResponse);
            }
        });
    }
}
