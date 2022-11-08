package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BookBottomViewBindingArImpl extends BookBottomViewBinding implements com.taximobility.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.promo_code_lay, 3);
        sViewsWithIds.put(R.id.fare_minimum_ppl, 4);
        sViewsWithIds.put(R.id.requestBooking, 5);
        sViewsWithIds.put(R.id.textRequestTaxi, 6);
        sViewsWithIds.put(R.id.textBookLater, 7);
        sViewsWithIds.put(R.id.corporateBooking, 8);
        sViewsWithIds.put(R.id.corporateBookLater, 9);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BookBottomViewBindingArImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private BookBottomViewBindingArImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.TextView) bindings[2]
            , (android.widget.LinearLayout) bindings[1]
            , null
            , (android.view.View) bindings[9]
            , (android.widget.LinearLayout) bindings[8]
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , (android.widget.TextView) bindings[4]
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , (android.widget.LinearLayout) bindings[3]
            , null
            , null
            , (android.widget.LinearLayout) bindings[5]
            , null
            , (android.widget.LinearLayout) bindings[0]
            , (android.view.View) bindings[7]
            , (android.widget.TextView) bindings[6]
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            , null
            );
        this.cashCard.setTag(null);
        this.cashCardLay.setTag(null);
        this.selectCarLay.setTag(null);
        setRootTag(root);
        // listeners
        mCallback2 = new com.taximobility.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.myBookViewModel == variableId) {
            setMyBookViewModel((com.taximobility.bookingmodule.BookTaxiHomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMyBookViewModel(@Nullable com.taximobility.bookingmodule.BookTaxiHomeViewModel MyBookViewModel) {
        this.mMyBookViewModel = MyBookViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.myBookViewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeMyBookViewModelPayType((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeMyBookViewModelCashCardEnable((androidx.lifecycle.MutableLiveData<java.lang.Boolean>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeMyBookViewModelPayType(androidx.lifecycle.MutableLiveData<java.lang.String> MyBookViewModelPayType, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeMyBookViewModelCashCardEnable(androidx.lifecycle.MutableLiveData<java.lang.Boolean> MyBookViewModelCashCardEnable, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
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
        androidx.lifecycle.MutableLiveData<java.lang.String> myBookViewModelPayType = null;
        boolean androidxDatabindingViewDataBindingSafeUnboxMyBookViewModelCashCardEnableGetValue = false;
        com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;
        java.lang.String myBookViewModelPayTypeGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> myBookViewModelCashCardEnable = null;
        java.lang.Boolean myBookViewModelCashCardEnableGetValue = null;

        if ((dirtyFlags & 0xfL) != 0) {


            if ((dirtyFlags & 0xdL) != 0) {

                    if (myBookViewModel != null) {
                        // read myBookViewModel.payType
                        myBookViewModelPayType = myBookViewModel.getPayType();
                    }
                    updateLiveDataRegistration(0, myBookViewModelPayType);


                    if (myBookViewModelPayType != null) {
                        // read myBookViewModel.payType.getValue()
                        myBookViewModelPayTypeGetValue = myBookViewModelPayType.getValue();
                    }
            }
            if ((dirtyFlags & 0xeL) != 0) {

                    if (myBookViewModel != null) {
                        // read myBookViewModel.cashCardEnable
                        myBookViewModelCashCardEnable = myBookViewModel.getCashCardEnable();
                    }
                    updateLiveDataRegistration(1, myBookViewModelCashCardEnable);


                    if (myBookViewModelCashCardEnable != null) {
                        // read myBookViewModel.cashCardEnable.getValue()
                        myBookViewModelCashCardEnableGetValue = myBookViewModelCashCardEnable.getValue();
                    }


                    // read androidx.databinding.ViewDataBinding.safeUnbox(myBookViewModel.cashCardEnable.getValue())
                    androidxDatabindingViewDataBindingSafeUnboxMyBookViewModelCashCardEnableGetValue = androidx.databinding.ViewDataBinding.safeUnbox(myBookViewModelCashCardEnableGetValue);
            }
        }
        // batch finished
        if ((dirtyFlags & 0xeL) != 0) {
            // api target 1

            this.cashCard.setEnabled(androidxDatabindingViewDataBindingSafeUnboxMyBookViewModelCashCardEnableGetValue);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.cashCard, myBookViewModelPayTypeGetValue);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.cashCardLay.setOnClickListener(mCallback2);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // myBookViewModel
        com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;
        // myBookViewModel != null
        boolean myBookViewModelJavaLangObjectNull = false;



        myBookViewModelJavaLangObjectNull = (myBookViewModel) != (null);
        if (myBookViewModelJavaLangObjectNull) {


            myBookViewModel.cashCardClick();
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): myBookViewModel.payType
        flag 1 (0x2L): myBookViewModel.cashCardEnable
        flag 2 (0x3L): myBookViewModel
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}