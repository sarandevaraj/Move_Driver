package com.taximobility.tripCancel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00152\u00020\u0001:\u0004\u0015\u0016\u0017\u0018B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004Jb\u0010\t\u001a^\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b \f*.\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b\u0018\u00010\n0\nJ\u0014\u0010\r\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0018\u00010\u000eJ\u0014\u0010\u0011\u001a\u00020\u000b2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\u000e\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0019"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardRepository;", "", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "creditCardDao", "Lcom/taximobility/tripCancel/CreditCardDao;", "getMContext", "()Landroid/content/Context;", "deleteAllCards", "Landroid/os/AsyncTask;", "", "kotlin.jvm.PlatformType", "getCardList", "Landroidx/lifecycle/LiveData;", "", "Lcom/taximobility/tripCancel/CreditCardData;", "insertAllCreditCards", "cardList", "insertCreditCard", "creditCardData", "Companion", "DeleteAllCreditCards", "InsertAllCreditCards", "InsertCreditCardLog", "app_debug"})
public final class CreditCardRepository {
    private final com.taximobility.tripCancel.CreditCardDao creditCardDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    private static volatile com.taximobility.tripCancel.CreditCardRepository creditCardRepository;
    public static final com.taximobility.tripCancel.CreditCardRepository.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.lifecycle.LiveData<java.util.List<com.taximobility.tripCancel.CreditCardData>> getCardList() {
        return null;
    }
    
    public final android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> deleteAllCards() {
        return null;
    }
    
    public final void insertAllCreditCards(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taximobility.tripCancel.CreditCardData> cardList) {
    }
    
    public final void insertCreditCard(@org.jetbrains.annotations.NotNull()
    com.taximobility.tripCancel.CreditCardData creditCardData) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    private CreditCardRepository(android.content.Context mContext) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final com.taximobility.tripCancel.CreditCardRepository getRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0002\u0010\u0006J%\u0010\u0007\u001a\u00020\u00022\u0016\u0010\b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\t\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\nR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardRepository$InsertAllCreditCards;", "Landroid/os/AsyncTask;", "", "cardList", "", "Lcom/taximobility/tripCancel/CreditCardData;", "(Lcom/taximobility/tripCancel/CreditCardRepository;Ljava/util/List;)V", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "app_debug"})
    final class InsertAllCreditCards extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        private final java.util.List<com.taximobility.tripCancel.CreditCardData> cardList = null;
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        public InsertAllCreditCards(@org.jetbrains.annotations.NotNull()
        java.util.List<com.taximobility.tripCancel.CreditCardData> cardList) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J%\u0010\u0006\u001a\u00020\u00022\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardRepository$InsertCreditCardLog;", "Landroid/os/AsyncTask;", "", "creditCardData", "Lcom/taximobility/tripCancel/CreditCardData;", "(Lcom/taximobility/tripCancel/CreditCardRepository;Lcom/taximobility/tripCancel/CreditCardData;)V", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "app_debug"})
    final class InsertCreditCardLog extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        private final com.taximobility.tripCancel.CreditCardData creditCardData = null;
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        public InsertCreditCardLog(@org.jetbrains.annotations.NotNull()
        com.taximobility.tripCancel.CreditCardData creditCardData) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J%\u0010\u0004\u001a\u00020\u00022\u0016\u0010\u0005\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0006\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardRepository$DeleteAllCreditCards;", "Landroid/os/AsyncTask;", "", "(Lcom/taximobility/tripCancel/CreditCardRepository;)V", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "app_debug"})
    final class DeleteAllCreditCards extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        public DeleteAllCreditCards() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/taximobility/tripCancel/CreditCardRepository$Companion;", "", "()V", "creditCardRepository", "Lcom/taximobility/tripCancel/CreditCardRepository;", "getRepository", "mContext", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.taximobility.tripCancel.CreditCardRepository getRepository(@org.jetbrains.annotations.NotNull()
        android.content.Context mContext) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}