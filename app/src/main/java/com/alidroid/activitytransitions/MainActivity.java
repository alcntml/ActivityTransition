package com.alidroid.activitytransitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putBoolean("someBoolean",true);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, ivProfile, "profile");

                new ActivityTransition.Builder(MainActivity.this,SecondActivity.class)
                        .setBundle(b)
                        .setTransition(new Transition.TransitionSlide())//you dont need to set slide animation. Because "TransitionSlide" is default.
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        .setActivityOptionsCompat(options)
                        .setClearTask(false)//you dont need to set this. Because "false" is default.
                        .build().startActivity();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        Transition transition = new Transition.TransitionSlide();
        overridePendingTransition(transition.getEnterAnim(), transition.getExitAnim());
    }

    protected void overridePendingTransitionExit() {
        Transition transition = new Transition.TransitionSlide();
        overridePendingTransition(transition.getEnterAnimBack(), transition.getExitAnimBack());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }
}
