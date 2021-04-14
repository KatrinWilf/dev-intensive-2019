package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = Date(),
    val isOnline: Boolean = false

) {
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String) : this(id, "John", "Doe")

    private constructor(builder: Builder) : this(
        builder.id,
        builder.firstName,
        builder.lastName,
        builder.avatar,
        builder.rating,
        builder.respect,
        builder.lastVisit,
        builder.isOnline
    )

    init {
        println(
            "It's Alive!!! \n" +
                    "${if (lastName == "Doe") "His name $id $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n"
        )
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }


        inline fun build(block: Builder.() ->Unit) = Builder().apply(block).build()


    class Builder {
        var id: String="0"
        var firstName: String?=""
        var lastName: String?=""
        var avatar: String?=""
        var rating: Int = 0
        var respect: Int = 0
        var lastVisit: Date? = Date()
        var isOnline: Boolean = false


        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String?) = apply { this.firstName = firstName }
        fun lastName(lastName: String?) = apply { this.lastName = lastName }
        fun avatar(avatar: String?) = apply { this.avatar = avatar }
        fun rating(rating: Int = 0) = apply { this.rating = rating }
        fun respect(respect: Int = 0) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date? = Date()) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean = false) = apply { this.isOnline = isOnline }

        fun build() = User(this)


//        data class Builder(
//            var model: String? = null,
//            var color: String? = null,
//            var type: String? = null) {
//
//            fun model(model: String) = apply { this.model = model }
//            fun color(color: String) = apply { this.color = color }
//            fun type(type: String) = apply { this.type = type }
//            fun build() = Car(model, color, type)

    }

}