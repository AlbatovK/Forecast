package com.albatros.forecast.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.albatros.forecast.R
import com.albatros.forecast.databinding.FragmentFirstBinding
import com.albatros.forecast.domain.getDirection
import com.albatros.forecast.domain.isDirection
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.data.Part
import com.albatros.forecast.ui.adapter.PartAdapter
import com.albatros.forecast.ui.fragment.viewmodel.FirstViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment : Fragment() {

    private val viewModel: FirstViewModel by viewModel()

    private lateinit var binding: FragmentFirstBinding

    private val onDataLoadedObserver = Observer<ForecastMain> {
        Log.d("FirstFragment", "Updated LiveData")
        with(binding) {
            progressBar.isActivated = false
            progressBar.clearAnimation()
            list.adapter = if (it.forecast?.parts?.size ?: 0 == 0) PartAdapter(listOf(Part(), Part()))
                else it.forecast?.parts?.let { parts -> PartAdapter(parts) }
            list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            with(contentLayout) {
                tempFact.text = getString(R.string.temp_data, it.fact?.temp ?: 0)
                feelsLikeFact.text = getString(R.string.temp_data, it.fact?.feelsLike ?: 0)
                windSpeedFact.text =
                    getString(R.string.wind_speed_fact, (it.fact?.windSpeed ?: 0).toString())
                windDirFact.text =
                    if (it.fact?.windDir?.isDirection() == true)
                        it.fact?.windDir!!.getDirection()
                    else getString(R.string.unknown_condition)
                humidityFact.text = (it.fact?.humidity ?: 0.0).toInt().toString()
                pressureFact.text =
                    getString(R.string.pressure_mm_data, (it.fact?.pressureMm ?: 0.0).toInt())
                sunsetFact.text = it.forecast?.sunset ?: getString(R.string.unknown_condition)
                sunriseFact.text = it.forecast?.sunrise ?: getString(R.string.unknown_condition)
            }
            lifecycleScope.launch {
                delay(500)
                motionBase.transitionToEnd()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.forecast.removeObserver(onDataLoadedObserver)
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