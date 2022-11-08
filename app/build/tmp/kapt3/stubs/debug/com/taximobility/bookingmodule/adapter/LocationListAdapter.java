package com.taximobility.bookingmodule.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003)*+B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u00020\u001dH\u0016J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u001dH\u0016J\u0014\u0010\'\u001a\u00020!2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\u00a8\u0006,"}, d2 = {"Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "mContext", "Landroid/app/Activity;", "data", "", "Lcom/taximobility/bookingmodule/LocationData;", "listener", "Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$DeleteClickListener;", "(Landroid/app/Activity;Ljava/util/List;Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$DeleteClickListener;)V", "getData", "()Ljava/util/List;", "setData", "(Ljava/util/List;)V", "layoutInflater", "Landroid/view/LayoutInflater;", "getLayoutInflater", "()Landroid/view/LayoutInflater;", "setLayoutInflater", "(Landroid/view/LayoutInflater;)V", "getListener", "()Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$DeleteClickListener;", "getMContext", "()Landroid/app/Activity;", "setMContext", "(Landroid/app/Activity;)V", "getItem", "position", "", "getItemCount", "getItemViewType", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateList", "list", "CustomFavViewHolder", "CustomViewHolder", "DeleteClickListener", "app_debug"})
public final class LocationListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    public android.view.LayoutInflater layoutInflater;
    @org.jetbrains.annotations.NotNull()
    private android.app.Activity mContext;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.taximobility.bookingmodule.LocationData> data;
    @org.jetbrains.annotations.NotNull()
    private final com.taximobility.bookingmodule.adapter.LocationListAdapter.DeleteClickListener listener = null;
    
    @org.jetbrains.annotations.NotNull()
    public final android.view.LayoutInflater getLayoutInflater() {
        return null;
    }
    
    public final void setLayoutInflater(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    public final void updateList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> list) {
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.LocationData getItem(int position) {
        return null;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Activity getMContext() {
        return null;
    }
    
    public final void setMContext(@org.jetbrains.annotations.NotNull()
    android.app.Activity p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.taximobility.bookingmodule.LocationData> getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.adapter.LocationListAdapter.DeleteClickListener getListener() {
        return null;
    }
    
    public LocationListAdapter(@org.jetbrains.annotations.NotNull()
    android.app.Activity mContext, @org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.bookingmodule.LocationData> data, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.adapter.LocationListAdapter.DeleteClickListener listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$CustomViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;Landroid/view/View;)V", "binding", "Lcom/taximobility/databinding/LocationListBinding;", "getBinding$app_debug", "()Lcom/taximobility/databinding/LocationListBinding;", "setBinding$app_debug", "(Lcom/taximobility/databinding/LocationListBinding;)V", "app_debug"})
    public final class CustomViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.Nullable()
        private com.taximobility.databinding.LocationListBinding binding;
        
        @org.jetbrains.annotations.Nullable()
        public final com.taximobility.databinding.LocationListBinding getBinding$app_debug() {
            return null;
        }
        
        public final void setBinding$app_debug(@org.jetbrains.annotations.Nullable()
        com.taximobility.databinding.LocationListBinding p0) {
        }
        
        public CustomViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$CustomFavViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/taximobility/bookingmodule/adapter/LocationListAdapter;Landroid/view/View;)V", "binding", "Lcom/taximobility/databinding/LocationFavouriteListBinding;", "getBinding$app_debug", "()Lcom/taximobility/databinding/LocationFavouriteListBinding;", "setBinding$app_debug", "(Lcom/taximobility/databinding/LocationFavouriteListBinding;)V", "app_debug"})
    public final class CustomFavViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.Nullable()
        private com.taximobility.databinding.LocationFavouriteListBinding binding;
        
        @org.jetbrains.annotations.Nullable()
        public final com.taximobility.databinding.LocationFavouriteListBinding getBinding$app_debug() {
            return null;
        }
        
        public final void setBinding$app_debug(@org.jetbrains.annotations.Nullable()
        com.taximobility.databinding.LocationFavouriteListBinding p0) {
        }
        
        public CustomFavViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View view) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/taximobility/bookingmodule/adapter/LocationListAdapter$DeleteClickListener;", "", "deleteClick", "", "locationName", "Lcom/taximobility/bookingmodule/LocationData;", "itemClick", "position", "", "app_debug"})
    public static abstract interface DeleteClickListener {
        
        public abstract void deleteClick(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.LocationData locationName);
        
        public abstract void itemClick(int position);
    }
}