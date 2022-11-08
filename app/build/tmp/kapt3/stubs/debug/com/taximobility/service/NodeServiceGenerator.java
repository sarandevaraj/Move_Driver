package com.taximobility.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0017\u0018B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/taximobility/service/NodeServiceGenerator;", "", "()V", "builder", "Lretrofit2/Retrofit$Builder;", "httpClient", "Lokhttp3/OkHttpClient$Builder;", "createCertificate", "Ljavax/net/ssl/SSLContext;", "trustedCertificateIS", "Ljava/io/InputStream;", "initSSL", "", "context", "Landroid/content/Context;", "nodeGetRetrofitWithTimeOut", "Lretrofit2/Retrofit;", "base_url", "", "timeOut", "", "systemDefaultTrustManager", "Ljavax/net/ssl/X509TrustManager;", "DecryptInterceptor", "RequestInterceptor", "app_debug"})
public final class NodeServiceGenerator {
    private static okhttp3.OkHttpClient.Builder httpClient;
    private static retrofit2.Retrofit.Builder builder;
    public static final com.taximobility.service.NodeServiceGenerator INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit nodeGetRetrofitWithTimeOut(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String base_url, long timeOut) {
        return null;
    }
    
    private final void initSSL(android.content.Context context) {
    }
    
    private final javax.net.ssl.SSLContext createCertificate(java.io.InputStream trustedCertificateIS) throws java.security.cert.CertificateException, java.io.IOException, java.security.KeyStoreException, java.security.KeyManagementException, java.security.NoSuchAlgorithmException {
        return null;
    }
    
    private final javax.net.ssl.X509TrustManager systemDefaultTrustManager() {
        return null;
    }
    
    private NodeServiceGenerator() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\f"}, d2 = {"Lcom/taximobility/service/NodeServiceGenerator$RequestInterceptor;", "Lokhttp3/Interceptor;", "c", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getC$app_debug", "()Landroid/content/Context;", "setC$app_debug", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_debug"})
    public static final class RequestInterceptor implements okhttp3.Interceptor {
        @org.jetbrains.annotations.NotNull()
        private android.content.Context c;
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
        okhttp3.Interceptor.Chain chain) throws java.io.IOException {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Context getC$app_debug() {
            return null;
        }
        
        public final void setC$app_debug(@org.jetbrains.annotations.NotNull()
        android.content.Context p0) {
        }
        
        public RequestInterceptor(@org.jetbrains.annotations.NotNull()
        android.content.Context c) {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004\u00a8\u0006\f"}, d2 = {"Lcom/taximobility/service/NodeServiceGenerator$DecryptInterceptor;", "Lokhttp3/Interceptor;", "c", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getC$app_debug", "()Landroid/content/Context;", "setC$app_debug", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_debug"})
    public static final class DecryptInterceptor implements okhttp3.Interceptor {
        @org.jetbrains.annotations.NotNull()
        private android.content.Context c;
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
        okhttp3.Interceptor.Chain chain) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Context getC$app_debug() {
            return null;
        }
        
        public final void setC$app_debug(@org.jetbrains.annotations.NotNull()
        android.content.Context p0) {
        }
        
        public DecryptInterceptor(@org.jetbrains.annotations.NotNull()
        android.content.Context c) {
            super();
        }
    }
}