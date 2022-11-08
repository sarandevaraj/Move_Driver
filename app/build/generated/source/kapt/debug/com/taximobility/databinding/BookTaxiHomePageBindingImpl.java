package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BookTaxiHomePageBindingImpl extends BookTaxiHomePageBinding implements com.taximobility.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(16);
        sIncludes.setIncludes(2, 
            new String[] {"book_bottom_view"},
            new int[] {4},
            new int[] {com.taximobility.R.layout.book_bottom_view});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.mapPageLay, 3);
        sViewsWithIds.put(R.id.navi_icon_book, 5);
        sViewsWithIds.put(R.id.instruction_header_lay, 6);
        sViewsWithIds.put(R.id.instruction_header, 7);
        sViewsWithIds.put(R.id.book_taxi_main_frag, 8);
        sViewsWithIds.put(R.id.txt_skip_drop_lay, 9);
        sViewsWithIds.put(R.id.fav_bot_lay, 10);
        sViewsWithIds.put(R.id.mov_cur_loc, 11);
        sViewsWithIds.put(R.id.ll_choose_service, 12);
        sViewsWithIds.put(R.id.rv_service, 13);
        sViewsWithIds.put(R.id.img_iv_line, 14);
        sViewsWithIds.put(R.id.car_model_view, 15);
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BookTaxiHomePageBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private BookTaxiHomePageBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (android.widget.FrameLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[2]
            , (com.taximobility.bookingmodule.utils.CarModelsView) bindings[15]
            , (android.widget.LinearLayout) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (android.widget.TextView) bindings[7]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[12]
            , (android.view.View) bindings[3]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (com.taximobility.databinding.BookBottomViewBinding) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (android.widget.TextView) bindings[1]
            , (android.widget.LinearLayout) bindings[9]
            );
        this.bottomViewLay.setTag(null);
        this.layHome.setTag(null);
        this.txtSkipDrop.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.taximobility.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        requestLay.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (requestLay.hasPendingBindings()) {
            return true;
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
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.myBookViewModel);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        requestLay.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeRequestLay((com.taximobility.databinding.BookBottomViewBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeRequestLay(com.taximobility.databinding.BookBottomViewBinding RequestLay, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
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
        com.taximobility.bookingmodule.BookTaxiHomeViewModel myBookViewModel = mMyBookViewModel;

        if ((dirtyFlags & 0x6L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            this.requestLay.setMyBookViewModel(myBookViewModel);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.txtSkipDrop.setOnClickListener(mCallback1);
        }
        executeBindingsOn(requestLay);
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



            myBookViewModel.skipDropLocClick(1);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): requestLay
        flag 1 (0x2L): myBookViewModel
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}