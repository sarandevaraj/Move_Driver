package com.taximobility.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by product team on 16/2/18.
 */

public class FreezedTextview extends androidx.appcompat.widget.AppCompatTextView implements View.OnTouchListener {

    public FreezedTextview(Context context) {
        super(context);
        init(null, context);
    }

    public FreezedTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public FreezedTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        // TODO Auto-generated constructor stub
        setOnTouchListener(this);
        //also apply the button font here
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        AnimateButton(view);
        FreezeButton(view);
        return false;
    }

    public void FreezeButton(final View view) {
        view.setEnabled(false);
        view.setClickable(false);
        view.performClick();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
                view.setClickable(true);
            }
        }, 1700);
    }

    public void AnimateButton(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            view.setAlpha(0.5f);
            ValueAnimator ani = ValueAnimator.ofFloat(0.5f, 1f); //change for (0,1) if you want a fade in
            ani.setDuration(300);
            ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.setAlpha((float) animation.getAnimatedValue());
                }
            });
            ani.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view.setAlpha(1f);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            ani.start();
        }
    }

}
