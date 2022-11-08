package com.taximobility.roomDB;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MapLoggerDatabase_Impl extends MapLoggerDatabase {
  private volatile MapLoggerDao _mapLoggerDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `google_maplogger` (`from_to` TEXT NOT NULL, `time` REAL NOT NULL, `distance` REAL NOT NULL, `routeResult` TEXT, `distanceResult` TEXT, PRIMARY KEY(`from_to`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `mapbox_maplogger` (`from_to` TEXT NOT NULL, `time` REAL NOT NULL, `distance` REAL NOT NULL, `routeResult` TEXT, `distanceResult` TEXT, PRIMARY KEY(`from_to`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `geocode_logger` (`lat_lng` TEXT NOT NULL, `result` TEXT, `type` INTEGER NOT NULL, PRIMARY KEY(`lat_lng`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"8d832bfeaa233b126c26d0f911d3a8a2\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `google_maplogger`");
        _db.execSQL("DROP TABLE IF EXISTS `mapbox_maplogger`");
        _db.execSQL("DROP TABLE IF EXISTS `geocode_logger`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsGoogleMaplogger = new HashMap<String, TableInfo.Column>(5);
        _columnsGoogleMaplogger.put("from_to", new TableInfo.Column("from_to", "TEXT", true, 1));
        _columnsGoogleMaplogger.put("time", new TableInfo.Column("time", "REAL", true, 0));
        _columnsGoogleMaplogger.put("distance", new TableInfo.Column("distance", "REAL", true, 0));
        _columnsGoogleMaplogger.put("routeResult", new TableInfo.Column("routeResult", "TEXT", false, 0));
        _columnsGoogleMaplogger.put("distanceResult", new TableInfo.Column("distanceResult", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGoogleMaplogger = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGoogleMaplogger = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGoogleMaplogger = new TableInfo("google_maplogger", _columnsGoogleMaplogger, _foreignKeysGoogleMaplogger, _indicesGoogleMaplogger);
        final TableInfo _existingGoogleMaplogger = TableInfo.read(_db, "google_maplogger");
        if (! _infoGoogleMaplogger.equals(_existingGoogleMaplogger)) {
          throw new IllegalStateException("Migration didn't properly handle google_maplogger(com.taximobility.roomDB.GoogleMapModel).\n"
                  + " Expected:\n" + _infoGoogleMaplogger + "\n"
                  + " Found:\n" + _existingGoogleMaplogger);
        }
        final HashMap<String, TableInfo.Column> _columnsMapboxMaplogger = new HashMap<String, TableInfo.Column>(5);
        _columnsMapboxMaplogger.put("from_to", new TableInfo.Column("from_to", "TEXT", true, 1));
        _columnsMapboxMaplogger.put("time", new TableInfo.Column("time", "REAL", true, 0));
        _columnsMapboxMaplogger.put("distance", new TableInfo.Column("distance", "REAL", true, 0));
        _columnsMapboxMaplogger.put("routeResult", new TableInfo.Column("routeResult", "TEXT", false, 0));
        _columnsMapboxMaplogger.put("distanceResult", new TableInfo.Column("distanceResult", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMapboxMaplogger = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMapboxMaplogger = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMapboxMaplogger = new TableInfo("mapbox_maplogger", _columnsMapboxMaplogger, _foreignKeysMapboxMaplogger, _indicesMapboxMaplogger);
        final TableInfo _existingMapboxMaplogger = TableInfo.read(_db, "mapbox_maplogger");
        if (! _infoMapboxMaplogger.equals(_existingMapboxMaplogger)) {
          throw new IllegalStateException("Migration didn't properly handle mapbox_maplogger(com.taximobility.roomDB.MapboxModel).\n"
                  + " Expected:\n" + _infoMapboxMaplogger + "\n"
                  + " Found:\n" + _existingMapboxMaplogger);
        }
        final HashMap<String, TableInfo.Column> _columnsGeocodeLogger = new HashMap<String, TableInfo.Column>(3);
        _columnsGeocodeLogger.put("lat_lng", new TableInfo.Column("lat_lng", "TEXT", true, 1));
        _columnsGeocodeLogger.put("result", new TableInfo.Column("result", "TEXT", false, 0));
        _columnsGeocodeLogger.put("type", new TableInfo.Column("type", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGeocodeLogger = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGeocodeLogger = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGeocodeLogger = new TableInfo("geocode_logger", _columnsGeocodeLogger, _foreignKeysGeocodeLogger, _indicesGeocodeLogger);
        final TableInfo _existingGeocodeLogger = TableInfo.read(_db, "geocode_logger");
        if (! _infoGeocodeLogger.equals(_existingGeocodeLogger)) {
          throw new IllegalStateException("Migration didn't properly handle geocode_logger(com.taximobility.roomDB.GeocoderModel).\n"
                  + " Expected:\n" + _infoGeocodeLogger + "\n"
                  + " Found:\n" + _existingGeocodeLogger);
        }
      }
    }, "8d832bfeaa233b126c26d0f911d3a8a2", "cf746663716c3a98d30a68d6931ebfa5");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "google_maplogger","mapbox_maplogger","geocode_logger");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `google_maplogger`");
      _db.execSQL("DELETE FROM `mapbox_maplogger`");
      _db.execSQL("DELETE FROM `geocode_logger`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MapLoggerDao loggerDao() {
    if (_mapLoggerDao != null) {
      return _mapLoggerDao;
    } else {
      synchronized(this) {
        if(_mapLoggerDao == null) {
          _mapLoggerDao = new MapLoggerDao_Impl(this);
        }
        return _mapLoggerDao;
      }
    }
  }
}
