package io.procrastination.foundation.data


import com.google.gson.GsonBuilder
import io.procrastination.foundation.BuildConfig
import io.procrastination.foundation.domain.errors.ServiceNotBuiltException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


abstract class BaseServiceGenerator<API>(private val baseUrl : String) {

    private var httpClient: OkHttpClient? = null


    private var mService: API? = null

    private var mRetrofit: Retrofit? = null

    protected abstract val serviceClass: Class<API>

    protected abstract val interceptors: List<Interceptor>

    private fun prepareOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(NETWORK_READ_WRITE_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(NETWORK_READ_WRITE_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .connectTimeout(NETWORK_CONNECT_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)


        for (i in interceptors)
            builder.addInterceptor(i)

        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }

        return builder.build()
    }

    private fun prepareRetrofit(client : OkHttpClient): Retrofit {
        val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    fun runAsMock(mockService: API) {
        mService = mockService
    }

    protected fun build() {
        httpClient = prepareOkHttpClient()

        if(httpClient == null) throw ServiceNotBuiltException(this)

        mRetrofit = prepareRetrofit(httpClient!!)
    }

    protected fun api(): API {
        if (mService == null) {
            mService = createService(serviceClass)
        }
        return mService!!
    }

    private fun createService(serviceClass: Class<API>): API {
        return if (mRetrofit == null)
            throw RuntimeException(this.javaClass.simpleName + " must run build() in its constructor.")
        else
            mRetrofit!!.create(serviceClass)
    }


    companion object {

        private const val NETWORK_READ_WRITE_TIMEOUT_IN_SECONDS = 60
        private const val NETWORK_CONNECT_TIMEOUT_IN_SECONDS    = 15
    }
}

