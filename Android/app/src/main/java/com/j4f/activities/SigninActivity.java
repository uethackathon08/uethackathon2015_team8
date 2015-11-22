package com.j4f.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.j4f.R;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.cores.CoreActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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

    public void login(final String username, final String password ) {
        showProgressDialog("Login", "Logging in ...");
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.POST, Configs.BASE_URL + Configs.LOGIN,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToastLong(response.toString());
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        removePreviousDialog("Login");
//                        mListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mListener.onError(error);
                loge(error.getMessage());
                removePreviousDialog("Login");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_sign_in_button:
                String email = username.getText().toString();
                String pass = password.getText().toString();
                if(email.equals("") && pass.equals("")) {
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    login(email, pass);
                }
                break;
            default:
                break;
        }
    }
}

