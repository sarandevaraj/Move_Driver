package com.movedriver.driver.locationSearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by developer on 20/3/17.
 */

public class DriverPlacesDetail implements Parcelable {
    public String location_name;
    public String label_name;
    public boolean isShow;
    public int placeType = 0;

    public int getPlaceType() {
        return placeType;
    }

    public void setPlaceType(int placeType) {
        this.placeType = placeType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String placeId;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getAndroid_image_unfocus() {
        return android_image_unfocus;
    }

    public void setAndroid_image_unfocus(String android_image_unfocus) {
        this.android_image_unfocus = android_image_unfocus;
    }

    public String android_image_unfocus;
    public Double latitude;
    public Double longtitute;

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitute() {
        return longtitute;
    }

    public void setLongtitute(Double longtitute) {
        this.longtitute = longtitute;
    }

    public DriverPlacesDetail() {

    }

    protected DriverPlacesDetail(Parcel in) {
        location_name = in.readString();
        label_name = in.readString();
        latitude = in.readDouble();
        longtitute = in.readDouble();
    }

    public static final Creator<DriverPlacesDetail> CREATOR = new Creator<DriverPlacesDetail>() {
        @Override
        public DriverPlacesDetail createFromParcel(Parcel in) {
            return new DriverPlacesDetail(in);
        }

        @Override
        public DriverPlacesDetail[] newArray(int size) {
            return new DriverPlacesDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location_name);
        dest.writeString(label_name);
        dest.writeDouble(latitude);
        dest.writeDouble(longtitute);
    }
}