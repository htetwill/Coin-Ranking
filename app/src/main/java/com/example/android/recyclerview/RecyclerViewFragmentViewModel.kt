package com.example.android.recyclerview

import androidx.annotation.UiThread
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.android.common.logger.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecyclerViewFragmentViewModel(private val repo: ArticleRepository): ViewModel() {
    // Expose to the outside world
    private val post: MutableLiveData<ResultOf<Post>> by lazy {
        MutableLiveData<ResultOf<Post>>().also {
                fetchPost()
        }
    }

    val pagedList: LiveData<PagedList<Article>> = repo.getList()
    val resultOfPost: LiveData<ResultOf<Post>> = post

    fun refreshUI(listOfArticle: List<Article>?) {
        Log.d("TAG", "refreshUI: ")
        if (listOfArticle != null && listOfArticle.isNotEmpty()) {
            //TODO comparing two list (remote vs local) with update date properties https://stackoverflow.com/questions/52054104/comparing-two-lists-in-kotlin
            repo.save(listOfArticle)
        }
    }

    private fun fetchPost() {
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getPosts()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        post.postValue(ResultOf.Success(response.body()!!))
                    } else {
                        post.postValue(ResultOf.Failure("Error: ${response.code()}", null))
                    }
                }
            } catch (e: HttpException) {
                    post.postValue(ResultOf.Failure(null, e))
            } catch (e: Throwable) {
                    post.postValue(ResultOf.Failure(null, e))
            }
        }
    }

    class Factory(private val repo: ArticleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecyclerViewFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecyclerViewFragmentViewModel(repo) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
