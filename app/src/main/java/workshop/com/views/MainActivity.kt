package workshop.com.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.util.onTextChange
import workshop.com.views.place.PLaceFormActivity
import workshop.com.views.place.PlaceAdapter
import workshop.com.views.place.PlaceViewModel


class MainActivity : AppCompatActivity() {

    private val disposable = CompositeDisposable()

    private lateinit var placeViewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeViewModel = PlaceViewModel(FactoryDAO.getPlaceDatabase(applicationContext))

        setListeners()
        initRecyclerView()
    }

    private fun setListeners() {
        addButton.setOnClickListener {
            startActivity(Intent(this, PLaceFormActivity::class.java))
        }

        searchEditText.onTextChange { (recyclerPlaceList.adapter as PlaceAdapter).filter(it) }
    }

    private fun initRecyclerView() {
        recyclerPlaceList.layoutManager = LinearLayoutManager(this)
        recyclerPlaceList.adapter = PlaceAdapter(placeViewModel.getAll())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}

