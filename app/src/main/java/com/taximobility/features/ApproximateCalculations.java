//package com.taximobility.features;
//
//import android.content.Context;
//
//import com.taximobility.util.SessionSave;
//import com.taximobility.util.Systems;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Locale;
//
///**
// * Created by developer on 5/3/16.
// * Used to calculate the approximate fare amount
// * if distance is less than minimun km then the min fare is set as fare amount
// * else if distance is greater than minimum km and less than below_above_km value fare amount=distance_travelled * below_above_km +base fare
// * else if distance is greater than minimum km and greater than below_above_km value fare amount=distance_travelled * below_above_km +base fare
// */
//public class ApproximateCalculations {
//    public static double aprrox_fare = 0;
//    private static float min_km, min_fare;
//    private static float km_wise_fare;
//    private static double additional_fare_per_km;
//
//
//    public static double approxFare(Context c, double dist, double time) {
//        try {
//            Double fareMulti;
//            float below_above_km;
//
//            String Server_Response = SessionSave.getSession("Server_Response", c);
//
//            final JSONObject json = new JSONObject(Server_Response);
//            if (json.has("fare_details")) {
//                final JSONObject farejson = json.getJSONObject("fare_details");
//                if (farejson.has("min_km")) {
//                    km_wise_fare = Float.parseFloat(farejson.getString("km_wise_fare"));
//                    below_above_km = Float.parseFloat(farejson.getString("below_above_km"));
//                    additional_fare_per_km = Double.parseDouble(farejson.getString("additional_fare_per_km"));
//                    min_km = Float.parseFloat(farejson.getString("min_km"));
//                    min_fare = Float.parseFloat(farejson.getString("min_fare"));
//                    if (farejson.getInt("fare_calculation_type") != 2) {
//                        if (dist > min_km) {
//                            if (km_wise_fare == 2) {
//                                if (dist > below_above_km)
//                                    fareMulti = Double.parseDouble(farejson.getString("above_km"));
//                                else
//                                    fareMulti = Double.parseDouble(farejson.getString("below_km"));
//                                aprrox_fare = Math.round(dist * fareMulti) + Float.parseFloat(farejson.getString("base_fare"));
//                            } else {
//                                double distanceminusmin = dist - min_km;
//                                aprrox_fare = (distanceminusmin * additional_fare_per_km) + min_fare + Float.parseFloat(farejson.getString("base_fare"));
//                            }
//                        } else {
//                            aprrox_fare = Double.valueOf(min_fare);
//                        }
//                    } else {
//                        aprrox_fare = 0;
//                    }
//
//
//                    if (farejson.getInt("fare_calculation_type") == 3
//                            || farejson.getInt("fare_calculation_type") == 2) {
//                        Double mmm = time * Double.parseDouble(farejson.getString("minutes_fare"));
//                        Systems.out.println("_____" + mmm);
//                        aprrox_fare = aprrox_fare + mmm;
//                    }
//                    aprrox_fare = Double.parseDouble(String.format(Locale.UK, String.valueOf(aprrox_fare)));
//
//                    try {
//                        if (Integer.parseInt(farejson.getString("eveningfare_applicable")) == 1) {
//                            float eveningFare = 0.0f;
//
//                            eveningFare = Float.parseFloat(farejson.getString("evening_fare"));
//
//                            if (eveningFare != 0) {
//                                float eveningFareAmount = (float) (aprrox_fare * (eveningFare / 100));
//                                aprrox_fare = aprrox_fare + eveningFareAmount;
//                            }
//                        }
//                        if (Integer.parseInt(farejson.getString("nightfare_applicable")) == 1) {
//                            float nightFare = 0.0f;
//                            try {
//                                nightFare = Float.parseFloat(farejson.getString("night_fare"));
//                            } catch (NumberFormatException e) {
//                                e.printStackTrace();
//                            }
//                            if (nightFare != 0) {
//                                float nightFareAmount = (float) (aprrox_fare * (nightFare / 100));
//                                aprrox_fare = aprrox_fare + nightFareAmount;
//                            }
//                        }
//
//
//                        float tax = 0.0f;
//                        try {
//                            tax = Float.parseFloat(SessionSave.getSession("tax", c));
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//                        if (tax != 0) {
//                            float taxAmount = (float) (aprrox_fare * (tax / 100));
//                            aprrox_fare = aprrox_fare + taxAmount;
//                            Systems.out.println("fare_" + dist + "dd" + aprrox_fare + "__ " + taxAmount + "tax" + tax);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    return 0;
//                }
//            } else {
//                return 0;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return aprrox_fare;
//    }
//
//
//}
