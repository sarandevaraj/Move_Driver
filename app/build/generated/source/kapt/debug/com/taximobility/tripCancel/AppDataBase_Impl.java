package com.taximobility.tripCancel;

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
public final class AppDataBase_Impl extends AppDataBase {
  private volatile CreditCardDao _creditCardDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `CardDetails` (`name` TEXT NOT NULL, `id` TEXT NOT NULL, `type` TEXT NOT NULL, `month` TEXT NOT NULL, `year` TEXT NOT NULL, `card` TEXT NOT NULL, `default_card` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"521a6ec38bdc92de7a371da1db519967\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `CardDetails`");
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
        final HashMap<String, TableInfo.Column> _columnsCardDetails = new HashMap<String, TableInfo.Column>(7);
        _columnsCardDetails.put("name", new TableInfo.Column("name", "TEXT", true, 0));
        _columnsCardDetails.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsCardDetails.put("type", new TableInfo.Column("type", "TEXT", true, 0));
        _columnsCardDetails.put("month", new TableInfo.Column("month", "TEXT", true, 0));
        _columnsCardDetails.put("year", new TableInfo.Column("year", "TEXT", true, 0));
        _columnsCardDetails.put("card", new TableInfo.Column("card", "TEXT", true, 0));
        _columnsCardDetails.put("default_card", new TableInfo.Column("default_card", "TEXT", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCardDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCardDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCardDetails = new TableInfo("CardDetails", _columnsCardDetails, _foreignKeysCardDetails, _indicesCardDetails);
        final TableInfo _existingCardDetails = TableInfo.read(_db, "CardDetails");
        if (! _infoCardDetails.equals(_existingCardDetails)) {
          throw new IllegalStateException("Migration didn't properly handle CardDetails(com.taximobility.tripCancel.CreditCardData).\n"
                  + " Expected:\n" + _infoCardDetails + "\n"
                  + " Found:\n" + _existingCardDetails);
        }
      }
    }, "521a6ec38bdc92de7a371da1db519967", "93a6a3f96a64dbaafe95bd9c4f0d71f8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "CardDetails");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `CardDetails`");
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
  public CreditCardDao creditCardDao() {
    if (_creditCardDao != null) {
      return _creditCardDao;
    } else {
      synchronized(this) {
        if(_creditCardDao == null) {
          _creditCardDao = new CreditCardDao_Impl(this);
        }
        return _creditCardDao;
      }
    }
  }
}
