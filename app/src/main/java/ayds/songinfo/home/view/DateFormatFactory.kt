package ayds.songinfo.home.view

import java.time.Month

interface DateFormatFactory {
    fun getDate(precision:String,date:String ): DateSong
}

object DateFormatFactoryImpl: DateFormatFactory{
    private var dateArray =  emptyArray<String>()

   override fun getDate(precision:String, date:String):DateSong{

        dateArray = date.split("-").toTypedArray()
        return when (precision){
            "day"-> DayFactory(dateArray)
            "month" -> MonthFactory(dateArray)
            "year" -> YearFactory(dateArray)
            else -> DefautFactory(dateArray)
        }
    }
}

sealed class DateSong{
    abstract fun createDate():String

}

private class DayFactory(val dateArray: Array<String>): DateSong() {
    override fun createDate(): String {
        val day = dateArray[2]
        val month = dateArray[1]
        val year = dateArray[0]
        return "$day/$month/$year"
    }
}

private class MonthFactory(val dateArray: Array<String>): DateSong(){
    override fun createDate(): String {
        val month = dateArray[1].toInt()
        val year = dateArray[0]
        val nameMonth = Month.of(month).toString()
        return "$nameMonth, $year"
    }
}

private class YearFactory(val dateArray: Array<String>): DateSong(){
    override fun createDate(): String {
        val year = dateArray[0].toInt()
        val isLeapYear = isLeapYear(year)
        return "$year $isLeapYear"
    }

    fun isLeapYear(year: Int): String{
        val result = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
        return if (result) "leap year" else "not leap year"
    }
}

private class DefautFactory(val dateArray: Array<String>): DateSong(){
    override fun createDate(): String {
        return dateArray.joinToString("-")
    }
}