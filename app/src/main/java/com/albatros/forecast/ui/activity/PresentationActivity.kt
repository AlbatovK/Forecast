package com.albatros.forecast.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
            temp.text = getString(R.string.temp_data, it.fact?.temp ?: 0)
            val condition = it.fact?.condition ?: getString(R.string.unknown_condition)
            state.text =
                if (condition.isWeatherDescription()) condition.getWeatherDescription() else condition
            temp.visibility = View.VISIBLE
            imgState.visibility = View.VISIBLE
            state.visibility = View.VISIBLE
        }
    }

    private fun setWindowState() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresentationBinding.inflate(layoutInflater)
        setWindowState()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_presentation)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        checkForPermissions()
        presentationViewModel.forecast.observe(this, onDataLoadedObserver)
    }

    private fun checkForPermissions() {
        val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val denied = permissions.any {
            ActivityCompat.checkSelfPermission(this@PresentationActivity, it) != PackageManager.PERMISSION_GRANTED
        }
        val reqCode = 1001
        if (denied) requestPermissions(permissions.toTypedArray(), reqCode)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        setWindowState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.exit -> {
            finishAffinity()
            true
        }
        R.id.settings -> {
            true
        }
        R.id.refresh -> {
            presentationViewModel.refreshData()
            true
        }
        else -> false
    }
}