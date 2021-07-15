package com.jt.stockquoteshiltmvvm.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository
import com.jt.stockquoteshiltmvvm.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var quoteRepository: QuoteRepository

    @Mock
    lateinit var observer: Observer<Boolean>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainViewModel(quoteRepository)
    }

    @Test
    fun `Verify livedata values changes on event`() {
        //Given
        val event = QuoteEvent.GetQuoteEvent("TEST")
        viewModel.getLoading().observeForever(observer)

        //When
        verify(observer).onChanged(false)
        viewModel.onTriggerEvent(event)

        //Then
        verify(observer).onChanged(true)
    }
}