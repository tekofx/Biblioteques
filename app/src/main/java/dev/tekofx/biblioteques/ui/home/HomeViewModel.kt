package dev.tekofx.biblioteques.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tekofx.biblioteques.dto.LibraryResponse
import dev.tekofx.biblioteques.model.Library
import dev.tekofx.biblioteques.repository.LibraryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val repository: LibraryRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>(false)
    val libraries = MutableLiveData<List<Library>>()
    private val _currentLibrary = MutableLiveData<Library?>()
    val currentLibrary: LiveData<Library?> = _currentLibrary
    val errorMessage = MutableLiveData<String>()
    private var _libraries = MutableLiveData<List<Library>>()
    var queryText by mutableStateOf("")
        private set

    init {
        getLibraries()
    }

    fun getLibrary(pointId: String) {
        Log.d(
            "HomeViewModel",
            "getLibrary called with pointId: $pointId"
        )
        isLoading.postValue(true)
        val response = repository.getLibrary(pointId)
        response.enqueue(object : Callback<LibraryResponse> {
            override fun onResponse(
                call: Call<LibraryResponse>,
                response: Response<LibraryResponse>
            ) {

                _currentLibrary.postValue(response.body()?.elements?.get(0))
                isLoading.postValue(false)
            }

            override fun onFailure(call: Call<LibraryResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                isLoading.postValue(false)
            }
        })
    }

    fun getLibraries() {
        Log.d("HomeViewModel", "getLibraries called")
        isLoading.postValue(true)
        val response = repository.getLibraries()
        response.enqueue(object : Callback<LibraryResponse> {
            override fun onResponse(
                call: Call<LibraryResponse>,
                response: Response<LibraryResponse>
            ) {

                libraries.postValue(response.body()?.elements)
                _libraries.postValue(response.body()?.elements)
                isLoading.postValue(false)
            }

            override fun onFailure(call: Call<LibraryResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
                isLoading.postValue(false)
            }
        })

    }

    fun getLibraryTest(pointId: String) {
        println("test")
        _libraries.value?.forEach() {
            println(it)
        }
        _currentLibrary.postValue(_libraries.value?.filter {
            it.puntId == pointId
        }?.get(0))
    }

    fun onSearchTextChanged(text: String) {
        queryText = text
        libraries.postValue(_libraries.value?.filter {
            it.adrecaNom.contains(
                text,
                ignoreCase = true
            ) || it.municipiNom.contains(text, ignoreCase = true)
        })


    }
}