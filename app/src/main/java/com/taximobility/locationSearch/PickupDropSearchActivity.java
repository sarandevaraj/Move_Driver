package com.taximobility.locationSearch;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taximobility.R;
import com.taximobility.bookingmodule.BookTaxiHomePage;
import com.taximobility.bookingmodule.BookTaxiHomeViewModel;
import com.taximobility.bookingmodule.LocationData;
import com.taximobility.bookingmodule.adapter.LocationListAdapter;
import com.taximobility.data.apiData.PlacesDetail;
import com.taximobility.databinding.ActivityPickupDropSearchBinding;
import com.taximobility.interfaces.SetPickup;
import com.taximobility.util.CL;
import com.taximobility.util.Colorchange;
import com.taximobility.util.ConstantsKt;
import com.taximobility.util.FontHelper;
import com.taximobility.util.NC;
import com.taximobility.util.SessionSave;
import com.taximobility.util.TaxiUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.taximobility.locationSearch.AddStopActivityKt.ADD_STOP_REQUEST_CODE;
import static com.taximobility.locationSearch.AddStopActivityKt.IS_FROM_ONGOING;
import static com.taximobility.util.ConstantsKt.BUNDLE_BOOKING_ADDRESS_TYPE;
import static com.taximobility.util.ConstantsKt.BUNDLE_PICKUP_DROP_ADDRESS;
import static com.taximobility.util.ConstantsKt.DropPlace;
import static com.taximobility.util.ConstantsKt.LANG;
import static com.taximobility.util.ConstantsKt.PASS_ID;

/**
 * Created by developer on 14/3/17.
 */
public class PickupDropSearchActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, SetPickup, SetPlaceResult, LocationListAdapter.DeleteClickListener {

    public static boolean SET_FOR_PICKUP;
    String city = " ";
    private String type;
    private LocationRequest locationRequest;
    private int incrementalId = 0;
    private boolean isFromOnGoing = false;
    private PlacesDetail pickup_obj, drop_obj;
    private boolean onlyDrop, onlyPickup;
    private String onlyPackage;
    private TextWatcher textWatcher, dropTextWatcher;
    private OnLocationSearched listener;
    private LinearLayout map_redirctTxt;

    LinearLayout pickupp;
    FrameLayout pickup_pinlay, searchFrag;
    private EditText drop_location, currentlocTxt;
    private ImageView imageAddStop, btn_back;
    RecyclerView locationListView;

    ArrayList<PlacesData> mList;
    ActivityPickupDropSearchBinding binding;
    BookTaxiHomeViewModel viewModel;
    LocationListAdapter mAdapter;
    ArrayList<LocationData> mLocationList = new ArrayList<>();
    ArrayList<LocationData> favList = new ArrayList<>();
    ArrayList<LocationData> popList = new ArrayList<>();
    ArrayList<LocationData> recList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_pickup_drop_search);
        viewModel = ViewModelProviders.of(this).get(BookTaxiHomeViewModel.class);
        binding.setMList(viewModel);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();
        Initialize();
    }

    public void Initialize() {
//        if (sf != null)
//            sf.setLocationListner(this);
        mAdapter = new LocationListAdapter(this, mLocationList, this);
        locationListView = findViewById(R.id.locationList);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        locationListView.setLayoutManager(manager);

        LocationSearchFragment locationSearchFragment = new LocationSearchFragment();
        listener = locationSearchFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.searchFrag, locationSearchFragment).commitNow();

        FontHelper.applyFont(this, findViewById(R.id.top));
        Colorchange.ChangeColor(findViewById(R.id.top), this);
        drop_location = findViewById(R.id.taxi__locationsearch_edittext_search);
        currentlocTxt = findViewById(R.id.currentlocTxt);
        pickupp = findViewById(R.id.pickupp);
        pickup_pinlay = findViewById(R.id.pickup_pinlay);
        imageAddStop = findViewById(R.id.add_stop);
        drop_location.setHintTextColor(CL.getColor(this, R.color.textviewcolor_light));
        drop_location.setTextColor(CL.getColor(this, R.color.black));

        currentlocTxt.setHintTextColor(CL.getColor(this, R.color.textviewcolor_light));
        currentlocTxt.setTextColor(CL.getColor(this, R.color.black));

        map_redirctTxt = findViewById(R.id.map_redirctTxt);
        map_redirctTxt.setVisibility(View.GONE);

        currentlocTxt.setHint(NC.getString(R.string.picklocation));
        searchFrag = findViewById(R.id.searchFrag);

        textWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 2) {
                    listener.onLocationSearched(s.toString());
                    searchFrag.setVisibility(View.VISIBLE);
                    locationListView.setVisibility(View.GONE);
                } else {
                    searchFrag.setVisibility(View.GONE);
                    locationListView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        dropTextWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 2) {
                    listener.onLocationSearched(s.toString());
                    searchFrag.setVisibility(View.VISIBLE);
                    locationListView.setVisibility(View.GONE);
                } else {
                    searchFrag.setVisibility(View.GONE);
                    locationListView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        btn_back = findViewById(R.id.back_icon);
        ImageButton locationsearch_clear = findViewById(R.id.taxi__locationsearch_imagebutton_clear);

        locationsearch_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drop_location.setText("");
            }
        });


        Bundle b = getIntent().getExtras();
        if (b != null) {
            type = b.getString("type");
            pickup_obj = b.getParcelable("pickup_obj");
            drop_obj = b.getParcelable("drop_obj");
            try {
                onlyDrop = b.getBoolean("onlyDrop");
            } catch (Exception e) {
                e.printStackTrace();
            }
            onlyPickup = b.getBoolean("onlyPickup");

            if (b.getBoolean(IS_FROM_ONGOING, false)) {
                currentlocTxt.setEnabled(false);
                isFromOnGoing = true;
            } else {
                isFromOnGoing = false;
                currentlocTxt.setEnabled(true);
            }

            mList = b.getParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS);
        }

        if (type.equals(DropPlace)) {
            onlyPackage = b.getString("Package");
            drop_location.setHint(NC.getString(R.string.enter_dest));
            drop_location.requestFocus();
            drop_location.performClick();
            map_redirctTxt.setVisibility(View.VISIBLE);
            if (pickup_obj != null) {
                currentlocTxt.setText(pickup_obj.getLocation_name());
            }
            SET_FOR_PICKUP = false;
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(drop_location, InputMethodManager.SHOW_IMPLICIT);
        } else {
            currentlocTxt.setHint(NC.getString(R.string.enter_pickup));
            currentlocTxt.requestFocus();
            currentlocTxt.performClick();
            map_redirctTxt.setVisibility(View.GONE);

            if (drop_obj != null) {
                drop_location.setText(drop_obj.getLocation_name());
            }
            SET_FOR_PICKUP = true;
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(currentlocTxt, InputMethodManager.SHOW_IMPLICIT);
        }
        if (onlyDrop) {
            pickupp.setVisibility(View.GONE);
            pickup_pinlay.setVisibility(View.GONE);
            findViewById(R.id.pickup_drop_Sep).setVisibility(View.GONE);

            if (SessionSave.getSession(LANG, PickupDropSearchActivity.this).equals("ar") || SessionSave.getSession(LANG, PickupDropSearchActivity.this).equals("fa"))
                drop_location.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot, 0);
            else
                drop_location.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dot, 0, 0, 0);

            drop_location.setCompoundDrawablePadding(20);
        }

        if (onlyPickup) {
            findViewById(R.id.dropppp).setVisibility(View.GONE);
            pickup_pinlay.setVisibility(View.GONE);
            findViewById(R.id.pickup_drop_Sep).setVisibility(View.GONE);

            if (SessionSave.getSession(LANG, PickupDropSearchActivity.this).equals("ar") || SessionSave.getSession(LANG, PickupDropSearchActivity.this).equals("fa"))
                currentlocTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dot, 0);
            else
                currentlocTxt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dot, 0, 0, 0);

            currentlocTxt.setCompoundDrawablePadding(20);
        }

        drop_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        currentlocTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        drop_location.addTextChangedListener(dropTextWatcher);
        currentlocTxt.addTextChangedListener(textWatcher);

        currentlocTxt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SET_FOR_PICKUP = true;
                    currentlocTxt.setText("");
                    currentlocTxt.setHint(NC.getString(R.string.enter_pickup));
                    map_redirctTxt.setVisibility(View.GONE);
                    currentlocTxt.addTextChangedListener(textWatcher);
                    if (drop_obj != null) {
                        drop_location.removeTextChangedListener(dropTextWatcher);
                        drop_location.setText(drop_obj.getLocation_name());
                    }

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(currentlocTxt, InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }

        });

        drop_location.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drop_location.setText("");
                    drop_location.setHint(NC.getString(R.string.enter_dest));
                    map_redirctTxt.setVisibility(View.VISIBLE);
                    drop_location.addTextChangedListener(dropTextWatcher);
                    if (pickup_obj != null) {
                        currentlocTxt.removeTextChangedListener(textWatcher);
                        currentlocTxt.setText(pickup_obj.getLocation_name());
                    }

                    SET_FOR_PICKUP = false;

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(drop_location, InputMethodManager.SHOW_IMPLICIT);
                }
                return false;
            }
        });

        map_redirctTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle conData = new Bundle();
                conData.putString("param_result", pickup_obj.getLocation_name());
                conData.putDouble("lat", pickup_obj.latitude);
                conData.putDouble("lng", pickup_obj.longtitute);
                conData.putBoolean("set_for_pickup", SET_FOR_PICKUP);
                conData.putString("Package", onlyPackage);
                conData.putParcelableArrayList(BUNDLE_BOOKING_ADDRESS_TYPE, null);
                conData.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, mList);


                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                    InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    im.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                finish();
            }
        });

        viewModel.getAllFavPopRecPlaces().observe(this, new Observer<List<LocationData>>() {
            @Override
            public void onChanged(@Nullable List<LocationData> locationData) {

                if (locationData != null) {
                    favList.clear();
                    popList.clear();
                    recList.clear();
                    mLocationList.clear();
                    int listSize = locationData.size();
                    for (int i = 0; i < listSize; i++) {
                        if (locationData.get(i).getType().equals("1")) {
                            favList.add(locationData.get(i));
                        } else if (locationData.get(i).getType().equals("2")) {
                            popList.add(locationData.get(i));
                        } else {
                            recList.add(locationData.get(i));
                        }
                    }
                    updateListAdapter();
                }
            }
        });
        locationListView.setAdapter(mAdapter);

    }

    public void updateListAdapter() {

       if (recList.size() > 0) {
            mLocationList.add(new LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.recent), "", "", "", ConstantsKt.RecentPlace));
            mLocationList.addAll(recList);
        }

        if (favList.size() > 0) {
            mLocationList.add(new LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.favourite), "", "", "", ConstantsKt.FavouritePlace));
            mLocationList.addAll(favList);
        }
        if (popList.size() > 0) {
            mLocationList.add(new LocationData(0, "", "", 0.0, 0.0, 0.0, 0.0,
                    "", NC.getString(R.string.popular), "", "", "", ConstantsKt.PopularPlace));
            mLocationList.addAll(popList);
        }

        mAdapter.updateList(mLocationList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionSave.getSession(TaxiUtil.IS_STOP_ENABLED, this, false)) {
            imageAddStop.setVisibility(View.VISIBLE);
            imageAddStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<PlacesData> stopData = new ArrayList<>();
                    if (pickup_obj != null)
                        stopData.add(new PlacesData(0, pickup_obj.latitude, pickup_obj.longtitute, pickup_obj.location_name, pickup_obj.placeId != null ? pickup_obj.placeId : "", "", pickup_obj.getFavPlaceType(), ""));

                    if (drop_obj != null)
                        stopData.add(new PlacesData((++incrementalId + new Random().nextInt()), drop_obj.latitude, drop_obj.longtitute, drop_obj.location_name, drop_obj.placeId != null ? drop_obj.placeId : "", "", drop_obj.getFavPlaceType(), ""));

                    Intent intent = new Intent(PickupDropSearchActivity.this, AddStopActivity.class);
                    intent.putParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS, mList);
                    intent.putExtra("Package", onlyPackage);
                    intent.putExtra(IS_FROM_ONGOING, isFromOnGoing);
                    startActivityForResult(intent, ADD_STOP_REQUEST_CODE);
                }
            });
        } else {
            imageAddStop.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i("Connected ", "Connection started");


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // Update location every second

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void setPickupAddress(final String Address) {
        if (pickup_obj != null)
            pickup_obj.setLocation_name(Address);
        currentlocTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        currentlocTxt.setText(Address);
        currentlocTxt.addTextChangedListener(textWatcher);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_STOP_REQUEST_CODE) {
                if (data != null) {
                    Intent intent = new Intent();
                    Bundle conData = new Bundle();
                    conData.putString("param_result", "");
                    conData.putDouble("lat", TaxiUtil.Latitude);
                    conData.putDouble("lng", TaxiUtil.Longitude);
                    conData.putBoolean("set_for_pickup", SET_FOR_PICKUP);
                    conData.putString("Package", onlyPackage);
                    conData.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, data.getParcelableArrayListExtra(BUNDLE_PICKUP_DROP_ADDRESS));
                    intent.putExtras(conData);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        finish();

    }


    @Override
    public void onPlaceSelected(@NotNull PlacesDetail placesDetail) {
        try {

            if (SET_FOR_PICKUP) {
                currentlocTxt.removeTextChangedListener(textWatcher);
                currentlocTxt.setText("" + placesDetail.getLocation_name());
                String[] ss = placesDetail.getLocation_name().split(",");
                if (ss.length > 2)
                    city = ss[ss.length - 3];
            } else {
                drop_location.removeTextChangedListener(dropTextWatcher);
                drop_location.setText(placesDetail.getLocation_name());
            }

            TaxiUtil.Latitude = placesDetail.getLatitude();
            TaxiUtil.Longitude = placesDetail.getLongtitute();

            Bundle conData = new Bundle();
            String selectedAddress = placesDetail.getLocation_name();

            if (!selectedAddress.contains(placesDetail.getLabel_name()))
                selectedAddress = /*placesDetail.getLabel_name() + " " + */selectedAddress;

            conData.putString("param_result", selectedAddress);
            conData.putDouble("lat", TaxiUtil.Latitude);
            conData.putDouble("lng", TaxiUtil.Longitude);
            conData.putBoolean("set_for_pickup", SET_FOR_PICKUP);
            conData.putString("Package", onlyPackage);
            conData.putString(BUNDLE_BOOKING_ADDRESS_TYPE, placesDetail.favPlaceType);
            if (SET_FOR_PICKUP) {
                String cityName = BookTaxiHomePage.Companion.getDefaultCityName();
                cityName = city.equals("") ? city : city.trim();
                if (mList.size() == 0) {
                    mList.add(0, new PlacesData((++incrementalId + new Random().nextInt()), placesDetail.getLatitude(), placesDetail.getLongtitute(), placesDetail.getLocation_name(), placesDetail.getPlaceId(), "", placesDetail.getFavPlaceType(), ""));
                } else {
                    mList.set(0, new PlacesData(0, placesDetail.getLatitude(), placesDetail.getLongtitute(), placesDetail.getLocation_name(), placesDetail.getPlaceId(), "", placesDetail.getFavPlaceType(), ""));
                }
            } else {
                if (mList.size() > 1)
                    mList.set(1, new PlacesData((++incrementalId + new Random().nextInt()), placesDetail.getLatitude(), placesDetail.getLongtitute(), placesDetail.getLocation_name(), placesDetail.getPlaceId(), "", placesDetail.getFavPlaceType(), ""));
                else
                    mList.add(1, new PlacesData((++incrementalId + new Random().nextInt()), placesDetail.getLatitude(), placesDetail.getLongtitute(), placesDetail.getLocation_name(), placesDetail.getPlaceId(), "", placesDetail.getFavPlaceType(), ""));
            }
            conData.putParcelableArrayList(BUNDLE_PICKUP_DROP_ADDRESS, mList);

            Intent intent = new Intent();
            intent.putExtras(conData);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClick(@NotNull LocationData locationData) {
        viewModel.deleteFavourite(locationData.getLocation_name());
        JSONObject j = new JSONObject();
        try {
            j.put("passenger_id", "" + SessionSave.getSession(PASS_ID, this));
            j.put("p_favourite_id", "" + locationData.get_id());
            viewModel.deleteFavouriteApiCall(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void itemClick(int pos) {

        LocationData item = mAdapter.getItem(pos);
        TaxiUtil.Latitude = item.getLatitude();
        TaxiUtil.Longitude = item.getLongtitute();
        TaxiUtil.Address = item.getLocation_name();
        PlacesDetail detail = new PlacesDetail();
        detail.location_name = item.getLocation_name();
        detail.label_name = item.getLabel_name();
        detail.latitude = item.getLatitude();
        detail.longtitute = item.getLongtitute();
        detail.placeId = String.valueOf(item.get_id());
        detail.favPlaceType = item.getType();
        onPlaceSelected(detail);
    }
}