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

class MainActivity : AppCompatActivity() {
    lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInfo = UserInfo()
        val maleCard: CardView = findViewById(R.id.cardViewMale)
        val femaleCard: CardView = findViewById(R.id.cardViewFemale)
        val heightSeekBar: SeekBar = findViewById(R.id.heightSeekBar)
        val heightTextView: TextView = findViewById(R.id.heightTextView)
        val weightSeekBar: SeekBar = findViewById(R.id.weightSeekBar)
        val weightTextView: TextView = findViewById(R.id.weightTextView)

        maleCard.setOnClickListener {
            selectGender( "Male")
            applyCorrectBackgroundToCards(it as CardView, femaleCard)
        }

        femaleCard.setOnClickListener {
            selectGender("Female")
            applyCorrectBackgroundToCards(it as CardView, maleCard)
        }

        heightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                val heightInMeters: Double = (progress.toDouble() / 100.0)
                userInfo.height = heightInMeters
                updateTextView(heightTextView, "$progress")
            }

            override fun onStartTrackingTouch(seek: SeekBar?) { }

            override fun onStopTrackingTouch(seek: SeekBar?) { }
        })

        weightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, progress: Int, fromUser: Boolean) {
                val heightInMeters: Double = (progress.toDouble() / 100.0)
                userInfo.height = heightInMeters
                updateTextView(heightTextView, "$progress")
            }

            override fun onStartTrackingTouch(seek: SeekBar?) { }

            override fun onStopTrackingTouch(seek: SeekBar?) { }
        })
    }

    private fun updateTextView(textView: TextView, message: String){
        textView.text = message
    }

    private fun selectGender(selectedGender: String){
        userInfo.gender = selectedGender
        println(userInfo.gender)
    }

    private fun applyCorrectBackgroundToCards(selected: CardView, other: CardView){
        selected.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.selected_card_background))
        other.setCardBackgroundColor(ContextCompat.getColor(applicationContext, R.color.card_background))
    }

    fun calculateBMI(view: View?){
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }
}

data class UserInfo(
    var gender: String = "",
    var height: Double = 1.0,
    var weight: Double = 1.0,
    var age: Int = 1
)