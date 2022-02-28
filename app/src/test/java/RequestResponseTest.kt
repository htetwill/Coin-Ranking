import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class RequestResponseTest {
//    private lateinit var apiHelper: CustomApi

    @Before
    fun setup() {
//        apiHelper = RetrofitModule.makeRetrofitService()
    }

    @Test
    fun fetchAndCheck200Code() = runBlocking {
//        val actualResponse = apiHelper.fetchPost()
//        assertEquals(actualResponse.isSuccessful, true)
    }
    @Test
    fun fetchAndCheckArticle() = runBlocking {
//        val actualResponse = apiHelper.fetchPost()
//        assertNotNull(actualResponse.body())
    }
}