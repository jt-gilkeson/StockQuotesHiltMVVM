package com.jt.stockquoteshiltmvvm.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jt.stockquoteshiltmvvm.domain.model.Quote
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository
import com.jt.stockquoteshiltmvvm.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

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
    fun `Verify livedata values changes on event`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        //Given
        val testSymbol = "TEST"
        doReturn(Quote(5.0)).`when`(quoteRepository).getQuote(testSymbol)
        val event = QuoteEvent.GetQuoteEvent(testSymbol)
        viewModel.getLoading().observeForever(observer)

        //When
        viewModel.onTriggerEvent(event)

        //Then
        verify(observer).onChanged(true)
    }
}