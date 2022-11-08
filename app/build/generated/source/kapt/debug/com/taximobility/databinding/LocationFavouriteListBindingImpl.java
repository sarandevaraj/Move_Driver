package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LocationFavouriteListBindingImpl extends LocationFavouriteListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.imgLocType, 3);
        sViewsWithIds.put(R.id.imgLocDelete, 4);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LocationFavouriteListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private LocationFavouriteListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[3]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            );
        this.mainLocLay.setTag(null);
        this.txtLoc.setTag(null);
        this.txtLocHeader.setTag(null);
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
        if (BR.mLocationData == variableId) {
            setMLocationData((com.taximobility.bookingmodule.LocationData) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMLocationData(@Nullable com.taximobility.bookingmodule.LocationData MLocationData) {
        this.mMLocationData = MLocationData;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.mLocationData);
        super.requestRebind();
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
        java.lang.String mLocationDataLabelName = null;
        java.lang.String mLocationDataLocationName = null;
        com.taximobility.bookingmodule.LocationData mLocationData = mMLocationData;

        if ((dirtyFlags & 0x3L) != 0) {



                if (mLocationData != null) {
                    // read mLocationData.label_name
                    mLocationDataLabelName = mLocationData.getLabel_name();
                    // read mLocationData.location_name
                    mLocationDataLocationName = mLocationData.getLocation_name();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLoc, mLocationDataLocationName);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtLocHeader, mLocationDataLabelName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): mLocationData
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}