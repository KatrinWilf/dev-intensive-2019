package ru.skillbranch.devintensive.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import ru.skillbranch.devintensive.utils.Utils
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.Period
import java.time.ZoneId
import java.util.*


const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 25 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
//        else -> throw IllegalStateException("invalid unit")
    }

    this.time = time
    return this
}


@RequiresApi(Build.VERSION_CODES.O)
fun Date.humanizeDiff(date: Date = Date()): String {
    //var currentDate = Date()
    //var compareDate = this.compareTo(currentDate)
    val dateNeg = when {
        date < this -> true
        else -> false
    }

    var diffMillisec = date.time - this.time


    var difInHour = diffMillisec / HOUR * when {
        dateNeg -> -1
        else -> 1
    }
    var difInMinute = diffMillisec / MINUTE * when {
        dateNeg -> -1
        else -> 1
    }


    val _firstDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    val _secondDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val period = when {
        dateNeg -> Period.between(_secondDate, _firstDate)
        else -> Period.between(_firstDate, _secondDate)
    }

    val _year = period.years
    val _month = period.months
    val _days = period.days

    val txtAfter = when {
        dateNeg -> "через "
        else -> ""
    }
    val txtBefore = when {
        !dateNeg -> "назад"
        else -> ""
    }
    return when {
        date == this -> "сейчас"
        _year > 0 && dateNeg -> "более чем через год"
        _year > 0 && !dateNeg -> "более года назад"
        _month > 0 -> "$txtAfter$_month ${Utils.wordInCase(
            _month,
            "месяц",
            "месяца",
            "месяцев"
        )} $txtBefore"
        _days > 0 -> "$txtAfter$_days ${Utils.wordInCase(_days, "день", "дня", "дней")} $txtBefore"
        difInHour > 0 -> "$txtAfter$difInHour ${Utils.wordInCase(
            difInHour.toInt(),
            "час",
            "часа",
            "часов"
        )} $txtBefore"
        difInMinute > 0 -> "$txtAfter$difInMinute ${Utils.wordInCase(
            difInMinute.toInt(),
            "минута",
            "минуты",
            "минут"
        )} $txtBefore"
        else -> "сейчас"
    }
}

fun TimeUnits.plural(value: Int):String{
    return when (this) {
        TimeUnits.DAY -> Utils.wordInCase(value, "день", "дня", "дней")
        TimeUnits.MINUTE -> Utils.wordInCase(value, "минута", "минуты", "минут")
        TimeUnits.HOUR -> Utils.wordInCase(value, "час", "часа", "часов")
        TimeUnits.SECOND -> Utils.wordInCase(value, "секунда", "секунды", "секунд")
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}