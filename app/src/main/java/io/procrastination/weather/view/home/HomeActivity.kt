package io.procrastination.weather.view.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.sample.BR
import io.procrastination.sample.R
import io.procrastination.sample.databinding.ActivityHomeBinding
import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.*
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : FoundationActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator{

    companion object {
        fun getStartIntent(context : Context) : Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    @Inject lateinit var mViewModel: HomeViewModel

    override fun getViewModel(): HomeViewModel = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_home

    override fun getBindingVariableId(): Int? = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().weatherInfo.observe(this, Observer {
            Timber.i("here")
        })

        getViewModel().isLoading.observe(this, Observer { loading ->
            getViewBinding().progressIndicator.visibility = if(loading == true) View.VISIBLE else View.GONE
        })
    }

    override fun handleError(error: Throwable) {

        if(error is NoInformationAvailableToPresentToTheUserException){

        }

    }

    override fun getDirectionAsString(direction: Int): String {
        return when(direction){
            NORTH       -> getString(R.string.north)
            NORTH_EAST  -> getString(R.string.north_east)
            EAST        -> getString(R.string.east)
            SOUTH_EAST  -> getString(R.string.south_east)
            SOUTH       -> getString(R.string.south)
            SOUTH_WEST  -> getString(R.string.south_west)
            WEST        -> getString(R.string.west)
            NORTH_WEST  -> getString(R.string.north_west)
            else        -> getString(R.string.unknown)
        }
    }
}