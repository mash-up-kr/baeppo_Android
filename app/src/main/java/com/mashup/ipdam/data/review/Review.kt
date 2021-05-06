package com.mashup.ipdam.data.review

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LATITUDE
import com.mashup.ipdam.data.map.MapConstants.DEFAULT_LONGITUDE
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class Review(
    val id: String? = null,
    val title: String?,
    val description: String?,
    val buildingName: String?,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val rating: Double?,
    val owner: Int?,
    val safety: Int?,
    val clean: Int?,
    val distance: Int?,
    val amenities: String?,
    val userId: String?,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val images: List<ReviewImage>? = null,
    val isBookmark: Boolean = false,
) : TedClusterItem, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        parcel.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        parcel.createTypedArray(ReviewImage.CREATOR)?.asList(),
        parcel.readByte() != 0.toByte()
    )

    fun isEmpty(): Boolean {
        return title.isNullOrEmpty() or description.isNullOrEmpty() or address.isNullOrEmpty() or
                buildingName.isNullOrEmpty()
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }

    override fun getTedLatLng(): TedLatLng =
        TedLatLng(
            latitude ?: DEFAULT_LATITUDE,
            longitude ?: DEFAULT_LONGITUDE
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(buildingName)
        parcel.writeString(address)
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeValue(rating)
        parcel.writeValue(owner)
        parcel.writeValue(safety)
        parcel.writeValue(clean)
        parcel.writeValue(distance)
        parcel.writeString(amenities)
        parcel.writeString(userId)
        parcel.writeParcelable(createdAt, flags)
        parcel.writeParcelable(updatedAt, flags)
        parcel.writeTypedArray(images?.toTypedArray(), flags)
        parcel.writeByte(if (isBookmark) 1 else 0)
    }
}
