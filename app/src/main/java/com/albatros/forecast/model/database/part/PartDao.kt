package com.albatros.forecast.model.database.part

import androidx.room.*
import com.albatros.forecast.model.data.Part
import com.albatros.forecast.model.database.dao.BaseDao

@Dao
interface PartDao : BaseDao<Part> {

    @Query("Delete From part")
    suspend fun clearTable()

    @Query("Select * From part")
    suspend fun getAll(): List<Part>
}