package com.albatros.forecast.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.albatros.forecast.R
import com.albatros.forecast.databinding.ActivityPresentationBinding
import com.albatros.forecast.domain.getWeatherDescription
import com.albatros.forecast.domain.isWeatherDescription
import com.albatros.forecast.domain.link
import com.albatros.forecast.domain.loadSvgInto
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.ui.activity.viewmodel.PresentationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PresentationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val presentationViewModel: PresentationViewModel by viewModel()

    private lateinit var binding: ActivityPresentationBinding

    private val onDataLoadedObserver = Observer<ForecastMain> {
        with(binding) {
            val downloadLink = link.format(it.fact?.icon ?: "ovc")
            imgState.loadSvgInto(downloadLink)
            imgState.visibility = View.VISIBLE
            temp.text = getString(R.string.temp_data, it.fact?.temp ?: 0)
            temp.visibility = View.VISIBLE
            val condition = it.fact?.condition ?: getString(R.string.unknown_condition)
            state.text =
                if (condition.isWeatherDescription()) condition.getWeatherDescription() else condition
            state.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresentationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_presentation)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        presentationViewModel.forecast.observe(this, onDataLoadedObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.exit -> {
            finishAffinity()
            true
        }
        R.id.settings -> {
            true
        }
        else -> false
    }
}