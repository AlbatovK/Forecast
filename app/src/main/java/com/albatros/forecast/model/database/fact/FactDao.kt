package com.albatros.forecast.model.database.fact

import androidx.room.*
import com.albatros.forecast.model.data.Fact
import com.albatros.forecast.model.database.dao.BaseDao

@Dao
interface FactDao : BaseDao<Fact> {

    @Query("Delete From fact")
    suspend fun clearTable()

    @Query("Select * From fact")
    suspend fun getAll(): List<Fact>
}