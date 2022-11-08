package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SearchLocationBindingImpl extends SearchLocationBinding implements com.taximobility.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.searchlay, 7);
        sViewsWithIds.put(R.id.pickup_pinlay, 8);
        sViewsWithIds.put(R.id.drop_pin, 9);
        sViewsWithIds.put(R.id.pick_lay, 10);
        sViewsWithIds.put(R.id.pick_pin, 11);
        sViewsWithIds.put(R.id.pic_loc_select, 12);
        sViewsWithIds.put(R.id.pickup_drop_Sep, 13);
        sViewsWithIds.put(R.id.drop_lay, 14);
        sViewsWithIds.put(R.id.drop_pin1, 15);
        sViewsWithIds.put(R.id.drop_loc_select, 16);
        sViewsWithIds.put(R.id.view1, 17);
        sViewsWithIds.put(R.id.view2, 18);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    @Nullable
    private final android.view.View.OnClickListener mCallback4;
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SearchLocationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private SearchLocationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 6
            , null
            , (android.widget.LinearLayout) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[16]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[15]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.ImageView) bindings[11]
            , null
            , (android.view.View) bindings[13]
            , (android.widget.FrameLayout) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[1]
            , (android.view.View) bindings[17]
            , (android.view.View) bindings[18]
            );
        this.recentItemLay.setTag(null);
        this.relativelay.setTag(null);
        this.txtDrop.setTag(null);
        this.txtLocation1.setTag(null);
        this.txtLocation2.setTag(null);
        this.txtLocation3.setTag(null);
        this.txtPickup.setTag(null);
        setRootTag(root);
        // listeners
        mCallback5 = new com.taximobility.generated.callback.OnClickListener(this, 3);
        mCallback4 = new com.taximobility.generated.callback.OnClickListener(this, 2);
        mCallback3 = new com.taximobility.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.myViewModel == variableId) {
            setMyViewModel((com.taximobility.bookingmodule.pickDropLoc.SearchViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMyViewModel(@Nullable com.taximobility.bookingmodule.pickDropLoc.SearchViewModel MyViewModel) {
        this.mMyViewModel = MyViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.myViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMyViewModelRecentPlace3((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeMyViewModelRecentPlace1((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 2 :
                return onChangeMyViewModelDropLoc((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 3 :
                return onChangeMyViewModelRecentPlace2((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 4 :
                return onChangeMyViewModelPickupLoc((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 5 :
                return onChangeMyViewModelRecentVisible((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMyViewModelRecentPlace3(androidx.lifecycle.MutableLiveData<java.lang.String> MyViewModelRecentPlace3, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelRecentPlace1(androidx.lifecycle.MutableLiveData<java.lang.String> MyViewModelRecentPlace1, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelDropLoc(androidx.lifecycle.MutableLiveData<java.lang.String> MyViewModelDropLoc, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelRecentPlace2(androidx.lifecycle.MutableLiveData<java.lang.String> MyViewModelRecentPlace2, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelPickupLoc(androidx.lifecycle.MutableLiveData<java.lang.String> MyViewModelPickupLoc, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelRecentVisible(androidx.lifecycle.MutableLiveData<java.lang.Integer> MyViewModelRecentVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelRecentPlace3 = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelRecentPlace1 = null;
        boolean myViewModelRecentVisibleInt2 = false;
        java.lang.String myViewModelRecentPlace2GetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelDropLoc = null;
        java.lang.String myViewModelRecentPlace1GetValue = null;
        boolean myViewModelRecentVisibleInt3 = false;
        int androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue = 0;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelRecentPlace2 = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelPickupLoc = null;
        int myViewModelRecentVisibleInt1ViewVISIBLEViewGONE = 0;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> myViewModelRecentVisible = null;
        int myViewModelRecentVisibleInt2ViewVISIBLEViewGONE = 0;
        java.lang.String myViewModelPickupLocGetValue = null;
        boolean myViewModelRecentVisibleInt1 = false;
        int myViewModelRecentVisibleInt3ViewVISIBLEViewGONE = 0;
        java.lang.String myViewModelDropLocGetValue = null;
        com.taximobility.bookingmodule.pickDropLoc.SearchViewModel myViewModel = mMyViewModel;
        java.lang.String myViewModelRecentPlace3GetValue = null;
        java.lang.Integer myViewModelRecentVisibleGetValue = null;

        if ((dirtyFlags & 0xffL) != 0) {


            if ((dirtyFlags & 0xc1L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.recentPlace3
                        myViewModelRecentPlace3 = myViewModel.getRecentPlace3();
                    }
                    updateLiveDataRegistration(0, myViewModelRecentPlace3);


                    if (myViewModelRecentPlace3 != null) {
                        // read myViewModel.recentPlace3.getValue()
                        myViewModelRecentPlace3GetValue = myViewModelRecentPlace3.getValue();
                    }
            }
            if ((dirtyFlags & 0xc2L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.recentPlace1
                        myViewModelRecentPlace1 = myViewModel.getRecentPlace1();
                    }
                    updateLiveDataRegistration(1, myViewModelRecentPlace1);


                    if (myViewModelRecentPlace1 != null) {
                        // read myViewModel.recentPlace1.getValue()
                        myViewModelRecentPlace1GetValue = myViewModelRecentPlace1.getValue();
                    }
            }
            if ((dirtyFlags & 0xc4L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.dropLoc
                        myViewModelDropLoc = myViewModel.getDropLoc();
                    }
                    updateLiveDataRegistration(2, myViewModelDropLoc);


                    if (myViewModelDropLoc != null) {
                        // read myViewModel.dropLoc.getValue()
                        myViewModelDropLocGetValue = myViewModelDropLoc.getValue();
                    }
            }
            if ((dirtyFlags & 0xc8L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.recentPlace2
                        myViewModelRecentPlace2 = myViewModel.getRecentPlace2();
                    }
                    updateLiveDataRegistration(3, myViewModelRecentPlace2);


                    if (myViewModelRecentPlace2 != null) {
                        // read myViewModel.recentPlace2.getValue()
                        myViewModelRecentPlace2GetValue = myViewModelRecentPlace2.getValue();
                    }
            }
            if ((dirtyFlags & 0xd0L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.pickupLoc
                        myViewModelPickupLoc = myViewModel.getPickupLoc();
                    }
                    updateLiveDataRegistration(4, myViewModelPickupLoc);


                    if (myViewModelPickupLoc != null) {
                        // read myViewModel.pickupLoc.getValue()
                        myViewModelPickupLocGetValue = myViewModelPickupLoc.getValue();
                    }
            }
            if ((dirtyFlags & 0xe0L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.recentVisible
                        myViewModelRecentVisible = myViewModel.getRecentVisible();
                    }
                    updateLiveDataRegistration(5, myViewModelRecentVisible);


                    if (myViewModelRecentVisible != null) {
                        // read myViewModel.recentVisible.getValue()
                        myViewModelRecentVisibleGetValue = myViewModelRecentVisible.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue = androidx.databinding.ViewDataBinding.safeUnbox(myViewModelRecentVisibleGetValue);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2
                    myViewModelRecentVisibleInt2 = (androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue) >= (2);
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3
                    myViewModelRecentVisibleInt3 = (androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue) >= (3);
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1
                    myViewModelRecentVisibleInt1 = (androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue) >= (1);
                if((dirtyFlags & 0xe0L) != 0) {
                    if(myViewModelRecentVisibleInt2) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }
                if((dirtyFlags & 0xe0L) != 0) {
                    if(myViewModelRecentVisibleInt3) {
                            dirtyFlags |= 0x2000L;
                    }
                    else {
                            dirtyFlags |= 0x1000L;
                    }
                }
                if((dirtyFlags & 0xe0L) != 0) {
                    if(myViewModelRecentVisibleInt1) {
                            dirtyFlags |= 0x200L;
                    }
                    else {
                            dirtyFlags |= 0x100L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt2ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt2) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt3ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt3) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt1ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt1) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0xe0L) != 0) {
            // api target 1

            this.recentItemLay.setVisibility(myViewModelRecentVisibleInt1ViewVISIBLEViewGONE);
            this.txtLocation1.setVisibility(myViewModelRecentVisibleInt1ViewVISIBLEViewGONE);
            this.txtLocation2.setVisibility(myViewModelRecentVisibleInt2ViewVISIBLEViewGONE);
            this.txtLocation3.setVisibility(myViewModelRecentVisibleInt3ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0xc4L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtDrop, myViewModelDropLocGetValue);
        }
        if ((dirtyFlags & 0x80L) != 0) {
            // api target 1

            this.txtLocation1.setOnClickListener(mCallback3);
            this.txtLocation2.setOnClickListener(mCallback4);
            this.txtLocation3.setOnClickListener(mCallback5);
        }
        if ((dirtyFlags & 0xc2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation1, myViewModelRecentPlace1GetValue);
        }
        if ((dirtyFlags & 0xc8L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation2, myViewModelRecentPlace2GetValue);
        }
        if ((dirtyFlags & 0xc1L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation3, myViewModelRecentPlace3GetValue);
        }
        if ((dirtyFlags & 0xd0L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtPickup, myViewModelPickupLocGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // myViewModel
                com.taximobility.bookingmodule.pickDropLoc.SearchViewModel myViewModel = mMyViewModel;
                // myViewModel != null
                boolean myViewModelJavaLangObjectNull = false;



                myViewModelJavaLangObjectNull = (myViewModel) != (null);
                if (myViewModelJavaLangObjectNull) {



                    myViewModel.recentPlaceClick(3);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // myViewModel
                com.taximobility.bookingmodule.pickDropLoc.SearchViewModel myViewModel = mMyViewModel;
                // myViewModel != null
                boolean myViewModelJavaLangObjectNull = false;



                myViewModelJavaLangObjectNull = (myViewModel) != (null);
                if (myViewModelJavaLangObjectNull) {



                    myViewModel.recentPlaceClick(2);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // myViewModel
                com.taximobility.bookingmodule.pickDropLoc.SearchViewModel myViewModel = mMyViewModel;
                // myViewModel != null
                boolean myViewModelJavaLangObjectNull = false;



                myViewModelJavaLangObjectNull = (myViewModel) != (null);
                if (myViewModelJavaLangObjectNull) {



                    myViewModel.recentPlaceClick(1);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): myViewModel.recentPlace3
        flag 1 (0x2L): myViewModel.recentPlace1
        flag 2 (0x3L): myViewModel.dropLoc
        flag 3 (0x4L): myViewModel.recentPlace2
        flag 4 (0x5L): myViewModel.pickupLoc
        flag 5 (0x6L): myViewModel.recentVisible
        flag 6 (0x7L): myViewModel
        flag 7 (0x8L): null
        flag 8 (0x9L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
        flag 9 (0xaL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
        flag 12 (0xdL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
        flag 13 (0xeL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}