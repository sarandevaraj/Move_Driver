package com.movedriverdriver.roomDB;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by developer on 10/5/18.
 */
@Dao
public interface LoggerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLog(LoggerModel... models);

    @Query("UPDATE loggerModel SET url = :first_name, time= :last_name WHERE id =:id")
    void update(String first_name, String last_name, int id);

    @Delete
    void deleteLog(LoggerModel... models);

    @Query("SELECT * From loggerModel ORDER BY id DESC")
    LiveData<List<LoggerModel>> loadAllUsers();

    @Query("SELECT DISTINCT api_type From loggerModel ORDER BY id ASC")
    LiveData<List<String>> loadDistinctApi();


    @Query("SELECT * From loggerModel WHERE api_type =:query ORDER BY id DESC")
    LiveData<List<LoggerModel>> loadAllUser(String query);

//    @Query("SELECT  COUNT (:query) From loggerModel group by :query")

    @Query("SELECT COUNT(api_type) FROM loggerModel  WHERE api_type =:query ")
    LiveData<Integer> getCount(String query);

    @Query("SELECT * FROM loggerModel WHERE api_type =:apiType ORDER BY id DESC")
    DataSource.Factory<Integer, LoggerModel> logsByApiType(String apiType);

    @Query("SELECT * From loggerModel WHERE url =:url ORDER BY id DESC")
    LiveData<List<LoggerModel>> loadQuery(String url);
}
