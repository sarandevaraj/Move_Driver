package com.taximobility.driver.errorLog;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.taximobility.driver.data.DriverModelDriverInfo;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@SuppressWarnings("unchecked")
public final class DriverErrorLogDao_Impl implements DriverErrorLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDriverApiErrorModel;

  private final DriverConverter __driverConverter = new DriverConverter();

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllApiErrorLogs;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSendStatus;

  public DriverErrorLogDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDriverApiErrorModel = new EntityInsertionAdapter<DriverApiErrorModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `apiErrorModel`(`ids`,`timeStamp`,`apiCase`,`error`,`driverDataDriver`,`inputParams`,`classContext`,`sendStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DriverApiErrorModel value) {
        stmt.bindLong(1, value.getIds());
        if (value.getTimeStamp() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTimeStamp());
        }
        if (value.getApiCase() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getApiCase());
        }
        if (value.getError() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getError());
        }
        final String _tmp;
        _tmp = __driverConverter.driverInfotoString(value.getDriverDataDriver());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __driverConverter.inputParamsToString(value.getInputParams());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        if (value.getClassContext() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getClassContext());
        }
        stmt.bindLong(8, value.getSendStatus());
      }
    };
    this.__preparedStmtOfDeleteAllApiErrorLogs = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM apiErrorModel WHERE timeStamp < ? ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSendStatus = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE apiErrorModel set sendStatus = ? WHERE ids = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertApiErrorLog(DriverApiErrorModel... modelDrivers) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfDriverApiErrorModel.insert(modelDrivers);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllApiErrorLogs(String date) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllApiErrorLogs.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (date == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, date);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllApiErrorLogs.release(_stmt);
    }
  }

  @Override
  public void updateSendStatus(int status, int id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSendStatus.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, status);
      _argIndex = 2;
      _stmt.bindLong(_argIndex, id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateSendStatus.release(_stmt);
    }
  }

  @Override
  public List<DriverApiErrorModel> getAllApiErrorLogs() {
    final String _sql = "SELECT * FROM apiErrorModel WHERE sendStatus = 0 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIds = _cursor.getColumnIndexOrThrow("ids");
      final int _cursorIndexOfTimeStamp = _cursor.getColumnIndexOrThrow("timeStamp");
      final int _cursorIndexOfApiCase = _cursor.getColumnIndexOrThrow("apiCase");
      final int _cursorIndexOfError = _cursor.getColumnIndexOrThrow("error");
      final int _cursorIndexOfDriverDataDriver = _cursor.getColumnIndexOrThrow("driverDataDriver");
      final int _cursorIndexOfInputParams = _cursor.getColumnIndexOrThrow("inputParams");
      final int _cursorIndexOfClassContext = _cursor.getColumnIndexOrThrow("classContext");
      final int _cursorIndexOfSendStatus = _cursor.getColumnIndexOrThrow("sendStatus");
      final List<DriverApiErrorModel> _result = new ArrayList<DriverApiErrorModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final DriverApiErrorModel _item;
        final int _tmpIds;
        _tmpIds = _cursor.getInt(_cursorIndexOfIds);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        final String _tmpApiCase;
        _tmpApiCase = _cursor.getString(_cursorIndexOfApiCase);
        final String _tmpError;
        _tmpError = _cursor.getString(_cursorIndexOfError);
        final DriverModelDriverInfo _tmpDriverDataDriver;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfDriverDataDriver);
        _tmpDriverDataDriver = __driverConverter.stringToDriverInfo(_tmp);
        final JSONObject _tmpInputParams;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfInputParams);
        _tmpInputParams = __driverConverter.inputParamsToJson(_tmp_1);
        final String _tmpClassContext;
        _tmpClassContext = _cursor.getString(_cursorIndexOfClassContext);
        final int _tmpSendStatus;
        _tmpSendStatus = _cursor.getInt(_cursorIndexOfSendStatus);
        _item = new DriverApiErrorModel(_tmpIds,_tmpTimeStamp,_tmpApiCase,_tmpError,_tmpDriverDataDriver,_tmpInputParams,_tmpClassContext,_tmpSendStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCount(String error, String currTime) {
    final String _sql = "SELECT COUNT(timeStamp) FROM apiErrorModel WHERE error = ? and timeStamp = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (error == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, error);
    }
    _argIndex = 2;
    if (currTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, currTime);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
