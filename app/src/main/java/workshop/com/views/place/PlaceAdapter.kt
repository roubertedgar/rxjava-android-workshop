package workshop.com.views.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import workshop.com.R
import workshop.com.models.place.Place

class PlaceAdapter(observable: Flowable<List<Place>>) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {
    private var disposable: Disposable? = null

    private var items = mutableListOf<Place>()
    private var toFilter = listOf<Place>()

    init {
        observable
                .subscribe {
                    items = it.toMutableList()
                    toFilter = it.toList()
                    notifyDataSetChanged()
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return PlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(place: Place) {
            itemView.findViewById<TextView>(R.id.item_name).text = place.name
            itemView.findViewById<TextView>(R.id.item_description).text = place.description
        }
    }

    fun filter(query: String) {
        disposable?.dispose()

        if (disposable == null || disposable?.isDisposed!!) {
            disposable = asyncFilter(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { notifyDataSetChanged() }
        }
    }

    private fun asyncFilter(query: String): Completable {
        return Completable.fromAction {
            items.clear()
            toFilter.filterTo(items) { place -> placeFilter(place, query) }
        }
    }

    private fun placeFilter(place: Place, query: String): Boolean {
        return query.isBlank()
                || place.name.toUpperCase().contains(query.toUpperCase())
                || place.description.toUpperCase().contains(query.toUpperCase())
    }
}




