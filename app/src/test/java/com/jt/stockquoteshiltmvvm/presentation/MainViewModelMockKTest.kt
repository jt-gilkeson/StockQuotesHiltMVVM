package com.jt.stockquoteshiltmvvm.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jt.stockquoteshiltmvvm.domain.model.Quote
import com.jt.stockquoteshiltmvvm.domain.repository.QuoteRepository
import com.jt.stockquoteshiltmvvm.utils.CoroutinesTestRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelMockKTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @MockK
    lateinit var mockQuoteRepository: QuoteRepository

    @MockK
    lateinit var mockObserver: Observer<Boolean>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(mockQuoteRepository)
    }

    @Test
    fun `Verify livedata values changes on event`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //Given
            val testSymbol = "TEST"
            coEvery { mockQuoteRepository.getQuote(testSymbol) } returns Quote(5.0)
            val event = QuoteEvent.GetQuoteEvent(testSymbol)
            val loading = viewModel.getLoading()
            loading.observeForever(mockObserver)

            //When
            viewModel.onTriggerEvent(event)
            advanceTimeBy(1000)

            //Then
            coVerifyOrder() {
                mockObserver.onChanged(true)
                mockObserver.onChanged(false)
            }
        }
}

