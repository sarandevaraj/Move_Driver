package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FavListBindingImpl extends FavListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.lay_fav_res1, 1);
        sViewsWithIds.put(R.id.check1, 2);
        sViewsWithIds.put(R.id.reason1, 3);
        sViewsWithIds.put(R.id.line1, 4);
        sViewsWithIds.put(R.id.lay_fav_res2, 5);
        sViewsWithIds.put(R.id.check2, 6);
        sViewsWithIds.put(R.id.reason2, 7);
        sViewsWithIds.put(R.id.lay_fav_res3, 8);
        sViewsWithIds.put(R.id.check3, 9);
        sViewsWithIds.put(R.id.reason3, 10);
        sViewsWithIds.put(R.id.lay_fav_res4, 11);
        sViewsWithIds.put(R.id.check4, 12);
        sViewsWithIds.put(R.id.other_details, 13);
        sViewsWithIds.put(R.id.et_others, 14);
        sViewsWithIds.put(R.id.ok_others, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FavListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private FavListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[12]
            , (android.widget.EditText) bindings[14]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[11]
            , (android.view.View) bindings[4]
            , (android.widget.TextView) bindings[15]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[10]
            );
        this.favouriteroot.setTag(null);
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
        if (BR.myFavViewModel == variableId) {
            setMyFavViewModel((com.taximobility.bookingmodule.favourite.FavouriteViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMyFavViewModel(@Nullable com.taximobility.bookingmodule.favourite.FavouriteViewModel MyFavViewModel) {
        this.mMyFavViewModel = MyFavViewModel;
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
        flag 0 (0x1L): myFavViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}