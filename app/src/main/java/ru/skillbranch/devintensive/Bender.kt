package ru.skillbranch.devintensive

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME, var countFailAnswer: Int = 0) {


    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        var validationResult: Pair<Boolean, String> = question.validateAnswer(answer)
        return when (validationResult.first) {
            true -> "${validationResult.second}\n${question.question}" to status.color
            false -> if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                countFailAnswer++
                when (countFailAnswer) {
                    4 -> {
                        countFailAnswer = 0
                        status = Status.NORMAL
                        question = Question.NAME
                        "Это не правильный ответ. Давай все по новой\n${question.question}" to status.color
                    }
                    else -> {
                        status = status.nextStatus()
                        "Это не правильный ответ!\n${question.question}" to status.color
                    }
                }
            }
        }
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return if (answer.first().isLowerCase()) {
                    true to "Имя должно начинаться с заглавной буквы"
                } else {
                    false to ""
                }
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return if (answer.first().isUpperCase()) {
                    true to "Профессия должна начинаться со строчной буквы"
                } else {
                    false to ""
                }
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return if (answer.contains("""[0-9]+""".toRegex())) {
                    true to "Материал не должен содержать цифры"
                } else {
                    false to ""
                }
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return if (!answer.matches("""[0-9]+""".toRegex())) {
                    true to "Год моего рождения должен содержать только цифры"
                } else {
                    false to ""
                }
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): Pair<Boolean, String> {
                return if (answer.length!=7||!answer.matches("""[0-9]+""".toRegex())) {
                    true to "Серийный номер содержит только цифры, и их 7"
                } else {
                    false to ""
                }
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE

            override fun validateAnswer(answer: String): Pair<Boolean, String> = false to ""


        };

        abstract fun nextQuestion(): Question

        abstract fun validateAnswer(answer: String): Pair<Boolean, String>
    }
}