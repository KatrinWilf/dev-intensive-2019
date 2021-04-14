package ru.skillbranch.devintensive.utils

import androidx.core.text.buildSpannedString

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        //TODO FIX ME!!!
        when (fullName?.trim()) {
            "" -> return null to null
            else -> {
                val parts
                        : List<String>
                ? = fullName?.split(" ")
                val firstName = parts?.getOrNull(0)
                val lastName = parts?.getOrNull(1)
                // return Pair(firstName,lastName)
                return firstName to lastName
            }
        }

    }

    fun transliteration(payload: String, divider: String = " "): String {

        val symbolsMap = mapOf(
            "а" to "a", "б" to "b", "в" to "v", "г" to "g", "д" to "d",
            "е" to "e", "ё" to "e", "ж" to "zh", "з" to "z", "и" to "i",
            "й" to "i", "к" to "k", "л" to "l", "м" to "m", "н" to "n",
            "о" to "o", "п" to "p", "р" to "r", "с" to "s", "т" to "t",
            "у" to "u", "ф" to "f", "х" to "h", "ц" to "c", "ч" to "ch",
            "ш" to "sh", "щ" to "sh'", "ъ" to "", "ы" to "i", "ь" to "",
            "э" to "e", "ю" to "yu", "я" to "ya"
        )
        var payloadEn: String = buildString {
            for (symb in payload) {
                when {
                    symb.isUpperCase() ->
                        append(symbolsMap[symb.toLowerCase().toString()]?.capitalize() ?: symb)
                    else -> append(symbolsMap[symb.toString()] ?: symb)

                }
            }
        }
        payloadEn = payloadEn.replace(" ", divider)
        return payloadEn

    }

    fun toInitials(firstName: String?, lastName: String?): String? =

        when {
            firstName.isNullOrBlank() && lastName.isNullOrBlank() -> null
            else -> listOfNotNull(firstName, lastName)
                .map {
                    when {
                        it.isEmpty() -> ""
                        else -> it.first().toUpperCase()
                    }
                }
                .joinToString("")
        }

    fun wordInCase(
        inputNum: Int,
        firstForm: String,
        secondForm: String,
        thirdForm: String
    ): String {

        var devNum = inputNum % 10
        var devNum100 = inputNum % 100
        return when (devNum100) {
            in 11..19 -> thirdForm
            else -> when (devNum) {
                    1 -> firstForm
                    2, 3, 4 -> secondForm
                    5, 6, 7, 8, 9, 0 -> thirdForm
                    else ->firstForm
                }
        }

    }

}