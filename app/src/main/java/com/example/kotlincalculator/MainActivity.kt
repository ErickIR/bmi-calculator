package com.example.kotlincalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.kotlincalculator.TimeStampConverter.fromDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private var weight: Double = 0.0
    private var height: Double = 0.0
    private var gender: Gender = Gender.UNSPECIFIED
    private var age: Int = 1
    private var isWeightLoss = false
    private var weightDiff = 0.0
    private lateinit var bmiCalculator: BmiCalculator

    private lateinit var maleCard: CardView
    private lateinit var femaleCard: CardView
    private lateinit var heightSeekBar: SeekBar
    private lateinit var heightTextView: TextView
    private lateinit var weightSeekBar: SeekBar
    private lateinit var weightTextView: TextView
    private lateinit var ageSeekBar: SeekBar
    private lateinit var ageTextView: TextView
    private lateinit var historyBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bmiCalculator = BmiCalculator()
        maleCard = findViewById(R.id.cardViewMale)
        femaleCard = findViewById(R.id.cardViewFemale)
        heightSeekBar = findViewById(R.id.heightSeekBar)
        heightTextView = findViewById(R.id.heightTextView)
        weightSeekBar = findViewById(R.id.weightSeekBar)
        weightTextView = findViewById(R.id.weightTextView)
        ageSeekBar = findViewById(R.id.ageSeekBar)
        ageTextView = findViewById(R.id.ageTextView)
        historyBtn = findViewById(R.id.btnHistory)

        historyBtn.setOnClickListener {
            goToResultActivity()
        }

        maleCard.setOnClickListener {
            selectGender( Gender.MALE)
            applyCorrectBackgroundToCards(it as CardView, femaleCard)
        }

        femaleCard.setOnClickListener {
            selectGender(Gender.FEMALE)
            applyCorrectBackgroundToCards(it as CardView, maleCard)
        }

        heightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                val heightInMeters: Double = (progress.toDouble() / 100.0)
                height = heightInMeters
                updateTextView(heightTextView, "$progress")
            }

            override fun onStartTrackingTouch(seek: SeekBar?) { }

            override fun onStopTrackingTouch(seek: SeekBar?) { }
        })

        weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                val weightInKg: Double = (progress.toDouble() * 0.453592)
                weight = weightInKg
                updateTextView(weightTextView, "$progress")
            }

            override fun onStartTrackingTouch(seek: SeekBar?) { }

            override fun onStopTrackingTouch(seek: SeekBar?) { }
        })

        ageSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                age = progress
                updateTextView(ageTextView , "$progress")
            }

            override fun onStartTrackingTouch(seek: SeekBar?) { }

            override fun onStopTrackingTouch(seek: SeekBar?) { }
        })
    }

    private fun updateTextView(textView: TextView, message: String){
        textView.text = message
    }

    private fun selectGender(selectedGender: Gender){
        gender = selectedGender
    }

    private fun applyCorrectBackgroundToCards(selected: CardView, other: CardView){
        selected.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.selected_card_background))
        other.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.card_background))
    }

    fun calculateBMI(view: View?){
        saveMeasurementToLocalDb()
    }

    private fun goToResultActivity() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }


    private fun saveMeasurementToLocalDb(){
        val validation = validateInfo()

        if(validation.isNotEmpty()){
            Toast.makeText(applicationContext, validation, Toast.LENGTH_SHORT).show()
            return
        }
        val db = AppDatabase(this)

        val timeStamp = Date().fromDate()
        val bmi = bmiCalculator.calculateBmi(weight, height)
        val bmiClass = getWeightClass(bmi)

        GlobalScope.launch {
            println("Calculate weight loss started")
            val lastMeasurement = db.userDao().getLastMeasurement()
            val lastWeight: Double = lastMeasurement?.weight ?: 0.0
            weightDiff = lastWeight - weight
            isWeightLoss = weightDiff > 0
            println("Calculate weight loss finished")
            println("Save info in userInfo start")
            val userInfo = UserInfo(
                    gender = gender, height = height, weight = weight,
                    age =  age, timeStamp = timeStamp, bmi =  bmi, bmiClass = bmiClass,
                    isWeighLoss = isWeightLoss, weightDiff = weightDiff)
            println("Save info in userInfo finish")
            println("Insert started")
            db.userDao().insertUserInfo(userInfo)
            println("Insert finished")
            resetInfo()
        }
        println("All finished")
        goToResultActivity()
    }

//    private suspend fun calculateWeightLoss(db: AppDatabase) {
//
//    }

    private fun getWeightClass(bmi: Double): BmiClass{
        return when {
            bmi < 18.5 -> BmiClass.UNDERWEIGHT
            bmi in 18.5..24.9 -> BmiClass.NORMAL
            bmi in 25.0..29.9 -> BmiClass.OVERWEIGHT
            bmi >= 30.0 -> BmiClass.OBESITY
            else -> BmiClass.OBESITY
        }
    }

    private fun resetInfo() {
        gender = Gender.UNSPECIFIED
        maleCard.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.card_background))
        femaleCard.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.card_background))
        height = 1.0
        heightTextView.text = height.toInt().toString()
        heightSeekBar.progress = height.toInt()
        weight = 1.0
        weightTextView.text = weight.toInt().toString()
        weightSeekBar.progress = weight.toInt()
        age = 0
        ageTextView.text = age.toString()
        ageSeekBar.progress = age
    }

    private fun validateInfo(): String {
        return when {
            gender == Gender.UNSPECIFIED -> "You must select a gender."
            age <= 1 -> "You must select a age higher than 1."
            height <= 0.01 -> "You must select a height higher than 1."
            weight <= 1 -> "You must select a weight higher than 1."
            else -> ""
        }
    }
}

