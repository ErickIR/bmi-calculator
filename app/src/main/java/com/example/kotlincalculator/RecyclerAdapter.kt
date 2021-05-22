package com.example.kotlincalculator

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincalculator.TimeStampConverter.toDate
import java.math.RoundingMode

class RecyclerAdapter(private val measurements: ArrayList<UserInfo>) :
        RecyclerView.Adapter<RecyclerAdapter.MeasurementHolder>() {

    class MeasurementHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private var userInfo: UserInfo? = null
        private lateinit var dayTextView: TextView
        private lateinit var dateTextView: TextView
        private lateinit var weightTextView: TextView
        private lateinit var weightUnitTextView: TextView
        private lateinit var isWeightLossTextView: TextView
        private lateinit var weightLossTextView: TextView
        private lateinit var bmiTextView: TextView

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bindUserInfo(userInfo: UserInfo){
            this.userInfo = userInfo
            bindView()
            dayTextView.text = userInfo.timeStamp?.toDate()?.dayOfWeek?.name
            val date = userInfo.timeStamp?.toDate()
            val dateText = "${date?.dayOfMonth.toString()}-${date?.monthValue}-${date?.year}"
            dateTextView.text = dateText
            val weigth = userInfo.weight.toBigDecimal().setScale(
                    2,
                    RoundingMode.HALF_EVEN
            ).toString()
            weightTextView.text = weigth
            isWeightLossTextView.text = if(userInfo.isWeighLoss) "LOSS" else "GAIN"
            val weightLoss = userInfo.weightDiff.toBigDecimal().setScale(
                    2,
                    RoundingMode.HALF_EVEN
            ).toString()
            weightLossTextView.text = weightLoss
            bmiTextView.text = userInfo.bmiClass.toString()
        }

        private fun bindView(){
            dayTextView = view.findViewById(R.id.dayTextView)
            dateTextView = view.findViewById(R.id.dateTextView)
            weightTextView = view.findViewById(R.id.weightTextView)
            weightUnitTextView = view.findViewById(R.id.weightUnitTextView)
            isWeightLossTextView = view.findViewById(R.id.isWeightLossTextView)
            weightLossTextView = view.findViewById(R.id.weightLossTextView)
            bmiTextView = view.findViewById(R.id.bmiTextView)
        }

        companion object {
            private val MEASUREMENT_KEY = "MEASUREMENT"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementHolder {
        val inflatedView = parent.inflate(R.layout.result_item_row, false)
        return MeasurementHolder(inflatedView)
    }

    override fun getItemCount() = measurements.size

    override fun onBindViewHolder(holder: MeasurementHolder, position: Int) {
        val itemUserInfo = measurements[position]
        holder.bindUserInfo(itemUserInfo)
    }

}