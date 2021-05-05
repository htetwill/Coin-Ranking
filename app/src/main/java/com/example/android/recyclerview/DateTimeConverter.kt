package com.example.android.recyclerview

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


class DateTimeConverter {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun toCustomDate(input: String): String {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy' 'HH:mm")
            val sameYearFormatter = SimpleDateFormat("d MMMM")
            val diffYearFormatter = SimpleDateFormat("d MMMM yyyy")
            val inputCal = Calendar.getInstance()
            inputCal.time = inputFormat.parse(input)
            val currentCal = Calendar.getInstance()

            return if (inputCal.get(Calendar.YEAR) == currentCal.get(Calendar.YEAR))
                sameYearFormatter.format(inputCal.time)
            else
                diffYearFormatter.format(inputCal.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun toCustomTime(input: String?, is24Hours: Boolean): String {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy' 'HH:mm")
            val inputCal = Calendar.getInstance()
            inputCal.time = inputFormat.parse(input)
            val use24HourFormatter = SimpleDateFormat("HH:mm")
            val use12HourFormatter = SimpleDateFormat("hh:mm aaa")

            return if (is24Hours) use24HourFormatter.format(inputCal.time)
            else
                use12HourFormatter.format(inputCal.time)
        }
    }
}
