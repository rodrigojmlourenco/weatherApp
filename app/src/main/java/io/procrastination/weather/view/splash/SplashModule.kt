package io.procrastination.weather.view.splash

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

    @Provides
    fun provideViewModel(view: SplashActivity): SplashViewModel {
        val vm = ViewModelProviders.of(view).get(SplashViewModel::class.java)

        vm.setNavigator(view)
        vm.setLifeCycleOwner(view)

        return vm
    }
}