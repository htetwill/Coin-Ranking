package com.example.android.recyclerview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RecyclerViewFragmentViewModel : ViewModel() {
    // Expose to the outside world
    private val post: MutableLiveData<ResultOf<Post>> by lazy {
        MutableLiveData<ResultOf<Post>>().also {
            kotlin.runCatching {
                fetchPost()
            }.exceptionOrNull()
        }
    }
    val resultOfPost: LiveData<ResultOf<Post>> = post

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

    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecyclerViewFragmentViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecyclerViewFragmentViewModel() as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
