package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.models.User.Factory.makeUser
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user2 = User("2", "John", "Cena")
    }

    @Test
    fun test_factory() {
//        val user = User.makeUser("John Cena")
//        val user2 = User.makeUser("John Wick")
        val user = makeUser("John Wick")
        val user2 = user.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user \n$user2")
    }

    @Test
    fun test_decomposition() {
        val user = makeUser("John Wick")

        fun getUserInfo() = user

        val (id, firstName, lastName) = getUserInfo()

        println("$id, $firstName, $lastName")
        println("${user.component1()}, ${user.component2()}, ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = makeUser("Jhon Wick")
        var user2 = user.copy(lastVisit = Date())
        var user3 = user.copy(lastVisit = Date().add(-2, TimeUnits.SECOND))
        var user4 = user.copy(lastName = "Cena", lastVisit = Date().add(2, TimeUnits.HOUR))

        println(
            """
            ${user.lastVisit?.format()}
            ${user2.lastVisit?.format()}
            ${user3.lastVisit?.format()} 
            ${user4.lastVisit?.format()}
        """.trimIndent()
        )


//        if (user == user2) {
//            println("equals data and hash ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
//        } else {
//            println("not equals data and hash ${user.hashCode()} $user \n ${user2.hashCode()} $user2")
//        }
//
//        if (user === user2) {
//            println("equals address ${System.identityHashCode(user)} ${System.identityHashCode(user2)}")
//        } else {
//            println("not equals address ${System.identityHashCode(user)} ${System.identityHashCode(user2)}")
//        }
    }

    @Test
    fun test_data_maping() {
        val user = User.makeUser("Макеев Михаил")
        val newUser = user.copy(lastVisit = Date().add(-4, TimeUnits.DAY))
        println(newUser)

        val userView = user.toUserView()
        userView.printMe()

    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Макеев Михаил")
        val txtMessage =
            BaseMessage.makeMessage(user, Chat("0"), payload = "any text message", type = "text")
        val imgMessage =
            BaseMessage.makeMessage(user, Chat("0"), payload = "any image url", type = "image")

//        when(imgMessage) {
//            is BaseMessage -> println("this is base message")
//            is TextMessage -> println("this is text message")
//            is ImageMessage -> println("this is image message")
//        }

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_parse_full_name() {
        val (firstName, lastName) = Utils.parseFullName("null")
        val (firstName2, lastName2) = Utils.parseFullName("")
        val (firstName3, lastName3) = Utils.parseFullName(" ")
        val (firstName4, lastName4) = Utils.parseFullName("John")

        println("$firstName \n$lastName\n")
        println("$firstName2 \n$lastName2\n")
        println("$firstName3 \n$lastName3\n")
        println("$firstName4 \n$lastName4\n")

    }

    @Test
    fun test_to_initials() {
        val initials = Utils.toInitials("john", "doe")
        val initials2 = Utils.toInitials("John", null)
        val initials3 = Utils.toInitials(null, null)
        val initials4 = Utils.toInitials("", "")
        val initials5 = Utils.toInitials("", "liom")
        println("$initials")
        println("$initials2")
        println("$initials3")
        println("$initials4")
        println("$initials5")
    }


    @Test
    fun test_transliteration() {
        val trans = Utils.transliteration("Женя Стереотипов") //Zhenya Stereotipov
        val trans2 = Utils.transliteration("Amazing Петр", "_") //Amazing_Petr
        println("$trans")
        println("$trans2")
    }

    @Test
    fun test_humanize_diff() {
        var date1 = Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
        var date2 = Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
        var date3 = Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
        var date4 = Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
        var date5 = Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
        var date6 = Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
        var date7 = Date().humanizeDiff()//сейчас
        var date8 = Date().add(-332, TimeUnits.DAY).humanizeDiff() //11 месяцев назад

        println("${Date()} - ${Date().add(-2, TimeUnits.HOUR)} = $date1")
        println("${Date()} - ${Date().add(-5, TimeUnits.DAY)} = $date2")
        println("${Date()} - ${Date().add(2, TimeUnits.MINUTE)} = $date3")
        println("${Date()} - ${Date().add(7, TimeUnits.DAY)} = $date4")
        println("${Date()} - ${Date().add(-400, TimeUnits.DAY)} = $date5")
        println("${Date()} - ${Date().add(400, TimeUnits.DAY)} = $date6")
        println("${Date()} - ${Date()} = $date7")
        println("${Date()} - ${Date().add(-332, TimeUnits.DAY)} = $date8")
    }

    @Test
    fun test_time_unit_plural() {
        var tup1 = TimeUnits.SECOND.plural(1) //1 секунду
        var tup2 =TimeUnits.MINUTE.plural(4) //4 минуты
        var tup3 =TimeUnits.HOUR.plural(19) //19 часов
        var tup4 =TimeUnits.DAY.plural(222) //222 дня

        println("$tup1")
        println("$tup2")
        println("$tup3")
        println("$tup4")
    }

    @Test
    fun test_string_truncate(){
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        println("A  ".truncate(3))
        println("A  ".truncate(2))
    }

    @Test
    fun test_string_stripHTML(){
        var st = """<p class="title">Образовательное IT-сообщество Skill Branch</p>"""
        println(st.stripHtml())  //Образовательное IT-сообщество Skill Branch
       var st2 = """<p>Образовательное       IT-сообщество Skill Branch</p>"""
        println(st2.stripHtml()) // Образовательное IT-сообщество Skill Branch

        var st3 = """<p>Образовательное       IT-сообщество &quote Skill   '/n'  Branch</p>"""
        println(st3.stripHtml()) // Образовательное IT-сообщество Skill Branch
    }

    @Test
    fun test_builder() {

        val s = "sss"
        val n = 8
        val d = Date()
        val b = true
        val user:User = User.Builder().id(s)
            .firstName(s)
            .lastName(s)
            .avatar(s)
            .rating(n)
            .respect(n)
            .lastVisit(d)
            .isOnline(b)
            .build()
        print("$user")
    }

}