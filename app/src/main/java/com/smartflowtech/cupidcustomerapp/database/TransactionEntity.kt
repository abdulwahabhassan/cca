package com.smartflowtech.cupidcustomerapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "amount")
    val amount: String,
    @ColumnInfo(name = "date")
    val date: String
        )