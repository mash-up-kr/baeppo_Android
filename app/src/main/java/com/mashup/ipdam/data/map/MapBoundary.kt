package com.mashup.ipdam.data.map

import android.os.Parcel
import android.os.Parcelable
import com.naver.maps.geometry.LatLng

data class MapBoundary(val topLeftLatLng: LatLng, val bottomRightLatLng: LatLng): Parcelable {

    constructor(parcel: Parcel) : this(
        topLeftLatLng = LatLng(parcel.readDouble(), parcel.readDouble()),
        bottomRightLatLng = LatLng(parcel.readDouble(), parcel.readDouble())
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.run {
            writeDouble(topLeftLatLng.latitude)
            writeDouble(topLeftLatLng.longitude)
            writeDouble(bottomRightLatLng.latitude)
            writeDouble(bottomRightLatLng.longitude)
        }
    }

    companion object CREATOR : Parcelable.Creator<MapBoundary> {
        override fun createFromParcel(parcel: Parcel): MapBoundary {
            return MapBoundary(parcel)
        }

        override fun newArray(size: Int): Array<MapBoundary?> {
            return arrayOfNulls(size)
        }
    }
}
