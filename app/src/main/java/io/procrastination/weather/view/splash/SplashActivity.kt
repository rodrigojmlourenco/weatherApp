package io.procrastination.weather.view.splash

import android.Manifest
import android.os.Bundle
import android.view.View
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.sample.BR
import io.procrastination.sample.R
import io.procrastination.sample.databinding.ActivitySplashBinding
import io.procrastination.weather.view.RC_LOCATION
import io.procrastination.weather.view.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

class SplashActivity : FoundationActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator,
    EasyPermissions.PermissionCallbacks {

    private val mViewModel: SplashViewModel by viewModel()

    override fun getViewModel(): SplashViewModel? = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun getBindingVariableId(): Int? = BR.viewModel

    override fun goToHome() {
        startActivity(HomeActivity.getStartIntent(this))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun requestLocationPermissions() {
        if (EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Timber.i("User has granted access to location.")
            goToHome()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.location_rationale),
                RC_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        getViewBinding().btnRequestPermissions.visibility = View.VISIBLE
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        goToHome()
    }
}