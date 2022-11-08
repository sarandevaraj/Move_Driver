package com.taximobility.tripCancel;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class CreditCardDao_Impl implements CreditCardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCreditCardData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllCards;

  public CreditCardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCreditCardData = new EntityInsertionAdapter<CreditCardData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CardDetails`(`name`,`id`,`type`,`month`,`year`,`card`,`default_card`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CreditCardData value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getType() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getType());
        }
        if (value.getMonth() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMonth());
        }
        if (value.getYear() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getYear());
        }
        if (value.getCard() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCard());
        }
        if (value.getDefault_card() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDefault_card());
        }
      }
    };
    this.__preparedStmtOfDeleteAllCards = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE From CardDetails";
        return _query;
      }
    };
  }

  @Override
  public void insertCreditCard(CreditCardData... models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCreditCardData.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertAllCreditCards(List<CreditCardData> models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCreditCardData.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllCards() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllCards.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllCards.release(_stmt);
    }
  }

  @Override
  public LiveData<List<CreditCardData>> loadAllCards() {
    final String _sql = "SELECT * From CardDetails";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<CreditCardData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<CreditCardData> compute() {
        if (_observer == null) {
          _observer = new Observer("CardDetails") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
          final int _cursorIndexOfYear = _cursor.getColumnIndexOrThrow("year");
          final int _cursorIndexOfCard = _cursor.getColumnIndexOrThrow("card");
          final int _cursorIndexOfDefaultCard = _cursor.getColumnIndexOrThrow("default_card");
          final List<CreditCardData> _result = new ArrayList<CreditCardData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final CreditCardData _item;
            _item = new CreditCardData();
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item.setType(_tmpType);
            final String _tmpMonth;
            _tmpMonth = _cursor.getString(_cursorIndexOfMonth);
            _item.setMonth(_tmpMonth);
            final String _tmpYear;
            _tmpYear = _cursor.getString(_cursorIndexOfYear);
            _item.setYear(_tmpYear);
            final String _tmpCard;
            _tmpCard = _cursor.getString(_cursorIndexOfCard);
            _item.setCard(_tmpCard);
            final String _tmpDefault_card;
            _tmpDefault_card = _cursor.getString(_cursorIndexOfDefaultCard);
            _item.setDefault_card(_tmpDefault_card);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
