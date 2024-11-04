package dev.tekofx.biblioteques.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tekofx.biblioteques.dto.BookResponse
import dev.tekofx.biblioteques.model.book.Book
import dev.tekofx.biblioteques.repository.BookRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel(private val repository: BookRepository) : ViewModel() {
    private var _books = MutableLiveData<List<Book>>()
    val books = MutableLiveData<List<Book>>()
    val isLoading = MutableLiveData<Boolean>(false)
    val currentBook = MutableLiveData<Book>()

    var queryText by mutableStateOf("")
        private set
    val errorMessage = MutableLiveData<String>()

    fun getBook(query: String) {
        val response = repository.getBook(query)
        println(response.toString())
        isLoading.postValue(true)

        response.enqueue(object : Callback<BookResponse> {
            override fun onResponse(
                call: Call<BookResponse>, response: Response<BookResponse>
            ) {
                _books.postValue(response.body()?.books ?: arrayListOf())
                books.postValue(response.body()?.books ?: arrayListOf())
                isLoading.postValue(false)

            }

            override fun onFailure(p0: Call<BookResponse>, t: Throwable) {
                Log.e("BookViewModel", t.message.toString())
                errorMessage.postValue(t.message)
                isLoading.postValue(false)

            }

        })

    }

    fun filterBook(string: String) {
        println(string)
        println(_books.value)
        currentBook.postValue(_books.value?.find { book: Book -> book.id == string })
    }
}