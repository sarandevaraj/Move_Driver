package com.taximobility.data.apiData;

import java.util.ArrayList;

/**
 * Created by developer on 18/8/17.
 */

public class PreferencesDataList {
    public class PreferencesData {
        public String preference_fare;
        public String preference_name;
        public String preference_id;
        public boolean isSelected = false;


    }

    public ArrayList<PreferencesData> getSelectedPreferencesData() {
        ArrayList<PreferencesData> preferenceList = new ArrayList<>();
        for (PreferencesData preferencesData : preferencesDatas) {
            if (preferencesData.isSelected) {
                preferenceList.add(preferencesData);
            }
        }
        return preferenceList;
    }

    public ArrayList<PreferencesData> preferencesDatas = new ArrayList<>();
}
