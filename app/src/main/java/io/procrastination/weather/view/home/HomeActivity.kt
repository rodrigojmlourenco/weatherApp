package io.procrastination.weather.view.home

import android.content.Context
import android.content.Intent
import io.procrastination.foundation.view.FoundationActivity
import io.procrastination.sample.BR
import io.procrastination.sample.R
import io.procrastination.sample.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : FoundationActivity<ActivityHomeBinding, HomeViewModel>(){

    companion object {
        fun getStartIntent(context : Context) : Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    @Inject lateinit var mViewModel: HomeViewModel

    override fun getViewModel(): HomeViewModel = mViewModel

    override fun getLayoutResId(): Int = R.layout.activity_home

    override fun getBindingVariableId(): Int? = BR.viewModel

}