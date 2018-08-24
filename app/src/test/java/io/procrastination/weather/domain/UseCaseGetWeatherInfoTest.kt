package io.procrastination.weather.domain

import io.procrastination.weather.data.network.NetworkWeatherRepo
import io.procrastination.weather.domain.error.CachedInformationIsTooOldException
import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.mocks.*
import org.junit.Assert
import org.junit.Test
import timber.log.Timber

class UseCaseGetWeatherInfoTest {

    val mockNetRepo = NetworkWeatherRepo(mockBaseUrl(), mockApiKey(), mockIconsUrl())

    @Test
    fun testOnline_loadInfo(){

        val tawarano = mockCoordsWeatherInfo()

        val networkHandler = MockNetworkHandler().setNetworkState(true)
        val localWeatherRepository = MockLocalWeatherRepository()

        val usecase = UseCaseGetWeatherInfo(MockScheduler(), mockNetRepo, networkHandler, localWeatherRepository)

        val usecaseTest = usecase.createInteractorObservable(LocationInfo(33.0, 33.0)).test()
        val repoTest = localWeatherRepository.getWeatherInfo(tawarano.location.city!!).test()

        val response = usecaseTest.values()[0]
        val cachedResponse = repoTest.values()[0]

        Assert.assertEquals(tawarano, response)
        Assert.assertEquals(tawarano, cachedResponse)
    }

    @Test
    fun testOffline_loadInfoWithoutCachedData(){

        val tawarano = mockCoordsWeatherInfo()

        val networkHandler = MockNetworkHandler().setNetworkState(false)
        val localWeatherRepository = MockLocalWeatherRepository()

        val usecase = UseCaseGetWeatherInfo(MockScheduler(), mockNetRepo, networkHandler, localWeatherRepository)

        val usecaseTest = usecase.createInteractorObservable(LocationInfo(33.0, 33.0)).test()

        usecaseTest.assertError { error ->
            error is NoInformationAvailableToPresentToTheUserException
        }
    }

    @Test
    fun testOffline_loadInfoWithCachedData(){

        val tawarano = mockCoordsWeatherInfo(System.currentTimeMillis())

        val networkHandler = MockNetworkHandler().setNetworkState(false)
        val localWeatherRepository = MockLocalWeatherRepository()
        localWeatherRepository.syncInsert(tawarano)

        val usecase = UseCaseGetWeatherInfo(MockScheduler(), mockNetRepo, networkHandler, localWeatherRepository)

        val usecaseTest = usecase.createInteractorObservable(mockTawaraLocation()).test()

        usecaseTest.assertValue {
             it == tawarano
        }


    }


    @Test
    fun testOffline_loadInfoWithCachedData_outdated(){

        val tawarano = mockCoordsWeatherInfo()

        val networkHandler = MockNetworkHandler().setNetworkState(false)
        val localWeatherRepository = MockLocalWeatherRepository()
        localWeatherRepository.syncInsert(tawarano)

        val usecase = UseCaseGetWeatherInfo(MockScheduler(), mockNetRepo, networkHandler, localWeatherRepository)

        val usecaseTest = usecase.createInteractorObservable(mockTawaraLocation()).test()


        usecaseTest.assertError { it is CachedInformationIsTooOldException }
    }



}


