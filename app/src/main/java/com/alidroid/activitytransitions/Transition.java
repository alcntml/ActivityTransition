package com.alidroid.activitytransitions;

/**
 * Created by alicantemel on 5.04.2018.
 */

public class Transition{
    private int mEnterAnim;
    private int mExitAnim;
    private int mEnterAnimBack;
    private int mExitAnimBack;

    public Transition(int mEnterAnim, int mExitAnim, int mEnterAnimBack, int mExitAnimBack) {
        this.mEnterAnim = mEnterAnim;
        this.mExitAnim = mExitAnim;
        this.mEnterAnimBack = mEnterAnimBack;
        this.mExitAnimBack = mExitAnimBack;
    }

    public int getEnterAnim() {
        return mEnterAnim;
    }

    public int getExitAnim() {
        return mExitAnim;
    }

    public int getEnterAnimBack() {
        return mEnterAnimBack;
    }

    public int getExitAnimBack() {
        return mExitAnimBack;
    }

    public static class TransitionSlide extends Transition{
        public TransitionSlide() {
            super(R.anim.slide_from_right, R.anim.slide_to_left, R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }

    public static class TransitionSlideUpDown extends Transition{
        public TransitionSlideUpDown() {
            super(R.anim.enter_in_bottom, R.anim.no_anim, R.anim.no_anim, R.anim.exit_out_bottom);
        }
    }

    public static class TransitionAlpha extends Transition{
        public TransitionAlpha() {
            super(R.anim.alpha_enter, R.anim.alpha_exit, R.anim.no_anim, R.anim.alpha_exit);
        }
    }

    public static class TransitionFadeInOut extends Transition{
        public TransitionFadeInOut() {
            super(R.anim.fade_in, R.anim.fade_out, R.anim.no_anim, R.anim.fade_out);
        }
    }
}
