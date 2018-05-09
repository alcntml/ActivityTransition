package com.alidroid.activitytransitions;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alicantemel on 5.04.2018.
 */

public class ActivityTransition {
    private Activity mActivity;
    private ActivityOptionsCompat mActivityOptionsCompat;
    private Bundle mBundle;
    private List<Integer> mFlags;
    private Transition mTransition;
    private Class<?> mClass;
    private boolean clearTask;

    public ActivityTransition(final Builder builder) {
        this.mActivity = builder.mActivity;
        this.mClass = builder.mClass;
        this.mActivityOptionsCompat = builder.mActivityOptionsCompat;
        this.mBundle = builder.mBundle;
        this.mFlags = builder.mFlags;
        this.mTransition = builder.mTransition;
        this.clearTask = builder.clearTask;
    }

    public void startActivity(){
        start(getIntent());
    }

    public void startActivity(int delay){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start(getIntent());
            }
        }, delay);
    }

    private Intent getIntent(){
        final Intent intent = new Intent(mActivity, mClass);
        if (mFlags != null && mFlags.size()>0){
            for (Integer flag : mFlags) {
                intent.addFlags(flag);
            }
        }
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }else{
            intent.putExtras(new Bundle());
        }
        return intent;
    }

    private void start(Intent intent){
        if (mActivity != null && intent != null) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN && mActivityOptionsCompat != null) {
                mActivity.startActivity(intent, mActivityOptionsCompat.toBundle());
            } else {
                mActivity.startActivity(intent);
            }
            if (mTransition != null) {
                transition(mActivity, mTransition.getEnterAnim(), mTransition.getExitAnim());
            }
            clearTaskIfNeeded();
        }
    }

    private void clearTaskIfNeeded(){
        if (clearTask){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mActivity != null) {
                        mActivity.finishAffinity();
                    }
                }
            }, 270);
        }
    }

    private void transition(Activity activity,int enterAnim, int exitAnim){
        if (exitAnim != -1 && enterAnim != -1) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static class Builder{
        private Activity mActivity = null;
        private ActivityOptionsCompat mActivityOptionsCompat = null;
        private Bundle mBundle = null;
        private List<Integer> mFlags = null;
        private Transition mTransition = new Transition.TransitionSlide();
        private Class<?> mClass = null;
        private boolean clearTask = false;

        public Builder(Activity mActivity,Class<?> mClass) {
            this.mActivity = mActivity;
            this.mClass = mClass;
        }

        public ActivityTransition build(){
            ActivityTransition activityTransition = new ActivityTransition(this);
            if (activityTransition.mActivity == null) {
                throw new IllegalStateException("Activity can not be null!");
            }
            if (activityTransition.mClass == null) {
                throw new IllegalStateException("Target class can not be null!");
            }
            return activityTransition;
        }

        public Builder setActivityOptionsCompat(ActivityOptionsCompat mActivityOptionsCompat) {
            this.mActivityOptionsCompat = mActivityOptionsCompat;
            return this;
        }

        public Builder setBundle(Bundle mBundle) {
            this.mBundle = mBundle;
            return this;
        }

        public Builder setFlag(Integer mFlag) {
            this.mFlags = new ArrayList<>();
            this.mFlags.add(mFlag);
            return this;
        }

        public Builder setFlags(Integer... mFlags) {
            this.mFlags = new ArrayList<>();
            Collections.addAll(this.mFlags, mFlags);
            return this;
        }

        public Builder setFlags(List<Integer> mFlags) {
            this.mFlags = mFlags;
            return this;
        }

        public Builder setTransition(Transition mTransition) {
            this.mTransition = mTransition;
            return this;
        }

        public Builder setClearTask(boolean clearTask) {
            this.clearTask = clearTask;
            return this;
        }
    }
}
