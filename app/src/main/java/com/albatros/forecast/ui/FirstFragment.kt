package com.albatros.forecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.albatros.forecast.databinding.FragmentFirstBinding
import com.albatros.forecast.model.data.ForecastMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FirstFragment : Fragment() {

    private val viewModel: FirstViewModel by viewModel()

    private lateinit var binding: FragmentFirstBinding

    private fun ImageView.loadSvgInto(url: String) {
        val imageLoader = ImageLoader.Builder(context)
            .componentRegistry { add(SvgDecoder(context)) }
            .build()
        val request = ImageRequest.Builder(context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()
        imageLoader.enqueue(request)
    }

    private val onDataLoadedObserver = Observer<ForecastMain> {
        with(binding) {
            progressBar.isActivated = false
            progressBar.clearAnimation()
            textContent.text = it.toString()
            lifecycleScope.launch {
                delay(1000)
                motionBase.transitionToEnd()
            }
            val view = (activity as PresentationActivity).binding.imgState
            val link = "https://yastatic.net/weather/i/icons/funky/dark/${it.fact?.icon}.svg"
            view.loadSvgInto(link)
            view.visibility = View.VISIBLE
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