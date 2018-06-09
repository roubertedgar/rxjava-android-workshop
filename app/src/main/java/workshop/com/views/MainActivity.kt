package workshop.com.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO
import workshop.com.views.place.PLaceFormActivity
import workshop.com.views.place.PlaceAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var placeDao: PlaceDAO

    private val placeList: MutableList<Place> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        placeDao = FactoryDAO.getPlaceDatabase(applicationContext)

        setListeners()
        initRecyclerView()
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


    private fun updatePlaceList(places: List<Place>) {
        placeList.clear()
        placeList.addAll(places)
        recyclerPlaceList.adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val place = data?.getSerializableExtra("place") as Place
            placeDao.insert(place)
        }
    }
}
