import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.recyclerview.DateTimeConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DateTimeConverterTest{
    private val inputDateTime = "25.05.2018 14:13"
    private val expectedDate = "25 May 2018"
    private val expectedTimeIn12 = "02:13 PM"
    private val expectedTimeIn24 = "14:13"

    @Test
    fun customDateTest() {
        assertEquals(DateTimeConverter.toCustomDate(inputDateTime), expectedDate)
    }
    @Test
    fun customTime12HourTest() {
        assertEquals(DateTimeConverter.toCustomTime(inputDateTime, false), expectedTimeIn12)
    }
    @Test
    fun customTime24HourFormatTest() {
        assertEquals(DateTimeConverter.toCustomTime(inputDateTime, true), expectedTimeIn24)
    }
}