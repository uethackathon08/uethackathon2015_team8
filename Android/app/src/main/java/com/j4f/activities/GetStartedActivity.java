package com.j4f.activities;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
                finish();
                startActivity(intent);
            }
        });

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
