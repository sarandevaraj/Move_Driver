package com.taximobility.data.apiData;

import com.taximobility.driver.data.apiData.AddonsData;
import com.taximobility.locationSearch.PlacesData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 2/11/16.
 * Contains Trip detail response api
 */
public class TripDetailResponse {
    public String message;
    public Detail detail;
    public Integer status;
    public String site_currency;

    public class Detail {
        public String taxi_min_speed;
        public String trip_id;
        public String trip_type;
        public String current_location;
        public Double pickup_latitude;
        public Double pickup_longitude;
        public String drop_location;
        public String drop_latitude;
        public String drop_longitude;
        public String drop_time;
        public String pickup_date_time;
        public String pickup_time;
        public String booking_time;
        public String time_to_reach_passen;
        public String no_passengers;
        public String rating;
        public String notes;
        public String driver_name;
        public String driver_id;
        public String taxi_id;
        public String taxi_number;
        public String driver_phone;
        public String passenger_phone;
        public String passenger_name;
        public String travel_status;
        public String bookedby;
        public String waiting_time;
        public String waiting_fare;
        public String fare_calculation_type;
        public String distance;
        public String subtotal, tax_fare;
        public String pending_cancel_amount;
        public String actual_distance;
        public String metric;
        public String amt;
        public String used_wallet_amount, cancellation_fee;
        public String job_ref;
        public String payment_type;
        public String taxi_speed;
        public Integer isSplit_fare;
        public String credit_card_status;
        public Boolean is_primary;
        public String trip_duration;
        public String driver_image;
        public String passenger_image;
        public String driver_longtitute;
        public String driver_latitute;
        public String driver_status, nightfare, eveningfare;
        public String driver_rating, distance_fare, distance_fare_metric, tax_percentage, trip_minutes, fare_per_minute, minutes_fare, waiting_fare_minutes, actual_paid_amount,given_tips, promocode_fare, payment_type_label, map_image;
        public List<SplitFareDetail> splitFareDetails = new ArrayList<SplitFareDetail>();
        public String base_fare, paid_amount, promotion, tax;
        public String additional_time_fare;
        public String additional_distance_fare;

        public int min_distance_status;
        public String new_base_fare;
        public String new_distance_fare;

        public String service_id ;
        public String product_name  ;
        public String product_weight  ;
        public String product_size ;
        public String delivery_person_name , delivery_phone_number , delivery_date_time ;
        public String delivery_notes ;

        public String corporate_booking;
        public String total_preference_fare;
        public ArrayList<PlacesData> stops;
        public ArrayList<AddonsData> preferences = new ArrayList<AddonsData>();
        public String delivery_fare;

        public class SplitFareDetail {

            public String firstname;
            public String profile_image;
            public String approve_status;
            public Integer fare_percentage;
            public Double used_wallet_amount;
            public Double splitted_fare;

        }
    }
}