package com.taximobility.data;

import android.content.Context;
import android.os.Handler;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.taximobility.util.Systems;

/**
 * This class is used to get the MAP functionality is dragged,is touched,is moved.
 */

//
public class MapWrapperLayout extends RelativeLayout {
    private static final String DEBUG_TAG = "";
    private static boolean bookingPage;
    private int fingers = 0;
    private GoogleMap googleMap;
    private long lastZoomTime = 0;
    private float lastSpan = -1;
    private Handler handler = new Handler();
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private VelocityTracker mVelocityTracker;
    private int pointerId;
    private BottomSheetBehavior<View> carLay;

    private static boolean mMapIsTouched = true;
    private Runnable mRunnable;
    private Handler mHandler;

    public MapWrapperLayout(Context context) {
        super(context);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapWrapperLayout(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
    }


    public void init(GoogleMap map, int bottomOffsetPixels, boolean bookingpage, BottomSheetBehavior<View> carlayout) {
        bookingPage = bookingpage;
        carLay = carlayout;
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if (lastSpan == -1) {
                    lastSpan = detector.getCurrentSpan();
                } else if (detector.getEventTime() - lastZoomTime >= 50) {
                    lastZoomTime = detector.getEventTime();
                    googleMap.animateCamera(CameraUpdateFactory.zoomBy(getZoomValue(detector.getCurrentSpan(), lastSpan)), 50, null);
                    lastSpan = detector.getCurrentSpan();
                }
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                lastSpan = -1;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                lastSpan = -1;

            }
        });
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                disableScrolling();
                googleMap.animateCamera(CameraUpdateFactory.zoomIn(), 400, null);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        googleMap = map;
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                setmMapIsTouched(true);
            }
        };
    }

    private float getZoomValue(float currentSpan, float lastSpan) {
        double value = (Math.log(currentSpan / lastSpan) / Math.log(1.55d));
        return (float) value;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            gestureDetector.onTouchEvent(ev);
            pointerId = ev.getPointerId(ev.getActionIndex());
            switch (ev.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    fingers = fingers + 1;
                    googleMap.animateCamera(CameraUpdateFactory.zoomOut(), 400, null);
                    break;
                case MotionEvent.ACTION_POINTER_UP:

                    fingers = fingers - 1;
                    break;
                case MotionEvent.ACTION_UP:
                    mHandler.postDelayed(mRunnable, 10000);
                    fingers = 0;
                    break;
                case MotionEvent.ACTION_DOWN:
                    setmMapIsTouched(false);
                    if (carLay != null)
                        carLay.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    fingers = 1;

                    if (mVelocityTracker == null) {
                        // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                        mVelocityTracker = VelocityTracker.obtain();
                    } else {
                        // Reset the velocity tracker back to its initial state.
                        mVelocityTracker.clear();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Systems.out.println("________________movvvv");
                    if (mVelocityTracker == null) {
                        // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                        mVelocityTracker = VelocityTracker.obtain();
                    }
                    mVelocityTracker.addMovement(ev);
                    mVelocityTracker.computeCurrentVelocity(100);

                    break;

            }


            if (fingers > 1) {
                disableScrolling();
            } else if (fingers < 1) {
                enableScrolling();
            }
            if (fingers > 1) {
                return scaleGestureDetector.onTouchEvent(ev);
            } else {
                return super.dispatchTouchEvent(ev);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void enableScrolling() {
        if (googleMap != null && !googleMap.getUiSettings().isScrollGesturesEnabled()) {
            handler.postDelayed(() -> {
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
            }, 50);
        }
    }

    private void disableScrolling() {
        handler.removeCallbacksAndMessages(null);
        if (googleMap != null && googleMap.getUiSettings().isScrollGesturesEnabled()) {
            googleMap.getUiSettings().setAllGesturesEnabled(false);
        }
    }

    public static boolean ismMapIsTouched() {
        return mMapIsTouched;
    }

    public static void setmMapIsTouched(boolean mMapIsToucheds) {
        mMapIsTouched = mMapIsToucheds;
    }
}