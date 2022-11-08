package com.taximobility.driver.errorLog;

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
public final class DriverErrorLogDatabase_Impl extends DriverErrorLogDatabase {
  private volatile DriverErrorLogDao _driverErrorLogDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `apiErrorModel` (`ids` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timeStamp` TEXT NOT NULL, `apiCase` TEXT NOT NULL, `error` TEXT NOT NULL, `driverDataDriver` TEXT NOT NULL, `inputParams` TEXT NOT NULL, `classContext` TEXT NOT NULL, `sendStatus` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"46526f7d3680702d64ab408fc4154421\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `apiErrorModel`");
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
        final HashMap<String, TableInfo.Column> _columnsApiErrorModel = new HashMap<String, TableInfo.Column>(8);
        _columnsApiErrorModel.put("ids", new TableInfo.Column("ids", "INTEGER", true, 1));
        _columnsApiErrorModel.put("timeStamp", new TableInfo.Column("timeStamp", "TEXT", true, 0));
        _columnsApiErrorModel.put("apiCase", new TableInfo.Column("apiCase", "TEXT", true, 0));
        _columnsApiErrorModel.put("error", new TableInfo.Column("error", "TEXT", true, 0));
        _columnsApiErrorModel.put("driverDataDriver", new TableInfo.Column("driverDataDriver", "TEXT", true, 0));
        _columnsApiErrorModel.put("inputParams", new TableInfo.Column("inputParams", "TEXT", true, 0));
        _columnsApiErrorModel.put("classContext", new TableInfo.Column("classContext", "TEXT", true, 0));
        _columnsApiErrorModel.put("sendStatus", new TableInfo.Column("sendStatus", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysApiErrorModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesApiErrorModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoApiErrorModel = new TableInfo("apiErrorModel", _columnsApiErrorModel, _foreignKeysApiErrorModel, _indicesApiErrorModel);
        final TableInfo _existingApiErrorModel = TableInfo.read(_db, "apiErrorModel");
        if (! _infoApiErrorModel.equals(_existingApiErrorModel)) {
          throw new IllegalStateException("Migration didn't properly handle apiErrorModel(com.taximobility.driver.errorLog.DriverApiErrorModel).\n"
                  + " Expected:\n" + _infoApiErrorModel + "\n"
                  + " Found:\n" + _existingApiErrorModel);
        }
      }
    }, "46526f7d3680702d64ab408fc4154421", "68c774374bf4c10da167be51c561374a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "apiErrorModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `apiErrorModel`");
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
  public DriverErrorLogDao errorLogDao() {
    if (_driverErrorLogDao != null) {
      return _driverErrorLogDao;
    } else {
      synchronized(this) {
        if(_driverErrorLogDao == null) {
          _driverErrorLogDao = new DriverErrorLogDao_Impl(this);
        }
        return _driverErrorLogDao;
      }
    }
  }
}
