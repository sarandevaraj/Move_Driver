package com.moovex.driver.earningchart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 12/11/16.
 */
public class DriverEarningresponse {
    public String message;
    public List<TodayEarning> today_earnings = new ArrayList<>();
    public List<TotalEarning> total_earnings = new ArrayList<>();
    public List<WeeklyEarning> weekly_earnings = new ArrayList<>();
    int status;
    public List<WithDrawArray> withdraw_array;

    public static class TodayEarning {
        public String total_trips;
        public String total_amount;
    }

    public static class TotalEarning {
        public String total_trips;
        public String total_amount;
        public String total_distance;
        public String total_duration;
        public String total_shift_duration;
    }

    public static class WeeklyEarning {
        public List<Float> trip_amount = new ArrayList<>();
        public List<String> day_list = new ArrayList<>();
        public String date_text, this_week_earnings;
    }

    public static class WithDrawArray {
        public String driver_wallet_pending_amount;
        public String trip_amount;
        public String trip_pending_amount;
        public String total_amount;
        public String driver_wallet_amount;
        public String driver_trip_wallet_amount;
    }
}
