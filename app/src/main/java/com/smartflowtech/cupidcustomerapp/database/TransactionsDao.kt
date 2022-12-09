package com.smartflowtech.cupidcustomerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactions(transactions: List<TransactionEntity>)

    @Query("SELECT * FROM `transaction` ORDER BY date_time DESC")
    suspend fun getTransactions(): List<TransactionEntity>
}