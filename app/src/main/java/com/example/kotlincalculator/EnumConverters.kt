package com.example.kotlincalculator

import androidx.room.TypeConverter


class GenderConverters {
    @TypeConverter
    fun toGender(value: String) = enumValueOf<Gender>(value)

    @TypeConverter
    fun fromGender(value: Gender) = value.name
}

class BmiClassConverters {
    @TypeConverter
    fun toBmiClass(value: String) = enumValueOf<BmiClass>(value)

    @TypeConverter
    fun fromBmiClass(value: BmiClass) = value.name
}