package com.moovex.driver.interfaces;

//This interface used to get back the API call result and process the response as per the page need.
public interface DriverAPIResult {
    void getResult(boolean isSuccess, String result);
}
