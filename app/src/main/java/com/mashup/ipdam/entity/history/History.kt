package com.mashup.ipdam.entity.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val address: String
) {
    override fun equals(other: Any?): Boolean {
        return if (other == null || other !is History) {
            false
        } else {
            address == other.address
        }
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }
}

