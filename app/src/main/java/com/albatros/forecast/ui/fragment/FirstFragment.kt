package com.albatros.forecast.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.albatros.forecast.databinding.FragmentFirstBinding
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.ui.fragment.viewmodel.FirstViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment : Fragment() {

    private val viewModel: FirstViewModel by viewModel()

    private lateinit var binding: FragmentFirstBinding

    private val onDataLoadedObserver = Observer<ForecastMain> {
        with(binding) {
            progressBar.isActivated = false
            progressBar.clearAnimation()
            textContent.text = it.toString()
            lifecycleScope.launch {
                delay(1000)
                motionBase.transitionToEnd()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        viewModel.forecast.observe(viewLifecycleOwner, onDataLoadedObserver)
        return binding.root
    }
}