package com.taximobility.bookingmodule;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u00bc\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 \u00c3\u00022\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u00072\u00020\b2\u00020\t2\u00020\n2\u00020\u000b:\u0004\u00c2\u0002\u00c3\u0002B\u0005\u00a2\u0006\u0002\u0010\fJ\u001b\u0010\u00b6\u0001\u001a\u00030\u00b7\u00012\u000f\u0010\u00b8\u0001\u001a\n\u0012\u0005\u0012\u00030\u00ba\u00010\u00b9\u0001H\u0002J:\u0010\u00bb\u0001\u001a\u00030\u00b7\u00012\n\u0010\u00bc\u0001\u001a\u0005\u0018\u00010\u00bd\u00012\u0007\u0010\u00be\u0001\u001a\u00020\u001e2\u0007\u0010\u00bf\u0001\u001a\u00020\u001e2\u0007\u0010\u00c0\u0001\u001a\u00020\u001e2\u0007\u0010\u00c1\u0001\u001a\u00020\u001eH\u0002J\u0012\u0010\u00c2\u0001\u001a\u00020\u001e2\u0007\u0010\u00c3\u0001\u001a\u00020(H\u0002J\n\u0010\u00c4\u0001\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00c5\u0001\u001a\u00030\u00b7\u0001H\u0002J\u001c\u0010\u00c6\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u00c7\u0001\u001a\u00020(2\u0007\u0010\u00c8\u0001\u001a\u00020;H\u0002J\n\u0010\u00c9\u0001\u001a\u00030\u00b7\u0001H\u0002J\u0014\u0010\u00ca\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00cb\u0001\u001a\u00030\u00cc\u0001H\u0002J\b\u0010\u00cd\u0001\u001a\u00030\u00b7\u0001J\n\u0010\u00ce\u0001\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00cf\u0001\u001a\u00030\u00b7\u0001H\u0002J\b\u0010\u00d0\u0001\u001a\u00030\u00b7\u0001J:\u0010\u00d1\u0001\u001a\u00030\u00b7\u00012\n\u0010\u00bc\u0001\u001a\u0005\u0018\u00010\u00bd\u00012\u0007\u0010\u00be\u0001\u001a\u00020\u001e2\u0007\u0010\u00bf\u0001\u001a\u00020\u001e2\u0007\u0010\u00c0\u0001\u001a\u00020\u001e2\u0007\u0010\u00c1\u0001\u001a\u00020\u001eH\u0002J:\u0010\u00d2\u0001\u001a\u00030\u00b7\u00012\n\u0010\u00bc\u0001\u001a\u0005\u0018\u00010\u00bd\u00012\u0007\u0010\u00be\u0001\u001a\u00020\u001e2\u0007\u0010\u00bf\u0001\u001a\u00020\u001e2\u0007\u0010\u00c0\u0001\u001a\u00020\u001e2\u0007\u0010\u00c1\u0001\u001a\u00020\u001eH\u0002J\n\u0010\u00d3\u0001\u001a\u00030\u00b7\u0001H\u0002J/\u0010\u00d4\u0001\u001a\u00030\u00b7\u00012\t\u0010\u00a0\u0001\u001a\u0004\u0018\u00010\u000e2\t\u0010\u00d5\u0001\u001a\u0004\u0018\u00010\u000e2\u0007\u0010\u00d6\u0001\u001a\u00020\u000eH\u0016\u00a2\u0006\u0003\u0010\u00d7\u0001J\n\u0010\u00d8\u0001\u001a\u00030\u00b7\u0001H\u0016J.\u0010\u00d9\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u00da\u0001\u001a\u00020\u000e2\u0007\u0010\u00db\u0001\u001a\u00020\u000e2\u0007\u0010\u00dc\u0001\u001a\u00020\u001e2\u0007\u0010\u00dd\u0001\u001a\u00020\u001eH\u0016J\n\u0010\u00de\u0001\u001a\u00030\u00b7\u0001H\u0002J\u001e\u0010\u00df\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00e0\u0001\u001a\u00030\u00e1\u00012\b\u0010\u00e2\u0001\u001a\u00030\u00e3\u0001H\u0002J&\u0010\u00e4\u0001\u001a\u00030\u00b7\u00012\t\u0010\u00a0\u0001\u001a\u0004\u0018\u00010\u000e2\t\u0010\u00d5\u0001\u001a\u0004\u0018\u00010\u000eH\u0016\u00a2\u0006\u0003\u0010\u00e5\u0001J\u0013\u0010\u00e6\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u00e7\u0001\u001a\u00020(H\u0002J\u001d\u0010\u00e8\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00e9\u0001\u001a\u00030\u00cc\u00012\u0007\u0010\u00e7\u0001\u001a\u00020(H\u0016J\u0015\u0010\u00ea\u0001\u001a\u00030\u00b7\u00012\t\u0010\u00eb\u0001\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u00ec\u0001\u001a\u00030\u00b7\u0001J\u001c\u0010\u00ed\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u00ee\u0001\u001a\u00020(2\u0007\u0010\u00ef\u0001\u001a\u00020\u001eH\u0016J\u0014\u0010\u00f0\u0001\u001a\u00030\u00f1\u00012\b\u0010\u00cb\u0001\u001a\u00030\u00cc\u0001H\u0002J\n\u0010\u00f2\u0001\u001a\u00030\u00f1\u0001H\u0002J\u0014\u0010\u00f3\u0001\u001a\u00030\u00f1\u00012\b\u0010\u00cb\u0001\u001a\u00030\u00cc\u0001H\u0002J\u0014\u0010\u00f4\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00cb\u0001\u001a\u00030\u00cc\u0001H\u0002J\u0014\u0010\u00f5\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00c7\u0001\u001a\u00030\u00f6\u0001H\u0002J\u0014\u0010\u00f7\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00c7\u0001\u001a\u00030\u00f8\u0001H\u0002J\u0012\u0010\u00f9\u0001\u001a\u00020(2\u0007\u0010\u00fa\u0001\u001a\u00020\u001eH\u0002J\u0014\u0010\u00fb\u0001\u001a\u00030\u00b7\u00012\b\u0010\u00fc\u0001\u001a\u00030\u00fd\u0001H\u0002J\n\u0010\u00fe\u0001\u001a\u00030\u00b7\u0001H\u0016J\u001d\u0010\u00ff\u0001\u001a\u00030\u00b7\u00012\u0007\u0010\u0080\u0002\u001a\u00020x2\b\u0010\u0081\u0002\u001a\u00030\u00cc\u0001H\u0016J(\u0010\u0082\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u0083\u0002\u001a\u00020(2\u0007\u0010\u0084\u0002\u001a\u00020(2\n\u0010\u0085\u0002\u001a\u0005\u0018\u00010\u0086\u0002H\u0016J\b\u0010\u0087\u0002\u001a\u00030\u00b7\u0001J-\u0010\u0088\u0002\u001a\u0005\u0018\u00010\u00fd\u00012\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\n\u0010\u008b\u0002\u001a\u0005\u0018\u00010\u008c\u00022\t\u0010\u008d\u0002\u001a\u0004\u0018\u00010\u0016H\u0016J\n\u0010\u008e\u0002\u001a\u00030\u00b7\u0001H\u0016J \u0010\u008f\u0002\u001a\u00030\u00b7\u00012\t\u0010\u0090\u0002\u001a\u0004\u0018\u00010\u00182\t\u0010\u0091\u0002\u001a\u0004\u0018\u00010\u001eH\u0016J\n\u0010\u0092\u0002\u001a\u00030\u00b7\u0001H\u0016J\n\u0010\u0093\u0002\u001a\u00030\u00b7\u0001H\u0016J\n\u0010\u0094\u0002\u001a\u00030\u00b7\u0001H\u0016J3\u0010\u0095\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u0083\u0002\u001a\u00020(2\u000e\u0010\u0096\u0002\u001a\t\u0012\u0004\u0012\u00020\u001e0\u0097\u00022\b\u0010\u0098\u0002\u001a\u00030\u0099\u0002H\u0016\u00a2\u0006\u0003\u0010\u009a\u0002J\n\u0010\u009b\u0002\u001a\u00030\u00b7\u0001H\u0016JI\u0010\u009c\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u009d\u0002\u001a\u00020\u000e2\u0007\u0010\u009e\u0002\u001a\u00020\u000e2\u0007\u0010\u009f\u0002\u001a\u00020\u000e2\u0007\u0010\u00a0\u0002\u001a\u00020\u000e2\u0007\u0010\u00a1\u0002\u001a\u00020\u000e2\u0007\u0010\u00a2\u0002\u001a\u00020\u000e2\u0007\u0010\u00a3\u0002\u001a\u00020\u000eH\u0016J\n\u0010\u00a4\u0002\u001a\u00030\u00b7\u0001H\u0016J \u0010\u00a5\u0002\u001a\u00030\u00b7\u00012\t\u0010\u0090\u0002\u001a\u0004\u0018\u00010\u00182\t\u0010\u0091\u0002\u001a\u0004\u0018\u00010\u001eH\u0016J\n\u0010\u00a6\u0002\u001a\u00030\u00b7\u0001H\u0016J%\u0010\u00a7\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u00da\u0001\u001a\u00020\u000e2\u0007\u0010\u00db\u0001\u001a\u00020\u000e2\u0007\u0010\u00dc\u0001\u001a\u00020\u001eH\u0016J\n\u0010\u00a8\u0002\u001a\u00030\u00b7\u0001H\u0016J\u001b\u0010\u00a9\u0002\u001a\u00030\u00b7\u00012\u000f\u0010\u0085\u0002\u001a\n\u0012\u0004\u0012\u00020H\u0018\u00010DH\u0002J\n\u0010\u00aa\u0002\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00ab\u0002\u001a\u00030\u00b7\u0001H\u0002J%\u0010\u00ac\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u00da\u0001\u001a\u00020\u000e2\u0007\u0010\u00ad\u0002\u001a\u00020\u000e2\u0007\u0010\u00dc\u0001\u001a\u00020\u001eH\u0016J\u001b\u0010\u00ae\u0002\u001a\u00030\u00b7\u00012\u000f\u0010\u00c7\u0001\u001a\n\u0012\u0005\u0012\u00030\u00ba\u00010\u00b9\u0001H\u0002J\n\u0010\u00af\u0002\u001a\u00030\u00b7\u0001H\u0002J\u0011\u0010\u00b0\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u00b1\u0002\u001a\u00020\u001eJ\n\u0010\u00b2\u0002\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00b3\u0002\u001a\u00030\u00b7\u0001H\u0002J\u0014\u0010\u00b4\u0002\u001a\u00030\u00b7\u00012\b\u0010\u00b5\u0002\u001a\u00030\u00b6\u0002H\u0002J\n\u0010\u00b7\u0002\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00b8\u0002\u001a\u00030\u00b7\u0001H\u0002J\n\u0010\u00b9\u0002\u001a\u00030\u00b7\u0001H\u0016J%\u0010\u00ba\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u00bb\u0002\u001a\u00020\u001e2\u0007\u0010\u00bc\u0002\u001a\u00020\u001e2\u0007\u0010\u00c8\u0001\u001a\u00020;H\u0002J?\u0010\u00bd\u0002\u001a\u00030\u00b7\u00012\u0007\u0010\u00be\u0002\u001a\u00020(2\u0007\u0010\u00bf\u0002\u001a\u00020(2\u0007\u0010\u00c0\u0002\u001a\u00020(2\u0006\u0010\u007f\u001a\u00020(2\u0007\u0010\u00b1\u0001\u001a\u00020(2\u0007\u0010\u00c1\u0002\u001a\u00020(H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\'\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020*X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u000e\u0010/\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u00105\u001a\u0004\u0018\u000106X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010<\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020BX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020\u001e0DX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020FX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010G\u001a\b\u0012\u0004\u0012\u00020H0DX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010I\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020KX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020VX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020_X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020BX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u00020bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010h\u001a\u00020iX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bj\u0010k\"\u0004\bl\u0010mR\u000e\u0010n\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010q\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u000201X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010u\u001a\u0004\u0018\u00010vX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010w\u001a\u0004\u0018\u00010xX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010y\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010z\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010{\u001a\b\u0012\u0004\u0012\u00020|0DX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010}\u001a\u00020~X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u007f\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0081\u0001\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0082\u0001\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0083\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0084\u0001\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0085\u0001\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0086\u0001\u001a\t\u0012\u0005\u0012\u00030\u0087\u00010DX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0088\u0001\u001a\u00020~X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0089\u0001\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u008a\u0001\u001a\u00030\u008b\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u008c\u0001\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u008d\u0001\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u008e\u0001\u001a\u00030\u008f\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0090\u0001\u001a\u00020~X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0091\u0001\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0092\u0001\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0093\u0001\u001a\t\u0012\u0005\u0012\u00030\u0094\u00010DX\u0082.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0095\u0001\u001a\u0005\u0018\u00010\u0096\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0097\u0001\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0098\u0001\u001a\u00030\u0099\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u009a\u0001\u001a\u00030\u009b\u0001X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u009c\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u009e\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u009f\u0001\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a0\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a1\u0001\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a2\u0001\u001a\u00020>X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a3\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a4\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a5\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a6\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a7\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a8\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00a9\u0001\u001a\u00020;X\u0082.\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00aa\u0001\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u00ab\u0001\u001a\u00030\u00ac\u0001X\u0086.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00ad\u0001\u0010\u00ae\u0001\"\u0006\b\u00af\u0001\u0010\u00b0\u0001R\u000f\u0010\u00b1\u0001\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00b2\u0001\u001a\u00020BX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00b3\u0001\u001a\u00020BX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00b4\u0001\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u00b5\u0001\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00c4\u0002"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomePage;", "Lcom/taximobility/bookingmodule/MapModule/MapFragment;", "Lcom/taximobility/bookingmodule/Interface/PickDropSetListener;", "Lcom/taximobility/interfaces/DialogInterface;", "Lcom/taximobility/interfaces/splitfareDialog;", "Lcom/taximobility/bookingmodule/Interface/RouteListeners;", "Lcom/taximobility/interfaces/GetAddress;", "Lcom/taximobility/interfaces/FragPopFront;", "Lcom/taximobility/interfaces/PackageClick;", "Lcom/taximobility/interfaces/PromoClick;", "Lcom/taximobility/interfaces/OpenPackage;", "Lcom/taximobility/interfaces/GetModelDetails;", "()V", "ETime", "", "action", "Ljava/lang/Runnable;", "getAction$app_debug", "()Ljava/lang/Runnable;", "setAction$app_debug", "(Ljava/lang/Runnable;)V", "alertBundle", "Landroid/os/Bundle;", "alertDialog", "Landroid/app/Dialog;", "getAlertDialog", "()Landroid/app/Dialog;", "setAlertDialog", "(Landroid/app/Dialog;)V", "alertMsg", "", "ampm", "animatedVectorDrawable", "Landroid/graphics/drawable/AnimatedVectorDrawable;", "animationLay", "Landroid/widget/RelativeLayout;", "approximateDistance", "approximateFare", "approximateTime", "availablecarcount", "", "binding", "Lcom/taximobility/databinding/BookTaxiHomePageBinding;", "getBinding", "()Lcom/taximobility/databinding/BookTaxiHomePageBinding;", "setBinding", "(Lcom/taximobility/databinding/BookTaxiHomePageBinding;)V", "bookFavDriver", "book_taxi_main_frag", "Landroid/widget/FrameLayout;", "bookingType", "botFavLay", "Landroid/widget/LinearLayout;", "bottomSheetFragment", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "bottomViewLay", "carModelView", "Lcom/taximobility/bookingmodule/utils/CarModelsView;", "cashCardTxt", "Landroid/widget/TextView;", "choose_service", "corporateBookLater", "Landroid/widget/Button;", "date", "delivery_lay", "doubleBackToExitPressedOnce", "", "driverIdData", "Ljava/util/ArrayList;", "driverLiveMovement", "Lcom/taximobility/bookingmodule/DriverLiveMovement/DriverLiveMove;", "driverMarkerService", "Lcom/google/android/gms/maps/model/Marker;", "dtMDialog", "et_date", "Landroid/widget/EditText;", "et_name", "et_notes", "et_phone", "et_product_name", "et_size", "et_weight", "fareMinimumPpl", "favDriverAvailable", "favDriverMessage", "findETAFare", "Lcom/taximobility/bookingmodule/FindETAFare;", "friend1S", "friend1SA", "friend2S", "friend2SA", "friend3S", "friend3SA", "friendA", "handler", "Landroid/os/Handler;", "hour", "img_model", "Landroid/widget/ImageView;", "instructionHeader", "intPaymentType", "isNeedToDrawRoute", "ivLine", "lay_rental", "listener", "Landroid/content/BroadcastReceiver;", "getListener$app_debug", "()Landroid/content/BroadcastReceiver;", "setListener$app_debug", "(Landroid/content/BroadcastReceiver;)V", "ll_choose_service", "ll_oneWay", "ll_round_trip", "loadingDialog", "locImg", "locationImg", "locationLay", "mAdapter", "Lcom/taximobility/bookingmodule/adapter/ModelListAdapter;", "mMap", "Lcom/google/android/gms/maps/GoogleMap;", "mcDialog", "min", "modelArray", "Lcom/taximobility/bookingmodule/Data/ModelData;", "model_list", "Landroidx/recyclerview/widget/RecyclerView;", "month", "naviIcon", "os_days", "os_type", "pass_count", "pickupTime", "pickupTimeAndDate", "placesDetailArrayList", "Lcom/taximobility/locationSearch/PlacesData;", "preferenceRv", "preference_lay", "preferencesDataList", "Lcom/taximobility/data/apiData/PreferencesDataList;", "promoCodeLay", "requestBooking", "rg_rental_out", "Landroid/widget/RadioGroup;", "rv_service", "selected_model_id", "selected_model_size", "serviceArray", "Lcom/taximobility/bookingmodule/Data/ServiceData;", "serviceListAdapter", "Lcom/taximobility/bookingmodule/adapter/ServiceListAdapter;", "serviceType", "showPackage", "Lcom/taximobility/bookingmodule/Alert/AlertPackagePlan;", "showPromo", "Lcom/taximobility/bookingmodule/Alert/PromoCodeAlert;", "skipDropLoc", "splitFareDialog", "Lcom/taximobility/fragments/SplitFareDialog;", "textBookLater", "time", "travelModelId", "tv_confirm", "tv_fare", "tv_minus", "tv_model_name", "tv_pick", "tv_plus", "txtRequestTaxi", "txt_now_later", "txt_skip_drop_lay", "viewModel", "Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "getViewModel", "()Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;", "setViewModel", "(Lcom/taximobility/bookingmodule/BookTaxiHomeViewModel;)V", "year", "zoneFareApplicable", "zoneTozone_applicable", "zoneTozone_fare", "zoneZoneFare", "addFavPlaceData", "", "locationDataList", "", "Lcom/taximobility/bookingmodule/LocationData;", "alertView", "mContext", "Landroid/app/Activity;", "title", "message", "successTxt", "failureTxt", "ampmValidation", "inputHour", "animationInScreen", "bookLaterFun", "bottomSheet", "it", "cardTxt", "callBookNow", "callBookNowApi", "lastKnownLatLng", "Lcom/google/android/gms/maps/model/LatLng;", "callGetPassengerInfoApi", "chooseServiceDialog", "closeDialog", "deliveryDateFun", "divertToOngoingScreen", "divertToTripHistory", "drawRoute", "drawRoutePickToDrop", "dist", "approxFare", "(Ljava/lang/Double;Ljava/lang/Double;D)V", "dropListener", "dropSet", "latitude", "longtitue", "address", "focus", "forceLogout", "getCurrentDateAndTime", "_timePicker", "Landroid/widget/TimePicker;", "_datePicker", "Landroid/widget/DatePicker;", "getETADiverToPickup", "(Ljava/lang/Double;Ljava/lang/Double;)V", "getLastKnownLatlngForNearestApiCall", "lastLocationReqType", "getLastKnownLattitudeLongtitude", "lastLatLng", "getModelDetails", "id", "getModelPreference", "getNearestDriver", "model_id", "model_size", "getNearestJsonObject", "Lorg/json/JSONObject;", "getPromoCodeObject", "getSaveBookingObject", "getSaveBookingObjectBookLater", "handlingNearestRes", "Lcom/taximobility/bookingmodule/Data/NearestDriverDatas;", "handlingSaveBookingRes", "Lcom/taximobility/bookingmodule/Data/SaveBookingResponse;", "hoursAgo", "datetime", "initialize", "view", "Landroid/view/View;", "kmRestrictListener", "mapGpsInitialized", "map", "currLatLng", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPress", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "onDestroy", "onFailure", "dialog", "resultcode", "onPackageFailureClick", "onPause", "onPromoApply", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "onResume", "onSplitSuccess", "primary_Percent", "f1", "f2", "f3", "fa1", "fa2", "fa3", "onStop", "onSuccess", "openPackage", "pickUpSet", "pickupListener", "removeMarker", "repeatAnimation", "setCarLay", "setDraggedAddress", "longitude", "setFavPlace", "setPaymentType", "setServiceModelArray", "array", "showDialog", "startLineProgress", "startSOSService", "context", "Landroid/content/Context;", "stopLineProgress", "stopLineProgressOnError", "trigger_FragPopFront", "updatePaymentMode", "type", "promocode", "updateTimer", "hours", "mins", "day", "sec", "BOOKINGSTATE", "Companion", "app_debug"})
public final class BookTaxiHomePage extends com.taximobility.bookingmodule.MapModule.MapFragment implements com.taximobility.bookingmodule.Interface.PickDropSetListener, com.taximobility.interfaces.DialogInterface, com.taximobility.interfaces.splitfareDialog, com.taximobility.bookingmodule.Interface.RouteListeners, com.taximobility.interfaces.GetAddress, com.taximobility.interfaces.FragPopFront, com.taximobility.interfaces.PackageClick, com.taximobility.interfaces.PromoClick, com.taximobility.interfaces.OpenPackage, com.taximobility.interfaces.GetModelDetails {
    private com.google.android.material.bottomsheet.BottomSheetDialogFragment bottomSheetFragment;
    private android.widget.LinearLayout botFavLay;
    private android.widget.TextView skipDropLoc;
    private android.widget.TextView txtRequestTaxi;
    private android.widget.LinearLayout textBookLater;
    private android.widget.TextView cashCardTxt;
    private android.widget.ImageView locImg;
    private android.widget.ImageView locationImg;
    private android.widget.ImageView naviIcon;
    private android.widget.FrameLayout locationLay;
    private com.taximobility.bookingmodule.utils.CarModelsView carModelView;
    private android.widget.ImageView ivLine;
    private android.widget.TextView instructionHeader;
    private android.widget.LinearLayout promoCodeLay;
    private android.widget.TextView fareMinimumPpl;
    private android.widget.RelativeLayout animationLay;
    private android.widget.LinearLayout bottomViewLay;
    private android.widget.Button corporateBookLater;
    private android.widget.LinearLayout requestBooking;
    private com.taximobility.data.apiData.PreferencesDataList preferencesDataList;
    private java.util.ArrayList<com.taximobility.locationSearch.PlacesData> placesDetailArrayList;
    private java.util.ArrayList<java.lang.String> driverIdData;
    private java.util.ArrayList<com.google.android.gms.maps.model.Marker> driverMarkerService;
    private com.taximobility.bookingmodule.FindETAFare findETAFare;
    private com.taximobility.bookingmodule.DriverLiveMovement.DriverLiveMove driverLiveMovement;
    private com.google.android.gms.maps.GoogleMap mMap;
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.databinding.BookTaxiHomePageBinding binding;
    @org.jetbrains.annotations.NotNull()
    public com.taximobility.bookingmodule.BookTaxiHomeViewModel viewModel;
    private android.graphics.drawable.AnimatedVectorDrawable animatedVectorDrawable;
    private android.os.Handler handler;
    private com.taximobility.fragments.SplitFareDialog splitFareDialog;
    private android.os.Bundle alertBundle;
    private com.taximobility.bookingmodule.Alert.AlertPackagePlan showPackage;
    private com.taximobility.bookingmodule.Alert.PromoCodeAlert showPromo;
    private boolean isNeedToDrawRoute = false;
    private java.lang.String alertMsg = "";
    private int availablecarcount = 0;
    private java.lang.String pickupTime = "";
    private java.lang.String pickupTimeAndDate = "";
    private android.app.Dialog dtMDialog;
    private android.app.Dialog mcDialog;
    private android.app.Dialog loadingDialog;
    @org.jetbrains.annotations.NotNull()
    public android.app.Dialog alertDialog;
    private java.lang.String ampm = "AM";
    private int hour = 0;
    private int min = 0;
    private int date = 0;
    private int month = 0;
    private int year = 0;
    private int intPaymentType = 0;
    private double approximateFare = 0.0;
    private double approximateDistance = 0.0;
    private double approximateTime = 0.0;
    private double ETime = 0.0;
    private int favDriverAvailable = 0;
    private int bookFavDriver = 0;
    private double friend1S = 0.0;
    private double friend2S = 0.0;
    private double friend3S = 0.0;
    private double friendA = 100.0;
    private double friend1SA = 0.0;
    private double friend2SA = 0.0;
    private double friend3SA = 0.0;
    private java.lang.String favDriverMessage = "";
    private java.lang.String travelModelId = "";
    private java.lang.String bookingType = "";
    private boolean zoneFareApplicable = false;
    private double zoneZoneFare = 0.0;
    private boolean doubleBackToExitPressedOnce = false;
    private int selected_model_id = 0;
    private java.lang.String selected_model_size = "";
    private android.app.Dialog choose_service;
    private androidx.recyclerview.widget.RecyclerView model_list;
    private java.util.ArrayList<com.taximobility.bookingmodule.Data.ModelData> modelArray;
    private android.widget.ImageView img_model;
    private android.widget.TextView tv_model_name;
    private android.widget.TextView pass_count;
    private android.widget.TextView time;
    private android.widget.TextView tv_fare;
    private androidx.recyclerview.widget.RecyclerView preferenceRv;
    private android.widget.FrameLayout book_taxi_main_frag;
    private android.widget.LinearLayout txt_skip_drop_lay;
    private com.taximobility.bookingmodule.adapter.ModelListAdapter mAdapter;
    private java.util.ArrayList<com.taximobility.bookingmodule.Data.ServiceData> serviceArray;
    private android.widget.LinearLayout ll_choose_service;
    private androidx.recyclerview.widget.RecyclerView rv_service;
    private android.widget.TextView txt_now_later;
    private android.widget.Button tv_confirm;
    private com.taximobility.bookingmodule.adapter.ServiceListAdapter serviceListAdapter;
    private java.lang.String serviceType = "";
    private android.widget.LinearLayout delivery_lay;
    private android.widget.EditText et_product_name;
    private android.widget.EditText et_weight;
    private android.widget.EditText et_size;
    private android.widget.EditText et_name;
    private android.widget.EditText et_phone;
    private android.widget.EditText et_date;
    private android.widget.LinearLayout lay_rental;
    private android.widget.RadioGroup rg_rental_out;
    private android.widget.LinearLayout preference_lay;
    private android.widget.EditText et_notes;
    private int os_type = 1;
    private android.widget.LinearLayout ll_oneWay;
    private android.widget.LinearLayout ll_round_trip;
    private android.widget.TextView tv_minus;
    private android.widget.TextView tv_pick;
    private android.widget.TextView tv_plus;
    private int os_days = 0;
    private double zoneTozone_fare = 0.0;
    private boolean zoneTozone_applicable = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.Runnable action;
    @org.jetbrains.annotations.NotNull()
    private android.content.BroadcastReceiver listener;
    @org.jetbrains.annotations.NotNull()
    private static com.taximobility.bookingmodule.BookTaxiHomePage.BOOKINGSTATE bookingState = com.taximobility.bookingmodule.BookTaxiHomePage.BOOKINGSTATE.STATE_ONE;
    @org.jetbrains.annotations.NotNull()
    public static com.taximobility.bookingmodule.pickDropLoc.SearchLocationFrag searchPage;
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String defaultCityName = "";
    private static boolean IS_HOME_PAGE = false;
    private static int displayHeight = 0;
    private static int displayWidth = 0;
    private static boolean isDropSetFirstTime = false;
    public static final com.taximobility.bookingmodule.BookTaxiHomePage.Companion Companion = null;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.databinding.BookTaxiHomePageBinding getBinding() {
        return null;
    }
    
    public final void setBinding(@org.jetbrains.annotations.NotNull()
    com.taximobility.databinding.BookTaxiHomePageBinding p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taximobility.bookingmodule.BookTaxiHomeViewModel getViewModel() {
        return null;
    }
    
    public final void setViewModel(@org.jetbrains.annotations.NotNull()
    com.taximobility.bookingmodule.BookTaxiHomeViewModel p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.app.Dialog getAlertDialog() {
        return null;
    }
    
    public final void setAlertDialog(@org.jetbrains.annotations.NotNull()
    android.app.Dialog p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    private final void initialize(android.view.View view) {
    }
    
    private final void animationInScreen() {
    }
    
    /**
     * Custom alert dialog used in entire project.can call from anywhere with the following
     *
     * @param title       set the title for alert dialog
     * @param message     set the message for alert dialog
     * @param successTxt set the success text in success button
     * @param failureTxt set the failure text in failure button
     */
    private final void alertView(android.app.Activity mContext, java.lang.String title, java.lang.String message, java.lang.String successTxt, java.lang.String failureTxt) {
    }
    
    public final void setServiceModelArray(@org.jetbrains.annotations.NotNull()
    java.lang.String array) {
    }
    
    private final void setPaymentType() {
    }
    
    private final void handlingNearestRes(com.taximobility.bookingmodule.Data.NearestDriverDatas it) {
    }
    
    private final void handlingSaveBookingRes(com.taximobility.bookingmodule.Data.SaveBookingResponse it) {
    }
    
    private final void divertToOngoingScreen(android.app.Activity mContext, java.lang.String title, java.lang.String message, java.lang.String successTxt, java.lang.String failureTxt) {
    }
    
    private final void divertToTripHistory(android.app.Activity mContext, java.lang.String title, java.lang.String message, java.lang.String successTxt, java.lang.String failureTxt) {
    }
    
    private final void callBookNow() {
    }
    
    private final void startLineProgress() {
    }
    
    private final void stopLineProgress() {
    }
    
    private final void stopLineProgressOnError() {
    }
    
    private final void repeatAnimation() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Runnable getAction$app_debug() {
        return null;
    }
    
    public final void setAction$app_debug(@org.jetbrains.annotations.NotNull()
    java.lang.Runnable p0) {
    }
    
    private final void setCarLay() {
    }
    
    private final void drawRoute() {
    }
    
    private final void addFavPlaceData(java.util.List<com.taximobility.bookingmodule.LocationData> locationDataList) {
    }
    
    private final void setFavPlace(java.util.List<com.taximobility.bookingmodule.LocationData> it) {
    }
    
    private final void getLastKnownLatlngForNearestApiCall(int lastLocationReqType) {
    }
    
    @java.lang.Override()
    public void getLastKnownLattitudeLongtitude(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng lastLatLng, int lastLocationReqType) {
    }
    
    private final void callBookNowApi(com.google.android.gms.maps.model.LatLng lastKnownLatLng) {
    }
    
    private final org.json.JSONObject getNearestJsonObject(com.google.android.gms.maps.model.LatLng lastKnownLatLng) {
        return null;
    }
    
    private final org.json.JSONObject getSaveBookingObject(com.google.android.gms.maps.model.LatLng lastKnownLatLng) {
        return null;
    }
    
    private final org.json.JSONObject getPromoCodeObject() {
        return null;
    }
    
    /**
     * this method is used to set time
     *
     * @param day   set day of the month
     * @param hours set hour of the day
     * @param mins  set minutes of the hour
     * @param month set month of the year
     * @param sec   set seconds of the minute
     * @param year  set the selected year
     */
    private final void updateTimer(int hours, int mins, int day, int month, int year, int sec) {
    }
    
    @java.lang.Override()
    public void onResume() {
    }
    
    @java.lang.Override()
    public void onPause() {
    }
    
    @java.lang.Override()
    public void pickUpSet(double latitude, double longtitue, @org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    @java.lang.Override()
    public void pickupListener() {
    }
    
    @java.lang.Override()
    public void kmRestrictListener() {
    }
    
    @java.lang.Override()
    public void dropSet(double latitude, double longtitue, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String focus) {
    }
    
    @java.lang.Override()
    public void dropListener() {
    }
    
    @java.lang.Override()
    public void onRequestPermissionsResult(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    @java.lang.Override()
    public void onSplitSuccess(double primary_Percent, double f1, double f2, double f3, double fa1, double fa2, double fa3) {
    }
    
    @java.lang.Override()
    public void onSuccess(@org.jetbrains.annotations.Nullable()
    android.app.Dialog dialog, @org.jetbrains.annotations.Nullable()
    java.lang.String resultcode) {
    }
    
    @java.lang.Override()
    public void onFailure(@org.jetbrains.annotations.Nullable()
    android.app.Dialog dialog, @org.jetbrains.annotations.Nullable()
    java.lang.String resultcode) {
    }
    
    @java.lang.Override()
    public void onPackageFailureClick() {
    }
    
    @java.lang.Override()
    public void onPromoApply() {
    }
    
    @java.lang.Override()
    public void setDraggedAddress(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    @java.lang.Override()
    public void mapGpsInitialized(@org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.GoogleMap map, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.maps.model.LatLng currLatLng) {
    }
    
    @java.lang.Override()
    public void openPackage() {
    }
    
    @java.lang.Override()
    public void getNearestDriver(int model_id, @org.jetbrains.annotations.NotNull()
    java.lang.String model_size) {
    }
    
    @java.lang.Override()
    public void trigger_FragPopFront() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.BroadcastReceiver getListener$app_debug() {
        return null;
    }
    
    public final void setListener$app_debug(@org.jetbrains.annotations.NotNull()
    android.content.BroadcastReceiver p0) {
    }
    
    private final void startSOSService(android.content.Context context) {
    }
    
    private final void forceLogout() {
    }
    
    /**
     * @param data list of marker to update
     */
    private final void removeMarker(java.util.ArrayList<com.google.android.gms.maps.model.Marker> data) {
    }
    
    public final void onBackPress() {
    }
    
    @java.lang.Override()
    public void onStop() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @java.lang.Override()
    public void drawRoutePickToDrop(@org.jetbrains.annotations.Nullable()
    java.lang.Double time, @org.jetbrains.annotations.Nullable()
    java.lang.Double dist, double approxFare) {
    }
    
    @java.lang.Override()
    public void getETADiverToPickup(@org.jetbrains.annotations.Nullable()
    java.lang.Double time, @org.jetbrains.annotations.Nullable()
    java.lang.Double dist) {
    }
    
    private final void chooseServiceDialog() {
    }
    
    /**
     * Book later dialog popup
     */
    private final void bookLaterFun() {
    }
    
    public final void deliveryDateFun() {
    }
    
    private final void getSaveBookingObjectBookLater(com.google.android.gms.maps.model.LatLng lastKnownLatLng) {
    }
    
    @java.lang.Override()
    public void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    /**
     * Method to get date and time from date picker dialog
     *
     * @param _timePicker - timePikcer object to get selected time
     * @param _datePicker - datePicker object to get selected date
     */
    private final void getCurrentDateAndTime(android.widget.TimePicker _timePicker, android.widget.DatePicker _datePicker) {
    }
    
    /**
     * this method is used to check the am & pm for given input time
     *
     * @param inputHour hour is given as input
     */
    private final java.lang.String ampmValidation(int inputHour) {
        return null;
    }
    
    /**
     * Method used to calculate difference between dates
     *
     * @param datetime - Date with time to find the difference from current time
     * @return - Difference of time between that give date and time
     */
    private final int hoursAgo(java.lang.String datetime) {
        return 0;
    }
    
    private final void showDialog() {
    }
    
    private final void closeDialog() {
    }
    
    private final void bottomSheet(int it, android.widget.TextView cardTxt) {
    }
    
    private final void updatePaymentMode(java.lang.String type, java.lang.String promocode, android.widget.TextView cardTxt) {
    }
    
    public final void callGetPassengerInfoApi() {
    }
    
    @java.lang.Override()
    public void getModelDetails(@org.jetbrains.annotations.Nullable()
    java.lang.String id) {
    }
    
    public final void getModelPreference() {
    }
    
    public BookTaxiHomePage() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomePage$BOOKINGSTATE;", "", "(Ljava/lang/String;I)V", "STATE_ONE", "STATE_TWO", "app_debug"})
    public static enum BOOKINGSTATE {
        /*public static final*/ STATE_ONE /* = new STATE_ONE() */,
        /*public static final*/ STATE_TWO /* = new STATE_TWO() */;
        
        BOOKINGSTATE() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0018\"\u0004\b\u001d\u0010\u001aR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\u001a\u0010 \u001a\u00020!X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%\u00a8\u0006&"}, d2 = {"Lcom/taximobility/bookingmodule/BookTaxiHomePage$Companion;", "", "()V", "IS_HOME_PAGE", "", "getIS_HOME_PAGE", "()Z", "setIS_HOME_PAGE", "(Z)V", "bookingState", "Lcom/taximobility/bookingmodule/BookTaxiHomePage$BOOKINGSTATE;", "getBookingState", "()Lcom/taximobility/bookingmodule/BookTaxiHomePage$BOOKINGSTATE;", "setBookingState", "(Lcom/taximobility/bookingmodule/BookTaxiHomePage$BOOKINGSTATE;)V", "defaultCityName", "", "getDefaultCityName", "()Ljava/lang/String;", "setDefaultCityName", "(Ljava/lang/String;)V", "displayHeight", "", "getDisplayHeight", "()I", "setDisplayHeight", "(I)V", "displayWidth", "getDisplayWidth", "setDisplayWidth", "isDropSetFirstTime", "setDropSetFirstTime", "searchPage", "Lcom/taximobility/bookingmodule/pickDropLoc/SearchLocationFrag;", "getSearchPage", "()Lcom/taximobility/bookingmodule/pickDropLoc/SearchLocationFrag;", "setSearchPage", "(Lcom/taximobility/bookingmodule/pickDropLoc/SearchLocationFrag;)V", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.bookingmodule.BookTaxiHomePage.BOOKINGSTATE getBookingState() {
            return null;
        }
        
        public final void setBookingState(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.BookTaxiHomePage.BOOKINGSTATE p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taximobility.bookingmodule.pickDropLoc.SearchLocationFrag getSearchPage() {
            return null;
        }
        
        public final void setSearchPage(@org.jetbrains.annotations.NotNull()
        com.taximobility.bookingmodule.pickDropLoc.SearchLocationFrag p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getDefaultCityName() {
            return null;
        }
        
        public final void setDefaultCityName(@org.jetbrains.annotations.NotNull()
        java.lang.String p0) {
        }
        
        public final boolean getIS_HOME_PAGE() {
            return false;
        }
        
        public final void setIS_HOME_PAGE(boolean p0) {
        }
        
        public final int getDisplayHeight() {
            return 0;
        }
        
        public final void setDisplayHeight(int p0) {
        }
        
        public final int getDisplayWidth() {
            return 0;
        }
        
        public final void setDisplayWidth(int p0) {
        }
        
        public final boolean isDropSetFirstTime() {
            return false;
        }
        
        public final void setDropSetFirstTime(boolean p0) {
        }
        
        private Companion() {
            super();
        }
    }
}