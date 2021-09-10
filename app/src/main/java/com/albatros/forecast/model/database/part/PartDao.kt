package com.albatros.forecast.model.database.part

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
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