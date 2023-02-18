package com.movedriverdriver.roomDB;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.movedriverdriver.driver.utils.DriverSystems;
import com.movedriverdriver.util.TaxiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by developer on 10/5/18.
 */

public class LoggerRepository {
    //    public final LiveData<PagedList<LoggerModel>> loggerList;
    private final LoggerDao mWordDao;
    private final LiveData<List<LoggerModel>> mAllWords;

    public LoggerRepository(Context application) {
        LoggerDatabase db = LoggerDatabase.getDatabase(application);
        mWordDao = db.loggerDao();
        mAllWords = mWordDao.loadAllUsers();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<LoggerModel>> getAllWords() {
        return mAllWords;
    }

    public LiveData<List<String>> loadDistinctApi() {
        return mWordDao.loadDistinctApi();
    }

    //    public MutableLiveData<List<LoggerModel>> getAllUser() {
//        return mAllWord;
//    }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(LoggerModel word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void delete(LoggerModel word) {
        new deleteAsyncTask(mWordDao).execute(word);
    }

    public void update(String firstName, String lastName, int id) {
        Bundle bundle = new Bundle();
        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putInt("id", id);
        new updateAsyncTask(mWordDao).execute(bundle);
    }

    public LiveData<List<LoggerModel>> getAllUser(String query) {
        return mWordDao.loadAllUser(query);
    }

    public LiveData<Integer> getCount(String query) {
        return mWordDao.getCount(query);
    }

    /**
     * Method to create log of Places APIs
     *
     * @param data     - Response as string from server
     * @param request  - Request as string to server
     * @param status   - Status we got from google place auto complete API
     * @param apiType  Type of places APIs
     * @param reqsTime - Time at request send server
     * @param respTime - Time at server responded
     */
    public void createApiLog(String data, String request, String status, String apiType, long reqsTime, long respTime) {
        LoggerModel loggerModel = new LoggerModel();

        loggerModel.apiType = apiType;
        loggerModel.time = getDate(new Date().getTime());
        loggerModel.requested_time = getDate(reqsTime);
        loggerModel.responded_time = getDate(respTime);
        loggerModel.request = request;
        loggerModel.response = data;
        loggerModel.url = status;

        new insertAsyncTask(mWordDao).execute(loggerModel);
    }

    /**
     * Method to create log of location history update through socket
     *
     * @param data        - Response as string from server
     * @param requestJson - Request as string from server
     * @param apiType     - Type of request
     * @param url         - Url of api call
     * @param respTime    - Time at server responded
     */
    public void createApiLog(String data, String requestJson, String url, String apiType, long respTime) {
        String request = "";
        long reqsTime = 0;
        DriverSystems.out.println("JsonKeyyy " + data);
        if (requestJson != null) {
            String[] reqJson = requestJson.split("___");
            request = reqJson[0];
            reqsTime = Long.parseLong(reqJson[1]);
        }

        String responseStatus;
        try {
            JSONObject response = new JSONObject("" + data);
            if (response.has("STATUS")) {
                responseStatus = response.getString("STATUS");
            } else if (response.has("status")) {
                responseStatus = response.getString("status");
            } else {
                String res = TaxiUtil.tostring(response);
                if (res.length() < 500) responseStatus = res;
                else responseStatus = res.substring(0, 500);
            }

            LoggerModel loggerModel = new LoggerModel();
            loggerModel.apiType = apiType;
            loggerModel.time = getDate(new Date().getTime());
            loggerModel.requested_time = getDate(reqsTime);
            loggerModel.responded_time = getDate(respTime);
            loggerModel.request = request;
            loggerModel.response = responseStatus;
            loggerModel.url = url;

            new insertAsyncTask(mWordDao).execute(loggerModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to create log of common APIs and includes google APIs
     *
     * @param data     - Response as string from server
     * @param request  - Request as string from server
     * @param url      - Url of api call
     * @param reqTime  - Time at request send server
     * @param respTime - Time at server responded
     */
    public void createApiLog(String data, String request, String url, long reqTime, long respTime) {
        String responseStatus;
        try {
            JSONObject response = new JSONObject("" + data);
            if (response.has("STATUS")) {
                responseStatus = response.getString("STATUS");
            } else if (response.has("status")) {
                responseStatus = response.getString("status");
            } else {
                if (TaxiUtil.tostring(response).length() < 100)
                    responseStatus = TaxiUtil.tostring(response);
                else responseStatus = TaxiUtil.tostring(response).substring(0, 500);
            }
            Uri path = Uri.parse(url);

            LoggerModel loggerModel = new LoggerModel();
            if (path.getBooleanQueryParameter("type", false))
                loggerModel.apiType = path.getQueryParameter("type");
            else loggerModel.apiType = path.getPath();

            loggerModel.time = getDate(new Date().getTime());
            loggerModel.requested_time = getDate(reqTime);
            loggerModel.responded_time = getDate(respTime);
            loggerModel.request = request;
            loggerModel.response = responseStatus;
            loggerModel.url = url;

            new insertAsyncTask(mWordDao).execute(loggerModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = android.text.format.DateFormat.format("MM/dd/yyyy hh:mm:ss a", cal).toString();
        return date;
    }

    public LiveData<PagedList<LoggerModel>> logsByApiType(String apiType) {

        PagedList.Config config = new PagedList.Config.Builder().setEnablePlaceholders(true).setPageSize(5)
                /*.setInitialLoadSizeHint(3) */ // default: page size * 3
                .build();

        return new LivePagedListBuilder<>(mWordDao.logsByApiType(apiType), config).build();
    }

    public LiveData<List<LoggerModel>> logsByQuery(String url) {

        return mWordDao.loadQuery(url);
    }

    private static class insertAsyncTask extends AsyncTask<LoggerModel, Void, Void> {

        private final LoggerDao mLoggerDao;

        insertAsyncTask(LoggerDao dao) {
            mLoggerDao = dao;
        }

        @Override
        protected Void doInBackground(final LoggerModel... params) {
            mLoggerDao.insertLog(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<LoggerModel, Void, Void> {

        private final LoggerDao mLoggerDao;

        deleteAsyncTask(LoggerDao dao) {
            mLoggerDao = dao;
        }

        @Override
        protected Void doInBackground(final LoggerModel... params) {
            mLoggerDao.deleteLog(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Bundle, Void, Void> {

        private final LoggerDao mLoggerDao;

        updateAsyncTask(LoggerDao dao) {
            mLoggerDao = dao;
        }

        @Override
        protected Void doInBackground(final Bundle... params) {
            String fName = params[0].getString("firstName", "sssss");
            String lName = params[0].getString("lastName", "kljjn");
            int id = params[0].getInt("id");
            mLoggerDao.update(fName, lName, id);
            return null;
        }
    }
}
