package com.albatros.forecast.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
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
import androidx.work.*
import com.albatros.forecast.R
import com.albatros.forecast.databinding.ActivityPresentationBinding
import com.albatros.forecast.databinding.DialogLayoutBinding
import com.albatros.forecast.domain.getWeatherDescription
import com.albatros.forecast.model.data.GradientType
import com.albatros.forecast.domain.isWeatherDescription
import com.albatros.forecast.domain.loadSvgInto
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.worker.NotificationsWorker
import com.albatros.forecast.ui.activity.viewmodel.PresentationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PresentationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val presentationViewModel: PresentationViewModel by viewModel()

    private lateinit var binding: ActivityPresentationBinding

    private val onDataLoadedObserver = Observer<ForecastMain> {
        Log.d("DataLoaded", "Activity")
        with(binding) {
            val downloadLink = it.fact?.icon ?: "ovc"
            imgState.loadSvgInto(downloadLink)
            temp.text = getString(R.string.temp_data, it.fact?.temp ?: 0)
            val condition = it.fact?.condition ?: getString(R.string.unknown_condition)
            state.text = if (condition.isWeatherDescription()) condition.getWeatherDescription() else condition
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
        setUpWorker()
        presentationViewModel.gradient.observe(this, onGradientChanged)
    }

    private fun setUpWorker() {
        if (presentationViewModel.isSendingEnabled())
            WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                NotificationsWorker.work_id, ExistingPeriodicWorkPolicy.REPLACE, presentationViewModel.getWorker())
    }

    private fun checkForPermissions() {
        val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        val denied = permissions.any {
            ActivityCompat.checkSelfPermission(this@PresentationActivity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (denied) requestPermissions(permissions.toTypedArray(), 1001)
        else {
            presentationViewModel.init()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED } && requestCode == 1001) {
            presentationViewModel.init()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        } else {
            presentationViewModel.errorInit()
            presentationViewModel.forecast.observe(this, onDataLoadedObserver)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun makeGradient(
        type: GradientType,
        res: Resources,
        theme: Resources.Theme
    ): GradientDrawable {
        val colors: IntArray = when (type) {
            GradientType.TYPE_CLEAR -> intArrayOf(res.getColor(R.color.sky_dark, theme), res.getColor(
                R.color.sky_light, theme))
            GradientType.TYPE_CLOUDY ->  intArrayOf(res.getColor(R.color.cloud_light, theme), res.getColor(
                R.color.cloud_dark, theme))
            GradientType.TYPE_SNOW -> intArrayOf(res.getColor(R.color.snow_light, theme), res.getColor(
                R.color.snow_dark, theme))
            GradientType.TYPE_THUNDER -> intArrayOf(res.getColor(R.color.thunder_light, theme), res.getColor(
                R.color.thunder_dark, theme))
        }
        return GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
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
            val gradMap = mapOf(
                grad1 to GradientType.TYPE_CLEAR,
                grad2 to GradientType.TYPE_CLOUDY,
                grad3 to GradientType.TYPE_SNOW,
                grad4 to GradientType.TYPE_THUNDER,
            )
            val gradList = listOf(grad1, grad2, grad3, grad4)
            gradList.forEach {
                it.background = makeGradient(gradMap[it]!!, resources, theme)
                it.tag = gradMap[it]!!
            }
            var current = gradList[0]
            gradList.forEach {
                it.setOnClickListener { v ->
                    with(v as ImageView) {
                        alpha = 0.7F
                        setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_done, theme))
                        if (current != this)
                            current.setImageResource(android.R.color.transparent)
                        current = this
                    }
                    if (presentationViewModel.isChangingDisabled())
                        presentationViewModel.setRegularGradient(v.tag as GradientType)
                }
            }
            with(switchLayout) {
                isChecked = presentationViewModel.isChangingEnabled()
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked)
                        presentationViewModel.enableGradientChanging()
                    else presentationViewModel.disableGradientChanging()
                }
            }
            with(sendSwitch) {
                isChecked = presentationViewModel.isSendingEnabled()
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        presentationViewModel.enableSending()
                        setUpWorker()
                    }
                    else presentationViewModel.disableSending()
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