package com.moovex.driver.data.apiData;

import java.util.ArrayList;
import java.util.List;

public class DriverUpcomingResponse {

    public String message;
    public Detail detail;
    public Integer status;

    public class Detail {
        public List<PastBooking> pending_booking = new ArrayList<>();
        public List<PastBooking> past_booking = new ArrayList<>();
    }

    public class PastBooking {
        public String passengers_log_id;
        public String pickup_location;
        public String drop_location;
        public String pickup_time;
        public String pickup_date;
        public String pickup_time_oly;
        public String travel_status;
        public String trip_id;
        public String passenger_name;
        public String driver_id;
        public String drop_time;
        public String model_name;
        public String profile_image;
        public String fare, map_image, payment_type, amt;
        public String corporate_booking;

        public String schedule;
        public String passenger_phone;
        public String time;
        public String away;
        public String passenger_country_code;
        public String pickup_latitude;
        public String pickup_longitude;
        public String drop_latitude;
        public String drop_longitude;
        public String distance_fare_km;
        public String distance;
        public String approx_fare;
        public String approx_distance;
    }
}