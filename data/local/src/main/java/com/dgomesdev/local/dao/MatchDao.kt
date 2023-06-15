package com.dgomesdev.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dgomesdev.local.entity.MatchEntity

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMatch(match: MatchEntity)

    @Delete
    suspend fun deleteMatch(vararg match: MatchEntity)

    @Query("SELECT * FROM MATCH_TABLE WHERE name LIKE :name ORDER BY name")
    suspend fun getFavoriteMatchesFromDB(name: String = "%"): List<MatchEntity>

}