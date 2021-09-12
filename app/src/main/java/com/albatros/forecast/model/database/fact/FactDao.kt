package com.albatros.forecast.model.database.fact

import androidx.room.*
import com.albatros.forecast.model.data.Fact

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFact(fact: Fact)

    @Delete
    suspend fun deleteFact(fact: Fact)

    @Update
    suspend fun updateFact(fact: Fact)

    @Query("Delete From fact")
    suspend fun clearTable()

    @Query("Select * From fact")
    suspend fun getAll(): List<Fact>
}