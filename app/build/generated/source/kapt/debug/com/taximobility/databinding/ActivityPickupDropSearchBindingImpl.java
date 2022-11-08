package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPickupDropSearchBindingImpl extends ActivityPickupDropSearchBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_icon, 1);
        sViewsWithIds.put(R.id.header_titleTxt, 2);
        sViewsWithIds.put(R.id.searchlay, 3);
        sViewsWithIds.put(R.id.pickup_pinlay, 4);
        sViewsWithIds.put(R.id.drop_pin, 5);
        sViewsWithIds.put(R.id.pickupp, 6);
        sViewsWithIds.put(R.id.currentlocTxt, 7);
        sViewsWithIds.put(R.id.pickup_drop_Sep, 8);
        sViewsWithIds.put(R.id.dropppp, 9);
        sViewsWithIds.put(R.id.taxi__locationsearch_edittext_search, 10);
        sViewsWithIds.put(R.id.taxi__locationsearch_imagebutton_clear, 11);
        sViewsWithIds.put(R.id.add_stop, 12);
        sViewsWithIds.put(R.id.locationList, 13);
        sViewsWithIds.put(R.id.searchFrag, 14);
        sViewsWithIds.put(R.id.map_redirctTxt, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPickupDropSearchBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityPickupDropSearchBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.EditText) bindings[7]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ImageView) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (android.widget.LinearLayout) bindings[15]
            , (android.view.View) bindings[8]
            , (android.widget.FrameLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.FrameLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.EditText) bindings[10]
            , (android.widget.ImageButton) bindings[11]
            , (android.widget.LinearLayout) bindings[0]
            );
        this.top.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.mList == variableId) {
            setMList((com.taximobility.bookingmodule.BookTaxiHomeViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMList(@Nullable com.taximobility.bookingmodule.BookTaxiHomeViewModel MList) {
        this.mMList = MList;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): mList
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}