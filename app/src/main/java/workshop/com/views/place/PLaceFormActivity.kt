package workshop.com.views.place

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_place_form.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place

class PLaceFormActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()
    private lateinit var viewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_form)
        viewModel = PlaceViewModel(FactoryDAO.getPlaceDatabase(applicationContext))

        doneButton.setOnClickListener {
            val name = placeName.text.toString()
            val description = placeDescription.text.toString()
            savePlace(Place(name, description))
        }
    }

    private fun savePlace(place: Place) {
        disposable.add(viewModel.insert(place).subscribe { finish() })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
