package io.procrastination.weather.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.procrastination.weather.di.modules.ConstantsModule
import io.procrastination.weather.di.modules.RepositoriesModule
import io.procrastination.weather.di.modules.UseCasesModule
import io.procrastination.weather.view.WeatherApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ConstantsModule::class,
    RepositoriesModule::class,
    UseCasesModule::class,
    ActivityBuilder::class
])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApplication)
}