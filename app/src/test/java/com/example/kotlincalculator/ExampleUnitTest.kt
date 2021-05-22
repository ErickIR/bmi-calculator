package com.example.kotlincalculator

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class BmiCalculatorTest {
    @Test
    fun bmiCalculator_CalculateBMI_ReturnsCorrectResult() {
        val weight = 72.57
        val height = 1.78

        val bmiCalculator = BmiCalculator()

        val result: Double = bmiCalculator.calculateBmi(weight, height)

        assertEquals(22.904305011993433, result, 0.0043)
    }
}