package com.taximobility.driver.roomDB;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Double;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class MapLoggerDao_Impl implements MapLoggerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGoogleMapModel;

  private final EntityInsertionAdapter __insertionAdapterOfMapboxModel;

  private final EntityInsertionAdapter __insertionAdapterOfGeocoderModel;

  public MapLoggerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGoogleMapModel = new EntityInsertionAdapter<GoogleMapModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `google_maplogger`(`from_to`,`time`,`distance`,`routeResult`,`distanceResult`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GoogleMapModel value) {
        if (value.fromTo == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.fromTo);
        }
        stmt.bindDouble(2, value.time);
        stmt.bindDouble(3, value.distance);
        if (value.routeResult == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.routeResult);
        }
        if (value.distanceResult == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.distanceResult);
        }
      }
    };
    this.__insertionAdapterOfMapboxModel = new EntityInsertionAdapter<MapboxModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `mapbox_maplogger`(`from_to`,`time`,`distance`,`routeResult`,`distanceResult`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MapboxModel value) {
        if (value.fromTo == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.fromTo);
        }
        stmt.bindDouble(2, value.time);
        stmt.bindDouble(3, value.distance);
        if (value.routeResult == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.routeResult);
        }
        if (value.distanceResult == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.distanceResult);
        }
      }
    };
    this.__insertionAdapterOfGeocoderModel = new EntityInsertionAdapter<GeocoderModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `geocode_logger`(`lat_lng`,`result`,`type`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GeocoderModel value) {
        if (value.latLng == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.latLng);
        }
        if (value.result == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.result);
        }
        stmt.bindLong(3, value.type);
      }
    };
  }

  @Override
  public void insertGoogleLog(GoogleMapModel... models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGoogleMapModel.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertMapboxLog(MapboxModel... models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMapboxModel.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertGeocodeLog(GeocoderModel... models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGeocoderModel.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<String> getRouteResult(String key) {
    final String _sql = "SELECT routeResult FROM google_maplogger WHERE from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    return new ComputableLiveData<String>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected String compute() {
        if (_observer == null) {
          _observer = new Observer("google_maplogger") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final String _result;
          if(_cursor.moveToFirst()) {
            _result = _cursor.getString(0);
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
  public LiveData<Double> getDistanceAndTime(String key) {
    final String _sql = "SELECT distance FROM google_maplogger WHERE from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    return new ComputableLiveData<Double>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected Double compute() {
        if (_observer == null) {
          _observer = new Observer("google_maplogger") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getDouble(0);
            }
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
  public LiveData<String> getDistanceResult(String key) {
    final String _sql = "SELECT distanceResult FROM google_maplogger WHERE from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    return new ComputableLiveData<String>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected String compute() {
        if (_observer == null) {
          _observer = new Observer("google_maplogger") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final String _result;
          if(_cursor.moveToFirst()) {
            _result = _cursor.getString(0);
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
  public LiveData<List<GoogleMapModel>> loadAll(String fromTo) {
    final String _sql = "SELECT * From google_maplogger where from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (fromTo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, fromTo);
    }
    return new ComputableLiveData<List<GoogleMapModel>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<GoogleMapModel> compute() {
        if (_observer == null) {
          _observer = new Observer("google_maplogger") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfFromTo = _cursor.getColumnIndexOrThrow("from_to");
          final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
          final int _cursorIndexOfDistance = _cursor.getColumnIndexOrThrow("distance");
          final int _cursorIndexOfRouteResult = _cursor.getColumnIndexOrThrow("routeResult");
          final int _cursorIndexOfDistanceResult = _cursor.getColumnIndexOrThrow("distanceResult");
          final List<GoogleMapModel> _result = new ArrayList<GoogleMapModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final GoogleMapModel _item;
            _item = new GoogleMapModel();
            _item.fromTo = _cursor.getString(_cursorIndexOfFromTo);
            _item.time = _cursor.getDouble(_cursorIndexOfTime);
            _item.distance = _cursor.getDouble(_cursorIndexOfDistance);
            _item.routeResult = _cursor.getString(_cursorIndexOfRouteResult);
            _item.distanceResult = _cursor.getString(_cursorIndexOfDistanceResult);
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
  public GoogleMapModel getGoogleModel(String key) {
    final String _sql = "SELECT * FROM google_maplogger WHERE from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfFromTo = _cursor.getColumnIndexOrThrow("from_to");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final int _cursorIndexOfDistance = _cursor.getColumnIndexOrThrow("distance");
      final int _cursorIndexOfRouteResult = _cursor.getColumnIndexOrThrow("routeResult");
      final int _cursorIndexOfDistanceResult = _cursor.getColumnIndexOrThrow("distanceResult");
      final GoogleMapModel _result;
      if(_cursor.moveToFirst()) {
        _result = new GoogleMapModel();
        _result.fromTo = _cursor.getString(_cursorIndexOfFromTo);
        _result.time = _cursor.getDouble(_cursorIndexOfTime);
        _result.distance = _cursor.getDouble(_cursorIndexOfDistance);
        _result.routeResult = _cursor.getString(_cursorIndexOfRouteResult);
        _result.distanceResult = _cursor.getString(_cursorIndexOfDistanceResult);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public MapboxModel getMapboxModel(String key) {
    final String _sql = "SELECT * FROM mapbox_maplogger WHERE from_to =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfFromTo = _cursor.getColumnIndexOrThrow("from_to");
      final int _cursorIndexOfTime = _cursor.getColumnIndexOrThrow("time");
      final int _cursorIndexOfDistance = _cursor.getColumnIndexOrThrow("distance");
      final int _cursorIndexOfRouteResult = _cursor.getColumnIndexOrThrow("routeResult");
      final int _cursorIndexOfDistanceResult = _cursor.getColumnIndexOrThrow("distanceResult");
      final MapboxModel _result;
      if(_cursor.moveToFirst()) {
        _result = new MapboxModel();
        _result.fromTo = _cursor.getString(_cursorIndexOfFromTo);
        _result.time = _cursor.getDouble(_cursorIndexOfTime);
        _result.distance = _cursor.getDouble(_cursorIndexOfDistance);
        _result.routeResult = _cursor.getString(_cursorIndexOfRouteResult);
        _result.distanceResult = _cursor.getString(_cursorIndexOfDistanceResult);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public GeocoderModel getGeocodeModel(String key, int type) {
    final String _sql = "SELECT * FROM geocode_logger WHERE lat_lng =? AND type =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, type);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfLatLng = _cursor.getColumnIndexOrThrow("lat_lng");
      final int _cursorIndexOfResult = _cursor.getColumnIndexOrThrow("result");
      final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
      final GeocoderModel _result;
      if(_cursor.moveToFirst()) {
        _result = new GeocoderModel();
        _result.latLng = _cursor.getString(_cursorIndexOfLatLng);
        _result.result = _cursor.getString(_cursorIndexOfResult);
        _result.type = _cursor.getInt(_cursorIndexOfType);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
