package com.doro.marsweatherapp.main.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.doro.marsweatherapp.R
import com.doro.marsweatherapp.TestApp
import com.doro.marsweatherapp.main.data.repository.model.Resource
import com.doro.marsweatherapp.main.domain.interactor.GetWeatherUseCase
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import com.doro.marsweatherapp.main.domain.model.WeatherOverview
import com.doro.marsweatherapp.rules.DataBindingIdlingResourceRule
import com.doro.marsweatherapp.utils.disableProgressBarAnimations
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainFragmentTest {

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule()

    private val mockMainAdapter: MainAdapter = mockk(relaxed = true)
    private val mockGetWeatherUseCase: GetWeatherUseCase = mockk(relaxed = true)

    private val viewModel =  spyk(MainViewModel(mockGetWeatherUseCase))

    private val weatherStateFlow = MutableStateFlow<Resource<List<WeatherInformation>>>(Resource.loading())

    @Before
    fun setup() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val application = instrumentation.targetContext.applicationContext as TestApp

        every { viewModel.weather } returns weatherStateFlow

        application.injectModule(module {
            single(override = true) { viewModel }
            single(override = true) { mockMainAdapter }
            single(override = true) { mockGetWeatherUseCase }
        })
    }

    private fun launchFragment() : NavController {

        val fragment =  MainFragment()

        val scenario = launchFragmentInContainer(fragmentArgs = Bundle(), themeResId = R.style.Theme_MarsWeatherApp) { fragment }

        dataBindingIdlingResourceRule.monitorFragment(scenario)

        val navController = mockk<NavController>(relaxed = true)

        scenario.onFragment { f ->
            Navigation.setViewNavController(f.requireView(), navController)
            fragment.disableProgressBarAnimations()
        }
        return navController
    }

    @Test
    fun testLoadingDuringFetching() {
        weatherStateFlow.value = Resource.loading()
        launchFragment()

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        onView(withId(R.id.list)).check(matches(not(isDisplayed())))

        onView(withId(R.id.retry)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorMessage)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testErrorDuringFetching() {
        weatherStateFlow.value = Resource.error("Oops! Error occurred!")

        launchFragment()

        onView(withId(R.id.list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.errorMessage)).check(matches(isDisplayed()))
        onView(withId(R.id.retry)).check(matches(isDisplayed()))
    }

    @Test
    fun testSuccessfulFetch() {
        setWeatherInformation("771", "772")

        launchFragment()

        onView(withId(R.id.list)).check(matches(isDisplayed()))

        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.errorMessage)).check(matches(not(isDisplayed())))
        onView(withId(R.id.retry)).check(matches(not(isDisplayed())))
    }

    private fun setWeatherInformation(vararg keys: String) : List<WeatherInformation> {
        val sols = keys.mapIndexed { index, key ->
            createWeatherInformation(key = key, index = index)
        }
        weatherStateFlow.value = Resource.success(sols)
        return sols
    }

    private fun createWeatherInformation(key: String, index: Int) = WeatherInformation(
        key = key,
        solName = "Sol $key",
        lastDate = "27 June",
        firstDate = "04 July",
        ordinal = 3,
        southernSeason = "late winter",
        northernSeason = "late winter",
        season = "Winter",
        pressure = WeatherOverview(averageValue = (index + 1) * 10.0, minValue = (index + 1) * 20.0, maxValue = (index + 1) * 39.0),
        wind = WeatherOverview(averageValue = (index + 1) * 30.0, minValue = (index + 1) * 40.0, maxValue = (index + 1) * 59.0),
        temperature = WeatherOverview(averageValue = (index + 1) * 30.0, minValue = (index + 1) * 40.0, maxValue = (index + 1) * 59.0)
    )
}