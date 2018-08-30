package workshop.com.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO
import workshop.com.views.place.PLaceFormActivity
import workshop.com.views.place.PlaceAdapter


class MainActivity : AppCompatActivity() {
    private val placeList: MutableList<Place> = mutableListOf()
    private lateinit var placeDao: PlaceDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeDao = FactoryDAO.getPlaceDatabase(applicationContext)

        setListeners()
        initRecyclerView()
        loadPlaces()
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            startActivityForResult(Intent(this, PLaceFormActivity::class.java), 200)
        }
    }

    private fun initRecyclerView() {
        recyclerPlaceList.layoutManager = LinearLayoutManager(this)
        recyclerPlaceList.adapter = PlaceAdapter(placeList)
    }

    private fun loadPlaces() {
        placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    placeList.clear()
                    placeList.addAll(it)
                    recyclerPlaceList.adapter?.notifyDataSetChanged()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val place = data?.getSerializableExtra("place") as Place
            savePlace(place)
        }
    }


    private fun savePlace(place: Place) {
        Completable.fromAction { placeDao.insert(place) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Snackbar.make(mainContainer, "Place saved =D", Snackbar.LENGTH_LONG).show()
                    loadPlaces()
                }
    }
}

