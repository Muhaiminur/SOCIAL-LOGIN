package com.muhaiminuabir.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.phone_authincation)
    Button phoneAuthincation;
    @BindView(R.id.google_authincation)
    Button googleAuthincation;
    @BindView(R.id.facebook_authincation)
    Button facebookAuthincation;
    @BindView(R.id.github_authincation)
    Button githubAuthincation;
    @BindView(R.id.twitter_authincation)
    Button twitterAuthincation;
    @BindView(R.id.email_authincation)
    Button emailAuthincation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.phone_authincation, R.id.google_authincation, R.id.facebook_authincation, R.id.github_authincation, R.id.twitter_authincation, R.id.email_authincation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_authincation:
                startActivity(new Intent(MainActivity.this,PHONE_ACTIVITY.class));
                break;
            case R.id.google_authincation:
                startActivity(new Intent(MainActivity.this,GOOGLE_ACTIVITY.class));
                break;
            case R.id.facebook_authincation:
                startActivity(new Intent(MainActivity.this,FACEBOOK_ACTIVITY.class));
                break;
            case R.id.github_authincation:
                startActivity(new Intent(MainActivity.this,GITHUB_ACTIVITY.class));
                break;
            case R.id.twitter_authincation:
                break;
            case R.id.email_authincation:
                break;
        }
    }
}
