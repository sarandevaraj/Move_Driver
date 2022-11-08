package com.taximobility.databinding;
import com.taximobility.R;
import com.taximobility.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DriverSettlementHistoryListBindingImpl extends DriverSettlementHistoryListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.txt_status, 5);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DriverSettlementHistoryListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private DriverSettlementHistoryListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (android.widget.TextView) bindings[1]
            );
        this.mainLay.setTag(null);
        this.txtAmount.setTag(null);
        this.txtDate.setTag(null);
        this.txtPaymentBy.setTag(null);
        this.txtTripId.setTag(null);
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
        if (BR.list == variableId) {
            setList((com.taximobility.driver.data.apiData.DriverListClass) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setList(@Nullable com.taximobility.driver.data.apiData.DriverListClass List) {
        this.mList = List;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.list);
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
        double listSettlementProcessAmount = 0.0;
        int listId = 0;
        java.lang.String listSettlementType = null;
        java.lang.String doubleToStringListSettlementProcessAmount = null;
        java.lang.String integerToStringListId = null;
        java.lang.String listSettlementRequestDate = null;
        com.taximobility.driver.data.apiData.DriverListClass list = mList;

        if ((dirtyFlags & 0x3L) != 0) {



                if (list != null) {
                    // read list.settlement_process_amount
                    listSettlementProcessAmount = list.settlement_process_amount;
                    // read list._id
                    listId = list._id;
                    // read list.settlement_type
                    listSettlementType = list.settlement_type;
                    // read list.settlement_request_date
                    listSettlementRequestDate = list.settlement_request_date;
                }


                // read Double.toString(list.settlement_process_amount)
                doubleToStringListSettlementProcessAmount = java.lang.Double.toString(listSettlementProcessAmount);
                // read Integer.toString(list._id)
                integerToStringListId = java.lang.Integer.toString(listId);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtAmount, doubleToStringListSettlementProcessAmount);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtDate, listSettlementRequestDate);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtPaymentBy, listSettlementType);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.txtTripId, integerToStringListId);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): list
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}