package io.procrastination.weather.view.splash

import android.databinding.ViewDataBinding
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.sample.R
import javax.inject.Inject


class SplashActivity : FoundationActivity<ViewDataBinding, SplashViewModel>(), SplashNavigator {

    @Inject lateinit var mViewModel : SplashViewModel

    override fun getViewModel(): SplashViewModel? = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun getBindingVariableId(): Int? = null

    override fun goToHome() {
        TODO()
    }
}