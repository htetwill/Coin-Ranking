import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.recyclerview.CustomDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest{
//    private val article = Article(
//            id = 119302,
//            title = "Q7 - Greatness starts, when you don't stop.",
//            changed = 1534311497,
//            created = 1511968425,
//            dateTime = "25.05.2018 14:13",
//            image = "https://www.apphusetreach.no/sites/default/files/audi_q7.jpg",
//            ingress = "The Audi Q7 is the result of an ambitious idea: never cease to improve.")

    private lateinit var db: CustomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, CustomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetArticle() = runBlocking {
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}