package com.movedriver.driver.route;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.movedriver.R;
import com.movedriver.driver.data.DriverCommonData;
import com.movedriver.driver.interfaces.DriverDistanceMatrixInterface;
import com.movedriver.driver.roomDB.GoogleMapModel;
import com.movedriver.driver.roomDB.MapLoggerRepository;
import com.movedriver.driver.service.DriverCoreClient;
import com.movedriver.driver.service.DriverRetrofitCallbackClass;
import com.movedriver.driver.utils.DriverCToast;
import com.movedriver.driver.utils.DriverSessionSave;
import com.movedriver.driver.utils.DriverSystems;
import com.movedriver.util.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.google.android.gms.maps.model.JointType.ROUND;
import static com.movedriver.driver.utils.DriverNC.getResources;

/**
 * Draw the route to the map object .
 * Routes are drawn with attributes according to the constructor its triggered.
 */
public class DriverRoute {
    private static final String TAG = "DirectionsActivity";
    public static Polyline line;
    public Context mContext;
    protected MapLoggerRepository mRepository;
    GoogleMap mMap;
    DriverDistanceMatrixInterface matrixInterface;
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

    public DriverRoute(DriverDistanceMatrixInterface matrixInterface) {
        this.matrixInterface = matrixInterface;
    }

    public DriverRoute() {

    }

    /**
     * Entry point to draw route
     */
    public void setUpPolyLine(final GoogleMap map, final FragmentActivity mcontext, final LatLng source, final LatLng destination, ArrayList<LatLng> points) {
        this.mMap = map;
        this.mContext = mcontext;
        this.wayPoints = points;
        requestedType = 1;
        mRepository = new MapLoggerRepository(mContext);

        if (DriverSessionSave.getSession(DriverCommonData.isNeedtoDrawRoute, mcontext, false))
            if (source != null && destination != null) {
                new GetGoogleRouteLog(source, destination, points).execute();
            }
    }

    public void getRouteFromGoogle(double P_latitude, double P_longitude, double D_latitude, double D_longitude, ArrayList<LatLng> wayPoints) {
        String wayPointsUrl = makeDirectionUrl(P_latitude, P_longitude, D_latitude, D_longitude, wayPoints);

        DriverCoreClient client = AppController.getInstance().getApiManagerWithoutEncryptBaseUrl_driver();

        //     Call<ResponseBody> coreResponse = client.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", p_lat + "," + p_lng, d_lat + "," + d_lng, wayPointsUrl, SessionSave.getSession(CommonData.GOOGLE_KEY, mContext));
        Call<ResponseBody> coreResponse = client.getPolylineDataWithWayPoint("https://maps.googleapis.com/maps/api/directions/json", p_lat + "," + p_lng, d_lat + "," + d_lng, wayPointsUrl, getResources().getString(R.string.googleID));

        coreResponse.enqueue(new DriverRetrofitCallbackClass<>(mContext, new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                String data = null;
                if (response.isSuccessful()) {
                    try {

                        JSONObject gson = null;
                        if (response.body() != null) {
                            gson = new JSONObject(response.body().string());
                        }
                        if (gson != null) {
                            DriverSystems.out.println("routee onResponse " + gson.getString("status") + requestedType);
                            if (!gson.getString("status").equalsIgnoreCase("OK")) {
                                if (gson.has("status") && !gson.getString("status").equalsIgnoreCase("OK") && gson.has("error_message")) {
                                    String msg = gson.getString("error_message");
                                    DriverCToast.ShowToast(mContext, msg);
                                }
                            } else {
                                saveGoogleLog(P_latitude + "," + P_longitude + D_latitude + "," + D_longitude, gson.toString());
                                drawRoutePolyline(parsePolylineFromPoints(gson));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        }));
    }

    /**
     * Get a list of latlng from polyline by decode
     */
    public List<LatLng> parsePolylineFromPoints(JSONObject jObject) {
        JSONArray jRoutes;
        JSONObject jOverviewPoly;

        List path = new ArrayList<LatLng>();

        try {
            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            jOverviewPoly = ((JSONObject) jRoutes.get(0)).getJSONObject("overview_polyline");
            overViewPolyLine = jOverviewPoly.getString("points");
            DriverSessionSave.saveSession("overviewpolyline_saved",overViewPolyLine,mContext);
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
        drawRoutePolyline(decodePoly(overViewPolyLine));
    }

    public String getOverViewPolyLine() {
        return overViewPolyLine;
    }

    /**
     * latlng list from points
     */
    public String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st.charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt("" + st.charAt(i + 2) + st.charAt(i + 3) + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
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

    public String makeDirectionUrl(double p_latitude, double p_longitude, double d_latitude, double d_longitude, ArrayList<LatLng> points) {
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

    public String makeDirectionUrl(ArrayList<LatLng> points) {
        way_point = new StringBuilder();

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
        return way_point.toString();
    }

    void drawRoutePolyline(List<LatLng> result) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = new PolylineOptions();
        listLatLng = new ArrayList<>();
        this.listLatLng = result;
        if (mMap != null && lineOptions != null) {
            lineOptions.width(5);
            lineOptions.color(Color.GRAY);
            lineOptions.startCap(new SquareCap());
            lineOptions.endCap(new SquareCap());
            lineOptions.jointType(ROUND);
            blackPolyLine = mMap.addPolyline(lineOptions);

            PolylineOptions greyOptions = new PolylineOptions();
            greyOptions.width(5);
            greyOptions.color(Color.BLACK);
            greyOptions.startCap(new SquareCap());
            greyOptions.endCap(new SquareCap());
            greyOptions.jointType(ROUND);
            greyPolyLine = mMap.addPolyline(greyOptions);

            animatePolyLine(1000);
        }
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

    public void drawMarker() {
        if (wayPoints != null && wayPoints.size() > 2) {
            for (int i = 1; i < wayPoints.size() - 1; i++) {
                mMap.addMarker(new MarkerOptions().position(wayPoints.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.driver_drop_dot)));
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

            if (DriverSessionSave.getSession("Metric", mContext).trim().equalsIgnoreCase("MILES") && DriverSessionSave.getSession("isBUISNESSKEY", mContext, true)) {
                approx_travel_dist = approx_travel_dist / 1.60934;
            }

            DriverSystems.out.println("routee saveGoogleLog" + latLngKey + "***" + approx_travel_time + "*****" + approx_travel_dist);
            GoogleMapModel model = new GoogleMapModel();
            model.fromTo = latLngKey;
            model.time = approx_travel_time;
            model.distance = approx_travel_dist;
            model.routeResult = routeResult;
            model.distanceResult = "";
            mRepository.insertGoogleLog(model);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removePolyLines() {
        if (blackPolyLine != null) blackPolyLine.remove();
        if (greyPolyLine != null) greyPolyLine.remove();
    }

    /**
     * Check whether available in DB
     */
    private class GetGoogleRouteLog extends AsyncTask<Void, Void, GoogleMapModel> {
        private final double P_latitude;
        private final double P_longitude;
        private final double D_latitude;
        private final double D_longitude;
        private String from;
        private String to;

        public GetGoogleRouteLog(LatLng source, LatLng destination, ArrayList<LatLng> points) {
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
            GoogleMapModel model = mRepository.getGoogleModel(from.trim() + to.trim());
            return model;
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
            } else {
                DriverSystems.out.println("routee onPostExecute called ");
                getRouteFromGoogle(P_latitude, P_longitude, D_latitude, D_longitude, wayPoints);
            }
        }
    }
}