package workshop.com.views.place

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO

class PlaceViewModel(private val placeDao: PlaceDAO) {
    fun getAll(): Flowable<List<Place>> = placeDao
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun insert(place: Place): Completable = Completable
            .fromAction { placeDao.insert(place) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}