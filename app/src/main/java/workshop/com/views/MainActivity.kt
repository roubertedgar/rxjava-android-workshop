package workshop.com.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place
import workshop.com.util.onTextChange
import workshop.com.views.place.PLaceFormActivity
import workshop.com.views.place.PlaceAdapter
import workshop.com.views.place.PlaceViewModel


class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()
    private val placeList: MutableList<Place> = mutableListOf()

    private lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeViewModel = PlaceViewModel(FactoryDAO.getPlaceDatabase(applicationContext))

        setListeners()
        initRecyclerView()
        loadPlaces()
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            startActivity(Intent(this, PLaceFormActivity::class.java))
        }

        searchEditText.onTextChange {}
    }

    private fun initRecyclerView() {
        recyclerPlaceList.layoutManager = LinearLayoutManager(this)
        recyclerPlaceList.adapter = PlaceAdapter(placeList)
    }

    private fun loadPlaces() {
        disposable.add(placeViewModel.getAll().subscribe {
            placeList.clear()
            placeList.addAll(it)
            recyclerPlaceList.adapter?.notifyDataSetChanged()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}

