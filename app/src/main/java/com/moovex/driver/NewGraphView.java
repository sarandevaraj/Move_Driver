package com.moovex.driver;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.moovex.R;

public class NewGraphView extends LinearLayout {

    private MyCustomObjectListener listener = null;
    private boolean isClicked = false;
    private int prevPos = 0;
    private int mUnSelectColor;
    private int selectedColor;
    private Drawable bgdrawable;

    public NewGraphView(Context context) {
        super(context);
        init(context);
    }

    public NewGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NewGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = 350;
        int desiredHeight = 250;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = Math.max(desiredWidth, widthSize);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.max(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        //Measure Height
        height = desiredHeight;
        setMeasuredDimension(width, heightSize);
    }

    void init(Context context) {
        View.inflate(context, R.layout.graph_view, this);
        setPeekValues(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.graph_view, this);
    /*    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NewGraphView, 0, 0);
        selectedColor = a.getColor(R.styleable.NewGraphView_selectedColor, -1);
        mUnSelectColor = a.getColor(R.styleable.NewGraphView_unSelectedColor, -1);
        bgdrawable = a.getDrawable(R.styleable.NewGraphView_backgroundSelect);*/
        setPeekValues(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void setPeekValues(
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            float f7
    ) {

        float peekHeight;


        for (int i = 0; i < 7; i++) {

            switch (i) {
                case 0:
                    peekHeight = f1;
                    break;
                case 1:
                    peekHeight = f2;
                    break;
                case 2:
                    peekHeight = f3;
                    break;
                case 3:
                    peekHeight = f4;
                    break;
                case 4:
                    peekHeight = f5;
                    break;
                case 5:
                    peekHeight = f6;
                    break;
                case 6:
                    peekHeight = f7;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            final LinearLayout parentLayout = (LinearLayout) getChildAt(0);

            final LinearLayout dayLayout = (LinearLayout) parentLayout.getChildAt(i);
            dayLayout.setBackground(null);

            LinearLayout progressLayout = (LinearLayout) dayLayout.getChildAt(0);

            View viewOne = progressLayout.getChildAt(0);

            final View viewTwo = progressLayout.getChildAt(1);
            viewTwo.setBackgroundColor(mUnSelectColor);

            final float finalPeekHeight = peekHeight;
            final int finalI = i;

            dayLayout.setOnClickListener(v -> {

                if (!isClicked) {
                    prevPos = finalI;
                    isClicked = true;
                } else {
                    LinearLayout prevDayLayout = (LinearLayout) parentLayout.getChildAt(prevPos);
                    prevDayLayout.setBackground(null);
                    LinearLayout progressLayout1 = (LinearLayout) prevDayLayout.getChildAt(0);
                    View viewTwo1 = progressLayout1.getChildAt(1);
                    viewTwo1.setBackgroundColor(mUnSelectColor);
                    prevPos = finalI;
                }
                dayLayout.setBackground(bgdrawable);
                if (listener != null) {
                    listener.getValue(finalPeekHeight);
                }

                viewTwo.setBackgroundColor(selectedColor);
                if (listener != null) {
                    listener.getXY(dayLayout.getX(), dayLayout.getY());
                }
            });

            LayoutParams paramOne = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    peekHeight);

            LayoutParams paramTwo = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    (float) (100.0 - peekHeight));

            TranslateAnimation translateAnimation = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT,
                    0f,
                    Animation.RELATIVE_TO_PARENT,
                    0f,
                    Animation.RELATIVE_TO_PARENT,
                    30f,
                    Animation.RELATIVE_TO_PARENT,
                    0f
            );

            translateAnimation.setDuration(700);
            translateAnimation.setFillAfter(true);

            viewOne.setLayoutParams(paramOne);
            viewTwo.setLayoutParams(paramTwo);
            viewTwo.startAnimation(translateAnimation);
            progressLayout.setVisibility(View.VISIBLE);
        }
        invalidate();
    }

    public interface MyCustomObjectListener {

        void getValue(float peekHeight);

        void getXY(float xValue, float yValue);

    }

    public void setCustomObjectListener(MyCustomObjectListener listener) {
        this.listener = listener;
    }
}

//  paste in attrs.xml

// set value for  graph

//        cvEarningsLayout.setOnClickListener {
//        booleanFirst = if (booleanFirst) {
//        binding.graphView.setPeekValues(50.0f, 40.0f, 30.0f, 70.0f, 10.0f, 100.0f, 80.0f)
//        false
//        } else {
//        binding.graphView.setPeekValues(80.0f, 40.0f, 20.0f, 70.0f, 10.0f, 10.0f, 50.0f)
//        true
//        }
//        }

//// get value using listener

//        graphView.setCustomObjectListener(object : NewGraphView.MyCustomObjectListener {
//
//        override fun getValue(peekHeight: Float) {
//        Toast.makeText(context, peekHeight.toString(), Toast.LENGTH_SHORT).show()
//        }

//        override fun getXY(xValue: Float, yValue: Float) {
////                Toast.makeText(context, xValue.toString(), Toast.LENGTH_SHORT).show()

//        }
//        })