package com.taximobility;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.taximobility.databinding.ActivityFindLogBindingImpl;
import com.taximobility.databinding.ActivityLocationSearchBindingArImpl;
import com.taximobility.databinding.ActivityLocationSearchBindingImpl;
import com.taximobility.databinding.ActivityPickupDropSearchBindingArImpl;
import com.taximobility.databinding.ActivityPickupDropSearchBindingImpl;
import com.taximobility.databinding.BookBottomViewBindingArImpl;
import com.taximobility.databinding.BookBottomViewBindingImpl;
import com.taximobility.databinding.BookTaxiHomePageBindingArImpl;
import com.taximobility.databinding.BookTaxiHomePageBindingImpl;
import com.taximobility.databinding.DriverSettlementHistoryListBindingArImpl;
import com.taximobility.databinding.DriverSettlementHistoryListBindingImpl;
import com.taximobility.databinding.FavListBindingArImpl;
import com.taximobility.databinding.FavListBindingImpl;
import com.taximobility.databinding.FragmentAddCardBindingArImpl;
import com.taximobility.databinding.FragmentAddCardBindingImpl;
import com.taximobility.databinding.FragmentCancellationPaymentOptionsBindingArImpl;
import com.taximobility.databinding.FragmentCancellationPaymentOptionsBindingImpl;
import com.taximobility.databinding.FragmentCardListBindingArImpl;
import com.taximobility.databinding.FragmentCardListBindingImpl;
import com.taximobility.databinding.LocationFavouriteListBindingArImpl;
import com.taximobility.databinding.LocationFavouriteListBindingImpl;
import com.taximobility.databinding.LocationListBindingArImpl;
import com.taximobility.databinding.LocationListBindingImpl;
import com.taximobility.databinding.SearchLocationBindingArImpl;
import com.taximobility.databinding.SearchLocationBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYFINDLOG = 1;

  private static final int LAYOUT_ACTIVITYLOCATIONSEARCH = 2;

  private static final int LAYOUT_ACTIVITYPICKUPDROPSEARCH = 3;

  private static final int LAYOUT_BOOKBOTTOMVIEW = 4;

  private static final int LAYOUT_BOOKTAXIHOMEPAGE = 5;

  private static final int LAYOUT_DRIVERSETTLEMENTHISTORYLIST = 6;

  private static final int LAYOUT_FAVLIST = 7;

  private static final int LAYOUT_FRAGMENTADDCARD = 8;

  private static final int LAYOUT_FRAGMENTCANCELLATIONPAYMENTOPTIONS = 9;

  private static final int LAYOUT_FRAGMENTCARDLIST = 10;

  private static final int LAYOUT_LOCATIONFAVOURITELIST = 11;

  private static final int LAYOUT_LOCATIONLIST = 12;

  private static final int LAYOUT_SEARCHLOCATION = 13;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(13);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.activity_find_log, LAYOUT_ACTIVITYFINDLOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.activity_location_search, LAYOUT_ACTIVITYLOCATIONSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.activity_pickup_drop_search, LAYOUT_ACTIVITYPICKUPDROPSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.book_bottom_view, LAYOUT_BOOKBOTTOMVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.book_taxi_home_page, LAYOUT_BOOKTAXIHOMEPAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.driver_settlement_history_list, LAYOUT_DRIVERSETTLEMENTHISTORYLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.fav_list, LAYOUT_FAVLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.fragment_add_card, LAYOUT_FRAGMENTADDCARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.fragment_cancellation_payment_options, LAYOUT_FRAGMENTCANCELLATIONPAYMENTOPTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.fragment_card_list, LAYOUT_FRAGMENTCARDLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.location_favourite_list, LAYOUT_LOCATIONFAVOURITELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.location_list, LAYOUT_LOCATIONLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.taximobility.R.layout.search_location, LAYOUT_SEARCHLOCATION);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYFINDLOG: {
          if ("layout/activity_find_log_0".equals(tag)) {
            return new ActivityFindLogBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_find_log is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOCATIONSEARCH: {
          if ("layout/activity_location_search_0".equals(tag)) {
            return new ActivityLocationSearchBindingImpl(component, view);
          }
          if ("layout-ar/activity_location_search_0".equals(tag)) {
            return new ActivityLocationSearchBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_location_search is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPICKUPDROPSEARCH: {
          if ("layout-ar/activity_pickup_drop_search_0".equals(tag)) {
            return new ActivityPickupDropSearchBindingArImpl(component, view);
          }
          if ("layout/activity_pickup_drop_search_0".equals(tag)) {
            return new ActivityPickupDropSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_pickup_drop_search is invalid. Received: " + tag);
        }
        case  LAYOUT_BOOKBOTTOMVIEW: {
          if ("layout-ar/book_bottom_view_0".equals(tag)) {
            return new BookBottomViewBindingArImpl(component, view);
          }
          if ("layout/book_bottom_view_0".equals(tag)) {
            return new BookBottomViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for book_bottom_view is invalid. Received: " + tag);
        }
        case  LAYOUT_BOOKTAXIHOMEPAGE: {
          if ("layout/book_taxi_home_page_0".equals(tag)) {
            return new BookTaxiHomePageBindingImpl(component, view);
          }
          if ("layout-ar/book_taxi_home_page_0".equals(tag)) {
            return new BookTaxiHomePageBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for book_taxi_home_page is invalid. Received: " + tag);
        }
        case  LAYOUT_DRIVERSETTLEMENTHISTORYLIST: {
          if ("layout/driver_settlement_history_list_0".equals(tag)) {
            return new DriverSettlementHistoryListBindingImpl(component, view);
          }
          if ("layout-ar/driver_settlement_history_list_0".equals(tag)) {
            return new DriverSettlementHistoryListBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for driver_settlement_history_list is invalid. Received: " + tag);
        }
        case  LAYOUT_FAVLIST: {
          if ("layout/fav_list_0".equals(tag)) {
            return new FavListBindingImpl(component, view);
          }
          if ("layout-ar/fav_list_0".equals(tag)) {
            return new FavListBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fav_list is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTADDCARD: {
          if ("layout/fragment_add_card_0".equals(tag)) {
            return new FragmentAddCardBindingImpl(component, view);
          }
          if ("layout-ar/fragment_add_card_0".equals(tag)) {
            return new FragmentAddCardBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_add_card is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCANCELLATIONPAYMENTOPTIONS: {
          if ("layout-ar/fragment_cancellation_payment_options_0".equals(tag)) {
            return new FragmentCancellationPaymentOptionsBindingArImpl(component, view);
          }
          if ("layout/fragment_cancellation_payment_options_0".equals(tag)) {
            return new FragmentCancellationPaymentOptionsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_cancellation_payment_options is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCARDLIST: {
          if ("layout/fragment_card_list_0".equals(tag)) {
            return new FragmentCardListBindingImpl(component, view);
          }
          if ("layout-ar/fragment_card_list_0".equals(tag)) {
            return new FragmentCardListBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_card_list is invalid. Received: " + tag);
        }
        case  LAYOUT_LOCATIONFAVOURITELIST: {
          if ("layout/location_favourite_list_0".equals(tag)) {
            return new LocationFavouriteListBindingImpl(component, view);
          }
          if ("layout-ar/location_favourite_list_0".equals(tag)) {
            return new LocationFavouriteListBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for location_favourite_list is invalid. Received: " + tag);
        }
        case  LAYOUT_LOCATIONLIST: {
          if ("layout/location_list_0".equals(tag)) {
            return new LocationListBindingImpl(component, view);
          }
          if ("layout-ar/location_list_0".equals(tag)) {
            return new LocationListBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for location_list is invalid. Received: " + tag);
        }
        case  LAYOUT_SEARCHLOCATION: {
          if ("layout/search_location_0".equals(tag)) {
            return new SearchLocationBindingImpl(component, view);
          }
          if ("layout-ar/search_location_0".equals(tag)) {
            return new SearchLocationBindingArImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for search_location is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(9);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "list");
      sKeys.put(2, "locationLists");
      sKeys.put(3, "mData");
      sKeys.put(4, "mList");
      sKeys.put(5, "mLocationData");
      sKeys.put(6, "myBookViewModel");
      sKeys.put(7, "myFavViewModel");
      sKeys.put(8, "myViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(25);

    static {
      sKeys.put("layout/activity_find_log_0", com.taximobility.R.layout.activity_find_log);
      sKeys.put("layout/activity_location_search_0", com.taximobility.R.layout.activity_location_search);
      sKeys.put("layout-ar/activity_location_search_0", com.taximobility.R.layout.activity_location_search);
      sKeys.put("layout-ar/activity_pickup_drop_search_0", com.taximobility.R.layout.activity_pickup_drop_search);
      sKeys.put("layout/activity_pickup_drop_search_0", com.taximobility.R.layout.activity_pickup_drop_search);
      sKeys.put("layout-ar/book_bottom_view_0", com.taximobility.R.layout.book_bottom_view);
      sKeys.put("layout/book_bottom_view_0", com.taximobility.R.layout.book_bottom_view);
      sKeys.put("layout/book_taxi_home_page_0", com.taximobility.R.layout.book_taxi_home_page);
      sKeys.put("layout-ar/book_taxi_home_page_0", com.taximobility.R.layout.book_taxi_home_page);
      sKeys.put("layout/driver_settlement_history_list_0", com.taximobility.R.layout.driver_settlement_history_list);
      sKeys.put("layout-ar/driver_settlement_history_list_0", com.taximobility.R.layout.driver_settlement_history_list);
      sKeys.put("layout/fav_list_0", com.taximobility.R.layout.fav_list);
      sKeys.put("layout-ar/fav_list_0", com.taximobility.R.layout.fav_list);
      sKeys.put("layout/fragment_add_card_0", com.taximobility.R.layout.fragment_add_card);
      sKeys.put("layout-ar/fragment_add_card_0", com.taximobility.R.layout.fragment_add_card);
      sKeys.put("layout-ar/fragment_cancellation_payment_options_0", com.taximobility.R.layout.fragment_cancellation_payment_options);
      sKeys.put("layout/fragment_cancellation_payment_options_0", com.taximobility.R.layout.fragment_cancellation_payment_options);
      sKeys.put("layout/fragment_card_list_0", com.taximobility.R.layout.fragment_card_list);
      sKeys.put("layout-ar/fragment_card_list_0", com.taximobility.R.layout.fragment_card_list);
      sKeys.put("layout/location_favourite_list_0", com.taximobility.R.layout.location_favourite_list);
      sKeys.put("layout-ar/location_favourite_list_0", com.taximobility.R.layout.location_favourite_list);
      sKeys.put("layout/location_list_0", com.taximobility.R.layout.location_list);
      sKeys.put("layout-ar/location_list_0", com.taximobility.R.layout.location_list);
      sKeys.put("layout/search_location_0", com.taximobility.R.layout.search_location);
      sKeys.put("layout-ar/search_location_0", com.taximobility.R.layout.search_location);
    }
  }
}
