package io.procrastination.weather.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.foundation.view.popups.ErrorPopup
import io.procrastination.sample.BR
import io.procrastination.sample.R
import io.procrastination.sample.databinding.ActivityHomeBinding
import io.procrastination.weather.domain.model.EAST
import io.procrastination.weather.domain.model.NORTH
import io.procrastination.weather.domain.model.NORTH_EAST
import io.procrastination.weather.domain.model.NORTH_WEST
import io.procrastination.weather.domain.model.SOUTH
import io.procrastination.weather.domain.model.SOUTH_EAST
import io.procrastination.weather.domain.model.SOUTH_WEST
import io.procrastination.weather.domain.model.WEST
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeActivity : FoundationActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }


    private val mViewModel: HomeViewModel by viewModel { parametersOf(this)}

    private val mNetworkHandler: NetworkHandler by inject()

    private var mErrorPopup: ErrorPopup? = null

    override fun getViewModel(): HomeViewModel = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_home

    override fun getBindingVariableId(): Int? = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().weatherInfo.observe(this, Observer {
            it?.icon?.let { icon -> Glide.with(this).load(icon).into(getViewBinding().ivWeather) }
        })

        getViewModel().isLoading.observe(this, Observer { loading ->
            getViewBinding().progressIndicator.visibility = if (loading == true) View.VISIBLE else View.GONE
        })

        // Adjust according to the network state
        toggleRefreshVisibility(mNetworkHandler.hasNetworkConnectivity())
        mNetworkHandler.hasNetworkConnectivity(Consumer { hasNetwork ->
            if (hasNetwork) getViewModel().onPressedRefeshWeather()
            toggleRefreshVisibility(hasNetwork)
        })
    }

    override fun onDestroy() {

        if (mErrorPopup != null && mErrorPopup!!.isShowing)
            mErrorPopup!!.dismiss()

        super.onDestroy()
    }

    private fun toggleRefreshVisibility(hasNetwork: Boolean) {
        if (hasNetwork) {
            getViewBinding().fabRefresh.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_refresh))
        } else {
            getViewBinding().fabRefresh.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_no_network))
        }
    }

    override fun handleError(error: Throwable) {

        if (mErrorPopup == null || !mErrorPopup!!.isShowing) {
            mErrorPopup = ErrorPopup(this).setError(error.localizedMessage)
            mErrorPopup!!.show(getViewBinding().root)
        }
    }

    override fun getDirectionAsString(direction: Int): String {
        return when (direction) {
            NORTH -> getString(R.string.north)
            NORTH_EAST -> getString(R.string.north_east)
            EAST -> getString(R.string.east)
            SOUTH_EAST -> getString(R.string.south_east)
            SOUTH -> getString(R.string.south)
            SOUTH_WEST -> getString(R.string.south_west)
            WEST -> getString(R.string.west)
            NORTH_WEST -> getString(R.string.north_west)
            else -> getString(R.string.unknown)
        }
    }

    override fun goToWifiSettings() {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }
}