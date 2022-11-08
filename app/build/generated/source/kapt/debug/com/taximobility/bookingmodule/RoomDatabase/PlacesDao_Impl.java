package com.taximobility.bookingmodule.RoomDatabase;

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
import com.taximobility.bookingmodule.LocationData;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class PlacesDao_Impl implements PlacesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLocationData;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlace;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavourite;

  private final SharedSQLiteStatement __preparedStmtOfDeletePopular;

  public PlacesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocationData = new EntityInsertionAdapter<LocationData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `favModel`(`_id`,`p_location_name`,`d_location_name`,`latitude`,`longtitute`,`p_latitude`,`p_longtitute`,`loction_type`,`location_name`,`label_name`,`android_icon`,`ios_icon`,`type`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocationData value) {
        stmt.bindLong(1, value.get_id());
        if (value.getP_location_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getP_location_name());
        }
        if (value.getD_location_name() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getD_location_name());
        }
        stmt.bindDouble(4, value.getLatitude());
        stmt.bindDouble(5, value.getLongtitute());
        stmt.bindDouble(6, value.getP_latitude());
        stmt.bindDouble(7, value.getP_longtitute());
        if (value.getLoction_type() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getLoction_type());
        }
        if (value.getLocation_name() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLocation_name());
        }
        if (value.getLabel_name() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getLabel_name());
        }
        if (value.getAndroid_icon() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getAndroid_icon());
        }
        if (value.getIos_icon() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getIos_icon());
        }
        if (value.getType() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getType());
        }
      }
    };
    this.__preparedStmtOfDeletePlace = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM favModel";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteFavourite = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM favModel WHERE location_name LIKE ? || '%'";
        return _query;
      }
    };
    this.__preparedStmtOfDeletePopular = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM favModel WHERE type=2 or type=1";
        return _query;
      }
    };
  }

  @Override
  public void insertLog(List<LocationData> models) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocationData.insert(models);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePlace() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlace.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePlace.release(_stmt);
    }
  }

  @Override
  public void deleteFavourite(String str) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavourite.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (str == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, str);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteFavourite.release(_stmt);
    }
  }

  @Override
  public void deletePopular() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePopular.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePopular.release(_stmt);
    }
  }

  @Override
  public LiveData<List<LocationData>> loadFavPlaces() {
    final String _sql = "SELECT * From favModel WHERE type = 1 or type = 2 ORDER BY type ASC LIMIT 3 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LocationData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LocationData> compute() {
        if (_observer == null) {
          _observer = new Observer("favModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfPLocationName = _cursor.getColumnIndexOrThrow("p_location_name");
          final int _cursorIndexOfDLocationName = _cursor.getColumnIndexOrThrow("d_location_name");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongtitute = _cursor.getColumnIndexOrThrow("longtitute");
          final int _cursorIndexOfPLatitude = _cursor.getColumnIndexOrThrow("p_latitude");
          final int _cursorIndexOfPLongtitute = _cursor.getColumnIndexOrThrow("p_longtitute");
          final int _cursorIndexOfLoctionType = _cursor.getColumnIndexOrThrow("loction_type");
          final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("location_name");
          final int _cursorIndexOfLabelName = _cursor.getColumnIndexOrThrow("label_name");
          final int _cursorIndexOfAndroidIcon = _cursor.getColumnIndexOrThrow("android_icon");
          final int _cursorIndexOfIosIcon = _cursor.getColumnIndexOrThrow("ios_icon");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<LocationData> _result = new ArrayList<LocationData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocationData _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmpP_location_name;
            _tmpP_location_name = _cursor.getString(_cursorIndexOfPLocationName);
            final String _tmpD_location_name;
            _tmpD_location_name = _cursor.getString(_cursorIndexOfDLocationName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongtitute;
            _tmpLongtitute = _cursor.getDouble(_cursorIndexOfLongtitute);
            final double _tmpP_latitude;
            _tmpP_latitude = _cursor.getDouble(_cursorIndexOfPLatitude);
            final double _tmpP_longtitute;
            _tmpP_longtitute = _cursor.getDouble(_cursorIndexOfPLongtitute);
            final String _tmpLoction_type;
            _tmpLoction_type = _cursor.getString(_cursorIndexOfLoctionType);
            final String _tmpLocation_name;
            _tmpLocation_name = _cursor.getString(_cursorIndexOfLocationName);
            final String _tmpLabel_name;
            _tmpLabel_name = _cursor.getString(_cursorIndexOfLabelName);
            final String _tmpAndroid_icon;
            _tmpAndroid_icon = _cursor.getString(_cursorIndexOfAndroidIcon);
            final String _tmpIos_icon;
            _tmpIos_icon = _cursor.getString(_cursorIndexOfIosIcon);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new LocationData(_tmp_id,_tmpP_location_name,_tmpD_location_name,_tmpLatitude,_tmpLongtitute,_tmpP_latitude,_tmpP_longtitute,_tmpLoction_type,_tmpLocation_name,_tmpLabel_name,_tmpAndroid_icon,_tmpIos_icon,_tmpType);
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
  public LiveData<List<LocationData>> loadAllFavourite() {
    final String _sql = "SELECT * From favModel WHERE type=1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LocationData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LocationData> compute() {
        if (_observer == null) {
          _observer = new Observer("favModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfPLocationName = _cursor.getColumnIndexOrThrow("p_location_name");
          final int _cursorIndexOfDLocationName = _cursor.getColumnIndexOrThrow("d_location_name");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongtitute = _cursor.getColumnIndexOrThrow("longtitute");
          final int _cursorIndexOfPLatitude = _cursor.getColumnIndexOrThrow("p_latitude");
          final int _cursorIndexOfPLongtitute = _cursor.getColumnIndexOrThrow("p_longtitute");
          final int _cursorIndexOfLoctionType = _cursor.getColumnIndexOrThrow("loction_type");
          final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("location_name");
          final int _cursorIndexOfLabelName = _cursor.getColumnIndexOrThrow("label_name");
          final int _cursorIndexOfAndroidIcon = _cursor.getColumnIndexOrThrow("android_icon");
          final int _cursorIndexOfIosIcon = _cursor.getColumnIndexOrThrow("ios_icon");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<LocationData> _result = new ArrayList<LocationData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocationData _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmpP_location_name;
            _tmpP_location_name = _cursor.getString(_cursorIndexOfPLocationName);
            final String _tmpD_location_name;
            _tmpD_location_name = _cursor.getString(_cursorIndexOfDLocationName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongtitute;
            _tmpLongtitute = _cursor.getDouble(_cursorIndexOfLongtitute);
            final double _tmpP_latitude;
            _tmpP_latitude = _cursor.getDouble(_cursorIndexOfPLatitude);
            final double _tmpP_longtitute;
            _tmpP_longtitute = _cursor.getDouble(_cursorIndexOfPLongtitute);
            final String _tmpLoction_type;
            _tmpLoction_type = _cursor.getString(_cursorIndexOfLoctionType);
            final String _tmpLocation_name;
            _tmpLocation_name = _cursor.getString(_cursorIndexOfLocationName);
            final String _tmpLabel_name;
            _tmpLabel_name = _cursor.getString(_cursorIndexOfLabelName);
            final String _tmpAndroid_icon;
            _tmpAndroid_icon = _cursor.getString(_cursorIndexOfAndroidIcon);
            final String _tmpIos_icon;
            _tmpIos_icon = _cursor.getString(_cursorIndexOfIosIcon);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new LocationData(_tmp_id,_tmpP_location_name,_tmpD_location_name,_tmpLatitude,_tmpLongtitute,_tmpP_latitude,_tmpP_longtitute,_tmpLoction_type,_tmpLocation_name,_tmpLabel_name,_tmpAndroid_icon,_tmpIos_icon,_tmpType);
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
  public LiveData<List<LocationData>> loadAllFavouriteAndPopularAndRecent() {
    final String _sql = "SELECT * From favModel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LocationData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LocationData> compute() {
        if (_observer == null) {
          _observer = new Observer("favModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfPLocationName = _cursor.getColumnIndexOrThrow("p_location_name");
          final int _cursorIndexOfDLocationName = _cursor.getColumnIndexOrThrow("d_location_name");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongtitute = _cursor.getColumnIndexOrThrow("longtitute");
          final int _cursorIndexOfPLatitude = _cursor.getColumnIndexOrThrow("p_latitude");
          final int _cursorIndexOfPLongtitute = _cursor.getColumnIndexOrThrow("p_longtitute");
          final int _cursorIndexOfLoctionType = _cursor.getColumnIndexOrThrow("loction_type");
          final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("location_name");
          final int _cursorIndexOfLabelName = _cursor.getColumnIndexOrThrow("label_name");
          final int _cursorIndexOfAndroidIcon = _cursor.getColumnIndexOrThrow("android_icon");
          final int _cursorIndexOfIosIcon = _cursor.getColumnIndexOrThrow("ios_icon");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<LocationData> _result = new ArrayList<LocationData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocationData _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmpP_location_name;
            _tmpP_location_name = _cursor.getString(_cursorIndexOfPLocationName);
            final String _tmpD_location_name;
            _tmpD_location_name = _cursor.getString(_cursorIndexOfDLocationName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongtitute;
            _tmpLongtitute = _cursor.getDouble(_cursorIndexOfLongtitute);
            final double _tmpP_latitude;
            _tmpP_latitude = _cursor.getDouble(_cursorIndexOfPLatitude);
            final double _tmpP_longtitute;
            _tmpP_longtitute = _cursor.getDouble(_cursorIndexOfPLongtitute);
            final String _tmpLoction_type;
            _tmpLoction_type = _cursor.getString(_cursorIndexOfLoctionType);
            final String _tmpLocation_name;
            _tmpLocation_name = _cursor.getString(_cursorIndexOfLocationName);
            final String _tmpLabel_name;
            _tmpLabel_name = _cursor.getString(_cursorIndexOfLabelName);
            final String _tmpAndroid_icon;
            _tmpAndroid_icon = _cursor.getString(_cursorIndexOfAndroidIcon);
            final String _tmpIos_icon;
            _tmpIos_icon = _cursor.getString(_cursorIndexOfIosIcon);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new LocationData(_tmp_id,_tmpP_location_name,_tmpD_location_name,_tmpLatitude,_tmpLongtitute,_tmpP_latitude,_tmpP_longtitute,_tmpLoction_type,_tmpLocation_name,_tmpLabel_name,_tmpAndroid_icon,_tmpIos_icon,_tmpType);
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
  public LiveData<List<LocationData>> getLocationFilter(String values) {
    final String _sql = "SELECT * FROM favModel WHERE location_name LIKE ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (values == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, values);
    }
    return new ComputableLiveData<List<LocationData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LocationData> compute() {
        if (_observer == null) {
          _observer = new Observer("favModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfPLocationName = _cursor.getColumnIndexOrThrow("p_location_name");
          final int _cursorIndexOfDLocationName = _cursor.getColumnIndexOrThrow("d_location_name");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongtitute = _cursor.getColumnIndexOrThrow("longtitute");
          final int _cursorIndexOfPLatitude = _cursor.getColumnIndexOrThrow("p_latitude");
          final int _cursorIndexOfPLongtitute = _cursor.getColumnIndexOrThrow("p_longtitute");
          final int _cursorIndexOfLoctionType = _cursor.getColumnIndexOrThrow("loction_type");
          final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("location_name");
          final int _cursorIndexOfLabelName = _cursor.getColumnIndexOrThrow("label_name");
          final int _cursorIndexOfAndroidIcon = _cursor.getColumnIndexOrThrow("android_icon");
          final int _cursorIndexOfIosIcon = _cursor.getColumnIndexOrThrow("ios_icon");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<LocationData> _result = new ArrayList<LocationData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocationData _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmpP_location_name;
            _tmpP_location_name = _cursor.getString(_cursorIndexOfPLocationName);
            final String _tmpD_location_name;
            _tmpD_location_name = _cursor.getString(_cursorIndexOfDLocationName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongtitute;
            _tmpLongtitute = _cursor.getDouble(_cursorIndexOfLongtitute);
            final double _tmpP_latitude;
            _tmpP_latitude = _cursor.getDouble(_cursorIndexOfPLatitude);
            final double _tmpP_longtitute;
            _tmpP_longtitute = _cursor.getDouble(_cursorIndexOfPLongtitute);
            final String _tmpLoction_type;
            _tmpLoction_type = _cursor.getString(_cursorIndexOfLoctionType);
            final String _tmpLocation_name;
            _tmpLocation_name = _cursor.getString(_cursorIndexOfLocationName);
            final String _tmpLabel_name;
            _tmpLabel_name = _cursor.getString(_cursorIndexOfLabelName);
            final String _tmpAndroid_icon;
            _tmpAndroid_icon = _cursor.getString(_cursorIndexOfAndroidIcon);
            final String _tmpIos_icon;
            _tmpIos_icon = _cursor.getString(_cursorIndexOfIosIcon);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new LocationData(_tmp_id,_tmpP_location_name,_tmpD_location_name,_tmpLatitude,_tmpLongtitute,_tmpP_latitude,_tmpP_longtitute,_tmpLoction_type,_tmpLocation_name,_tmpLabel_name,_tmpAndroid_icon,_tmpIos_icon,_tmpType);
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
  public LiveData<List<LocationData>> loadAllPastBookingPlaces() {
    final String _sql = "SELECT * From favModel WHERE type=3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LocationData>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<LocationData> compute() {
        if (_observer == null) {
          _observer = new Observer("favModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("_id");
          final int _cursorIndexOfPLocationName = _cursor.getColumnIndexOrThrow("p_location_name");
          final int _cursorIndexOfDLocationName = _cursor.getColumnIndexOrThrow("d_location_name");
          final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
          final int _cursorIndexOfLongtitute = _cursor.getColumnIndexOrThrow("longtitute");
          final int _cursorIndexOfPLatitude = _cursor.getColumnIndexOrThrow("p_latitude");
          final int _cursorIndexOfPLongtitute = _cursor.getColumnIndexOrThrow("p_longtitute");
          final int _cursorIndexOfLoctionType = _cursor.getColumnIndexOrThrow("loction_type");
          final int _cursorIndexOfLocationName = _cursor.getColumnIndexOrThrow("location_name");
          final int _cursorIndexOfLabelName = _cursor.getColumnIndexOrThrow("label_name");
          final int _cursorIndexOfAndroidIcon = _cursor.getColumnIndexOrThrow("android_icon");
          final int _cursorIndexOfIosIcon = _cursor.getColumnIndexOrThrow("ios_icon");
          final int _cursorIndexOfType = _cursor.getColumnIndexOrThrow("type");
          final List<LocationData> _result = new ArrayList<LocationData>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocationData _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmpP_location_name;
            _tmpP_location_name = _cursor.getString(_cursorIndexOfPLocationName);
            final String _tmpD_location_name;
            _tmpD_location_name = _cursor.getString(_cursorIndexOfDLocationName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongtitute;
            _tmpLongtitute = _cursor.getDouble(_cursorIndexOfLongtitute);
            final double _tmpP_latitude;
            _tmpP_latitude = _cursor.getDouble(_cursorIndexOfPLatitude);
            final double _tmpP_longtitute;
            _tmpP_longtitute = _cursor.getDouble(_cursorIndexOfPLongtitute);
            final String _tmpLoction_type;
            _tmpLoction_type = _cursor.getString(_cursorIndexOfLoctionType);
            final String _tmpLocation_name;
            _tmpLocation_name = _cursor.getString(_cursorIndexOfLocationName);
            final String _tmpLabel_name;
            _tmpLabel_name = _cursor.getString(_cursorIndexOfLabelName);
            final String _tmpAndroid_icon;
            _tmpAndroid_icon = _cursor.getString(_cursorIndexOfAndroidIcon);
            final String _tmpIos_icon;
            _tmpIos_icon = _cursor.getString(_cursorIndexOfIosIcon);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            _item = new LocationData(_tmp_id,_tmpP_location_name,_tmpD_location_name,_tmpLatitude,_tmpLongtitute,_tmpP_latitude,_tmpP_longtitute,_tmpLoction_type,_tmpLocation_name,_tmpLabel_name,_tmpAndroid_icon,_tmpIos_icon,_tmpType);
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
