package com.example.kotlincalculator

import kotlin.math.pow

class BmiCalculator {
    fun calculateBmi(weight: Double, height: Double): Double {
        if(weight <= 0 || height <= 0) {
            throw IllegalArgumentException("Arguments cannot be Zero.")
        }

        return weight / height.pow(2)
    }

}