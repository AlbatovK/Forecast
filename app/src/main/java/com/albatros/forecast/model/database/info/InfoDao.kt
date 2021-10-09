package com.albatros.forecast.model.database.info

import androidx.room.*
import com.albatros.forecast.model.data.Info
import com.albatros.forecast.model.database.dao.BaseDao

@Dao
interface InfoDao : BaseDao<Info> {

    @Query("Delete From info")
    suspend fun clearTable()

    @Query("Select * From info")
    suspend fun getAll(): List<Info>
}