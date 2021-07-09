package com.jt.stockquoteshiltmvvm.presentation

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: QuoteRepository
) : ViewModel() {

    val quote = MutableLiveData<String?>()

    val loading = MutableLiveData<Boolean>()

    fun onTriggerEvent(event: QuoteEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is QuoteEvent.GetQuoteEvent -> {
                            getQuote(event.symbol)
                    }
                }
            }catch (e: Exception){
                loading.value = false
                quote.value = e.toString()
                e.printStackTrace()
            }
        }
    }

    private suspend fun getQuote(symbol: String){
        loading.value = true

        // simulate a delay to show loading
        delay(1000)

        val quote = repository.getQuote(symbol)
        this.quote.value = quote.quote.toString()

        loading.value = false
    }
}
