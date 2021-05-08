package com.example.android.recyclerview

import androidx.lifecycle.*
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Suppress("SpellCheckingInspection")
class RecyclerViewFragmentViewModel(private val repo: ArticleRepository) : ViewModel() {
    private val post: MutableLiveData<ResultOf<Post>> = MutableLiveData()
    val pagedList: LiveData<PagedList<Article>> = repo.getList()

    init {
        viewModelScope.launch { fetchPost() }
    }

    fun getPost(): LiveData<ResultOf<Post>> = post

    fun refreshUI(listOfArticle: List<Article>?) {
        if (listOfArticle != null && listOfArticle.isNotEmpty()) {
            val changedList = listOfArticle.filterIndexed{
                _, value ->
                !pagedList.value!!.contains(value)
            }
            for (value in changedList) {
                repo.delete(value.id)
            }
            repo.save(listOfArticle)
        }
    }


    fun fetchPost() {
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getPosts()
                if (response.isSuccessful) post.postValue(ResultOf.Success(response.body()!!))
                else post.postValue(ResultOf.Failure("Error: ${response.code()}. Please contact the developer", Exception()))
            } catch (e: HttpException) {
                post.postValue(ResultOf.Failure(e.message, e))
            } catch (e: Throwable) {
                post.postValue(ResultOf.Failure(e.message, e))
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
