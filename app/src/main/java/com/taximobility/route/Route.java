package com.taximobility.route;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import androidx.fragment.app.FragmentActivity;
import android.view.animation.LinearInterpolator;

import com.taximobility.R;
import com.taximobility.interfaces.DistanceMatrixInterface;
import com.taximobility.roomDB.GoogleMapModel;
import com.taximobility.roomDB.MapLoggerRepository;
import com.taximobility.service.CoreClient;
import com.taximobility.service.RetrofitCallbackClass;
import com.taximobility.util.AppController;
import com.taximobility.util.SessionSave;
import com.taximobility.util.Systems;
import com.taximobility.util.TaxiUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.taximobility.util.ConstantsKt.IS_BUISNESS_KEY;
import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * Draw the route to the map object .
 * Routes are drawn with attributes according to the constructor its triggered.
 */
public class Route {
    private static final String TAG = "DirectionsActivity";
    public Context mContext;
    protected MapLoggerRepository mRepository;
    GoogleMap mMap;
    DistanceMatrixInterface matrixInterface;
    StringBuilder way_point = new StringBuilder();
    String overViewPolyLine = "";
    private Polyline blackPolyLine, greyPolyLine;
    private List<LatLng> listLatLng;
    private Double p_lat, p_lng, d_lat, d_lng;
    private ArrayList<LatLng> wayPoints = new ArrayList<>();
    Animator.AnimatorListener polyLineAnimationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {

            List<LatLng> blackLatLng = blackPolyLine.getPoints();
            List<LatLng> greyLatLng = greyPolyLine.getPoints();

            greyLatLng.clear();
            greyLatLng.addAll(blackLatLng);
            blackLatLng.clear();

            blackPolyLine.setPoints(blackLatLng);
            greyPolyLine.setPoints(greyLatLng);

            blackPolyLine.setZIndex(2);

            drawMarker();
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {


        }
    };
    private int requestedType = 0;
    private int lineColor;

    public Route(DistanceMatrixInterface matrixInterface) {
        this.matrixInterface = matrixInterface;
    }

    /**
     * Entry point to draw route
     *
     * @param map
     * @param mcontext
     * @param source
     * @param destination
     */
    public void setUpPolyLineWithColor(final GoogleMap map, final FragmentActivity mcontext, final LatLng source, final LatLng destination, ArrayList<LatLng> points, int lineColor) {
        this.mMap = map;
        this.mContext = mcontext;
        requestedType = 1;
        this.lineColor = lineColor;
        mRepository = new MapLoggerRepository(mContext);

        if (SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mcontext, false))
            if (source != null && destination != null) {
                new GetGoogleRouteLog(source, destination, points).execute();
            }

    }

    /**
     * Entry point to draw route
     *
     * @param map
     * @param mcontext
     * @param source
     * @param destination
     */
    public void setUpPolyLine(final GoogleMap map, final FragmentActivity mcontext, final LatLng source, final LatLng destination, ArrayList<LatLng> points) {
        this.mMap = map;
        this.mContext = mcontext;
        requestedType = 1;
        this.lineColor = 0;
        mRepository = new MapLoggerRepository(mContext);

        Systems.out.println("routee setUpPolyLine " + SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mcontext, false));
        if (SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mcontext, false))
            if (source != null && destination != null) {
                new GetGoogleRouteLog(source, destination, points).execute();
            }

    }

    public void getApproximateDistance(final FragmentActivity mcontext, final LatLng source, final LatLng destination, ArrayList<LatLng> points) {
        this.mContext = mcontext;
        requestedType = 0;
        this.mMap = null;
        mRepository = new MapLoggerRepository(mContext);

        Systems.out.println("routee getApproximateDistance " + SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mcontext, false));

        if (SessionSave.getSession(TaxiUtil.isNeedtoDrawRoute, mcontext, false))
            if (source != null && destination != null) {
                new GetGoogleRouteLog(source, destination, points).execute();
            }
    }

    private void getRouteFromGoogle(double P_latitude, double P_longitude, double D_latitude, double D_longitude, ArrayList<LatLng> wayPoints) {
        String wayPointsUrl = makeDirectionUrl(P_latitude, P_longitude, D_latitude, D_longitude, wayPoints);

//        CoreClient polyline = new ServiceGenerator(mContext, "https://maps.googleapis.com/maps/api/directions/", true).createService(CoreClient.class);

        CoreClient polyline = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl();
        Call<ResponseBody> coreResponse = polyline.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", p_lat + "," + p_lng, d_lat + "," + d_lng, wayPointsUrl, SessionSave.getSession(TaxiUtil.GOOGLE_KEY, mContext));

        coreResponse.enqueue(new RetrofitCallbackClass<ResponseBody>(mContext, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                String data = null;
                if (response.isSuccessful() && response.body() != null) {
                    try {

                        JSONObject gson = new JSONObject(response.body().string());
                        if (!gson.getString("status").equalsIgnoreCase("OK")) {
                            if (matrixInterface != null)
                                matrixInterface.onDistanceCalled( null, 3, requestedType);
//                            new GetMapboxRouteLog(new LatLng(P_latitude, P_longitude), new LatLng(D_latitude, D_longitude), wayPoints).execute();
                        } else {
                            saveGoogleLog(P_latitude + "," + P_longitude + D_latitude + "," + D_longitude, gson.toString());
                            drawRoutePolyline(parsePolylineFromPoints(gson));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (matrixInterface != null)
                        matrixInterface.onDistanceCalled( null, 3, requestedType);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (matrixInterface != null)
                    matrixInterface.onDistanceCalled(null, 3, requestedType);
            }
        }));

    }

    /**
     * Get a list of latlng from polyline by decode
     *
     * @param jObject
     * @return
     */
    private List<LatLng> parsePolylineFromPoints(JSONObject jObject) {
        JSONArray jRoutes = null;
        JSONObject jOverviewPoly = null;

        List path = new ArrayList<LatLng>();

        try {
            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            jOverviewPoly = ((JSONObject) jRoutes.get(0)).getJSONObject("overview_polyline");
            overViewPolyLine = jOverviewPoly.getString("points");

            path = decodePoly(overViewPolyLine);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return path;
    }

    public void drawRouteFromPolyline(GoogleMap map, String polyline, ArrayList<LatLng> points) {
        this.mMap = map;
        wayPoints = new ArrayList<>();
        overViewPolyLine = polyline;
        wayPoints = points;
        this.lineColor = 0;
        drawRoutePolyline(decodePoly(overViewPolyLine));
    }


    public String getOverViewPolyLine() {
        return overViewPolyLine;
    }


    private List<LatLng> decodePoly(String encodedPath) {
        int len = encodedPath.length();
        List<LatLng> path = new ArrayList();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;

            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 31);

            lat += (result & 1) != 0 ? ~(result >> 1) : result >> 1;
            result = 1;
            shift = 0;

            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 31);

            lng += (result & 1) != 0 ? ~(result >> 1) : result >> 1;
            path.add(new LatLng((double) lat * 1.0E-5D, (double) lng * 1.0E-5D));
        }

        return path;
    }


    private String makeDirectionUrl(double p_latitude, double p_longitude, double d_latitude, double d_longitude, ArrayList<LatLng> points) {
        way_point = new StringBuilder();
        if (points != null && points.size() > 0) {
            if (points.size() > 2) {
                p_lat = points.get(0).latitude;
                p_lng = points.get(0).longitude;
                d_lat = points.get(points.size() - 1).latitude;
                d_lng = points.get(points.size() - 1).longitude;
                for (int i = 1; i < points.size() - 1; i++) {
                    way_point.append(points.get(i).latitude);
                    way_point.append(',');
                    way_point.append(points.get(i).longitude);
                    if (i != (points.size() - 2)) {
                        way_point.append("|");
                    }
                }
            } else {
                p_lat = points.get(0).latitude;
                p_lng = points.get(0).longitude;
                d_lat = points.get(points.size() - 1).latitude;
                d_lng = points.get(points.size() - 1).longitude;
            }
        } else {
            p_lat = p_latitude;
            p_lng = p_longitude;
            d_lat = d_latitude;
            d_lng = d_longitude;
        }
        return way_point.toString();
    }


    private void drawRoutePolyline(List<LatLng> result) {
//        if (IS_HOME_PAGE) {
//            if (booking_state != HomePage.BOOKINGSTATE.STATE_THREE) {
//                ArrayList<LatLng> points = null;
//                PolylineOptions lineOptions = new PolylineOptions();
//                listLatLng = new ArrayList<>();
//                this.listLatLng = result;
//                if (mMap != null) {
//                    lineOptions.width(5);
//                    lineOptions.color(Color.GRAY);
//                    lineOptions.startCap(new SquareCap());
//                    lineOptions.endCap(new SquareCap());
//                    lineOptions.jointType(ROUND);
//                    blackPolyLine = mMap.addPolyline(lineOptions);
//
//                    PolylineOptions greyOptions = new PolylineOptions();
//                    greyOptions.width(5);
//                    greyOptions.color(Color.BLACK);
//                    greyOptions.startCap(new SquareCap());
//                    greyOptions.endCap(new SquareCap());
//                    greyOptions.jointType(ROUND);
//                    greyPolyLine = mMap.addPolyline(greyOptions);
//
//                    animatePolyLine(1000);
//                }
//            }
//        } else {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = new PolylineOptions();
        listLatLng = new ArrayList<>();
        this.listLatLng = result;
        if (mMap != null) {
            lineOptions.width(7);
            lineOptions.color(Color.GRAY);
            lineOptions.startCap(new SquareCap());
            lineOptions.endCap(new SquareCap());
            lineOptions.jointType(ROUND);
            blackPolyLine = mMap.addPolyline(lineOptions);

            PolylineOptions greyOptions = new PolylineOptions();
            if (lineColor != 0) {
                greyOptions.color(lineColor);
                greyOptions.width(12);
            } else {
                greyOptions.color(Color.BLACK);
                greyOptions.width(7);
            }
            greyOptions.startCap(new SquareCap());
            greyOptions.endCap(new SquareCap());
            greyOptions.jointType(ROUND);
            greyPolyLine = mMap.addPolyline(greyOptions);

            animatePolyLine(1000);
        }
//        }
    }


    private void animatePolyLine(long duration) {

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                List<LatLng> latLngList = blackPolyLine.getPoints();
                int initialPointSize = latLngList.size();
                int animatedValue = (int) animator.getAnimatedValue();
                int newPoints = (animatedValue * listLatLng.size()) / 100;

                if (initialPointSize < newPoints) {
                    latLngList.addAll(listLatLng.subList(initialPointSize, newPoints));
                    blackPolyLine.setPoints(latLngList);
                }
            }
        });

        animator.addListener(polyLineAnimationListener);
        animator.start();

    }

    private void drawMarker() {
        if (wayPoints != null && wayPoints.size() > 2) {
            for (int i = 1; i < wayPoints.size() - 1; i++) {
                mMap.addMarker(new MarkerOptions()
                        .position(wayPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.drop_dot)));
            }
        }
    }

    private void saveGoogleLog(String latLngKey, String routeResult) {
        double time = 0.0, distance = 0.0;
        try {
            JSONArray legsArray = new JSONObject(routeResult).getJSONArray("routes").getJSONObject(0).getJSONArray("legs");
            if (legsArray != null) {
                for (int i = 0; i < legsArray.length(); i++) {
                    JSONObject distanceObject = legsArray.getJSONObject(i).getJSONObject("distance");
                    String distanceString = distanceObject.getString("value");
                    if (distanceString != null && !distanceString.isEmpty()) {
                        distance += Double.parseDouble(distanceString);
                    }
                    JSONObject timeObject = legsArray.getJSONObject(i).getJSONObject("duration");
                    String timeString = timeObject.getString("value");
                    if (timeString != null && !timeString.isEmpty()) {
                        time += Double.parseDouble(timeString);
                    }
                }
            }


            double approx_travel_time = time / 60;
            double approx_travel_dist = distance / 1000;

            if (SessionSave.getSession("Metric", mContext).trim().equalsIgnoreCase("MILES") && SessionSave.getSession(IS_BUISNESS_KEY, mContext, true)) {
                approx_travel_dist = approx_travel_dist / 1.60934;
            }

            GoogleMapModel model = new GoogleMapModel();
            model.fromTo = latLngKey;
            model.time = approx_travel_time;
            model.distance = approx_travel_dist;
            model.routeResult = routeResult;
            model.distanceResult = "";

            mRepository.insertGoogleLog(model);


            if (matrixInterface != null)
                matrixInterface.onDistanceCalled(approx_travel_time, 3, requestedType);
        } catch (JSONException e) {
            e.printStackTrace();
            if (matrixInterface != null)
                matrixInterface.onDistanceCalled(time, 3, requestedType);
        }
    }


    /**
     * Check whether available in DB
     */
    private class GetGoogleRouteLog extends AsyncTask<Void, Void, GoogleMapModel> {
        private double P_latitude, P_longitude, D_latitude, D_longitude;
        private String from = "";
        private String to = "";


        GetGoogleRouteLog(LatLng source, LatLng destination, ArrayList<LatLng> points) {
            this.P_latitude = source.latitude;
            this.P_longitude = source.longitude;
            this.D_latitude = destination.latitude;
            this.D_longitude = destination.longitude;
            wayPoints = points;
            from = P_latitude + "," + P_longitude;
            to = D_latitude + "," + D_longitude;
        }

        @Override
        protected GoogleMapModel doInBackground(Void... voids) {
            return mRepository.getGoogleModel(from.trim() + to.trim());
        }

        @Override
        protected void onPostExecute(GoogleMapModel model) {
            super.onPostExecute(model);
            if (model != null && !model.routeResult.equals("")) {

                try {
                    parsePolylineFromPoints(new JSONObject(model.routeResult));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (matrixInterface != null)
                    matrixInterface.onDistanceCalled(model.time,3, requestedType);
            } else {
                getRouteFromGoogle(P_latitude, P_longitude, D_latitude, D_longitude, wayPoints);
            }
        }
    }
}