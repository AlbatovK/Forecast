package com.albatros.forecast

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.albatros.forecast.model.data.GradientType
import com.albatros.forecast.model.module.appModule
import com.albatros.forecast.model.module.repoModule
import com.albatros.forecast.model.repo.PreferencesRepository
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

@RunWith(AndroidJUnit4::class)
class SharedPreferenceTest : TestCase() {

    private val repo: PreferencesRepository by inject(PreferencesRepository::class.java)
    private var sendingEnabled: Boolean = false
    private var changingEnabled: Boolean = false
    private lateinit var type: GradientType
    private val modules = listOf(appModule, repoModule)

    init {
        loadKoinModules(modules)
    }

    @Before
    public override fun setUp() {
        super.setUp()
        with(repo) {
            sendingEnabled = isSendingEnabled()
            changingEnabled = isChangingEnabled()
            type = getGradientType()
        }
    }

    @Test(expected = Exception::class)
    fun exceptionThrowing_isCorrect() {
        repo.putGradientMode("Not a constant")
    }

    @Test
    fun enabling_isCorrect() {
        with(repo) {
            disableSending()
            disableGradientChanging()
            var actual = listOf(isChangingEnabled(), isSendingEnabled(), isChangingDisabled(), isSendingDisabled())
            var expected = listOf(false, false, true, true)
            assertEquals(actual, expected)
            enableSending()
            enableGradientChanging()
            actual = listOf(isChangingEnabled(), isSendingEnabled(), isChangingDisabled(), isSendingDisabled())
            expected = listOf(true, true, false, false)
            assertEquals(actual, expected)
        }
    }

    @Test
    fun adding_isCorrect() {
        with(repo) {
            putGradientType(GradientType.TYPE_THUNDER)
            assertEquals(getGradientType(), GradientType.TYPE_THUNDER)
        }
    }

    @After
    public override fun tearDown() {
        if (sendingEnabled) repo.enableSending() else repo.disableSending()
        if (changingEnabled) repo.enableGradientChanging() else repo.disableGradientChanging()
        repo.putGradientType(type)
        unloadKoinModules(modules)
        super.tearDown()
    }
}