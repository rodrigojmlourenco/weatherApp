package io.procrastination.weather.view.splash

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.procrastination.foundation.domain.schedueler.Scheduler

@Module
class SplashModule {

    @Provides
    fun provideViewModel(view: SplashActivity, scheduler: Scheduler): SplashViewModel {
        val vm = ViewModelProviders.of(view).get(SplashViewModel::class.java)

        vm.setNavigator(view)
        vm.setLifeCycleOwner(view)
        vm.scheduler = scheduler

        return vm
    }
}