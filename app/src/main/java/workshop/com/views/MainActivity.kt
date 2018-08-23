package workshop.com.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place
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
    }

    private fun TextView.onTextChange(textWatcher: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {}

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textWatcher(text.toString())
            }

        })
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

