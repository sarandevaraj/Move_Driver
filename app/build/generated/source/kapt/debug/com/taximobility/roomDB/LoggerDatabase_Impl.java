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
public final class LoggerDatabase_Impl extends LoggerDatabase {
  private volatile LoggerDao _loggerDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `loggerModel` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `api_type` TEXT, `time` TEXT, `requested_time` TEXT, `responded_time` TEXT, `url` TEXT, `request` TEXT, `response` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"6e157bdf17a62cc46d59a9c65b0ac43a\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `loggerModel`");
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
        final HashMap<String, TableInfo.Column> _columnsLoggerModel = new HashMap<String, TableInfo.Column>(8);
        _columnsLoggerModel.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsLoggerModel.put("api_type", new TableInfo.Column("api_type", "TEXT", false, 0));
        _columnsLoggerModel.put("time", new TableInfo.Column("time", "TEXT", false, 0));
        _columnsLoggerModel.put("requested_time", new TableInfo.Column("requested_time", "TEXT", false, 0));
        _columnsLoggerModel.put("responded_time", new TableInfo.Column("responded_time", "TEXT", false, 0));
        _columnsLoggerModel.put("url", new TableInfo.Column("url", "TEXT", false, 0));
        _columnsLoggerModel.put("request", new TableInfo.Column("request", "TEXT", false, 0));
        _columnsLoggerModel.put("response", new TableInfo.Column("response", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLoggerModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLoggerModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLoggerModel = new TableInfo("loggerModel", _columnsLoggerModel, _foreignKeysLoggerModel, _indicesLoggerModel);
        final TableInfo _existingLoggerModel = TableInfo.read(_db, "loggerModel");
        if (! _infoLoggerModel.equals(_existingLoggerModel)) {
          throw new IllegalStateException("Migration didn't properly handle loggerModel(com.taximobility.roomDB.LoggerModel).\n"
                  + " Expected:\n" + _infoLoggerModel + "\n"
                  + " Found:\n" + _existingLoggerModel);
        }
      }
    }, "6e157bdf17a62cc46d59a9c65b0ac43a", "4da3d3ca2232c300c53120cd5664a38c");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "loggerModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `loggerModel`");
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
  public LoggerDao loggerDao() {
    if (_loggerDao != null) {
      return _loggerDao;
    } else {
      synchronized(this) {
        if(_loggerDao == null) {
          _loggerDao = new LoggerDao_Impl(this);
        }
        return _loggerDao;
      }
    }
  }
}
