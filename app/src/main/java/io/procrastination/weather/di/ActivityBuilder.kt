package io.procrastination.weather.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.procrastination.weather.view.home.HomeActivity
import io.procrastination.weather.view.home.HomeModule
import io.procrastination.weather.view.splash.SplashActivity
import io.procrastination.weather.view.splash.SplashModule

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun provideSplashActivity() : SplashActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun bindHomeActivity() : HomeActivity

}