package com.example.kotlincalculator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info ORDER BY id DESC")
    suspend fun getAll(): List<UserInfo>

    @Insert
    suspend fun insertUserInfo(vararg user: UserInfo)

    @Query("SELECT * FROM user_info ORDER BY id DESC LIMIT 1 ")
    suspend fun getLastMeasurement(): UserInfo?
}