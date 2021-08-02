package com.jt.stockquoteshiltmvvm.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jt.stockquoteshiltmvvm.domain.model.Quote
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository
import com.jt.stockquoteshiltmvvm.utils.CoroutinesTestRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelMockitoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var mockQuoteRepository: QuoteRepository

    @Mock
    lateinit var mockObserver: Observer<Boolean>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel(mockQuoteRepository)
    }

    @Test
    fun `Verify livedata values changes on event`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //Given
            val testSymbol = "TEST"
            doReturn(Quote(5.0)).`when`(mockQuoteRepository).getQuote(testSymbol)
            val event = QuoteEvent.GetQuoteEvent(testSymbol)
            val loading = viewModel.getLoading()
            loading.observeForever(mockObserver)

            //When
            viewModel.onTriggerEvent(event)

            //Then
            verify(mockObserver).onChanged(true)
            assertEquals(true, loading.value)

            advanceTimeBy(1000)
            assertEquals(false, loading.value)
        }
}