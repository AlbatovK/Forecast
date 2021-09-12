package com.albatros.forecast.model.database.part

import androidx.room.*
import com.albatros.forecast.model.data.Part

@Dao
interface PartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPart(vararg parts: Part)

    @Delete
    suspend fun deletePart(part: Part)

    @Update
    suspend fun updatePart(part: Part)

    @Query("Delete From part")
    suspend fun clearTable()

    @Query("Select * From part")
    suspend fun getAll(): List<Part>
}