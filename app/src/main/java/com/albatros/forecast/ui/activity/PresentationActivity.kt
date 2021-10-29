package com.albatros.forecast.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.albatros.forecast.R
import com.albatros.forecast.databinding.ActivityPresentationBinding
import com.albatros.forecast.databinding.DialogLayoutBinding
import com.albatros.forecast.domain.getWeatherDescription
import com.albatros.forecast.model.data.GradientType
import com.albatros.forecast.domain.makeGradient
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
        Log.d("DataLoaded", "Activity")
        with(binding) {
            val downloadLink = link.format(it.fact?.icon ?: "ovc")
            imgState.loadSvgInto(downloadLink)
            temp.text = getString(R.string.temp_data, it.fact?.temp ?: 0)
            val condition = it.fact?.condition ?: getString(R.string.unknown_condition)
            state.text = if (condition.isWeatherDescription()) condition.getWeatherDescription() else condition
            temp.visibility = View.VISIBLE
            imgState.visibility = View.VISIBLE
            state.visibility = View.VISIBLE
        }
    }

    private val onGradientChanged = Observer<GradientType> {
        TransitionManager.beginDelayedTransition(binding.root)
        setGradient(it)
        TransitionManager.endTransitions(binding.root)
    }

    private fun setGradient(type: GradientType) {
        binding.idPresentation.contentPresentation.background = makeGradient(type, resources, theme)
        binding.appBar.background = when(type) {
            GradientType.TYPE_CLOUDY -> ColorDrawable(resources.getColor(R.color.cloud_light, theme))
            GradientType.TYPE_CLEAR -> ColorDrawable(resources.getColor(R.color.sky_dark, theme))
            GradientType.TYPE_SNOW -> ColorDrawable(resources.getColor(R.color. snow_light, theme))
            GradientType.TYPE_THUNDER -> ColorDrawable(resources.getColor(R.color.thunder_light, theme))
        }
        binding.toolbarLayout.contentScrim = when(type) {
            GradientType.TYPE_CLOUDY -> ColorDrawable(resources.getColor(R.color.cloud_light, theme))
            GradientType.TYPE_CLEAR -> ColorDrawable(resources.getColor(R.color.sky_dark, theme))
            GradientType.TYPE_SNOW -> ColorDrawable(resources.getColor(R.color.snow_light, theme))
            GradientType.TYPE_THUNDER -> ColorDrawable(resources.getColor(R.color.thunder_light, theme))
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
        presentationViewModel.gradient.observe(this, onGradientChanged)
    }

    private fun checkForPermissions() {
        val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val denied = permissions.any {
            ActivityCompat.checkSelfPermission(this@PresentationActivity, it) != PackageManager.PERMISSION_GRANTED
        }
        val reqCode = 1001
        if (denied) requestPermissions(permissions.toTypedArray(), reqCode)
        else {
            presentationViewModel.init()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED } ) {
            presentationViewModel.init()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        } else {
            presentationViewModel.errorInit()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        setWindowState()
    }

    override fun onDestroy() {
        super.onDestroy()
        presentationViewModel.forecast.removeObservers(this)
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        val binding = DialogLayoutBinding.inflate(layoutInflater)
        val dialog = with(builder) {
            setView(binding.root)
            create()
        }
        with(binding) {
            grad1.background = makeGradient(GradientType.TYPE_CLEAR, resources, theme)
            grad1.tag = GradientType.TYPE_CLEAR
            grad2.background = makeGradient(GradientType.TYPE_CLOUDY, resources, theme)
            grad2.tag = GradientType.TYPE_CLOUDY
            grad3.background = makeGradient(GradientType.TYPE_SNOW, resources, theme)
            grad3.tag = GradientType.TYPE_SNOW
            grad4.background = makeGradient(GradientType.TYPE_THUNDER, resources, theme)
            grad4.tag = GradientType.TYPE_THUNDER
            var current = grad1
            val onClick = View.OnClickListener { v ->
                val img = v as ImageView
                img.alpha = 0.7F
                img.background
                img.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_done, theme))
                if (current != img)
                    current.setImageResource(android.R.color.transparent)
                current = img
                if (!presentationViewModel.isChangingEnabled())
                    presentationViewModel.setRegularGradient(v.tag as GradientType)
            }
            grad1.setOnClickListener(onClick)
            grad2.setOnClickListener(onClick)
            grad3.setOnClickListener(onClick)
            grad4.setOnClickListener(onClick)
            with(switchLayout) {
                isChecked = presentationViewModel.isChangingEnabled()
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
                        presentationViewModel.enableGradientChanging()
                    else presentationViewModel.disableGradientChanging()
                }
            }
        }
        dialog.window?.let {
            it.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.dialog_shape, theme))
            it.setGravity(Gravity.BOTTOM)
            it.attributes.verticalMargin = 0.08F
        }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.exit -> {
            finish()
            finishAffinity()
            true
        }
        R.id.settings -> {
            showSettingsDialog()
            true
        }
        R.id.refresh -> {
            presentationViewModel.refreshData()
            true
        }
        else -> false
    }
}