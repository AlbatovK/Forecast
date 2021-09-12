package com.albatros.forecast.model.database.info

import androidx.room.*
import com.albatros.forecast.model.data.Info

@Dao
interface InfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(info: Info)

    @Delete
    suspend fun deleteInfo(info: Info)

    @Update
    suspend fun updateInfo(info: Info)

    @Query("Delete From info")
    suspend fun clearTable()

    @Query("Select * From info")
    suspend fun getAll(): List<Info>
}