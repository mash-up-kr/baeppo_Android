package com.mashup.ipdam.data.review

import android.os.Parcel
import android.os.Parcelable

data class ReviewImage(
    val id: String? = null,
    val reviewId: String?,
    val imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(reviewId)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewImage> {
        override fun createFromParcel(parcel: Parcel): ReviewImage {
            return ReviewImage(parcel)
        }

        override fun newArray(size: Int): Array<ReviewImage?> {
            return arrayOfNulls(size)
        }
    }
}