package com.mashup.ipdam.data.review

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Review(
    val id: String? = null,
    val title: String,
    val description: String,
    val buildingName: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val owner: Int,
    val safety: Int,
    val clean: Int,
    val distance: Int,
    val amenities: String,
    val userId: String,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null,
    val images: List<String>? = null,
    val bookmark: Boolean = false,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        parcel.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        parcel.createStringArrayList(),
        parcel.readByte() != 0.toByte()
    )

    fun isEmpty(): Boolean {
        return title.isEmpty() or description.isEmpty() or address.isEmpty() or
                buildingName.isEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(buildingName)
        parcel.writeString(address)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeDouble(rating)
        parcel.writeInt(owner)
        parcel.writeInt(safety)
        parcel.writeInt(clean)
        parcel.writeInt(distance)
        parcel.writeString(amenities)
        parcel.writeString(userId)
        parcel.writeParcelable(createdAt, 0)
        parcel.writeParcelable(updatedAt, 0)
        parcel.writeStringList(images)
        parcel.writeByte(if (bookmark) 1 else 0)
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
}
