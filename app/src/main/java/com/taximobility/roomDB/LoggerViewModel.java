package com.taximobility.roomDB;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

/**
 * Created by developer on 10/5/18.
 */

public class LoggerViewModel extends AndroidViewModel {
    private LoggerRepository mRepository;
    private LiveData<List<LoggerModel>> mAllUsers;
//    public final LiveData<PagedList<LoggerModel>> loggerList;
    public LoggerViewModel(Application application) {
        super(application);
        mRepository = new LoggerRepository(application);
        mAllUsers = mRepository.getAllWords();
    }

    public LiveData<List<LoggerModel>> getAllUsers() {
        return mAllUsers;
    }
//    public MutableLiveData<List<LoggerModel>> getAllUser() {
//        return mAllUser;
//    }

    public void insert(LoggerModel mLogger) {
        mRepository.insert(mLogger);
    }

    public void delete(LoggerModel mLogger) {
        mRepository.delete(mLogger);
    }

    public void update(String firstName, String lastName, int id) {
        mRepository.update(firstName, lastName, id);
    }

    public LiveData<List<LoggerModel>> getAllUser(String query) {
        return mRepository.getAllUser(query);
    }
    public LiveData<List<String>> loadDistinctApi() {
        return mRepository.loadDistinctApi();
    }


    public LiveData<Integer> getCount(String query) {
        return mRepository.getCount(query);
    }

    public LiveData<PagedList<LoggerModel>> logsByApiType(String apiType) {
        return mRepository.logsByApiType(apiType);
    }

    public LiveData<List<LoggerModel>> logsByQuery(String url) {
        return mRepository.logsByQuery(url);
    }
}
