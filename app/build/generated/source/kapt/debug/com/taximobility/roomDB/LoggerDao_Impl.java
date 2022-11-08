package com.taximobility.roomDB;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.DataSource.Factory;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class LoggerDao_Impl implements LoggerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLoggerModel;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfLoggerModel;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  public LoggerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLoggerModel = new EntityInsertionAdapter<LoggerModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `loggerModel`(`id`,`api_type`,`time`,`requested_time`,`responded_time`,`url`,`request`,`response`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LoggerModel value) {
        stmt.bindLong(1, value.id);
        if (value.apiType == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.apiType);
        }
        if (value.time == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.time);
        }
        if (value.requested_time == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.requested_time);
        }
        if (value.responded_time == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.responded_time);
        }
        if (value.url == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.url);
        }
        if (value.request == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.request);
        }
        if (value.response == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.response);
        }
      }
    };
    this.__deletionAdapterOfLoggerModel = new EntityDeletionOrUpdateAdapter<LoggerModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `loggerModel` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LoggerModel value) {
        stmt.bindLong(1, value.id);
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE loggerModel SET url = ?, time= ? WHERE id =?";
        return _query;
      }
    };
  }

  @Override
  public void insertLog(LoggerModel... models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLoggerModel.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteLog(LoggerModel... models) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfLoggerModel.handleMultiple(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(String first_name, String last_name, int id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (first_name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, first_name);
      }
      _argIndex = 2;
      if (last_name == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, last_name);
      }
      _argIndex = 3;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public LiveData<List<LoggerModel>> loadAllUsers() {
    final String _sql = "SELECT * From loggerModel ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LoggerModel>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LoggerModel> compute() {
        if (_observer == null) {
          _observer = new Observer("loggerModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfApiType = _cursor.getColumnIndexOrThrow("api_type");
          final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
          final int _cursorIndexOfRequestedTime = _cursor.getColumnIndexOrThrow("requested_time");
          final int _cursorIndexOfRespondedTime = _cursor.getColumnIndexOrThrow("responded_time");
          final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
          final int _cursorIndexOfRequest = _cursor.getColumnIndexOrThrow("request");
          final int _cursorIndexOfResponse = _cursor.getColumnIndexOrThrow("response");
          final List<LoggerModel> _result = new ArrayList<LoggerModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LoggerModel _item;
            _item = new LoggerModel();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.apiType = _cursor.getString(_cursorIndexOfApiType);
            _item.time = _cursor.getString(_cursorIndexOfTime);
            _item.requested_time = _cursor.getString(_cursorIndexOfRequestedTime);
            _item.responded_time = _cursor.getString(_cursorIndexOfRespondedTime);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.request = _cursor.getString(_cursorIndexOfRequest);
            _item.response = _cursor.getString(_cursorIndexOfResponse);
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

  @Override
  public LiveData<List<String>> loadDistinctApi() {
    final String _sql = "SELECT DISTINCT api_type From loggerModel ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<String>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<String> compute() {
        if (_observer == null) {
          _observer = new Observer("loggerModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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

  @Override
  public LiveData<List<LoggerModel>> loadAllUser(String query) {
    final String _sql = "SELECT * From loggerModel WHERE api_type =? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return new ComputableLiveData<List<LoggerModel>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LoggerModel> compute() {
        if (_observer == null) {
          _observer = new Observer("loggerModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfApiType = _cursor.getColumnIndexOrThrow("api_type");
          final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
          final int _cursorIndexOfRequestedTime = _cursor.getColumnIndexOrThrow("requested_time");
          final int _cursorIndexOfRespondedTime = _cursor.getColumnIndexOrThrow("responded_time");
          final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
          final int _cursorIndexOfRequest = _cursor.getColumnIndexOrThrow("request");
          final int _cursorIndexOfResponse = _cursor.getColumnIndexOrThrow("response");
          final List<LoggerModel> _result = new ArrayList<LoggerModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LoggerModel _item;
            _item = new LoggerModel();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.apiType = _cursor.getString(_cursorIndexOfApiType);
            _item.time = _cursor.getString(_cursorIndexOfTime);
            _item.requested_time = _cursor.getString(_cursorIndexOfRequestedTime);
            _item.responded_time = _cursor.getString(_cursorIndexOfRespondedTime);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.request = _cursor.getString(_cursorIndexOfRequest);
            _item.response = _cursor.getString(_cursorIndexOfResponse);
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

  @Override
  public LiveData<Integer> getCount(String query) {
    final String _sql = "SELECT COUNT(api_type) FROM loggerModel  WHERE api_type =? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return new ComputableLiveData<Integer>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected Integer compute() {
        if (_observer == null) {
          _observer = new Observer("loggerModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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

  @Override
  public DataSource.Factory<Integer, LoggerModel> logsByApiType(String apiType) {
    final String _sql = "SELECT * FROM loggerModel WHERE api_type =? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (apiType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, apiType);
    }
    return new DataSource.Factory<Integer, LoggerModel>() {
      @Override
      public LimitOffsetDataSource<LoggerModel> create() {
        return new LimitOffsetDataSource<LoggerModel>(__db, _statement, false , "loggerModel") {
          @Override
          protected List<LoggerModel> convertRows(Cursor cursor) {
            final int _cursorIndexOfId = cursor.getColumnIndexOrThrow("id");
            final int _cursorIndexOfApiType = cursor.getColumnIndexOrThrow("api_type");
            final int _cursorIndexOfTime = cursor.getColumnIndexOrThrow("time");
            final int _cursorIndexOfRequestedTime = cursor.getColumnIndexOrThrow("requested_time");
            final int _cursorIndexOfRespondedTime = cursor.getColumnIndexOrThrow("responded_time");
            final int _cursorIndexOfUrl = cursor.getColumnIndexOrThrow("url");
            final int _cursorIndexOfRequest = cursor.getColumnIndexOrThrow("request");
            final int _cursorIndexOfResponse = cursor.getColumnIndexOrThrow("response");
            final List<LoggerModel> _res = new ArrayList<LoggerModel>(cursor.getCount());
            while(cursor.moveToNext()) {
              final LoggerModel _item;
              _item = new LoggerModel();
              _item.id = cursor.getInt(_cursorIndexOfId);
              _item.apiType = cursor.getString(_cursorIndexOfApiType);
              _item.time = cursor.getString(_cursorIndexOfTime);
              _item.requested_time = cursor.getString(_cursorIndexOfRequestedTime);
              _item.responded_time = cursor.getString(_cursorIndexOfRespondedTime);
              _item.url = cursor.getString(_cursorIndexOfUrl);
              _item.request = cursor.getString(_cursorIndexOfRequest);
              _item.response = cursor.getString(_cursorIndexOfResponse);
              _res.add(_item);
            }
            return _res;
          }
        };
      }
    };
  }

  @Override
  public LiveData<List<LoggerModel>> loadQuery(String url) {
    final String _sql = "SELECT * From loggerModel WHERE url =? ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (url == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, url);
    }
    return new ComputableLiveData<List<LoggerModel>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LoggerModel> compute() {
        if (_observer == null) {
          _observer = new Observer("loggerModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfApiType = _cursor.getColumnIndexOrThrow("api_type");
          final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
          final int _cursorIndexOfRequestedTime = _cursor.getColumnIndexOrThrow("requested_time");
          final int _cursorIndexOfRespondedTime = _cursor.getColumnIndexOrThrow("responded_time");
          final int _cursorIndexOfUrl = _cursor.getColumnIndexOrThrow("url");
          final int _cursorIndexOfRequest = _cursor.getColumnIndexOrThrow("request");
          final int _cursorIndexOfResponse = _cursor.getColumnIndexOrThrow("response");
          final List<LoggerModel> _result = new ArrayList<LoggerModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LoggerModel _item;
            _item = new LoggerModel();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _item.apiType = _cursor.getString(_cursorIndexOfApiType);
            _item.time = _cursor.getString(_cursorIndexOfTime);
            _item.requested_time = _cursor.getString(_cursorIndexOfRequestedTime);
            _item.responded_time = _cursor.getString(_cursorIndexOfRespondedTime);
            _item.url = _cursor.getString(_cursorIndexOfUrl);
            _item.request = _cursor.getString(_cursorIndexOfRequest);
            _item.response = _cursor.getString(_cursorIndexOfResponse);
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
