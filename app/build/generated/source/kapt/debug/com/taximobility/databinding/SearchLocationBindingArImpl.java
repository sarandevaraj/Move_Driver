package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class SearchLocationBindingArImpl extends SearchLocationBinding implements com.taximobility.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.pick_lay, 9);
        sViewsWithIds.put(R.id.pick_pin, 10);
        sViewsWithIds.put(R.id.pic_loc_select, 11);
        sViewsWithIds.put(R.id.drop_lay, 12);
        sViewsWithIds.put(R.id.drop_pin, 13);
        sViewsWithIds.put(R.id.drop_loc_select, 14);
        sViewsWithIds.put(R.id.view1, 15);
        sViewsWithIds.put(R.id.view2, 16);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public SearchLocationBindingArImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private SearchLocationBindingArImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 8
            , (androidx.cardview.widget.CardView) bindings[3]
            , (android.widget.LinearLayout) bindings[12]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (android.widget.ImageView) bindings[13]
            , null
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ImageView) bindings[10]
            , (androidx.cardview.widget.CardView) bindings[1]
            , null
            , null
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , null
            , null
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[15]
            , (android.view.View) bindings[16]
            );
        this.dropCard.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pickupCard.setTag(null);
        this.recentItemLay.setTag(null);
        this.txtDrop.setTag(null);
        this.txtLocation1.setTag(null);
        this.txtLocation2.setTag(null);
        this.txtLocation3.setTag(null);
        this.txtPickup.setTag(null);
        setRootTag(root);
        // listeners
        mCallback6 = new com.taximobility.generated.callback.OnClickListener(this, 1);
        mCallback8 = new com.taximobility.generated.callback.OnClickListener(this, 3);
        mCallback7 = new com.taximobility.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x200L;
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
            mDirtyFlags |= 0x100L;
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
                return onChangeMyViewModelPickupVisible((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 6 :
                return onChangeMyViewModelRecentVisible((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
            case 7 :
                return onChangeMyViewModelDropVisible((androidx.lifecycle.MutableLiveData<java.lang.Integer>) object, fieldId);
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
    private boolean onChangeMyViewModelPickupVisible(androidx.lifecycle.MutableLiveData<java.lang.Integer> MyViewModelPickupVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelRecentVisible(androidx.lifecycle.MutableLiveData<java.lang.Integer> MyViewModelRecentVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyViewModelDropVisible(androidx.lifecycle.MutableLiveData<java.lang.Integer> MyViewModelDropVisible, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x80L;
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
        int myViewModelDropVisibleInt1ViewVISIBLEViewGONE = 0;
        java.lang.String myViewModelRecentPlace1GetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxMyViewModelPickupVisibleGetValue = 0;
        boolean myViewModelRecentVisibleInt3 = false;
        java.lang.Integer myViewModelDropVisibleGetValue = null;
        java.lang.Integer myViewModelPickupVisibleGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxMyViewModelRecentVisibleGetValue = 0;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelRecentPlace2 = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> myViewModelPickupLoc = null;
        boolean myViewModelPickupVisibleInt1 = false;
        int myViewModelRecentVisibleInt1ViewVISIBLEViewGONE = 0;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> myViewModelPickupVisible = null;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> myViewModelRecentVisible = null;
        int myViewModelRecentVisibleInt2ViewVISIBLEViewGONE = 0;
        boolean myViewModelDropVisibleInt1 = false;
        java.lang.String myViewModelPickupLocGetValue = null;
        int androidxDatabindingViewDataBindingSafeUnboxMyViewModelDropVisibleGetValue = 0;
        int myViewModelPickupVisibleInt1ViewVISIBLEViewGONE = 0;
        boolean myViewModelRecentVisibleInt1 = false;
        androidx.lifecycle.MutableLiveData<java.lang.Integer> myViewModelDropVisible = null;
        int myViewModelRecentVisibleInt3ViewVISIBLEViewGONE = 0;
        java.lang.String myViewModelDropLocGetValue = null;
        com.taximobility.bookingmodule.pickDropLoc.SearchViewModel myViewModel = mMyViewModel;
        java.lang.String myViewModelRecentPlace3GetValue = null;
        java.lang.Integer myViewModelRecentVisibleGetValue = null;

        if ((dirtyFlags & 0x3ffL) != 0) {


            if ((dirtyFlags & 0x301L) != 0) {

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
            if ((dirtyFlags & 0x302L) != 0) {

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
            if ((dirtyFlags & 0x304L) != 0) {

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
            if ((dirtyFlags & 0x308L) != 0) {

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
            if ((dirtyFlags & 0x310L) != 0) {

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
            if ((dirtyFlags & 0x320L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.pickupVisible
                        myViewModelPickupVisible = myViewModel.getPickupVisible();
                    }
                    updateLiveDataRegistration(5, myViewModelPickupVisible);


                    if (myViewModelPickupVisible != null) {
                        // read myViewModel.pickupVisible.getValue()
                        myViewModelPickupVisibleGetValue = myViewModelPickupVisible.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.pickupVisible.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxMyViewModelPickupVisibleGetValue = androidx.databinding.ViewDataBinding.safeUnbox(myViewModelPickupVisibleGetValue);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.pickupVisible.getValue()) == 1
                    myViewModelPickupVisibleInt1 = (androidxDatabindingViewDataBindingSafeUnboxMyViewModelPickupVisibleGetValue) == (1);
                if((dirtyFlags & 0x320L) != 0) {
                    if(myViewModelPickupVisibleInt1) {
                            dirtyFlags |= 0x20000L;
                    }
                    else {
                            dirtyFlags |= 0x10000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.pickupVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
                    myViewModelPickupVisibleInt1ViewVISIBLEViewGONE = ((myViewModelPickupVisibleInt1) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x340L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.recentVisible
                        myViewModelRecentVisible = myViewModel.getRecentVisible();
                    }
                    updateLiveDataRegistration(6, myViewModelRecentVisible);


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
                if((dirtyFlags & 0x340L) != 0) {
                    if(myViewModelRecentVisibleInt2) {
                            dirtyFlags |= 0x8000L;
                    }
                    else {
                            dirtyFlags |= 0x4000L;
                    }
                }
                if((dirtyFlags & 0x340L) != 0) {
                    if(myViewModelRecentVisibleInt3) {
                            dirtyFlags |= 0x80000L;
                    }
                    else {
                            dirtyFlags |= 0x40000L;
                    }
                }
                if((dirtyFlags & 0x340L) != 0) {
                    if(myViewModelRecentVisibleInt1) {
                            dirtyFlags |= 0x2000L;
                    }
                    else {
                            dirtyFlags |= 0x1000L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt2ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt2) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt3ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt3) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
                    myViewModelRecentVisibleInt1ViewVISIBLEViewGONE = ((myViewModelRecentVisibleInt1) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
            if ((dirtyFlags & 0x380L) != 0) {

                    if (myViewModel != null) {
                        // read myViewModel.dropVisible
                        myViewModelDropVisible = myViewModel.getDropVisible();
                    }
                    updateLiveDataRegistration(7, myViewModelDropVisible);


                    if (myViewModelDropVisible != null) {
                        // read myViewModel.dropVisible.getValue()
                        myViewModelDropVisibleGetValue = myViewModelDropVisible.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.dropVisible.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxMyViewModelDropVisibleGetValue = androidx.databinding.ViewDataBinding.safeUnbox(myViewModelDropVisibleGetValue);


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.dropVisible.getValue()) == 1
                    myViewModelDropVisibleInt1 = (androidxDatabindingViewDataBindingSafeUnboxMyViewModelDropVisibleGetValue) == (1);
                if((dirtyFlags & 0x380L) != 0) {
                    if(myViewModelDropVisibleInt1) {
                            dirtyFlags |= 0x800L;
                    }
                    else {
                            dirtyFlags |= 0x400L;
                    }
                }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.dropVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
                    myViewModelDropVisibleInt1ViewVISIBLEViewGONE = ((myViewModelDropVisibleInt1) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        // batch finished
        if ((dirtyFlags & 0x380L) != 0) {
            // api target 1

            this.dropCard.setVisibility(myViewModelDropVisibleInt1ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x320L) != 0) {
            // api target 1

            this.pickupCard.setVisibility(myViewModelPickupVisibleInt1ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x340L) != 0) {
            // api target 1

            this.recentItemLay.setVisibility(myViewModelRecentVisibleInt1ViewVISIBLEViewGONE);
            this.txtLocation1.setVisibility(myViewModelRecentVisibleInt1ViewVISIBLEViewGONE);
            this.txtLocation2.setVisibility(myViewModelRecentVisibleInt2ViewVISIBLEViewGONE);
            this.txtLocation3.setVisibility(myViewModelRecentVisibleInt3ViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x304L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtDrop, myViewModelDropLocGetValue);
        }
        if ((dirtyFlags & 0x200L) != 0) {
            // api target 1

            this.txtLocation1.setOnClickListener(mCallback6);
            this.txtLocation2.setOnClickListener(mCallback7);
            this.txtLocation3.setOnClickListener(mCallback8);
        }
        if ((dirtyFlags & 0x302L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation1, myViewModelRecentPlace1GetValue);
        }
        if ((dirtyFlags & 0x308L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation2, myViewModelRecentPlace2GetValue);
        }
        if ((dirtyFlags & 0x301L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocation3, myViewModelRecentPlace3GetValue);
        }
        if ((dirtyFlags & 0x310L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtPickup, myViewModelPickupLocGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
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
        flag 5 (0x6L): myViewModel.pickupVisible
        flag 6 (0x7L): myViewModel.recentVisible
        flag 7 (0x8L): myViewModel.dropVisible
        flag 8 (0x9L): myViewModel
        flag 9 (0xaL): null
        flag 10 (0xbL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.dropVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
        flag 11 (0xcL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.dropVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
        flag 12 (0xdL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
        flag 13 (0xeL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 1 ? View.VISIBLE : View.GONE
        flag 14 (0xfL): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
        flag 15 (0x10L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 2 ? View.VISIBLE : View.GONE
        flag 16 (0x11L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.pickupVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
        flag 17 (0x12L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.pickupVisible.getValue()) == 1 ? View.VISIBLE : View.GONE
        flag 18 (0x13L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
        flag 19 (0x14L): androidx.databinding.ViewDataBinding.safeUnbox(myViewModel.recentVisible.getValue()) >= 3 ? View.VISIBLE : View.GONE
    flag mapping end*/
    //end
}