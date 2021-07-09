package com.jt.stockquoteshiltmvvm.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jt.stockquoteshiltmvvm.R
import com.jt.stockquoteshiltmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        observeViewModel()

        binding.symbol.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (actionId == EditorInfo.IME_NULL && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow( binding.symbol.windowToken, 0)

                viewModel.onTriggerEvent(
                    QuoteEvent.GetQuoteEvent(
                        binding.symbol.text.toString()
                    )
                )
                
                false
            } else {
                true
            }
        }
    }

    private fun observeViewModel() {
        viewModel.quote.observe(this, { quote ->
            quote?.let {
                binding.price.visibility = View.VISIBLE
                binding.price.text = getString(R.string.price, it)
            }
        })

        viewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.price.visibility = View.INVISIBLE
                }
            }
        })
    }

}