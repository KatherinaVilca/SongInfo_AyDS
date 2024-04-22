package ayds.songinfo.home.view

import java.time.Month

private const val DAY_INDEX= 2
private const val MONTH_INDEX = 1
private const val YEAR_INDEX = 0
interface DateFormatFactory {
    fun getDateFormat(precision:String,date:String ): DateFormat
}

object DateFormatFactoryImpl: DateFormatFactory{
    private var dateArray =  emptyArray<String>()

   override fun getDateFormat(precision:String, date:String):DateFormat{

        dateArray = date.split("-").toTypedArray()
        return when (precision){
            "day"-> DayFormat(dateArray)
            "month" -> MonthFormat(dateArray)
            "year" -> YearFormat(dateArray)
            else -> DefaultFormat(dateArray)
        }
    }
}

sealed class DateFormat{
    abstract fun createDate():String

}

private class DayFormat(val dateArray: Array<String>): DateFormat() {
    override fun createDate(): String {
        val day = dateArray[DAY_INDEX]
        val month = dateArray[MONTH_INDEX]
        val year = dateArray[YEAR_INDEX]
        return "$day/$month/$year"
    }
}

private class MonthFormat(val dateArray: Array<String>): DateFormat(){
    override fun createDate(): String {
        val month = dateArray[MONTH_INDEX].toInt()
        val year = dateArray[YEAR_INDEX]
        val nameMonth = Month.of(month).toString()
        return "$nameMonth, $year"
    }
}

private class YearFormat(val dateArray: Array<String>): DateFormat(){
    override fun createDate(): String {
        val year = dateArray[YEAR_INDEX].toInt()
        return "$year ${ if (isLeapYear(year)) "leap year" else "not leap year"}"
    }

    fun isLeapYear(year: Int): Boolean{
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

}

private class DefaultFormat(val dateArray: Array<String>): DateFormat(){
    override fun createDate(): String {
        return dateArray.joinToString("-")
    }
}