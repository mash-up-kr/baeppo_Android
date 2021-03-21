package com.mashup.ipdam.ui.search.data.source

import androidx.room.*
import com.mashup.ipdam.ui.search.data.entity.history.History
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY id DESC")
    fun getAll(): Single<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History): Completable

    @Delete
    fun deleteHistory(history: History): Completable

    @Query("DELETE FROM History")
    fun deleteAll(): Completable
}