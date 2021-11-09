package com.albatros.forecast.model.worker

import android.content.Context
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.albatros.forecast.R
import com.albatros.forecast.domain.bitmapFromSvgAsync
import com.albatros.forecast.domain.getWeatherDescription
import com.albatros.forecast.model.api.Api
import com.albatros.forecast.model.data.ForecastMain
import com.albatros.forecast.model.module.appModule
import com.albatros.forecast.model.module.repoModule
import com.albatros.forecast.model.repo.DatabaseRepository
import com.albatros.forecast.model.repo.LocationRepository
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.delay
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.TimeUnit

class NotificationsWorker(context: Context, params: WorkerParameters)
    : CoroutineWorker(context, params) {

    companion object {
        var work_id = "fr_work"
        var interval_min: Long = 120
        var time_unit = TimeUnit.MINUTES
        private const val TAG = "Worker"
    }

    private var chnId = "fr_id"
    private var chnName = "Fr_Main_Thread"
    private var channel: NotificationChannel? = null
    private val notificationManager = NotificationManagerCompat.from(applicationContext)
    private val modules = listOf(appModule, repoModule)

    init { loadKoinModules(modules) }

    private val api: Api by inject(Api::class.java)
    private val locRepo: LocationRepository by inject(LocationRepository::class.java)
    private val dbRepo: DatabaseRepository by inject(DatabaseRepository::class.java)
    private val analytics: FirebaseAnalytics by inject(FirebaseAnalytics::class.java)

    private fun createCurrentChannel() {
        channel = NotificationChannel(chnId, chnName, NotificationManager.IMPORTANCE_HIGH)
        channel?.enableVibration(true)
        try { channel?.let { notificationManager.createNotificationChannel(it) } } catch (ignored: Exception) { }
    }

    private fun getNotification(header: String, content: String, bitmap: Bitmap): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, chnId)
                .setLargeIcon(bitmap)
                .setContentText(content)
                .setContentTitle(header)
                .setSmallIcon(R.drawable.icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
        return builder.setChannelId(chnId).build()
    }

    private fun resetWork() {
        val constraints = Constraints.Builder().let {
            it.setRequiredNetworkType(NetworkType.CONNECTED)
            it.build()
        }
        val request = PeriodicWorkRequest.Builder(NotificationsWorker::class.java, interval_min, time_unit)
                .setConstraints(constraints)
                .setInitialDelay(interval_min, time_unit)
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(work_id, ExistingPeriodicWorkPolicy.REPLACE, request)
    }

    private fun checkForPermissions(): Boolean {
        val permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissions.any { ActivityCompat.checkSelfPermission(applicationContext, it) != PackageManager.PERMISSION_GRANTED }
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: Started")
        if (checkForPermissions()) {
            unloadKoinModules(modules)
            val data = Data.Builder().apply {
                putString("Result", "Failure")
                putString("Reason", "Permissions denied")
            }.build()
            return Result.success(data)
        }
        Log.d(TAG, "doWork: passed permissions")
        delay(3_000)
        val (lat, lon) = locRepo.getLastLocation()
        Bundle().apply {
            putDoubleArray("location", doubleArrayOf(lat, lon))
            analytics.logEvent("notification", this)
        }
        val forecast = try { api.getForecast(lat, lon, "en_US") }
        catch (e1: Exception) {
            Log.d(TAG, "doWork: $e1")
            try { dbRepo.collectForecastFromDatabase() } catch (e2: Exception) {
                Log.d(TAG, "doWork: $e2")
                ForecastMain()
            }
        }
        delay(2_000)
        val content = (forecast.fact?.condition?.getWeatherDescription()?.replace("\n", "") ?: applicationContext.getString(R.string.unknown_condition))
        val tmp = applicationContext.getString(R.string.temp_data,forecast.fact?.temp ?: 0)
        val header = applicationContext.getString(R.string.send_message, tmp)
        val bitmap = bitmapFromSvgAsync(forecast.fact?.icon ?: "ovc", applicationContext).await()
        createCurrentChannel()
        val notification = getNotification(header, content, bitmap)
        val info = ForegroundInfo(0, notification, Service.BIND_IMPORTANT)
        setForeground(info)
        notificationManager.notify(1, notification)
        resetWork()
        unloadKoinModules(modules)
        val data = Data.Builder().apply {
            putString("Result", "Success")
            putString("Retried", "True")
        }.build()
        Log.d(TAG, "doWork: Success")
        return Result.success(data)
    }
}
