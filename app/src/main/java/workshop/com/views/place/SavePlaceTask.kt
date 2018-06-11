package workshop.com.views.place

import android.os.AsyncTask
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO

class SavePlaceTask(private val placeDAO: PlaceDAO, private val observer: () -> Unit) : AsyncTask<Place, Unit, Unit>() {

    override fun doInBackground(vararg places: Place) {
        places.forEach {
            placeDAO.insert(it)
        }
    }

    override fun onPostExecute(result: Unit) {
        observer()
    }
}