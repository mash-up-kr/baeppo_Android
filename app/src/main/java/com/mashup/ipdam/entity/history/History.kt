package com.mashup.ipdam.entity.history

import androidx.recyclerview.widget.DiffUtil
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

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }
}

