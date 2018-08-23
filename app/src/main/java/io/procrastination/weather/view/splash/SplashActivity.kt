package io.procrastination.weather.view.splash

import android.databinding.ViewDataBinding
import android.view.View
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.sample.BR
import io.procrastination.sample.R
import io.procrastination.sample.databinding.ActivitySplashBinding
import io.procrastination.weather.view.RC_LOCATION
import io.procrastination.weather.view.locationPermissions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject


class SplashActivity : FoundationActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator,  EasyPermissions.PermissionCallbacks {


    @Inject lateinit var mViewModel : SplashViewModel

    override fun getViewModel(): SplashViewModel? = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun getBindingVariableId(): Int? = BR.viewModel

    override fun goToHome() {
        //TODO()
    }

    @AfterPermissionGranted(RC_LOCATION)
    override fun requestLocationPermissions() {
        if(EasyPermissions.hasPermissions(this, *this.locationPermissions()))
            goToHome()
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.location_rationale), RC_LOCATION, *this.locationPermissions());
        }
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        getViewBinding().btnRequestPermissions.visibility = View.VISIBLE
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        goToHome()
    }

}