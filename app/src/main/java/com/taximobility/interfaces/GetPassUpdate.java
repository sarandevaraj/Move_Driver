package com.taximobility.interfaces;

import org.json.JSONObject;

public interface GetPassUpdate {
    void updateGetPassUpdate(int tripid, String message);
    void updateTips(JSONObject jsonObject);
}
