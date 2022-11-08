package com.taximobility.driver.errorLog;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u00102\u00020\u0001:\u0006\u0010\u0011\u0012\u0013\u0014\u0015B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0002J\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0016"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;", "", "mContext", "Landroid/content/Context;", "(Landroid/content/Context;)V", "errorLogDao", "Lcom/taximobility/driver/errorLog/DriverErrorLogDao;", "getMContext", "()Landroid/content/Context;", "callSubmitErrorLogsApi", "", "driverApiErrorLogs", "", "Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "insertAllApiErrorLogs", "driverApiErrorModel", "Companion", "DeleteApiErrorLogs", "GetAllApiErrorLogs", "GetCount", "InsertApiErrorLogs", "UpdateApiErrorLogs", "app_debug"})
public final class DriverErrorLogRepository {
    private final com.taximobility.driver.errorLog.DriverErrorLogDao errorLogDao = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context mContext = null;
    private static volatile com.taximobility.driver.errorLog.DriverErrorLogRepository driverErrorLogRepository;
    public static final com.taximobility.driver.errorLog.DriverErrorLogRepository.Companion Companion = null;
    
    public final void insertAllApiErrorLogs(@org.jetbrains.annotations.NotNull()
    com.taximobility.driver.errorLog.DriverApiErrorModel driverApiErrorModel) {
    }
    
    private final void callSubmitErrorLogsApi(java.util.List<com.taximobility.driver.errorLog.DriverApiErrorModel> driverApiErrorLogs) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getMContext() {
        return null;
    }
    
    private DriverErrorLogRepository(android.content.Context mContext) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final com.taximobility.driver.errorLog.DriverErrorLogRepository getRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context mContext) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J%\u0010\u0012\u001a\u00020\u00022\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0014\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00022\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0018R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0019"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$GetCount;", "Landroid/os/AsyncTask;", "", "driverApiErrorModel", "Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "timeStamp", "", "(Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;Lcom/taximobility/driver/errorLog/DriverApiErrorModel;Ljava/lang/String;)V", "count", "", "getCount", "()I", "setCount", "(I)V", "getDriverApiErrorModel", "()Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "getTimeStamp", "()Ljava/lang/String;", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "onPostExecute", "result", "(Lkotlin/Unit;)V", "app_debug"})
    final class GetCount extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        private int count = 0;
        @org.jetbrains.annotations.NotNull()
        private final com.taximobility.driver.errorLog.DriverApiErrorModel driverApiErrorModel = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String timeStamp = null;
        
        public final int getCount() {
            return 0;
        }
        
        public final void setCount(int p0) {
        }
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        kotlin.Unit result) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.driver.errorLog.DriverApiErrorModel getDriverApiErrorModel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getTimeStamp() {
            return null;
        }
        
        public GetCount(@org.jetbrains.annotations.NotNull()
        com.taximobility.driver.errorLog.DriverApiErrorModel driverApiErrorModel, @org.jetbrains.annotations.NotNull()
        java.lang.String timeStamp) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u001c\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0005J-\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u00022\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u0014\u00a8\u0006\f"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$GetAllApiErrorLogs;", "Landroid/os/AsyncTask;", "", "", "Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "(Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;)V", "doInBackground", "params", "", "([Lkotlin/Unit;)Ljava/util/List;", "onPostExecute", "result", "app_debug"})
    final class GetAllApiErrorLogs extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, java.util.List<? extends com.taximobility.driver.errorLog.DriverApiErrorModel>> {
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        protected java.util.List<com.taximobility.driver.errorLog.DriverApiErrorModel> doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
            return null;
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        java.util.List<com.taximobility.driver.errorLog.DriverApiErrorModel> result) {
        }
        
        public GetAllApiErrorLogs() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J%\u0010\u0006\u001a\u00020\u00022\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\tJ\u0017\u0010\n\u001a\u00020\u00022\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$InsertApiErrorLogs;", "Landroid/os/AsyncTask;", "", "driverApiErrorModel", "Lcom/taximobility/driver/errorLog/DriverApiErrorModel;", "(Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;Lcom/taximobility/driver/errorLog/DriverApiErrorModel;)V", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "onPostExecute", "result", "(Lkotlin/Unit;)V", "app_debug"})
    final class InsertApiErrorLogs extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        private final com.taximobility.driver.errorLog.DriverApiErrorModel driverApiErrorModel = null;
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        kotlin.Unit result) {
        }
        
        public InsertApiErrorLogs(@org.jetbrains.annotations.NotNull()
        com.taximobility.driver.errorLog.DriverApiErrorModel driverApiErrorModel) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0006J%\u0010\n\u001a\u00020\u00022\u0016\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\f\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\rJ\u0017\u0010\u000e\u001a\u00020\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b\u00a8\u0006\u0011"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$UpdateApiErrorLogs;", "Landroid/os/AsyncTask;", "", "status", "", "ids", "(Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;II)V", "getIds", "()I", "getStatus", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "onPostExecute", "result", "(Lkotlin/Unit;)V", "app_debug"})
    final class UpdateApiErrorLogs extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        private final int status = 0;
        private final int ids = 0;
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        kotlin.Unit result) {
        }
        
        public final int getStatus() {
            return 0;
        }
        
        public final int getIds() {
            return 0;
        }
        
        public UpdateApiErrorLogs(int status, int ids) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J%\u0010\b\u001a\u00020\u00022\u0016\u0010\t\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\n\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u000eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000f"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$DeleteApiErrorLogs;", "Landroid/os/AsyncTask;", "", "date", "", "(Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;Ljava/lang/String;)V", "getDate", "()Ljava/lang/String;", "doInBackground", "params", "", "([Lkotlin/Unit;)V", "onPostExecute", "result", "(Lkotlin/Unit;)V", "app_debug"})
    final class DeleteApiErrorLogs extends android.os.AsyncTask<kotlin.Unit, kotlin.Unit, kotlin.Unit> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String date = null;
        
        @java.lang.Override()
        protected void doInBackground(@org.jetbrains.annotations.NotNull()
        kotlin.Unit... params) {
        }
        
        @java.lang.Override()
        protected void onPostExecute(@org.jetbrains.annotations.Nullable()
        kotlin.Unit result) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDate() {
            return null;
        }
        
        public DeleteApiErrorLogs(@org.jetbrains.annotations.NotNull()
        java.lang.String date) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/taximobility/driver/errorLog/DriverErrorLogRepository$Companion;", "", "()V", "driverErrorLogRepository", "Lcom/taximobility/driver/errorLog/DriverErrorLogRepository;", "getRepository", "mContext", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.taximobility.driver.errorLog.DriverErrorLogRepository getRepository(@org.jetbrains.annotations.NotNull()
        android.content.Context mContext) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}