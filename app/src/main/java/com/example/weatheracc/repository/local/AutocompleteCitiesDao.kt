package com.example.weatheracc.repository.local

import androidx.room.*
import com.example.weatheracc.models.AutocompleteCities

@Dao
interface AutocompleteCitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(autocompleteCitiesDao: AutocompleteCities): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(autocompleteCitiesDao: List<AutocompleteCities>): List<Long>

    @Update
    suspend fun update(autocompleteCitiesDao: AutocompleteCities): Int

    @Delete
    suspend fun delete(autocompleteCitiesDao: AutocompleteCities): Int


    @Query("SELECT * FROM autocomplete")
    suspend fun getAll(): List<AutocompleteCities>
}