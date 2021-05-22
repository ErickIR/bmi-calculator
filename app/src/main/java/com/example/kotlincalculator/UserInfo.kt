package com.example.kotlincalculator

import androidx.room.*
import java.util.*

@Entity(tableName = "user_info")
data class UserInfo(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "gender")
        @TypeConverters(GenderConverters::class)
        val gender: Gender,
        @ColumnInfo(name = "height") val height: Double,
        @ColumnInfo(name = "weight") val weight: Double,
        @ColumnInfo(name = "age") val age: Int,
        @ColumnInfo(name = "time_stamp")
        @TypeConverters(TimeStampConverter::class)
        val timeStamp: String?,
        @ColumnInfo(name = "bmi") val bmi: Double,
        @ColumnInfo(name = "bmiClass")
        @TypeConverters(BmiClassConverters::class)
        val bmiClass: BmiClass,
        @ColumnInfo(name = "isWeightLoss") val isWeighLoss: Boolean,
        @ColumnInfo(name = "weightDiff") val weightDiff: Double
)

enum class Gender{
        MALE, FEMALE, UNSPECIFIED
}

enum class BmiClass {
        UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESITY
}