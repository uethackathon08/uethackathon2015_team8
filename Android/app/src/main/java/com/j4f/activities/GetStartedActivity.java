package com.j4f.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.customizes.MyAnimations;


public class GetStartedActivity extends CoreActivity {
    private Button signIn, signUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button btn_signin = (Button)findViewById(R.id.btn_signin);
        Button btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),SigninActivity.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        startActivity(new Intent(GetStartedActivity.this, PostQuestionActivity.class));

        initViews();
        initModels();
        initListeners();
        initAnimations();
    }

    @Override
    public void initViews() {
        signIn = (Button)findViewById(R.id.btn_signin);
        signUp = (Button)findViewById(R.id.btn_signup);
    }

    @Override
    public void initModels() {

    }

    @Override
    public void initListeners() {
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void initAnimations() {
        signIn.startAnimation(MyAnimations.fromLeft(500, 300));
        signUp.startAnimation(MyAnimations.fromRight(500, 300));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin:
                startActivity(new Intent(getBaseContext(),SigninActivity.class));
                break;
            case R.id.btn_signup:
                startActivity(new Intent(getBaseContext(), SignupActivity.class));
                break;
            default:
                break;
        }
    }
}
