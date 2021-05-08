import com.example.android.recyclerview.RetrofitFactory
import com.example.android.recyclerview.RetrofitInterface
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class RequestResponseTest {
    private lateinit var apiHelper: RetrofitInterface

    @Before
    fun setup() {
        apiHelper = RetrofitFactory.makeRetrofitService()
    }

    @Test
    fun fetchAndCheck200Code() = runBlocking {
        val actualResponse = apiHelper.getPosts()
        assertEquals(actualResponse.isSuccessful, true)
    }
    @Test
    fun fetchAndCheckArticle() = runBlocking {
        val actualResponse = apiHelper.getPosts()
        assertNotNull(actualResponse.body())
    }
}