package com.j4f.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.j4f.R;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;
import com.j4f.network.J4FClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A login screen that offers login via email/password.
 */
public class SigninActivity extends CoreActivity {

    private Button loginButton;
    private EditText password;
    private AutoCompleteTextView username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initViews();
        initModels();
        initListeners();
        initAnimations();

        if (Build.VERSION.SDK_INT >= 21) {
            setStatusBarColor();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void initViews() {
        loginButton = (Button) findViewById(R.id.confirm_sign_in_button);
        username = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initListeners() {
        loginButton.setOnClickListener(this);
    }

    @Override
    public void initAnimations() {

    }

//    public void login(final String username, final String password ) {
//        showProgressDialog("Login", "Logging in ...");
//        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, Configs.BASE_URL + Configs.LOGIN,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        loge(response.toString());
//                        String status = null;
//                        try {
//                            status = response.getString("status");
//                            if(status.equals("ok")) {
//                                JSONObject data = response.getJSONObject("data");
//                                String id = data.getString("id");
//                                String username = data.getString("username");
//                                String avatar = data.getString("avatar");
//                                Intent intent = new Intent(SigninActivity.this, MainActivity.class);
//                                intent.putExtra("id", id);
//                                intent.putExtra("username", username);
//                                intent.putExtra("avatar", avatar);
//                                startActivity(intent);
//                                removePreviousDialog("Login");
//                            } else {
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loge(error.getMessage());
//                removePreviousDialog("Login");
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", username);
//                params.put("password", password);
//                return params;
//            }
//        };
//        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
//    }

    public void login(String email, String pass) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", pass);
        showProgressDialog("Login", "Loging in...");
        J4FClient.post(Configs.LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("status").equals("ok")) {
                        JSONObject data = response.getJSONObject("data");
                        String id = data.getString("id");
                        String username = data.getString("username");
                        String avatar = data.getString("avatar");
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        MyApplication.USER_ID = id;
                        intent.putExtra("id", id);
                        intent.putExtra("username", username);
                        intent.putExtra("avatar", avatar);
                        finish();
                        startActivity(intent);
                        removePreviousDialog("Login");
                    }
                } catch (JSONException e) {
                    removePreviousDialog("Login");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable e) {
                removePreviousDialog("Login");
                Log.e("onFailure", e.toString());
                Log.e("errorResponse", errorResponse);
            }
        });
    }

    public boolean validate(String email, String password) {
        if(!email.equals("") && !password.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_sign_in_button:
                String email = username.getText().toString();
                String pass = password.getText().toString();
                if(validate(email, pass)) {
                    login(email, pass);
                } else {
                    Snackbar.make(v, "Opps, some fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}

