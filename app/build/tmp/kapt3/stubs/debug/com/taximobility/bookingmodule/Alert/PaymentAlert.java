package com.taximobility.bookingmodule.Alert;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0012"}, d2 = {"Lcom/taximobility/bookingmodule/Alert/PaymentAlert;", "", "()V", "payType", "", "getPayType", "()I", "setPayType", "(I)V", "cashCardPay", "", "context", "Landroid/app/Activity;", "intPaymentType", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "cashCardTxt", "Landroid/widget/TextView;", "app_debug"})
public final class PaymentAlert {
    private static int payType = 0;
    public static final com.taximobility.bookingmodule.Alert.PaymentAlert INSTANCE = null;
    
    public final int getPayType() {
        return 0;
    }
    
    public final void setPayType(int p0) {
    }
    
    public final void cashCardPay(@org.jetbrains.annotations.NotNull()
    android.app.Activity context, int intPaymentType, @org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    android.widget.TextView cashCardTxt) {
    }
    
    private PaymentAlert() {
        super();
    }
}