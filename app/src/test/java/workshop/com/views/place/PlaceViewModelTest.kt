package workshop.com.views.place

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import util.RxSchedulerTestSetup
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO


@RunWith(MockitoJUnitRunner::class)
class PlaceViewModelTest {
    @Mock
    lateinit var dao: PlaceDAO

    private lateinit var viewModel: PlaceViewModel

    @Before
    fun setUp() {
        RxSchedulerTestSetup.setupRxScheduler()
        viewModel = PlaceViewModel(dao)
    }

    @After
    fun tearDown() {
        RxSchedulerTestSetup.reset()
    }

    @Test
    fun completeWhenSavePlace() {
        viewModel.insert(Place("", ""))
                .test()
                .assertNoValues()
                .assertComplete()
    }

    @Test
    fun returnsAllSavedPlacesWhenGetAll() {
        whenever(dao.getAll()).thenReturn(Flowable.just(getPlaces()))

        viewModel.getAll().test()
                .assertValue {
                    it.size == 2
                }.assertComplete()
    }

    @Test
    fun emptyWhenAreNoItemsSaved() {
        whenever(dao.getAll()).thenReturn(Flowable.empty())

        viewModel.getAll().test()
                .assertNoValues()
                .assertComplete()
    }

    private fun getPlaces(): List<Place> =
            listOf(Place("", ""), Place("", ""))
}