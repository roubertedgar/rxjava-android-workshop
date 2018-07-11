package workshop.com.views.place

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_place_form.*
import workshop.com.R
import workshop.com.models.FactoryDAO
import workshop.com.models.place.Place

class PLaceFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_form)

        doneButton.setOnClickListener {
            val name = placeName.text.toString()
            val description = placeDescription.text.toString()
            val place = Place(name, description)
            intent.putExtra("place", place)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
