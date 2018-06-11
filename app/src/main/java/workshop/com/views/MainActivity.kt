package workshop.com.views

import android.app.Activity
import android.app.Fragment
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place
import workshop.com.models.place.PlaceDAO
import workshop.com.views.place.PLaceFormActivity
import workshop.com.views.place.PlaceAdapter
import workshop.com.views.place.SavePlaceTask
import java.util.concurrent.Executors


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            var place = data?.getSerializableExtra("place") as Place

            SavePlaceTask(placeDao, {
                Snackbar.make(mainContainer, "Place saved =D", Snackbar.LENGTH_LONG).show()
                placeList.add(place)
                recyclerPlaceList.adapter?.notifyDataSetChanged()

            }).execute(place)
        }
    }
}
