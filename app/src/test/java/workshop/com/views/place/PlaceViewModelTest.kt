package workshop.com.views.place

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO
import org.junit.Rule
import util.RxSchedulerTestSetup


@RunWith(MockitoJUnitRunner::class)
class PlaceViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var placeDAO: PlaceDAO

    lateinit var placeViewModel: PlaceViewModel

    @Before
    fun setUp() {
        RxSchedulerTestSetup.setupRxScheduler()
        placeViewModel = PlaceViewModel(placeDAO)
    }

    @After
    fun tearDown() {
        RxSchedulerTestSetup.reset()
    }

    @Test
    fun listAllPlacesWhenGetAll() {
        whenever(placeDAO.getAll()).thenReturn(Flowable.just(getFakePlaces()))

        placeViewModel.getAll().test().assertValue {
            it.size == 2
        }
    }

    private fun getFakePlaces(): List<Place> {
        return listOf(Place("", ""), Place("", ""))
    }
}