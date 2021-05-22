package com.example.kotlincalculator

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object TimeStampConverter {
    private val pattern: String = "yyyy-MM-dd HH:mm:ss"

    fun Date.fromDate(): String? {
        try {
            return SimpleDateFormat(pattern, Locale.US)
                .format(this)
        } catch (ex: ParseException){
            ex.printStackTrace()
        }
        return null
    }

    fun String.toDate(): LocalDate? {
        try {
            val simpleFormatter = DateTimeFormatter.ofPattern(pattern)
            return LocalDate.parse(this, simpleFormatter)
        } catch (ex: ParseException) {
            ex.printStackTrace()
        }
        return null
    }
}