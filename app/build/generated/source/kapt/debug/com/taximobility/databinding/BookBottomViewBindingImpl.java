package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BookBottomViewBindingImpl extends BookBottomViewBinding implements com.taximobility.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rg_rental_out, 5);
        sViewsWithIds.put(R.id.rb_one_way, 6);
        sViewsWithIds.put(R.id.rb_round_trip, 7);
        sViewsWithIds.put(R.id.img_model, 8);
        sViewsWithIds.put(R.id.tv_model_name, 9);
        sViewsWithIds.put(R.id.pass_count, 10);
        sViewsWithIds.put(R.id.time, 11);
        sViewsWithIds.put(R.id.tv_fare, 12);
        sViewsWithIds.put(R.id.lay_rental, 13);
        sViewsWithIds.put(R.id.tv_timing, 14);
        sViewsWithIds.put(R.id.ll_oneWay, 15);
        sViewsWithIds.put(R.id.ll_round_trip, 16);
        sViewsWithIds.put(R.id.tv_minus, 17);
        sViewsWithIds.put(R.id.tv_pick, 18);
        sViewsWithIds.put(R.id.tv_plus, 19);
        sViewsWithIds.put(R.id.delivery_lay, 20);
        sViewsWithIds.put(R.id.et_product_name, 21);
        sViewsWithIds.put(R.id.et_weight, 22);
        sViewsWithIds.put(R.id.et_size, 23);
        sViewsWithIds.put(R.id.et_name, 24);
        sViewsWithIds.put(R.id.et_phone, 25);
        sViewsWithIds.put(R.id.et_date, 26);
        sViewsWithIds.put(R.id.preference_lay, 27);
        sViewsWithIds.put(R.id.preference_rv, 28);
        sViewsWithIds.put(R.id.et_notes, 29);
        sViewsWithIds.put(R.id.promo_code_lay, 30);
        sViewsWithIds.put(R.id.fare_minimum_ppl, 31);
        sViewsWithIds.put(R.id.requestBooking, 32);
        sViewsWithIds.put(R.id.textRequestTaxi, 33);
        sViewsWithIds.put(R.id.tv_confirm, 34);
        sViewsWithIds.put(R.id.textBookLater, 35);
        sViewsWithIds.put(R.id.txt_now_later, 36);
        sViewsWithIds.put(R.id.corporateBooking, 37);
        sViewsWithIds.put(R.id.corporateBookLater, 38);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView3;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    @Nullable
    private final android.view.View.OnClickListener mCallback10;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BookBottomViewBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 39, sIncludes, sViewsWithIds));
    }
    private BookBottomViewBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.TextView) bindings[2]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.view.View) bindings[38]
            , (android.widget.LinearLayout) bindings[37]
            , (android.widget.LinearLayout) bindings[20]
            , (android.widget.EditText) bindings[26]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[29]
            , (android.widget.EditText) bindings[25]
            , (android.widget.EditText) bindings[21]
            , (android.widget.EditText) bindings[23]
            , (android.widget.EditText) bindings[22]
            , (android.widget.TextView) bindings[31]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.TextView) bindings[10]
            , (android.widget.LinearLayout) bindings[27]
            , (androidx.recyclerview.widget.RecyclerView) bindings[28]
            , (android.widget.LinearLayout) bindings[30]
            , (android.widget.RadioButton) bindings[6]
            , (android.widget.RadioButton) bindings[7]
            , (android.widget.LinearLayout) bindings[32]
            , (android.widget.RadioGroup) bindings[5]
            , (android.widget.LinearLayout) bindings[0]
            , (android.view.View) bindings[35]
            , (android.widget.TextView) bindings[33]
            , (android.widget.TextView) bindings[11]
            , (android.widget.Button) bindings[34]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[36]
            );
        this.cashCard.setTag(null);
        this.cashCardLay.setTag(null);
        this.cashCardTxt.setTag(null);
        this.mboundView3 = (android.widget.LinearLayout) bindings[3];
        this.mboundView3.setTag(null);
        this.selectCarLay.setTag(null);
        setRootTag(root);
        // listeners
        mCallback9 = new com.taximobility.generated.callback.OnClickListener(this, 1);
        mCallback10 = new com.taximobility.generated.callback.OnClickListener(this, 2);
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
        boolean androidxDatabindingViewDataBindingSafeUnboxMyBookViewModelCashCardEnableGetValue = false;
        java.lang.String myBookViewModelPayTypeGetValue = null;
        java.lang.Boolean myBookViewModelCashCardEnableGetValue = null;
        androidx.lifecycle.MutableLiveData<java.lang.String> myBookViewModelPayType = null;
        com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;
        androidx.lifecycle.MutableLiveData<java.lang.Boolean> myBookViewModelCashCardEnable = null;

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
            this.cashCardTxt.setEnabled(androidxDatabindingViewDataBindingSafeUnboxMyBookViewModelCashCardEnableGetValue);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.cashCard, myBookViewModelPayTypeGetValue);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.cashCardTxt, myBookViewModelPayTypeGetValue);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.cashCardLay.setOnClickListener(mCallback9);
            this.mboundView3.setOnClickListener(mCallback10);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // myBookViewModel
                com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;
                // myBookViewModel != null
                boolean myBookViewModelJavaLangObjectNull = false;



                myBookViewModelJavaLangObjectNull = (myBookViewModel) != (null);
                if (myBookViewModelJavaLangObjectNull) {


                    myBookViewModel.cashCardClick();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // myBookViewModel
                com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;
                // myBookViewModel != null
                boolean myBookViewModelJavaLangObjectNull = false;



                myBookViewModelJavaLangObjectNull = (myBookViewModel) != (null);
                if (myBookViewModelJavaLangObjectNull) {


                    myBookViewModel.cashCardClick();
                }
                break;
            }
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