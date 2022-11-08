package com.taximobility.bookingmodule.RoomDatabase;

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
public final class PlacesDatabase_Impl extends PlacesDatabase {
  private volatile PlacesDao _placesDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `favModel` (`_id` INTEGER NOT NULL, `p_location_name` TEXT NOT NULL, `d_location_name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longtitute` REAL NOT NULL, `p_latitude` REAL NOT NULL, `p_longtitute` REAL NOT NULL, `loction_type` TEXT NOT NULL, `location_name` TEXT NOT NULL, `label_name` TEXT NOT NULL, `android_icon` TEXT NOT NULL, `ios_icon` TEXT NOT NULL, `type` TEXT NOT NULL, PRIMARY KEY(`_id`, `type`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"e71a876db2c420f48a29ad9a0ac31854\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `favModel`");
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
        final HashMap<String, TableInfo.Column> _columnsFavModel = new HashMap<String, TableInfo.Column>(13);
        _columnsFavModel.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1));
        _columnsFavModel.put("p_location_name", new TableInfo.Column("p_location_name", "TEXT", true, 0));
        _columnsFavModel.put("d_location_name", new TableInfo.Column("d_location_name", "TEXT", true, 0));
        _columnsFavModel.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsFavModel.put("longtitute", new TableInfo.Column("longtitute", "REAL", true, 0));
        _columnsFavModel.put("p_latitude", new TableInfo.Column("p_latitude", "REAL", true, 0));
        _columnsFavModel.put("p_longtitute", new TableInfo.Column("p_longtitute", "REAL", true, 0));
        _columnsFavModel.put("loction_type", new TableInfo.Column("loction_type", "TEXT", true, 0));
        _columnsFavModel.put("location_name", new TableInfo.Column("location_name", "TEXT", true, 0));
        _columnsFavModel.put("label_name", new TableInfo.Column("label_name", "TEXT", true, 0));
        _columnsFavModel.put("android_icon", new TableInfo.Column("android_icon", "TEXT", true, 0));
        _columnsFavModel.put("ios_icon", new TableInfo.Column("ios_icon", "TEXT", true, 0));
        _columnsFavModel.put("type", new TableInfo.Column("type", "TEXT", true, 2));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavModel = new TableInfo("favModel", _columnsFavModel, _foreignKeysFavModel, _indicesFavModel);
        final TableInfo _existingFavModel = TableInfo.read(_db, "favModel");
        if (! _infoFavModel.equals(_existingFavModel)) {
          throw new IllegalStateException("Migration didn't properly handle favModel(com.taximobility.bookingmodule.LocationData).\n"
                  + " Expected:\n" + _infoFavModel + "\n"
                  + " Found:\n" + _existingFavModel);
        }
      }
    }, "e71a876db2c420f48a29ad9a0ac31854", "f43081470c5d89d2189564afe48b438d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "favModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favModel`");
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
  public PlacesDao getPlaceDao() {
    if (_placesDao != null) {
      return _placesDao;
    } else {
      synchronized(this) {
        if(_placesDao == null) {
          _placesDao = new PlacesDao_Impl(this);
        }
        return _placesDao;
      }
    }
  }
}
